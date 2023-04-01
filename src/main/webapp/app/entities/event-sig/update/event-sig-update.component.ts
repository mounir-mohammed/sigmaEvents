import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { EventSigFormService, EventSigFormGroup } from './event-sig-form.service';
import { IEventSig } from '../event-sig.model';
import { EventSigService } from '../service/event-sig.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { ILanguageSig } from 'app/entities/language-sig/language-sig.model';
import { LanguageSigService } from 'app/entities/language-sig/service/language-sig.service';

@Component({
  selector: 'sigma-event-sig-update',
  templateUrl: './event-sig-update.component.html',
})
export class EventSigUpdateComponent implements OnInit {
  isSaving = false;
  event: IEventSig | null = null;

  languagesSharedCollection: ILanguageSig[] = [];

  editForm: EventSigFormGroup = this.eventFormService.createEventSigFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected eventService: EventSigService,
    protected eventFormService: EventSigFormService,
    protected languageService: LanguageSigService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareLanguageSig = (o1: ILanguageSig | null, o2: ILanguageSig | null): boolean => this.languageService.compareLanguageSig(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ event }) => {
      this.event = event;
      if (event) {
        this.updateForm(event);
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
    const event = this.eventFormService.getEventSig(this.editForm);
    if (event.eventId !== null) {
      this.subscribeToSaveResponse(this.eventService.update(event));
    } else {
      this.subscribeToSaveResponse(this.eventService.create(event));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEventSig>>): void {
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

  protected updateForm(event: IEventSig): void {
    this.event = event;
    this.eventFormService.resetForm(this.editForm, event);

    this.languagesSharedCollection = this.languageService.addLanguageSigToCollectionIfMissing<ILanguageSig>(
      this.languagesSharedCollection,
      event.language
    );
  }

  protected loadRelationshipsOptions(): void {
    this.languageService
      .query()
      .pipe(map((res: HttpResponse<ILanguageSig[]>) => res.body ?? []))
      .pipe(
        map((languages: ILanguageSig[]) =>
          this.languageService.addLanguageSigToCollectionIfMissing<ILanguageSig>(languages, this.event?.language)
        )
      )
      .subscribe((languages: ILanguageSig[]) => (this.languagesSharedCollection = languages));
  }
}
