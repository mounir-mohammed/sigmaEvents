import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { InfoSuppSigFormService, InfoSuppSigFormGroup } from './info-supp-sig-form.service';
import { IInfoSuppSig } from '../info-supp-sig.model';
import { InfoSuppSigService } from '../service/info-supp-sig.service';
import { IInfoSuppTypeSig } from 'app/entities/info-supp-type-sig/info-supp-type-sig.model';
import { InfoSuppTypeSigService } from 'app/entities/info-supp-type-sig/service/info-supp-type-sig.service';
import { IAccreditationSig } from 'app/entities/accreditation-sig/accreditation-sig.model';
import { AccreditationSigService } from 'app/entities/accreditation-sig/service/accreditation-sig.service';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';
import { EventSigService } from 'app/entities/event-sig/service/event-sig.service';

@Component({
  selector: 'sigma-info-supp-sig-update',
  templateUrl: './info-supp-sig-update.component.html',
})
export class InfoSuppSigUpdateComponent implements OnInit {
  isSaving = false;
  infoSupp: IInfoSuppSig | null = null;

  infoSuppTypesSharedCollection: IInfoSuppTypeSig[] = [];
  accreditationsSharedCollection: IAccreditationSig[] = [];
  eventsSharedCollection: IEventSig[] = [];

  editForm: InfoSuppSigFormGroup = this.infoSuppFormService.createInfoSuppSigFormGroup();

  constructor(
    protected infoSuppService: InfoSuppSigService,
    protected infoSuppFormService: InfoSuppSigFormService,
    protected infoSuppTypeService: InfoSuppTypeSigService,
    protected accreditationService: AccreditationSigService,
    protected eventService: EventSigService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareInfoSuppTypeSig = (o1: IInfoSuppTypeSig | null, o2: IInfoSuppTypeSig | null): boolean =>
    this.infoSuppTypeService.compareInfoSuppTypeSig(o1, o2);

  compareAccreditationSig = (o1: IAccreditationSig | null, o2: IAccreditationSig | null): boolean =>
    this.accreditationService.compareAccreditationSig(o1, o2);

  compareEventSig = (o1: IEventSig | null, o2: IEventSig | null): boolean => this.eventService.compareEventSig(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ infoSupp }) => {
      this.infoSupp = infoSupp;
      if (infoSupp) {
        this.updateForm(infoSupp);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const infoSupp = this.infoSuppFormService.getInfoSuppSig(this.editForm);
    if (infoSupp.infoSuppId !== null) {
      this.subscribeToSaveResponse(this.infoSuppService.update(infoSupp));
    } else {
      this.subscribeToSaveResponse(this.infoSuppService.create(infoSupp));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInfoSuppSig>>): void {
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

  protected updateForm(infoSupp: IInfoSuppSig): void {
    this.infoSupp = infoSupp;
    this.infoSuppFormService.resetForm(this.editForm, infoSupp);

    this.infoSuppTypesSharedCollection = this.infoSuppTypeService.addInfoSuppTypeSigToCollectionIfMissing<IInfoSuppTypeSig>(
      this.infoSuppTypesSharedCollection,
      infoSupp.infoSuppType
    );
    this.accreditationsSharedCollection = this.accreditationService.addAccreditationSigToCollectionIfMissing<IAccreditationSig>(
      this.accreditationsSharedCollection,
      infoSupp.accreditation
    );
    this.eventsSharedCollection = this.eventService.addEventSigToCollectionIfMissing<IEventSig>(
      this.eventsSharedCollection,
      infoSupp.event
    );
  }

  protected loadRelationshipsOptions(): void {
    this.infoSuppTypeService
      .query()
      .pipe(map((res: HttpResponse<IInfoSuppTypeSig[]>) => res.body ?? []))
      .pipe(
        map((infoSuppTypes: IInfoSuppTypeSig[]) =>
          this.infoSuppTypeService.addInfoSuppTypeSigToCollectionIfMissing<IInfoSuppTypeSig>(infoSuppTypes, this.infoSupp?.infoSuppType)
        )
      )
      .subscribe((infoSuppTypes: IInfoSuppTypeSig[]) => (this.infoSuppTypesSharedCollection = infoSuppTypes));

    this.accreditationService
      .query()
      .pipe(map((res: HttpResponse<IAccreditationSig[]>) => res.body ?? []))
      .pipe(
        map((accreditations: IAccreditationSig[]) =>
          this.accreditationService.addAccreditationSigToCollectionIfMissing<IAccreditationSig>(
            accreditations,
            this.infoSupp?.accreditation
          )
        )
      )
      .subscribe((accreditations: IAccreditationSig[]) => (this.accreditationsSharedCollection = accreditations));

    this.eventService
      .query()
      .pipe(map((res: HttpResponse<IEventSig[]>) => res.body ?? []))
      .pipe(map((events: IEventSig[]) => this.eventService.addEventSigToCollectionIfMissing<IEventSig>(events, this.infoSupp?.event)))
      .subscribe((events: IEventSig[]) => (this.eventsSharedCollection = events));
  }
}
