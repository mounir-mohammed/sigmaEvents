import { Component, OnInit } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { combineLatest, filter, Observable, switchMap, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAccreditationSig } from '../accreditation-sig.model';
import { faSpinner } from '@fortawesome/free-solid-svg-icons';

import { ITEMS_PER_PAGE, PAGE_HEADER, TOTAL_COUNT_RESPONSE_HEADER } from 'app/config/pagination.constants';
import {
  ASC,
  DESC,
  SORT,
  ITEM_DELETED_EVENT,
  ITEM_VALIDATED_EVENT,
  DEFAULT_SORT_DATA,
  ITEM_PRINTED_EVENT,
  SEARCH_TEXT,
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
import { map } from 'rxjs/operators';
import { AccreditationSigPrintDialogComponent } from '../print/accreditation-sig-print-dialog.component';

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

  itemsPerPage = ITEMS_PER_PAGE;
  totalItems = 0;
  page = 1;
  authority = Authority;
  searchText = null;

  constructor(
    protected accreditationService: AccreditationSigService,
    protected accountService: AccountService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected dataUtils: DataUtils,
    protected modalService: NgbModal,
    protected statusService: StatusSigService
  ) {}

  trackAccreditationId = (_index: number, item: IAccreditationSig): number => this.accreditationService.getAccreditationSigIdentifier(item);

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => (this.currentAccount = account));
    this.load();

    this.filters.filterChanges.subscribe(filterOptions => this.handleNavigation(1, this.predicate, this.ascending, filterOptions));
    this.statusService
      .query()
      .pipe(map((res: HttpResponse<IStatusSig[]>) => res.body ?? []))
      .subscribe((statuses: IStatusSig[]) => (this.statusesSharedCollection = statuses));
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
    this.searchLoading = true;
    this.navigateToPage(0);
    this.load();
  }
}
