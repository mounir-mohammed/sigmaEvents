import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { CategorySigFormService, CategorySigFormGroup } from './category-sig-form.service';
import { ICategorySig } from '../category-sig.model';
import { CategorySigService } from '../service/category-sig.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IPrintingModelSig } from 'app/entities/printing-model-sig/printing-model-sig.model';
import { PrintingModelSigService } from 'app/entities/printing-model-sig/service/printing-model-sig.service';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';
import { EventSigService } from 'app/entities/event-sig/service/event-sig.service';

@Component({
  selector: 'sigma-category-sig-update',
  templateUrl: './category-sig-update.component.html',
})
export class CategorySigUpdateComponent implements OnInit {
  isSaving = false;
  category: ICategorySig | null = null;
  public color = '#cccccc';

  printingModelsSharedCollection: IPrintingModelSig[] = [];
  eventsSharedCollection: IEventSig[] = [];
  editForm: CategorySigFormGroup = this.categoryFormService.createCategorySigFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected categoryService: CategorySigService,
    protected categoryFormService: CategorySigFormService,
    protected printingModelService: PrintingModelSigService,
    protected eventService: EventSigService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute
  ) {}

  comparePrintingModelSig = (o1: IPrintingModelSig | null, o2: IPrintingModelSig | null): boolean =>
    this.printingModelService.comparePrintingModelSig(o1, o2);

  compareEventSig = (o1: IEventSig | null, o2: IEventSig | null): boolean => this.eventService.compareEventSig(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ category }) => {
      this.category = category;
      if (category) {
        this.color = this.category?.categoryColor!;
        this.updateForm(category);
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
    const category = this.categoryFormService.getCategorySig(this.editForm);
    if (category.categoryId !== null) {
      this.subscribeToSaveResponse(this.categoryService.update(category));
    } else {
      this.subscribeToSaveResponse(this.categoryService.create(category));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategorySig>>): void {
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

  protected updateForm(category: ICategorySig): void {
    this.category = category;
    this.categoryFormService.resetForm(this.editForm, category);

    this.printingModelsSharedCollection = this.printingModelService.addPrintingModelSigToCollectionIfMissing<IPrintingModelSig>(
      this.printingModelsSharedCollection,
      category.printingModel
    );
    this.eventsSharedCollection = this.eventService.addEventSigToCollectionIfMissing<IEventSig>(
      this.eventsSharedCollection,
      category.event
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
            this.category?.printingModel
          )
        )
      )
      .subscribe((printingModels: IPrintingModelSig[]) => (this.printingModelsSharedCollection = printingModels));

    this.eventService
      .query()
      .pipe(map((res: HttpResponse<IEventSig[]>) => res.body ?? []))
      .pipe(map((events: IEventSig[]) => this.eventService.addEventSigToCollectionIfMissing<IEventSig>(events, this.category?.event)))
      .subscribe((events: IEventSig[]) => (this.eventsSharedCollection = events));
  }

  public onChangeColor(color: string): void {
    this.color = color;
    this.editForm.patchValue({ categoryColor: color });
  }
}
