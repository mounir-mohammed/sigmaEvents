import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { CodeTypeSigFormService, CodeTypeSigFormGroup } from './code-type-sig-form.service';
import { ICodeTypeSig } from '../code-type-sig.model';
import { CodeTypeSigService } from '../service/code-type-sig.service';

@Component({
  selector: 'sigma-code-type-sig-update',
  templateUrl: './code-type-sig-update.component.html',
})
export class CodeTypeSigUpdateComponent implements OnInit {
  isSaving = false;
  codeType: ICodeTypeSig | null = null;

  editForm: CodeTypeSigFormGroup = this.codeTypeFormService.createCodeTypeSigFormGroup();

  constructor(
    protected codeTypeService: CodeTypeSigService,
    protected codeTypeFormService: CodeTypeSigFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ codeType }) => {
      this.codeType = codeType;
      if (codeType) {
        this.updateForm(codeType);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const codeType = this.codeTypeFormService.getCodeTypeSig(this.editForm);
    if (codeType.codeTypeId !== null) {
      this.subscribeToSaveResponse(this.codeTypeService.update(codeType));
    } else {
      this.subscribeToSaveResponse(this.codeTypeService.create(codeType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICodeTypeSig>>): void {
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

  protected updateForm(codeType: ICodeTypeSig): void {
    this.codeType = codeType;
    this.codeTypeFormService.resetForm(this.editForm, codeType);
  }
}
