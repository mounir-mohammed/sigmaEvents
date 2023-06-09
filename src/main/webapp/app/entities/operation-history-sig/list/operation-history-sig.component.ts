import { Component, OnInit } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { combineLatest, filter, Observable, switchMap, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IOperationHistorySig } from '../operation-history-sig.model';

import { ITEMS_PER_PAGE, PAGE_HEADER, TOTAL_COUNT_RESPONSE_HEADER } from 'app/config/pagination.constants';
import { ASC, DESC, SORT, ITEM_DELETED_EVENT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { EntityArrayResponseType, OperationHistorySigService } from '../service/operation-history-sig.service';
import { OperationHistorySigDeleteDialogComponent } from '../delete/operation-history-sig-delete-dialog.component';
import { FilterOptions, IFilterOptions, IFilterOption } from 'app/shared/filter/filter.model';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { Authority } from 'app/config/authority.constants';
import { Entity } from 'app/config/operationType.contants';
import { TranslateService } from '@ngx-translate/core';
import { ExportUtil } from 'app/shared/util/export.shared';
import { OperationHistorySearchSigComponent } from '../search/operation-history-sig-search-dialog.component';

@Component({
  selector: 'sigma-operation-history-sig',
  templateUrl: './operation-history-sig.component.html',
})
export class OperationHistorySigComponent implements OnInit {
  currentAccount: Account | null = null;
  operationHistories?: IOperationHistorySig[];
  isLoading = false;
  Entity = Entity;

  predicate = 'operationHistoryId';
  ascending = true;
  filters: IFilterOptions = new FilterOptions();

  itemsPerPage = ITEMS_PER_PAGE;
  totalItems = 0;
  page = 1;
  authority = Authority;

  constructor(
    protected operationHistoryService: OperationHistorySigService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected modalService: NgbModal,
    private accountService: AccountService,
    protected translateService: TranslateService
  ) {}

  trackOperationHistoryId = (_index: number, item: IOperationHistorySig): number =>
    this.operationHistoryService.getOperationHistorySigIdentifier(item);

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => (this.currentAccount = account));
    this.loadListSize();
    this.load();

    this.filters.filterChanges.subscribe(filterOptions => this.handleNavigation(1, this.predicate, this.ascending, filterOptions));
  }

  delete(operationHistory: IOperationHistorySig): void {
    const modalRef = this.modalService.open(OperationHistorySigDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.operationHistory = operationHistory;
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
    this.predicate = sort[0];
    this.ascending = sort[1] === DESC;
    this.filters.initializeFromParams(params);
  }

  protected onResponseSuccess(response: EntityArrayResponseType): void {
    this.fillComponentAttributesFromResponseHeader(response.headers);
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.operationHistories = dataFromBody;
  }

  protected fillComponentAttributesFromResponseBody(data: IOperationHistorySig[] | null): IOperationHistorySig[] {
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
      sort: this.getSortQueryParam(predicate, ascending),
    };
    filterOptions?.forEach(filterOption => {
      queryObject[filterOption.name] = filterOption.values;
    });
    return this.operationHistoryService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
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

  export(): void {
    if (this.operationHistories && this.operationHistories.length > 0) {
      ExportUtil.initialize(this.translateService);
      ExportUtil.exportTableToExcel(
        'operationHistoriesTable',
        'sigmaEventsApp.operationHistory.home.title',
        'sigmaEventsApp.operationHistory.home.title'
      );
    } else {
      alert(this.translateService.instant('sigmaEventsApp.accreditation.alerts.noDataFoundToExport'));
    }
  }

  advancedSearch(): void {
    const modalRef = this.modalService.open(OperationHistorySearchSigComponent, { size: 'lg', backdrop: 'static' });
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
}
