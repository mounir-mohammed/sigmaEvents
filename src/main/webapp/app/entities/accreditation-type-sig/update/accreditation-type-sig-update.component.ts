import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { AccreditationTypeSigFormService, AccreditationTypeSigFormGroup } from './accreditation-type-sig-form.service';
import { IAccreditationTypeSig } from '../accreditation-type-sig.model';
import { AccreditationTypeSigService } from '../service/accreditation-type-sig.service';
import { IPrintingModelSig } from 'app/entities/printing-model-sig/printing-model-sig.model';
import { PrintingModelSigService } from 'app/entities/printing-model-sig/service/printing-model-sig.service';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';
import { EventSigService } from 'app/entities/event-sig/service/event-sig.service';

@Component({
  selector: 'sigma-accreditation-type-sig-update',
  templateUrl: './accreditation-type-sig-update.component.html',
})
export class AccreditationTypeSigUpdateComponent implements OnInit {
  isSaving = false;
  accreditationType: IAccreditationTypeSig | null = null;

  printingModelsSharedCollection: IPrintingModelSig[] = [];
  eventsSharedCollection: IEventSig[] = [];

  editForm: AccreditationTypeSigFormGroup = this.accreditationTypeFormService.createAccreditationTypeSigFormGroup();

  constructor(
    protected accreditationTypeService: AccreditationTypeSigService,
    protected accreditationTypeFormService: AccreditationTypeSigFormService,
    protected printingModelService: PrintingModelSigService,
    protected eventService: EventSigService,
    protected activatedRoute: ActivatedRoute
  ) {}

  comparePrintingModelSig = (o1: IPrintingModelSig | null, o2: IPrintingModelSig | null): boolean =>
    this.printingModelService.comparePrintingModelSig(o1, o2);

  compareEventSig = (o1: IEventSig | null, o2: IEventSig | null): boolean => this.eventService.compareEventSig(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ accreditationType }) => {
      this.accreditationType = accreditationType;
      if (accreditationType) {
        this.updateForm(accreditationType);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const accreditationType = this.accreditationTypeFormService.getAccreditationTypeSig(this.editForm);
    if (accreditationType.accreditationTypeId !== null) {
      this.subscribeToSaveResponse(this.accreditationTypeService.update(accreditationType));
    } else {
      this.subscribeToSaveResponse(this.accreditationTypeService.create(accreditationType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAccreditationTypeSig>>): void {
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

  protected updateForm(accreditationType: IAccreditationTypeSig): void {
    this.accreditationType = accreditationType;
    this.accreditationTypeFormService.resetForm(this.editForm, accreditationType);

    this.printingModelsSharedCollection = this.printingModelService.addPrintingModelSigToCollectionIfMissing<IPrintingModelSig>(
      this.printingModelsSharedCollection,
      accreditationType.printingModel
    );
    this.eventsSharedCollection = this.eventService.addEventSigToCollectionIfMissing<IEventSig>(
      this.eventsSharedCollection,
      accreditationType.event
    );
  }

  protected loadRelationshipsOptions(): void {
    this.printingModelService
      .query()
      .pipe(map((res: HttpResponse<IPrintingModelSig[]>) => res.body ?? []))
      .pipe(
        map((printingModels: IPrintingModelSig[]) =>
          this.printingModelService.addPrintingModelSigToCollectionIfMissing<IPrintingModelSig>(
            printingModels,
            this.accreditationType?.printingModel
          )
        )
      )
      .subscribe((printingModels: IPrintingModelSig[]) => (this.printingModelsSharedCollection = printingModels));

    this.eventService
      .query()
      .pipe(map((res: HttpResponse<IEventSig[]>) => res.body ?? []))
      .pipe(
        map((events: IEventSig[]) => this.eventService.addEventSigToCollectionIfMissing<IEventSig>(events, this.accreditationType?.event))
      )
      .subscribe((events: IEventSig[]) => (this.eventsSharedCollection = events));
  }
}
