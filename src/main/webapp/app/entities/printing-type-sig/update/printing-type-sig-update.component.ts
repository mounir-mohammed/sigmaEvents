import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { PrintingTypeSigFormService, PrintingTypeSigFormGroup } from './printing-type-sig-form.service';
import { IPrintingTypeSig } from '../printing-type-sig.model';
import { PrintingTypeSigService } from '../service/printing-type-sig.service';

@Component({
  selector: 'sigma-printing-type-sig-update',
  templateUrl: './printing-type-sig-update.component.html',
})
export class PrintingTypeSigUpdateComponent implements OnInit {
  isSaving = false;
  printingType: IPrintingTypeSig | null = null;

  editForm: PrintingTypeSigFormGroup = this.printingTypeFormService.createPrintingTypeSigFormGroup();

  constructor(
    protected printingTypeService: PrintingTypeSigService,
    protected printingTypeFormService: PrintingTypeSigFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ printingType }) => {
      this.printingType = printingType;
      if (printingType) {
        this.updateForm(printingType);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const printingType = this.printingTypeFormService.getPrintingTypeSig(this.editForm);
    if (printingType.printingTypeId !== null) {
      this.subscribeToSaveResponse(this.printingTypeService.update(printingType));
    } else {
      this.subscribeToSaveResponse(this.printingTypeService.create(printingType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPrintingTypeSig>>): void {
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

  protected updateForm(printingType: IPrintingTypeSig): void {
    this.printingType = printingType;
    this.printingTypeFormService.resetForm(this.editForm, printingType);
  }
}
