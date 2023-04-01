import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import {
  CheckAccreditationHistorySigFormService,
  CheckAccreditationHistorySigFormGroup,
} from './check-accreditation-history-sig-form.service';
import { ICheckAccreditationHistorySig } from '../check-accreditation-history-sig.model';
import { CheckAccreditationHistorySigService } from '../service/check-accreditation-history-sig.service';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';
import { EventSigService } from 'app/entities/event-sig/service/event-sig.service';
import { IAccreditationSig } from 'app/entities/accreditation-sig/accreditation-sig.model';
import { AccreditationSigService } from 'app/entities/accreditation-sig/service/accreditation-sig.service';

@Component({
  selector: 'sigma-check-accreditation-history-sig-update',
  templateUrl: './check-accreditation-history-sig-update.component.html',
})
export class CheckAccreditationHistorySigUpdateComponent implements OnInit {
  isSaving = false;
  checkAccreditationHistory: ICheckAccreditationHistorySig | null = null;

  eventsSharedCollection: IEventSig[] = [];
  accreditationsSharedCollection: IAccreditationSig[] = [];

  editForm: CheckAccreditationHistorySigFormGroup = this.checkAccreditationHistoryFormService.createCheckAccreditationHistorySigFormGroup();

  constructor(
    protected checkAccreditationHistoryService: CheckAccreditationHistorySigService,
    protected checkAccreditationHistoryFormService: CheckAccreditationHistorySigFormService,
    protected eventService: EventSigService,
    protected accreditationService: AccreditationSigService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareEventSig = (o1: IEventSig | null, o2: IEventSig | null): boolean => this.eventService.compareEventSig(o1, o2);

  compareAccreditationSig = (o1: IAccreditationSig | null, o2: IAccreditationSig | null): boolean =>
    this.accreditationService.compareAccreditationSig(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ checkAccreditationHistory }) => {
      this.checkAccreditationHistory = checkAccreditationHistory;
      if (checkAccreditationHistory) {
        this.updateForm(checkAccreditationHistory);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const checkAccreditationHistory = this.checkAccreditationHistoryFormService.getCheckAccreditationHistorySig(this.editForm);
    if (checkAccreditationHistory.checkAccreditationHistoryId !== null) {
      this.subscribeToSaveResponse(this.checkAccreditationHistoryService.update(checkAccreditationHistory));
    } else {
      this.subscribeToSaveResponse(this.checkAccreditationHistoryService.create(checkAccreditationHistory));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICheckAccreditationHistorySig>>): void {
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

  protected updateForm(checkAccreditationHistory: ICheckAccreditationHistorySig): void {
    this.checkAccreditationHistory = checkAccreditationHistory;
    this.checkAccreditationHistoryFormService.resetForm(this.editForm, checkAccreditationHistory);

    this.eventsSharedCollection = this.eventService.addEventSigToCollectionIfMissing<IEventSig>(
      this.eventsSharedCollection,
      checkAccreditationHistory.event
    );
    this.accreditationsSharedCollection = this.accreditationService.addAccreditationSigToCollectionIfMissing<IAccreditationSig>(
      this.accreditationsSharedCollection,
      checkAccreditationHistory.accreditation
    );
  }

  protected loadRelationshipsOptions(): void {
    this.eventService
      .query()
      .pipe(map((res: HttpResponse<IEventSig[]>) => res.body ?? []))
      .pipe(
        map((events: IEventSig[]) =>
          this.eventService.addEventSigToCollectionIfMissing<IEventSig>(events, this.checkAccreditationHistory?.event)
        )
      )
      .subscribe((events: IEventSig[]) => (this.eventsSharedCollection = events));

    this.accreditationService
      .query()
      .pipe(map((res: HttpResponse<IAccreditationSig[]>) => res.body ?? []))
      .pipe(
        map((accreditations: IAccreditationSig[]) =>
          this.accreditationService.addAccreditationSigToCollectionIfMissing<IAccreditationSig>(
            accreditations,
            this.checkAccreditationHistory?.accreditation
          )
        )
      )
      .subscribe((accreditations: IAccreditationSig[]) => (this.accreditationsSharedCollection = accreditations));
  }
}
