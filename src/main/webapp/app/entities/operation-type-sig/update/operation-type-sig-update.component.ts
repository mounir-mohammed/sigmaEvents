import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { OperationTypeSigFormService, OperationTypeSigFormGroup } from './operation-type-sig-form.service';
import { IOperationTypeSig } from '../operation-type-sig.model';
import { OperationTypeSigService } from '../service/operation-type-sig.service';

@Component({
  selector: 'sigma-operation-type-sig-update',
  templateUrl: './operation-type-sig-update.component.html',
})
export class OperationTypeSigUpdateComponent implements OnInit {
  isSaving = false;
  operationType: IOperationTypeSig | null = null;

  editForm: OperationTypeSigFormGroup = this.operationTypeFormService.createOperationTypeSigFormGroup();

  constructor(
    protected operationTypeService: OperationTypeSigService,
    protected operationTypeFormService: OperationTypeSigFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ operationType }) => {
      this.operationType = operationType;
      if (operationType) {
        this.updateForm(operationType);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const operationType = this.operationTypeFormService.getOperationTypeSig(this.editForm);
    if (operationType.operationTypeId !== null) {
      this.subscribeToSaveResponse(this.operationTypeService.update(operationType));
    } else {
      this.subscribeToSaveResponse(this.operationTypeService.create(operationType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOperationTypeSig>>): void {
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

  protected updateForm(operationType: IOperationTypeSig): void {
    this.operationType = operationType;
    this.operationTypeFormService.resetForm(this.editForm, operationType);
  }
}
