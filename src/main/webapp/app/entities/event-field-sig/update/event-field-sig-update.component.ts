import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { EventFieldSigFormService, EventFieldSigFormGroup } from './event-field-sig-form.service';
import { IEventFieldSig } from '../event-field-sig.model';
import { EventFieldSigService } from '../service/event-field-sig.service';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';
import { EventSigService } from 'app/entities/event-sig/service/event-sig.service';
import { IEventFormSig } from 'app/entities/event-form-sig/event-form-sig.model';
import { EventFormSigService } from 'app/entities/event-form-sig/service/event-form-sig.service';

@Component({
  selector: 'sigma-event-field-sig-update',
  templateUrl: './event-field-sig-update.component.html',
})
export class EventFieldSigUpdateComponent implements OnInit {
  isSaving = false;
  eventField: IEventFieldSig | null = null;

  eventsSharedCollection: IEventSig[] = [];
  eventFormsSharedCollection: IEventFormSig[] = [];

  editForm: EventFieldSigFormGroup = this.eventFieldFormService.createEventFieldSigFormGroup();

  constructor(
    protected eventFieldService: EventFieldSigService,
    protected eventFieldFormService: EventFieldSigFormService,
    protected eventService: EventSigService,
    protected eventFormService: EventFormSigService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareEventSig = (o1: IEventSig | null, o2: IEventSig | null): boolean => this.eventService.compareEventSig(o1, o2);

  compareEventFormSig = (o1: IEventFormSig | null, o2: IEventFormSig | null): boolean => this.eventFormService.compareEventFormSig(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eventField }) => {
      this.eventField = eventField;
      if (eventField) {
        this.updateForm(eventField);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const eventField = this.eventFieldFormService.getEventFieldSig(this.editForm);
    if (eventField.fieldId !== null) {
      this.subscribeToSaveResponse(this.eventFieldService.update(eventField));
    } else {
      this.subscribeToSaveResponse(this.eventFieldService.create(eventField));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEventFieldSig>>): void {
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

  protected updateForm(eventField: IEventFieldSig): void {
    this.eventField = eventField;
    this.eventFieldFormService.resetForm(this.editForm, eventField);

    this.eventsSharedCollection = this.eventService.addEventSigToCollectionIfMissing<IEventSig>(
      this.eventsSharedCollection,
      eventField.event
    );
    this.eventFormsSharedCollection = this.eventFormService.addEventFormSigToCollectionIfMissing<IEventFormSig>(
      this.eventFormsSharedCollection,
      eventField.eventForm
    );
  }

  protected loadRelationshipsOptions(): void {
    this.eventService
      .query()
      .pipe(map((res: HttpResponse<IEventSig[]>) => res.body ?? []))
      .pipe(map((events: IEventSig[]) => this.eventService.addEventSigToCollectionIfMissing<IEventSig>(events, this.eventField?.event)))
      .subscribe((events: IEventSig[]) => (this.eventsSharedCollection = events));

    this.eventFormService
      .query()
      .pipe(map((res: HttpResponse<IEventFormSig[]>) => res.body ?? []))
      .pipe(
        map((eventForms: IEventFormSig[]) =>
          this.eventFormService.addEventFormSigToCollectionIfMissing<IEventFormSig>(eventForms, this.eventField?.eventForm)
        )
      )
      .subscribe((eventForms: IEventFormSig[]) => (this.eventFormsSharedCollection = eventForms));
  }
}
