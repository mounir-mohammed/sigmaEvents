import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { OperationHistorySigFormService, OperationHistorySigFormGroup } from './operation-history-sig-form.service';
import { IOperationHistorySig } from '../operation-history-sig.model';
import { OperationHistorySigService } from '../service/operation-history-sig.service';
import { IOperationTypeSig } from 'app/entities/operation-type-sig/operation-type-sig.model';
import { OperationTypeSigService } from 'app/entities/operation-type-sig/service/operation-type-sig.service';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';
import { EventSigService } from 'app/entities/event-sig/service/event-sig.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { Authority } from 'app/config/authority.constants';

@Component({
  selector: 'sigma-operation-history-sig-update',
  templateUrl: './operation-history-sig-update.component.html',
})
export class OperationHistorySigUpdateComponent implements OnInit {
  currentAccount: Account | null = null;
  isSaving = false;
  operationHistory: IOperationHistorySig | null = null;

  operationTypesSharedCollection: IOperationTypeSig[] = [];
  eventsSharedCollection: IEventSig[] = [];

  editForm: OperationHistorySigFormGroup = this.operationHistoryFormService.createOperationHistorySigFormGroup();
  authority = Authority;

  constructor(
    protected operationHistoryService: OperationHistorySigService,
    protected operationHistoryFormService: OperationHistorySigFormService,
    protected operationTypeService: OperationTypeSigService,
    protected eventService: EventSigService,
    protected activatedRoute: ActivatedRoute,
    private accountService: AccountService
  ) {}

  compareOperationTypeSig = (o1: IOperationTypeSig | null, o2: IOperationTypeSig | null): boolean =>
    this.operationTypeService.compareOperationTypeSig(o1, o2);

  compareEventSig = (o1: IEventSig | null, o2: IEventSig | null): boolean => this.eventService.compareEventSig(o1, o2);

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => (this.currentAccount = account));
    this.activatedRoute.data.subscribe(({ operationHistory }) => {
      this.operationHistory = operationHistory;
      if (operationHistory) {
        this.updateForm(operationHistory);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const operationHistory = this.operationHistoryFormService.getOperationHistorySig(this.editForm);
    if (!this.accountService.hasAnyAuthority(Authority.ADMIN)) {
      operationHistory.event = this.currentAccount?.printingCentre?.event;
    }
    if (operationHistory.operationHistoryId !== null) {
      this.subscribeToSaveResponse(this.operationHistoryService.update(operationHistory));
    } else {
      this.subscribeToSaveResponse(this.operationHistoryService.create(operationHistory));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOperationHistorySig>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(operationHistory: IOperationHistorySig): void {
    this.operationHistory = operationHistory;
    this.operationHistoryFormService.resetForm(this.editForm, operationHistory);

    this.operationTypesSharedCollection = this.operationTypeService.addOperationTypeSigToCollectionIfMissing<IOperationTypeSig>(
      this.operationTypesSharedCollection,
      operationHistory.typeoperation
    );
    this.eventsSharedCollection = this.eventService.addEventSigToCollectionIfMissing<IEventSig>(
      this.eventsSharedCollection,
      operationHistory.event
    );
  }

  protected loadRelationshipsOptions(): void {
    this.operationTypeService
      .query()
      .pipe(map((res: HttpResponse<IOperationTypeSig[]>) => res.body ?? []))
      .pipe(
        map((operationTypes: IOperationTypeSig[]) =>
          this.operationTypeService.addOperationTypeSigToCollectionIfMissing<IOperationTypeSig>(
            operationTypes,
            this.operationHistory?.typeoperation
          )
        )
      )
      .subscribe((operationTypes: IOperationTypeSig[]) => (this.operationTypesSharedCollection = operationTypes));

    this.eventService
      .query()
      .pipe(map((res: HttpResponse<IEventSig[]>) => res.body ?? []))
      .pipe(
        map((events: IEventSig[]) => this.eventService.addEventSigToCollectionIfMissing<IEventSig>(events, this.operationHistory?.event))
      )
      .subscribe((events: IEventSig[]) => (this.eventsSharedCollection = events));
  }
}
