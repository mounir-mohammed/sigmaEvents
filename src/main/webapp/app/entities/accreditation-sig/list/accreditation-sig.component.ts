import { Component, ElementRef, HostListener, OnInit, ViewChildren } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { combineLatest, filter, Observable, Subject, switchMap, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAccreditationSig } from '../accreditation-sig.model';
import { faSpinner } from '@fortawesome/free-solid-svg-icons';

import { CACHE_RECORD_ITEMS, ITEMS_PER_PAGE, PAGE_HEADER, TOTAL_COUNT_RESPONSE_HEADER } from 'app/config/pagination.constants';
import {
  ASC,
  DESC,
  SORT,
  ITEM_DELETED_EVENT,
  ITEM_VALIDATED_EVENT,
  DEFAULT_SORT_DATA,
  ITEM_PRINTED_EVENT,
  SEARCH_TEXT,
  ITEM_MASS_PRINTED_EVENT,
  ITEM_IMPORTED_EVENT,
} from 'app/config/navigation.constants';
import { EntityArrayResponseType, AccreditationSigService } from '../service/accreditation-sig.service';
import { AccreditationSigDeleteDialogComponent } from '../delete/accreditation-sig-delete-dialog.component';
import { AccreditationSigValidateDialogComponent } from '../validate/accreditation-sig-validate-dialog.component';
import { DataUtils } from 'app/core/util/data-util.service';
import { FilterOptions, IFilterOptions, IFilterOption } from 'app/shared/filter/filter.model';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { Authority } from 'app/config/authority.constants';
import { IStatusSig } from 'app/entities/status-sig/status-sig.model';
import { Status } from 'app/config/status.contants';
import { StatusSigService } from 'app/entities/status-sig/service/status-sig.service';
import { HttpResponse } from '@angular/common/http';
import { debounceTime, distinctUntilChanged, map } from 'rxjs/operators';
import { AccreditationSigPrintDialogComponent } from '../print/accreditation-sig-print-dialog.component';
import { AccreditationSigSearchDialogComponent } from '../search/accreditation-sig-search-dialog.component';
import { ExportUtil } from 'app/shared/util/export.shared';
import { TranslateService } from '@ngx-translate/core';
import { AccreditationMassSigPrintDialogComponent } from '../print/accreditation-sig-mass-print-dialog.component';
import { AccreditationSigImportDialogComponent } from '../import/accreditation-sig-import-dialog.component';

@Component({
  selector: 'sigma-accreditation-sig',
  templateUrl: './accreditation-sig.component.html',
})
export class AccreditationSigComponent implements OnInit {
  currentAccount: Account | null = null;
  accreditations?: IAccreditationSig[];
  isLoading = false;
  statusesSharedCollection: IStatusSig[] = [];

  predicate = 'accreditationId';
  ascending = true;
  searchLoading = false;
  faSpinner = faSpinner;
  filters: IFilterOptions = new FilterOptions();

  itemsPerPage = 0;
  totalItems = 0;
  page = 1;
  authority = Authority;
  searchText = null;
  private lastTouchTime: number = 0;
  selectAllRows: boolean = false;
  selectedCount: number = 0;

  searchTextChanged = new Subject<string | null>();

  constructor(
    protected accreditationService: AccreditationSigService,
    protected accountService: AccountService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected dataUtils: DataUtils,
    protected modalService: NgbModal,
    protected statusService: StatusSigService,
    protected translateService: TranslateService
  ) {}

  trackAccreditationId = (_index: number, item: IAccreditationSig): number => this.accreditationService.getAccreditationSigIdentifier(item);

  ngOnInit(): void {
    this.loadColumnVisibility();
    this.loadListSize();
    this.accountService.identity().subscribe(account => (this.currentAccount = account));

    // 👇 une seule souscription ici
    this.loadFromBackendWithRouteInformations().subscribe({
      next: (res: EntityArrayResponseType) => this.onResponseSuccess(res),
    });

    this.filters.filterChanges.subscribe(filterOptions => this.handleNavigation(1, this.predicate, this.ascending, filterOptions));

    this.statusService
      .query({ size: CACHE_RECORD_ITEMS })
      .pipe(map((res: HttpResponse<IStatusSig[]>) => res.body ?? []))
      .subscribe((statuses: IStatusSig[]) => (this.statusesSharedCollection = statuses));

    this.searchTextChanged
      .pipe(
        debounceTime(400), // ⏱ attend 400 ms après la dernière frappe
        distinctUntilChanged(), // ⚡ n’envoie que si le texte a vraiment changé
        switchMap(() => {
          this.searchLoading = true;
          this.page = 1;
          return this.queryBackend(this.page, this.predicate, this.ascending, this.filters.filterOptions);
        })
      )
      .subscribe({
        next: res => this.onResponseSuccess(res),
      });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    return this.dataUtils.openFile(base64String, contentType);
  }

  delete(accreditation: IAccreditationSig): void {
    const modalRef = this.modalService.open(AccreditationSigDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.accreditation = accreditation;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed
      .pipe(
        filter(reason => reason === ITEM_DELETED_EVENT),
        switchMap(() => this.loadFromBackendWithRouteInformations())
      )
      .subscribe({
        next: (res: EntityArrayResponseType) => {
          this.onResponseSuccess(res);
        },
      });
  }

  validate(accreditation: IAccreditationSig): void {
    const modalRef = this.modalService.open(AccreditationSigValidateDialogComponent, { size: 'lg', backdrop: 'static' });
    const status = this.statusesSharedCollection.filter(status => status.statusAbreviation == Status.VALIDATED).shift();
    //accreditation.status = status;
    console.log(accreditation);
    modalRef.componentInstance.accreditation = accreditation;
    modalRef.componentInstance.status = status;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed
      .pipe(
        filter(reason => reason === ITEM_VALIDATED_EVENT),
        switchMap(() => this.loadFromBackendWithRouteInformations())
      )
      .subscribe({
        next: (res: EntityArrayResponseType) => {
          this.onResponseSuccess(res);
        },
      });
  }

  advancedSearch(): void {
    const modalRef = this.modalService.open(AccreditationSigSearchDialogComponent, { size: 'lg', backdrop: 'static' });
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.pipe().subscribe(searchFilters => {
      if (searchFilters) {
        searchFilters.forEach((value: any, key: any) => {
          this.filters.addFilter(key, value);
        });
      }
    });
    console.log(this.filters.filterOptions);
  }

  view(accreditationId: number): void {
    this.router.navigate(['/accreditation-sig', accreditationId, 'view']);
  }

  onTouchStart(event: TouchEvent, accreditationId: number) {
    const currentTime = new Date().getTime();
    const timeSinceLastTouch = currentTime - this.lastTouchTime;
    this.lastTouchTime = currentTime;

    if (timeSinceLastTouch < 300) {
      this.view(accreditationId);
    }
  }

  print(accreditation: IAccreditationSig): void {
    const modalRef = this.modalService.open(AccreditationSigPrintDialogComponent, { size: 'lg', backdrop: 'static' });
    const status = this.statusesSharedCollection.filter(status => status.statusAbreviation == Status.PRINTED).shift();
    //accreditation.status = status;
    modalRef.componentInstance.accreditation = accreditation;
    modalRef.componentInstance.status = status;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed
      .pipe(
        filter(reason => reason === ITEM_PRINTED_EVENT),
        switchMap(() => this.loadFromBackendWithRouteInformations())
      )
      .subscribe({
        next: (res: EntityArrayResponseType) => {
          this.onResponseSuccess(res);
        },
      });
  }

  import(): void {
    const modalRef = this.modalService.open(AccreditationSigImportDialogComponent, { size: 'lg', backdrop: 'static' });
    const status = this.statusesSharedCollection.filter(status => status.statusAbreviation == Status.IMPORTED).shift();
    //accreditation.status = status;
    modalRef.componentInstance.status = status;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed
      .pipe(
        filter(reason => reason === ITEM_IMPORTED_EVENT),
        switchMap(() => this.loadFromBackendWithRouteInformations())
      )
      .subscribe({
        next: (res: EntityArrayResponseType) => {
          this.onResponseSuccess(res);
        },
      });
  }

  load(): void {
    this.loadFromBackendWithRouteInformations().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
      },
    });
  }

  navigateToWithComponentValues(): void {
    this.handleNavigation(this.page, this.predicate, this.ascending, this.filters.filterOptions);
  }

  navigateToPage(page = this.page): void {
    this.handleNavigation(page, this.predicate, this.ascending, this.filters.filterOptions);
  }

  protected loadFromBackendWithRouteInformations(): Observable<EntityArrayResponseType> {
    return combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data]).pipe(
      tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
      switchMap(() => this.queryBackend(this.page, this.predicate, this.ascending, this.filters.filterOptions))
    );
  }

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    const page = params.get(PAGE_HEADER);
    this.page = +(page ?? 1);
    const sort = (params.get(SORT) ?? data[DEFAULT_SORT_DATA]).split(',');
    const searchText = params.get(SEARCH_TEXT) ?? '';
    this.predicate = sort[0];
    this.ascending = sort[1] === ASC;
    this.filters.initializeFromParams(params);
  }

  protected onResponseSuccess(response: EntityArrayResponseType): void {
    this.fillComponentAttributesFromResponseHeader(response.headers);
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.accreditations = dataFromBody;
    this.searchLoading = false;
  }

  protected fillComponentAttributesFromResponseBody(data: IAccreditationSig[] | null): IAccreditationSig[] {
    return data ?? [];
  }

  protected fillComponentAttributesFromResponseHeader(headers: HttpHeaders): void {
    this.totalItems = Number(headers.get(TOTAL_COUNT_RESPONSE_HEADER));
  }

  protected queryBackend(
    page?: number,
    predicate?: string,
    ascending?: boolean,
    filterOptions?: IFilterOption[]
  ): Observable<EntityArrayResponseType> {
    this.isLoading = true;
    const pageToLoad: number = page ?? 1;
    const queryObject: any = {
      page: pageToLoad - 1,
      size: this.itemsPerPage,
      eagerload: true,
      sort: this.getSortQueryParam(predicate, ascending),
      searchText: this.searchText,
    };
    filterOptions?.forEach(filterOption => {
      queryObject[filterOption.name] = filterOption.values;
    });
    return this.accreditationService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
  }

  protected handleNavigation(page = this.page, predicate?: string, ascending?: boolean, filterOptions?: IFilterOption[]): void {
    const queryParamsObj: any = {
      page,
      size: this.itemsPerPage,
      sort: this.getSortQueryParam(predicate, ascending),
    };

    filterOptions?.forEach(filterOption => {
      queryParamsObj[filterOption.nameAsQueryParam()] = filterOption.values;
    });

    this.router.navigate(['./'], {
      relativeTo: this.activatedRoute,
      queryParams: queryParamsObj,
    });
  }

  protected getSortQueryParam(predicate = this.predicate, ascending = this.ascending): string[] {
    const ascendingQueryParam = ascending ? ASC : DESC;
    if (predicate === '') {
      return [];
    } else {
      return [predicate + ',' + ascendingQueryParam];
    }
  }

  search(): void {
    this.searchTextChanged.next(this.searchText);
  }

  //Costum columns code

  public columns: { name: string; visible: boolean; translationKey: string }[] = [];

  public showColumnsCheckboxes: boolean = false;

  public toggleColumnsCheckboxes(): void {
    this.showColumnsCheckboxes = !this.showColumnsCheckboxes;
  }

  toggleColumnVisibility(): void {
    this.saveColumnVisibility();
  }

  public isColumnVisible(columnName: string): boolean {
    const column = this.columns.find(col => col.name === columnName);
    return column ? column.visible : false;
  }

  public defaultColumnVisibility: { name: string; visible: boolean; translationKey: string }[] = [
    { name: 'accreditationId', visible: true, translationKey: 'sigmaEventsApp.accreditation.accreditationId' },
    // { name: 'accreditationPhoto', visible: true, translationKey: 'sigmaEventsApp.accreditation.accreditationPhoto' },
    { name: 'civility', visible: false, translationKey: 'sigmaEventsApp.accreditation.civility' },
    //{ name: 'accreditationFirstName', visible: true, translationKey: 'sigmaEventsApp.accreditation.accreditationFirstName' },
    //{ name: 'accreditationSecondName', visible: true, translationKey: 'sigmaEventsApp.accreditation.accreditationSecondName' },
    //{ name: 'accreditationLastName', visible: true, translationKey: 'sigmaEventsApp.accreditation.accreditationLastName' },
    { name: 'accreditationName', visible: true, translationKey: 'sigmaEventsApp.accreditation.accreditationName' },
    { name: 'accreditationBirthDay', visible: false, translationKey: 'sigmaEventsApp.accreditation.accreditationBirthDay' },
    { name: 'accreditationSexe', visible: false, translationKey: 'sigmaEventsApp.accreditation.accreditationSexe' },
    { name: 'accreditationOccupation', visible: false, translationKey: 'sigmaEventsApp.accreditation.accreditationOccupation' },
    { name: 'nationality', visible: true, translationKey: 'sigmaEventsApp.accreditation.nationality' },
    { name: 'country', visible: false, translationKey: 'sigmaEventsApp.accreditation.country' },
    { name: 'city', visible: false, translationKey: 'sigmaEventsApp.accreditation.city' },
    { name: 'category', visible: true, translationKey: 'sigmaEventsApp.accreditation.category' },
    { name: 'fonction', visible: true, translationKey: 'sigmaEventsApp.accreditation.fonction' },
    { name: 'organiz', visible: true, translationKey: 'sigmaEventsApp.accreditation.organiz' },
    { name: 'accreditationTypeId', visible: false, translationKey: 'sigmaEventsApp.accreditation.accreditationType' },
    { name: 'status', visible: true, translationKey: 'sigmaEventsApp.accreditation.status' },
    { name: 'accreditationDescription', visible: false, translationKey: 'sigmaEventsApp.accreditation.accreditationDescription' },
    { name: 'accreditationEmail', visible: false, translationKey: 'sigmaEventsApp.accreditation.accreditationEmail' },
    { name: 'accreditationTel', visible: false, translationKey: 'sigmaEventsApp.accreditation.accreditationTel' },
    { name: 'accreditationCinId', visible: false, translationKey: 'sigmaEventsApp.accreditation.accreditationCinId' },
    { name: 'accreditationPasseportId', visible: false, translationKey: 'sigmaEventsApp.accreditation.accreditationPasseportId' },
    { name: 'accreditationCartePresseId', visible: false, translationKey: 'sigmaEventsApp.accreditation.accreditationCartePresseId' },
    {
      name: 'accreditationCarteProfessionnelleId',
      visible: false,
      translationKey: 'sigmaEventsApp.accreditation.accreditationCarteProfessionnelleId',
    },
    { name: 'accreditationCreationDate', visible: false, translationKey: 'sigmaEventsApp.accreditation.accreditationCreationDate' },
    { name: 'accreditationCreatedByuser', visible: false, translationKey: 'sigmaEventsApp.accreditation.accreditationCreatedByuser' },
    { name: 'accreditationUpdateDate', visible: false, translationKey: 'sigmaEventsApp.accreditation.accreditationUpdateDate' },
    { name: 'accreditationUpdatedByuser', visible: false, translationKey: 'sigmaEventsApp.accreditation.accreditationUpdatedByuser' },
    { name: 'accreditationPrintDate', visible: false, translationKey: 'sigmaEventsApp.accreditation.accreditationPrintDate' },
    { name: 'accreditationPrintedByuser', visible: false, translationKey: 'sigmaEventsApp.accreditation.accreditationPrintedByuser' },
    { name: 'accreditationDateStart', visible: false, translationKey: 'sigmaEventsApp.accreditation.accreditationDateStart' },
    { name: 'accreditationDateEnd', visible: false, translationKey: 'sigmaEventsApp.accreditation.accreditationDateEnd' },
    { name: 'accreditationPrintStat', visible: false, translationKey: 'sigmaEventsApp.accreditation.accreditationPrintStat' },
    { name: 'accreditationPrintNumber', visible: false, translationKey: 'sigmaEventsApp.accreditation.accreditationPrintNumber' },
    { name: 'accreditationParams', visible: false, translationKey: 'sigmaEventsApp.accreditation.accreditationParams' },
    { name: 'accreditationAttributs', visible: false, translationKey: 'sigmaEventsApp.accreditation.accreditationAttributs' },
    { name: 'accreditationStat', visible: false, translationKey: 'sigmaEventsApp.accreditation.accreditationStat' },
    { name: 'accreditationActivated', visible: false, translationKey: 'sigmaEventsApp.accreditation.accreditationActivated' },
    { name: 'attachement', visible: false, translationKey: 'sigmaEventsApp.accreditation.attachement' },
    { name: 'code', visible: false, translationKey: 'sigmaEventsApp.accreditation.code' },
    { name: 'dayPassInfo', visible: false, translationKey: 'sigmaEventsApp.accreditation.dayPassInfo' },
    { name: 'event', visible: true, translationKey: 'sigmaEventsApp.accreditation.event' },
  ];

  // Save the column visibility configuration to localStorage
  public saveColumnVisibility(): void {
    localStorage.setItem('columnVisibility', JSON.stringify(this.columns));
  }

  // Load the column visibility configuration from localStorage
  public loadColumnVisibility(): void {
    const columnVisibility = localStorage.getItem('columnVisibility');
    if (columnVisibility) {
      this.columns = JSON.parse(columnVisibility);
    } else {
      // If no configuration found in localStorage, use the default configuration
      this.columns = JSON.parse(JSON.stringify(this.defaultColumnVisibility));
    }
  }

  // Reset the column visibility to the default configuration and remove it from localStorage
  public resetColumnVisibility(): void {
    this.columns = JSON.parse(JSON.stringify(this.defaultColumnVisibility));
    localStorage.removeItem('columnVisibility');
    this.toggleColumnsCheckboxes();
  }

  // Method to close the list when clicked outside
  closeColumnsCheckboxes() {
    this.showColumnsCheckboxes = false;
  }

  // Event listener to close the list when clicked outside
  @HostListener('document:click', ['$event'])
  onDocumentClick(event: MouseEvent) {
    if (!document.getElementById('select-columns')!.contains(event.target as Node)) {
      // Clicked outside the box
      this.closeColumnsCheckboxes();
    }
  }

  changeListSize(listSize: any) {
    this.itemsPerPage = listSize;
    this.saveListSize();
    this.load();
  }

  saveListSize(): void {
    localStorage.setItem('listSize', JSON.stringify(this.itemsPerPage));
  }

  loadListSize(): void {
    const listSize = localStorage.getItem('listSize');
    if (listSize) {
      this.itemsPerPage = JSON.parse(listSize);
    } else {
      this.itemsPerPage = ITEMS_PER_PAGE;
    }
    this.selectListSize();
  }

  selectListSize() {
    const selectElement = document.getElementById('field_listSize') as HTMLSelectElement;
    const valueToSelect = this.itemsPerPage.toString();

    for (let i = 0; i < selectElement.options.length; i++) {
      const option = selectElement.options[i] as HTMLOptionElement;
      if (option.value === valueToSelect) {
        option.selected = true;
        break;
      }
    }
  }

  onRowCheckboxChange(accreditation: IAccreditationSig) {
    accreditation.selected = !accreditation.selected;
    this.updateSelectedCount();
  }

  onSelectAllRowsChange() {
    for (const accreditation of this.accreditations!) {
      accreditation.selected = this.selectAllRows;
    }
    this.updateSelectedCount();
  }

  unSelectAll() {
    for (const accreditation of this.accreditations!) {
      accreditation.selected = false;
    }
    this.selectAllRows = false;
    this.updateSelectedCount();
  }

  getSelectedAccreditations(): IAccreditationSig[] {
    const selectedAccreditations: IAccreditationSig[] = [];
    for (const accreditation of this.accreditations!) {
      if (accreditation.selected) {
        selectedAccreditations.push(accreditation);
      }
    }
    return selectedAccreditations;
  }

  getSelectedAccreditationsIds(): number[] {
    const selectedAccreditationsIds: number[] = [];
    for (const accreditation of this.accreditations!) {
      if (accreditation.selected) {
        selectedAccreditationsIds.push(accreditation.accreditationId);
      }
    }
    return selectedAccreditationsIds;
  }

  updateSelectedCount() {
    this.selectedCount = this.accreditations!.filter(accreditation => accreditation.selected).length;
  }

  massUpdate(): void {
    if (this.selectedCount) {
      const accreditationsIds: number[] = this.getSelectedAccreditationsIds();
      this.router.navigate(['/accreditation-sig', 'massUpdate'], { state: { ids: accreditationsIds } });
      this.unSelectAll();
    } else {
      alert(this.translateService.instant('sigmaEventsApp.accreditation.alerts.noAccreditationSelected'));
    }
  }

  massPrint(): void {
    if (this.selectedCount) {
      const modalRef = this.modalService.open(AccreditationMassSigPrintDialogComponent, { size: 'lg', backdrop: 'static' });
      const status = this.statusesSharedCollection.filter(status => status.statusAbreviation == Status.PRINTED).shift();
      const accreditations: IAccreditationSig[] = this.getSelectedAccreditations();
      modalRef.componentInstance.accreditations = accreditations;
      modalRef.componentInstance.status = status;
      // // unsubscribe not needed because closed completes on modal close
      modalRef.closed
        .pipe(
          filter(reason => reason === ITEM_MASS_PRINTED_EVENT),
          switchMap(() => this.loadFromBackendWithRouteInformations())
        )
        .subscribe({
          next: (res: EntityArrayResponseType) => {
            this.onResponseSuccess(res);
          },
        });
      this.unSelectAll();
    } else {
      alert(this.translateService.instant('sigmaEventsApp.accreditation.alerts.noAccreditationSelected'));
    }
  }

  upload(): void {}

  export(): void {
    if (this.accreditations && this.accreditations.length > 0) {
      ExportUtil.initialize(this.translateService);
      ExportUtil.exportTableToExcel(
        'accreditationsTable',
        'sigmaEventsApp.accreditation.home.title',
        'sigmaEventsApp.accreditation.home.title'
      );
    } else {
      alert(this.translateService.instant('sigmaEventsApp.accreditation.alerts.noDataFoundToExport'));
    }
  }
}
