import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { PhotoArchiveSigFormService, PhotoArchiveSigFormGroup } from './photo-archive-sig-form.service';
import { IPhotoArchiveSig } from '../photo-archive-sig.model';
import { PhotoArchiveSigService } from '../service/photo-archive-sig.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IAccreditationSig } from 'app/entities/accreditation-sig/accreditation-sig.model';
import { AccreditationSigService } from 'app/entities/accreditation-sig/service/accreditation-sig.service';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';
import { EventSigService } from 'app/entities/event-sig/service/event-sig.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';

@Component({
  selector: 'sigma-photo-archive-sig-update',
  templateUrl: './photo-archive-sig-update.component.html',
})
export class PhotoArchiveSigUpdateComponent implements OnInit {
  currentAccount: Account | null = null;
  isSaving = false;
  photoArchive: IPhotoArchiveSig | null = null;

  accreditationsSharedCollection: IAccreditationSig[] = [];
  eventsSharedCollection: IEventSig[] = [];

  editForm: PhotoArchiveSigFormGroup = this.photoArchiveFormService.createPhotoArchiveSigFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected photoArchiveService: PhotoArchiveSigService,
    protected photoArchiveFormService: PhotoArchiveSigFormService,
    protected accreditationService: AccreditationSigService,
    protected eventService: EventSigService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private accountService: AccountService
  ) {}

  compareAccreditationSig = (o1: IAccreditationSig | null, o2: IAccreditationSig | null): boolean =>
    this.accreditationService.compareAccreditationSig(o1, o2);

  compareEventSig = (o1: IEventSig | null, o2: IEventSig | null): boolean => this.eventService.compareEventSig(o1, o2);

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => (this.currentAccount = account));
    this.activatedRoute.data.subscribe(({ photoArchive }) => {
      this.photoArchive = photoArchive;
      if (photoArchive) {
        this.updateForm(photoArchive);
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
    const photoArchive = this.photoArchiveFormService.getPhotoArchiveSig(this.editForm);
    if (!this.accountService.hasAnyAuthority('ROLE_ADMIN')) {
      photoArchive.event = this.currentAccount?.printingCentre?.event;
    }
    if (photoArchive.photoArchiveId !== null) {
      this.subscribeToSaveResponse(this.photoArchiveService.update(photoArchive));
    } else {
      this.subscribeToSaveResponse(this.photoArchiveService.create(photoArchive));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPhotoArchiveSig>>): void {
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

  protected updateForm(photoArchive: IPhotoArchiveSig): void {
    this.photoArchive = photoArchive;
    this.photoArchiveFormService.resetForm(this.editForm, photoArchive);

    this.accreditationsSharedCollection = this.accreditationService.addAccreditationSigToCollectionIfMissing<IAccreditationSig>(
      this.accreditationsSharedCollection,
      photoArchive.accreditation
    );
    this.eventsSharedCollection = this.eventService.addEventSigToCollectionIfMissing<IEventSig>(
      this.eventsSharedCollection,
      photoArchive.event
    );
  }

  protected loadRelationshipsOptions(): void {
    this.accreditationService
      .query()
      .pipe(map((res: HttpResponse<IAccreditationSig[]>) => res.body ?? []))
      .pipe(
        map((accreditations: IAccreditationSig[]) =>
          this.accreditationService.addAccreditationSigToCollectionIfMissing<IAccreditationSig>(
            accreditations,
            this.photoArchive?.accreditation
          )
        )
      )
      .subscribe((accreditations: IAccreditationSig[]) => (this.accreditationsSharedCollection = accreditations));

    this.eventService
      .query()
      .pipe(map((res: HttpResponse<IEventSig[]>) => res.body ?? []))
      .pipe(map((events: IEventSig[]) => this.eventService.addEventSigToCollectionIfMissing<IEventSig>(events, this.photoArchive?.event)))
      .subscribe((events: IEventSig[]) => (this.eventsSharedCollection = events));
  }
}
