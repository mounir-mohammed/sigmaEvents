import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { EventFormSigFormService, EventFormSigFormGroup } from './event-form-sig-form.service';
import { IEventFormSig } from '../event-form-sig.model';
import { EventFormSigService } from '../service/event-form-sig.service';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';
import { EventSigService } from 'app/entities/event-sig/service/event-sig.service';

@Component({
  selector: 'sigma-event-form-sig-update',
  templateUrl: './event-form-sig-update.component.html',
})
export class EventFormSigUpdateComponent implements OnInit {
  isSaving = false;
  eventForm: IEventFormSig | null = null;

  eventsSharedCollection: IEventSig[] = [];

  editForm: EventFormSigFormGroup = this.eventFormFormService.createEventFormSigFormGroup();

  constructor(
    protected eventFormService: EventFormSigService,
    protected eventFormFormService: EventFormSigFormService,
    protected eventService: EventSigService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareEventSig = (o1: IEventSig | null, o2: IEventSig | null): boolean => this.eventService.compareEventSig(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eventForm }) => {
      this.eventForm = eventForm;
      if (eventForm) {
        this.updateForm(eventForm);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const eventForm = this.eventFormFormService.getEventFormSig(this.editForm);
    if (eventForm.formId !== null) {
      this.subscribeToSaveResponse(this.eventFormService.update(eventForm));
    } else {
      this.subscribeToSaveResponse(this.eventFormService.create(eventForm));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEventFormSig>>): void {
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

  protected updateForm(eventForm: IEventFormSig): void {
    this.eventForm = eventForm;
    this.eventFormFormService.resetForm(this.editForm, eventForm);

    this.eventsSharedCollection = this.eventService.addEventSigToCollectionIfMissing<IEventSig>(
      this.eventsSharedCollection,
      eventForm.event
    );
  }

  protected loadRelationshipsOptions(): void {
    this.eventService
      .query()
      .pipe(map((res: HttpResponse<IEventSig[]>) => res.body ?? []))
      .pipe(map((events: IEventSig[]) => this.eventService.addEventSigToCollectionIfMissing<IEventSig>(events, this.eventForm?.event)))
      .subscribe((events: IEventSig[]) => (this.eventsSharedCollection = events));
  }
}
