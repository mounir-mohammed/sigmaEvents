import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { AccountService } from 'app/core/auth/account.service';
import { IAccreditationTypeSig } from 'app/entities/accreditation-type-sig/accreditation-type-sig.model';
import { IAttachementSig } from 'app/entities/attachement-sig/attachement-sig.model';
import { ICategorySig } from 'app/entities/category-sig/category-sig.model';
import { ICitySig } from 'app/entities/city-sig/city-sig.model';
import { ICivilitySig } from 'app/entities/civility-sig/civility-sig.model';
import { ICodeSig } from 'app/entities/code-sig/code-sig.model';
import { ICountrySig } from 'app/entities/country-sig/country-sig.model';
import { IDayPassInfoSig } from 'app/entities/day-pass-info-sig/day-pass-info-sig.model';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';
import { IFonctionSig } from 'app/entities/fonction-sig/fonction-sig.model';
import { INationalitySig } from 'app/entities/nationality-sig/nationality-sig.model';
import { IOrganizSig } from 'app/entities/organiz-sig/organiz-sig.model';
import { ISexeSig } from 'app/entities/sexe-sig/sexe-sig.model';
import { ISiteSig } from 'app/entities/site-sig/site-sig.model';
import { IStatusSig } from 'app/entities/status-sig/status-sig.model';
import { AccreditationTypeSigService } from 'app/entities/accreditation-type-sig/service/accreditation-type-sig.service';
import { AttachementSigService } from 'app/entities/attachement-sig/service/attachement-sig.service';
import { CategorySigService } from 'app/entities/category-sig/service/category-sig.service';
import { CitySigService } from 'app/entities/city-sig/service/city-sig.service';
import { CivilitySigService } from 'app/entities/civility-sig/service/civility-sig.service';
import { CodeSigService } from 'app/entities/code-sig/service/code-sig.service';
import { CountrySigService } from 'app/entities/country-sig/service/country-sig.service';
import { DayPassInfoSigService } from 'app/entities/day-pass-info-sig/service/day-pass-info-sig.service';
import { EventSigService } from 'app/entities/event-sig/service/event-sig.service';
import { FonctionSigService } from 'app/entities/fonction-sig/service/fonction-sig.service';
import { NationalitySigService } from 'app/entities/nationality-sig/service/nationality-sig.service';
import { OrganizSigService } from 'app/entities/organiz-sig/service/organiz-sig.service';
import { SexeSigService } from 'app/entities/sexe-sig/service/sexe-sig.service';
import { SiteSigService } from 'app/entities/site-sig/service/site-sig.service';
import { StatusSigService } from 'app/entities/status-sig/service/status-sig.service';
import { HttpResponse } from '@angular/common/http';
import { map } from 'rxjs';
import { FormControl, FormGroup } from '@angular/forms';
import { Authority } from 'app/config/authority.constants';
import dayjs from 'dayjs';
import { RECORD_ITEMS } from 'app/config/pagination.constants';
import { IOperationTypeSig } from 'app/entities/operation-type-sig/operation-type-sig.model';
import { OperationTypeSigService } from 'app/entities/operation-type-sig/service/operation-type-sig.service';

@Component({
  templateUrl: './operation-history-sig-search-dialog.component.html',
  styleUrls: ['./operation-history-sig-search-dialog.component.scss'],
})
export class OperationHistorySearchSigComponent implements OnInit {
  searchForm = new FormGroup({
    operationHistoryId: new FormControl(),
    typeoperation: new FormControl(),
    operationHistoryDescription: new FormControl(),
    operationHistoryDateStart: new FormControl(),
    operationHistoryDateEnd: new FormControl(),
    operationHistoryAttributs: new FormControl(),
    operationHistoryUserID: new FormControl(),
    operationHistoryOldId: new FormControl(),
    operationHistoryNewId: new FormControl(),
    operationHistoryOldValue: new FormControl(),
    operationHistoryNewValue: new FormControl(),
    operationHistoryImportedFile: new FormControl(),
    operationHistoryParams: new FormControl(),
    event: new FormControl(),
  });

  filters = new Map();
  operationTypesSharedCollection: IOperationTypeSig[] = [];
  eventsSharedCollection: IEventSig[] = [];
  authority = Authority;

  constructor(
    protected activeModal: NgbActiveModal,
    protected accountService: AccountService,
    protected operationTypeService: OperationTypeSigService,
    protected eventService: EventSigService
  ) {}

  ngOnInit(): void {
    this.loadRelationshipsOptions();
  }

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmSearch(): void {
    if (this.searchForm.get('operationHistoryId')?.value) {
      this.filters.set('operationHistoryId.equals', this.searchForm.get('operationHistoryId')?.value);
    }

    if (this.searchForm.get('typeoperation')?.value) {
      this.filters.set('typeoperationId.in', this.searchForm.get('typeoperation')?.value);
    }

    if (this.searchForm.get('operationHistoryDescription')?.value) {
      this.filters.set('operationHistoryDescription.equals', this.searchForm.get('operationHistoryDescription')?.value);
    }

    if (this.searchForm.get('operationHistoryDateStart')?.value) {
      this.filters.set(
        'operationHistoryDate.greaterThanOrEqual',
        dayjs(this.searchForm.get('operationHistoryDateStart')?.value).format('YYYY-MM-DDTHH:mm:ss.SSSZ')
      );
    }

    if (this.searchForm.get('operationHistoryDateEnd')?.value) {
      this.filters.set(
        'operationHistoryDate.lessThanOrEqual',
        dayjs(this.searchForm.get('operationHistoryDateEnd')?.value).format('YYYY-MM-DDTHH:mm:ss.SSSZ')
      );
    }

    if (this.searchForm.get('operationHistoryAttributs')?.value) {
      this.filters.set('operationHistoryAttributs.equals', this.searchForm.get('operationHistoryAttributs')?.value);
    }

    if (this.searchForm.get('operationHistoryUserID')?.value) {
      this.filters.set('operationHistoryUserID.in', this.searchForm.get('operationHistoryUserID')?.value);
    }

    if (this.searchForm.get('operationHistoryOldId')?.value) {
      this.filters.set('operationHistoryOldId.in', this.searchForm.get('operationHistoryOldId')?.value);
    }

    if (this.searchForm.get('operationHistoryNewId')?.value) {
      this.filters.set('operationHistoryNewId.in', this.searchForm.get('operationHistoryNewId')?.value);
    }

    if (this.searchForm.get('operationHistoryNewValue')?.value) {
      this.filters.set('operationHistoryNewValue.contains', this.searchForm.get('operationHistoryNewValue')?.value);
    }

    if (this.searchForm.get('operationHistoryImportedFile')?.value) {
      this.filters.set('operationHistoryImportedFile.contains', this.searchForm.get('operationHistoryImportedFile')?.value);
    }

    if (this.searchForm.get('operationHistoryParams')?.value) {
      this.filters.set('operationHistoryParams.contains', this.searchForm.get('operationHistoryParams')?.value);
    }

    if (this.searchForm.get('event')?.value) {
      if (this.searchForm.get('event')?.value == 'noEvent') {
        this.filters.set('eventId.specified', [false]);
      } else {
        this.filters.set('eventId.in', this.searchForm.get('event')?.value);
      }
    }

    if (this.filters) {
      this.activeModal.close(this.filters);
    }
  }

  protected loadRelationshipsOptions(): void {
    if (this.accountService.hasAnyAuthority([Authority.ADMIN])) {
      this.eventService
        .query({ size: RECORD_ITEMS })
        .pipe(map((res: HttpResponse<IEventSig[]>) => res.body ?? []))
        .subscribe((events: IEventSig[]) => (this.eventsSharedCollection = events));
    }

    this.operationTypeService
      .query({ size: RECORD_ITEMS })
      .pipe(map((res: HttpResponse<IOperationTypeSig[]>) => res.body ?? []))
      .subscribe((operationTypes: IOperationTypeSig[]) => (this.operationTypesSharedCollection = operationTypes));
  }
}
