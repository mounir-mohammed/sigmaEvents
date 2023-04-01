import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { SettingSigFormService, SettingSigFormGroup } from './setting-sig-form.service';
import { ISettingSig } from '../setting-sig.model';
import { SettingSigService } from '../service/setting-sig.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { ILanguageSig } from 'app/entities/language-sig/language-sig.model';
import { LanguageSigService } from 'app/entities/language-sig/service/language-sig.service';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';
import { EventSigService } from 'app/entities/event-sig/service/event-sig.service';

@Component({
  selector: 'sigma-setting-sig-update',
  templateUrl: './setting-sig-update.component.html',
})
export class SettingSigUpdateComponent implements OnInit {
  isSaving = false;
  setting: ISettingSig | null = null;

  languagesSharedCollection: ILanguageSig[] = [];
  eventsSharedCollection: IEventSig[] = [];

  editForm: SettingSigFormGroup = this.settingFormService.createSettingSigFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected settingService: SettingSigService,
    protected settingFormService: SettingSigFormService,
    protected languageService: LanguageSigService,
    protected eventService: EventSigService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareLanguageSig = (o1: ILanguageSig | null, o2: ILanguageSig | null): boolean => this.languageService.compareLanguageSig(o1, o2);

  compareEventSig = (o1: IEventSig | null, o2: IEventSig | null): boolean => this.eventService.compareEventSig(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ setting }) => {
      this.setting = setting;
      if (setting) {
        this.updateForm(setting);
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
    const setting = this.settingFormService.getSettingSig(this.editForm);
    if (setting.settingId !== null) {
      this.subscribeToSaveResponse(this.settingService.update(setting));
    } else {
      this.subscribeToSaveResponse(this.settingService.create(setting));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISettingSig>>): void {
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

  protected updateForm(setting: ISettingSig): void {
    this.setting = setting;
    this.settingFormService.resetForm(this.editForm, setting);

    this.languagesSharedCollection = this.languageService.addLanguageSigToCollectionIfMissing<ILanguageSig>(
      this.languagesSharedCollection,
      setting.language
    );
    this.eventsSharedCollection = this.eventService.addEventSigToCollectionIfMissing<IEventSig>(this.eventsSharedCollection, setting.event);
  }

  protected loadRelationshipsOptions(): void {
    this.languageService
      .query()
      .pipe(map((res: HttpResponse<ILanguageSig[]>) => res.body ?? []))
      .pipe(
        map((languages: ILanguageSig[]) =>
          this.languageService.addLanguageSigToCollectionIfMissing<ILanguageSig>(languages, this.setting?.language)
        )
      )
      .subscribe((languages: ILanguageSig[]) => (this.languagesSharedCollection = languages));

    this.eventService
      .query()
      .pipe(map((res: HttpResponse<IEventSig[]>) => res.body ?? []))
      .pipe(map((events: IEventSig[]) => this.eventService.addEventSigToCollectionIfMissing<IEventSig>(events, this.setting?.event)))
      .subscribe((events: IEventSig[]) => (this.eventsSharedCollection = events));
  }
}
