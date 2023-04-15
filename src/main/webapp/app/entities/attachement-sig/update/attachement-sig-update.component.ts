import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { AttachementSigFormService, AttachementSigFormGroup } from './attachement-sig-form.service';
import { IAttachementSig } from '../attachement-sig.model';
import { AttachementSigService } from '../service/attachement-sig.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IAttachementTypeSig } from 'app/entities/attachement-type-sig/attachement-type-sig.model';
import { AttachementTypeSigService } from 'app/entities/attachement-type-sig/service/attachement-type-sig.service';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';
import { EventSigService } from 'app/entities/event-sig/service/event-sig.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';

@Component({
  selector: 'sigma-attachement-sig-update',
  templateUrl: './attachement-sig-update.component.html',
})
export class AttachementSigUpdateComponent implements OnInit {
  currentAccount: Account | null = null;
  isSaving = false;
  attachement: IAttachementSig | null = null;

  attachementTypesSharedCollection: IAttachementTypeSig[] = [];
  eventsSharedCollection: IEventSig[] = [];

  editForm: AttachementSigFormGroup = this.attachementFormService.createAttachementSigFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected attachementService: AttachementSigService,
    protected attachementFormService: AttachementSigFormService,
    protected attachementTypeService: AttachementTypeSigService,
    protected eventService: EventSigService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private accountService: AccountService
  ) {}

  compareAttachementTypeSig = (o1: IAttachementTypeSig | null, o2: IAttachementTypeSig | null): boolean =>
    this.attachementTypeService.compareAttachementTypeSig(o1, o2);

  compareEventSig = (o1: IEventSig | null, o2: IEventSig | null): boolean => this.eventService.compareEventSig(o1, o2);

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => (this.currentAccount = account));
    this.activatedRoute.data.subscribe(({ attachement }) => {
      this.attachement = attachement;
      if (attachement) {
        this.updateForm(attachement);
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
    const attachement = this.attachementFormService.getAttachementSig(this.editForm);
    if (!this.accountService.hasAnyAuthority('ROLE_ADMIN')) {
      attachement.event = this.currentAccount?.printingCentre?.event;
    }
    if (attachement.attachementId !== null) {
      this.subscribeToSaveResponse(this.attachementService.update(attachement));
    } else {
      this.subscribeToSaveResponse(this.attachementService.create(attachement));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAttachementSig>>): void {
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

  protected updateForm(attachement: IAttachementSig): void {
    this.attachement = attachement;
    this.attachementFormService.resetForm(this.editForm, attachement);

    this.attachementTypesSharedCollection = this.attachementTypeService.addAttachementTypeSigToCollectionIfMissing<IAttachementTypeSig>(
      this.attachementTypesSharedCollection,
      attachement.attachementType
    );
    this.eventsSharedCollection = this.eventService.addEventSigToCollectionIfMissing<IEventSig>(
      this.eventsSharedCollection,
      attachement.event
    );
  }

  protected loadRelationshipsOptions(): void {
    this.attachementTypeService
      .query()
      .pipe(map((res: HttpResponse<IAttachementTypeSig[]>) => res.body ?? []))
      .pipe(
        map((attachementTypes: IAttachementTypeSig[]) =>
          this.attachementTypeService.addAttachementTypeSigToCollectionIfMissing<IAttachementTypeSig>(
            attachementTypes,
            this.attachement?.attachementType
          )
        )
      )
      .subscribe((attachementTypes: IAttachementTypeSig[]) => (this.attachementTypesSharedCollection = attachementTypes));

    this.eventService
      .query()
      .pipe(map((res: HttpResponse<IEventSig[]>) => res.body ?? []))
      .pipe(map((events: IEventSig[]) => this.eventService.addEventSigToCollectionIfMissing<IEventSig>(events, this.attachement?.event)))
      .subscribe((events: IEventSig[]) => (this.eventsSharedCollection = events));
  }
}
