import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import {
  CheckAccreditationReportSigFormService,
  CheckAccreditationReportSigFormGroup,
} from './check-accreditation-report-sig-form.service';
import { ICheckAccreditationReportSig } from '../check-accreditation-report-sig.model';
import { CheckAccreditationReportSigService } from '../service/check-accreditation-report-sig.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';
import { EventSigService } from 'app/entities/event-sig/service/event-sig.service';
import { ICheckAccreditationHistorySig } from 'app/entities/check-accreditation-history-sig/check-accreditation-history-sig.model';
import { CheckAccreditationHistorySigService } from 'app/entities/check-accreditation-history-sig/service/check-accreditation-history-sig.service';

@Component({
  selector: 'sigma-check-accreditation-report-sig-update',
  templateUrl: './check-accreditation-report-sig-update.component.html',
})
export class CheckAccreditationReportSigUpdateComponent implements OnInit {
  isSaving = false;
  checkAccreditationReport: ICheckAccreditationReportSig | null = null;

  eventsSharedCollection: IEventSig[] = [];
  checkAccreditationHistoriesSharedCollection: ICheckAccreditationHistorySig[] = [];

  editForm: CheckAccreditationReportSigFormGroup = this.checkAccreditationReportFormService.createCheckAccreditationReportSigFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected checkAccreditationReportService: CheckAccreditationReportSigService,
    protected checkAccreditationReportFormService: CheckAccreditationReportSigFormService,
    protected eventService: EventSigService,
    protected checkAccreditationHistoryService: CheckAccreditationHistorySigService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareEventSig = (o1: IEventSig | null, o2: IEventSig | null): boolean => this.eventService.compareEventSig(o1, o2);

  compareCheckAccreditationHistorySig = (o1: ICheckAccreditationHistorySig | null, o2: ICheckAccreditationHistorySig | null): boolean =>
    this.checkAccreditationHistoryService.compareCheckAccreditationHistorySig(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ checkAccreditationReport }) => {
      this.checkAccreditationReport = checkAccreditationReport;
      if (checkAccreditationReport) {
        this.updateForm(checkAccreditationReport);
      }

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('sigmaEventsApp.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const checkAccreditationReport = this.checkAccreditationReportFormService.getCheckAccreditationReportSig(this.editForm);
    if (checkAccreditationReport.checkAccreditationReportId !== null) {
      this.subscribeToSaveResponse(this.checkAccreditationReportService.update(checkAccreditationReport));
    } else {
      this.subscribeToSaveResponse(this.checkAccreditationReportService.create(checkAccreditationReport));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICheckAccreditationReportSig>>): void {
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

  protected updateForm(checkAccreditationReport: ICheckAccreditationReportSig): void {
    this.checkAccreditationReport = checkAccreditationReport;
    this.checkAccreditationReportFormService.resetForm(this.editForm, checkAccreditationReport);

    this.eventsSharedCollection = this.eventService.addEventSigToCollectionIfMissing<IEventSig>(
      this.eventsSharedCollection,
      checkAccreditationReport.event
    );
    this.checkAccreditationHistoriesSharedCollection =
      this.checkAccreditationHistoryService.addCheckAccreditationHistorySigToCollectionIfMissing<ICheckAccreditationHistorySig>(
        this.checkAccreditationHistoriesSharedCollection,
        checkAccreditationReport.checkAccreditationHistory
      );
  }

  protected loadRelationshipsOptions(): void {
    this.eventService
      .query()
      .pipe(map((res: HttpResponse<IEventSig[]>) => res.body ?? []))
      .pipe(
        map((events: IEventSig[]) =>
          this.eventService.addEventSigToCollectionIfMissing<IEventSig>(events, this.checkAccreditationReport?.event)
        )
      )
      .subscribe((events: IEventSig[]) => (this.eventsSharedCollection = events));

    this.checkAccreditationHistoryService
      .query()
      .pipe(map((res: HttpResponse<ICheckAccreditationHistorySig[]>) => res.body ?? []))
      .pipe(
        map((checkAccreditationHistories: ICheckAccreditationHistorySig[]) =>
          this.checkAccreditationHistoryService.addCheckAccreditationHistorySigToCollectionIfMissing<ICheckAccreditationHistorySig>(
            checkAccreditationHistories,
            this.checkAccreditationReport?.checkAccreditationHistory
          )
        )
      )
      .subscribe(
        (checkAccreditationHistories: ICheckAccreditationHistorySig[]) =>
          (this.checkAccreditationHistoriesSharedCollection = checkAccreditationHistories)
      );
  }
}
