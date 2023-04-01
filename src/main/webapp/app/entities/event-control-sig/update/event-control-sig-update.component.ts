import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { EventControlSigFormService, EventControlSigFormGroup } from './event-control-sig-form.service';
import { IEventControlSig } from '../event-control-sig.model';
import { EventControlSigService } from '../service/event-control-sig.service';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';
import { EventSigService } from 'app/entities/event-sig/service/event-sig.service';
import { IEventFieldSig } from 'app/entities/event-field-sig/event-field-sig.model';
import { EventFieldSigService } from 'app/entities/event-field-sig/service/event-field-sig.service';

@Component({
  selector: 'sigma-event-control-sig-update',
  templateUrl: './event-control-sig-update.component.html',
})
export class EventControlSigUpdateComponent implements OnInit {
  isSaving = false;
  eventControl: IEventControlSig | null = null;

  eventsSharedCollection: IEventSig[] = [];
  eventFieldsSharedCollection: IEventFieldSig[] = [];

  editForm: EventControlSigFormGroup = this.eventControlFormService.createEventControlSigFormGroup();

  constructor(
    protected eventControlService: EventControlSigService,
    protected eventControlFormService: EventControlSigFormService,
    protected eventService: EventSigService,
    protected eventFieldService: EventFieldSigService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareEventSig = (o1: IEventSig | null, o2: IEventSig | null): boolean => this.eventService.compareEventSig(o1, o2);

  compareEventFieldSig = (o1: IEventFieldSig | null, o2: IEventFieldSig | null): boolean =>
    this.eventFieldService.compareEventFieldSig(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eventControl }) => {
      this.eventControl = eventControl;
      if (eventControl) {
        this.updateForm(eventControl);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const eventControl = this.eventControlFormService.getEventControlSig(this.editForm);
    if (eventControl.controlId !== null) {
      this.subscribeToSaveResponse(this.eventControlService.update(eventControl));
    } else {
      this.subscribeToSaveResponse(this.eventControlService.create(eventControl));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEventControlSig>>): void {
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

  protected updateForm(eventControl: IEventControlSig): void {
    this.eventControl = eventControl;
    this.eventControlFormService.resetForm(this.editForm, eventControl);

    this.eventsSharedCollection = this.eventService.addEventSigToCollectionIfMissing<IEventSig>(
      this.eventsSharedCollection,
      eventControl.event
    );
    this.eventFieldsSharedCollection = this.eventFieldService.addEventFieldSigToCollectionIfMissing<IEventFieldSig>(
      this.eventFieldsSharedCollection,
      eventControl.eventField
    );
  }

  protected loadRelationshipsOptions(): void {
    this.eventService
      .query()
      .pipe(map((res: HttpResponse<IEventSig[]>) => res.body ?? []))
      .pipe(map((events: IEventSig[]) => this.eventService.addEventSigToCollectionIfMissing<IEventSig>(events, this.eventControl?.event)))
      .subscribe((events: IEventSig[]) => (this.eventsSharedCollection = events));

    this.eventFieldService
      .query()
      .pipe(map((res: HttpResponse<IEventFieldSig[]>) => res.body ?? []))
      .pipe(
        map((eventFields: IEventFieldSig[]) =>
          this.eventFieldService.addEventFieldSigToCollectionIfMissing<IEventFieldSig>(eventFields, this.eventControl?.eventField)
        )
      )
      .subscribe((eventFields: IEventFieldSig[]) => (this.eventFieldsSharedCollection = eventFields));
  }
}
