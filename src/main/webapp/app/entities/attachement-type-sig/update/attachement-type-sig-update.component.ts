import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { AttachementTypeSigFormService, AttachementTypeSigFormGroup } from './attachement-type-sig-form.service';
import { IAttachementTypeSig } from '../attachement-type-sig.model';
import { AttachementTypeSigService } from '../service/attachement-type-sig.service';

@Component({
  selector: 'sigma-attachement-type-sig-update',
  templateUrl: './attachement-type-sig-update.component.html',
})
export class AttachementTypeSigUpdateComponent implements OnInit {
  isSaving = false;
  attachementType: IAttachementTypeSig | null = null;

  editForm: AttachementTypeSigFormGroup = this.attachementTypeFormService.createAttachementTypeSigFormGroup();

  constructor(
    protected attachementTypeService: AttachementTypeSigService,
    protected attachementTypeFormService: AttachementTypeSigFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ attachementType }) => {
      this.attachementType = attachementType;
      if (attachementType) {
        this.updateForm(attachementType);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const attachementType = this.attachementTypeFormService.getAttachementTypeSig(this.editForm);
    if (attachementType.attachementTypeId !== null) {
      this.subscribeToSaveResponse(this.attachementTypeService.update(attachementType));
    } else {
      this.subscribeToSaveResponse(this.attachementTypeService.create(attachementType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAttachementTypeSig>>): void {
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

  protected updateForm(attachementType: IAttachementTypeSig): void {
    this.attachementType = attachementType;
    this.attachementTypeFormService.resetForm(this.editForm, attachementType);
  }
}
