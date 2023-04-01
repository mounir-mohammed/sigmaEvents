import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { AuthentificationTypeSigFormService, AuthentificationTypeSigFormGroup } from './authentification-type-sig-form.service';
import { IAuthentificationTypeSig } from '../authentification-type-sig.model';
import { AuthentificationTypeSigService } from '../service/authentification-type-sig.service';

@Component({
  selector: 'sigma-authentification-type-sig-update',
  templateUrl: './authentification-type-sig-update.component.html',
})
export class AuthentificationTypeSigUpdateComponent implements OnInit {
  isSaving = false;
  authentificationType: IAuthentificationTypeSig | null = null;

  editForm: AuthentificationTypeSigFormGroup = this.authentificationTypeFormService.createAuthentificationTypeSigFormGroup();

  constructor(
    protected authentificationTypeService: AuthentificationTypeSigService,
    protected authentificationTypeFormService: AuthentificationTypeSigFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ authentificationType }) => {
      this.authentificationType = authentificationType;
      if (authentificationType) {
        this.updateForm(authentificationType);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const authentificationType = this.authentificationTypeFormService.getAuthentificationTypeSig(this.editForm);
    if (authentificationType.authentificationTypeId !== null) {
      this.subscribeToSaveResponse(this.authentificationTypeService.update(authentificationType));
    } else {
      this.subscribeToSaveResponse(this.authentificationTypeService.create(authentificationType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAuthentificationTypeSig>>): void {
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

  protected updateForm(authentificationType: IAuthentificationTypeSig): void {
    this.authentificationType = authentificationType;
    this.authentificationTypeFormService.resetForm(this.editForm, authentificationType);
  }
}
