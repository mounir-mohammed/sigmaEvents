import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { PrintingModelSigFormService, PrintingModelSigFormGroup } from './printing-model-sig-form.service';
import { IPrintingModelSig } from '../printing-model-sig.model';
import { PrintingModelSigService } from '../service/printing-model-sig.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';
import { EventSigService } from 'app/entities/event-sig/service/event-sig.service';

@Component({
  selector: 'sigma-printing-model-sig-update',
  templateUrl: './printing-model-sig-update.component.html',
})
export class PrintingModelSigUpdateComponent implements OnInit {
  isSaving = false;
  printingModel: IPrintingModelSig | null = null;

  eventsSharedCollection: IEventSig[] = [];

  editForm: PrintingModelSigFormGroup = this.printingModelFormService.createPrintingModelSigFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected printingModelService: PrintingModelSigService,
    protected printingModelFormService: PrintingModelSigFormService,
    protected eventService: EventSigService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareEventSig = (o1: IEventSig | null, o2: IEventSig | null): boolean => this.eventService.compareEventSig(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ printingModel }) => {
      this.printingModel = printingModel;
      if (printingModel) {
        this.updateForm(printingModel);
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

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const printingModel = this.printingModelFormService.getPrintingModelSig(this.editForm);
    if (printingModel.printingModelId !== null) {
      this.subscribeToSaveResponse(this.printingModelService.update(printingModel));
    } else {
      this.subscribeToSaveResponse(this.printingModelService.create(printingModel));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPrintingModelSig>>): void {
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

  protected updateForm(printingModel: IPrintingModelSig): void {
    this.printingModel = printingModel;
    this.printingModelFormService.resetForm(this.editForm, printingModel);

    this.eventsSharedCollection = this.eventService.addEventSigToCollectionIfMissing<IEventSig>(
      this.eventsSharedCollection,
      printingModel.event
    );
  }

  protected loadRelationshipsOptions(): void {
    this.eventService
      .query()
      .pipe(map((res: HttpResponse<IEventSig[]>) => res.body ?? []))
      .pipe(map((events: IEventSig[]) => this.eventService.addEventSigToCollectionIfMissing<IEventSig>(events, this.printingModel?.event)))
      .subscribe((events: IEventSig[]) => (this.eventsSharedCollection = events));
  }
}
