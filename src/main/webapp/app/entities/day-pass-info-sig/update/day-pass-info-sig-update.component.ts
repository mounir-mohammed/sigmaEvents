import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { DayPassInfoSigFormService, DayPassInfoSigFormGroup } from './day-pass-info-sig-form.service';
import { IDayPassInfoSig } from '../day-pass-info-sig.model';
import { DayPassInfoSigService } from '../service/day-pass-info-sig.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';
import { EventSigService } from 'app/entities/event-sig/service/event-sig.service';

@Component({
  selector: 'sigma-day-pass-info-sig-update',
  templateUrl: './day-pass-info-sig-update.component.html',
})
export class DayPassInfoSigUpdateComponent implements OnInit {
  isSaving = false;
  dayPassInfo: IDayPassInfoSig | null = null;

  eventsSharedCollection: IEventSig[] = [];

  editForm: DayPassInfoSigFormGroup = this.dayPassInfoFormService.createDayPassInfoSigFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected dayPassInfoService: DayPassInfoSigService,
    protected dayPassInfoFormService: DayPassInfoSigFormService,
    protected eventService: EventSigService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareEventSig = (o1: IEventSig | null, o2: IEventSig | null): boolean => this.eventService.compareEventSig(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dayPassInfo }) => {
      this.dayPassInfo = dayPassInfo;
      if (dayPassInfo) {
        this.updateForm(dayPassInfo);
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
    const dayPassInfo = this.dayPassInfoFormService.getDayPassInfoSig(this.editForm);
    if (dayPassInfo.dayPassInfoId !== null) {
      this.subscribeToSaveResponse(this.dayPassInfoService.update(dayPassInfo));
    } else {
      this.subscribeToSaveResponse(this.dayPassInfoService.create(dayPassInfo));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDayPassInfoSig>>): void {
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

  protected updateForm(dayPassInfo: IDayPassInfoSig): void {
    this.dayPassInfo = dayPassInfo;
    this.dayPassInfoFormService.resetForm(this.editForm, dayPassInfo);

    this.eventsSharedCollection = this.eventService.addEventSigToCollectionIfMissing<IEventSig>(
      this.eventsSharedCollection,
      dayPassInfo.event
    );
  }

  protected loadRelationshipsOptions(): void {
    this.eventService
      .query()
      .pipe(map((res: HttpResponse<IEventSig[]>) => res.body ?? []))
      .pipe(map((events: IEventSig[]) => this.eventService.addEventSigToCollectionIfMissing<IEventSig>(events, this.dayPassInfo?.event)))
      .subscribe((events: IEventSig[]) => (this.eventsSharedCollection = events));
  }
}
