import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { NoteSigFormService, NoteSigFormGroup } from './note-sig-form.service';
import { INoteSig } from '../note-sig.model';
import { NoteSigService } from '../service/note-sig.service';
import { IAccreditationSig } from 'app/entities/accreditation-sig/accreditation-sig.model';
import { AccreditationSigService } from 'app/entities/accreditation-sig/service/accreditation-sig.service';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';
import { EventSigService } from 'app/entities/event-sig/service/event-sig.service';

@Component({
  selector: 'sigma-note-sig-update',
  templateUrl: './note-sig-update.component.html',
})
export class NoteSigUpdateComponent implements OnInit {
  isSaving = false;
  note: INoteSig | null = null;

  accreditationsSharedCollection: IAccreditationSig[] = [];
  eventsSharedCollection: IEventSig[] = [];

  editForm: NoteSigFormGroup = this.noteFormService.createNoteSigFormGroup();

  constructor(
    protected noteService: NoteSigService,
    protected noteFormService: NoteSigFormService,
    protected accreditationService: AccreditationSigService,
    protected eventService: EventSigService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareAccreditationSig = (o1: IAccreditationSig | null, o2: IAccreditationSig | null): boolean =>
    this.accreditationService.compareAccreditationSig(o1, o2);

  compareEventSig = (o1: IEventSig | null, o2: IEventSig | null): boolean => this.eventService.compareEventSig(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ note }) => {
      this.note = note;
      if (note) {
        this.updateForm(note);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const note = this.noteFormService.getNoteSig(this.editForm);
    if (note.noteId !== null) {
      this.subscribeToSaveResponse(this.noteService.update(note));
    } else {
      this.subscribeToSaveResponse(this.noteService.create(note));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INoteSig>>): void {
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

  protected updateForm(note: INoteSig): void {
    this.note = note;
    this.noteFormService.resetForm(this.editForm, note);

    this.accreditationsSharedCollection = this.accreditationService.addAccreditationSigToCollectionIfMissing<IAccreditationSig>(
      this.accreditationsSharedCollection,
      note.accreditation
    );
    this.eventsSharedCollection = this.eventService.addEventSigToCollectionIfMissing<IEventSig>(this.eventsSharedCollection, note.event);
  }

  protected loadRelationshipsOptions(): void {
    this.accreditationService
      .query()
      .pipe(map((res: HttpResponse<IAccreditationSig[]>) => res.body ?? []))
      .pipe(
        map((accreditations: IAccreditationSig[]) =>
          this.accreditationService.addAccreditationSigToCollectionIfMissing<IAccreditationSig>(accreditations, this.note?.accreditation)
        )
      )
      .subscribe((accreditations: IAccreditationSig[]) => (this.accreditationsSharedCollection = accreditations));

    this.eventService
      .query()
      .pipe(map((res: HttpResponse<IEventSig[]>) => res.body ?? []))
      .pipe(map((events: IEventSig[]) => this.eventService.addEventSigToCollectionIfMissing<IEventSig>(events, this.note?.event)))
      .subscribe((events: IEventSig[]) => (this.eventsSharedCollection = events));
  }
}
