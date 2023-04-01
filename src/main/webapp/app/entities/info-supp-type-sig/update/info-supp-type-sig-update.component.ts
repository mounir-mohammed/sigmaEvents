import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { InfoSuppTypeSigFormService, InfoSuppTypeSigFormGroup } from './info-supp-type-sig-form.service';
import { IInfoSuppTypeSig } from '../info-supp-type-sig.model';
import { InfoSuppTypeSigService } from '../service/info-supp-type-sig.service';

@Component({
  selector: 'sigma-info-supp-type-sig-update',
  templateUrl: './info-supp-type-sig-update.component.html',
})
export class InfoSuppTypeSigUpdateComponent implements OnInit {
  isSaving = false;
  infoSuppType: IInfoSuppTypeSig | null = null;

  editForm: InfoSuppTypeSigFormGroup = this.infoSuppTypeFormService.createInfoSuppTypeSigFormGroup();

  constructor(
    protected infoSuppTypeService: InfoSuppTypeSigService,
    protected infoSuppTypeFormService: InfoSuppTypeSigFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ infoSuppType }) => {
      this.infoSuppType = infoSuppType;
      if (infoSuppType) {
        this.updateForm(infoSuppType);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const infoSuppType = this.infoSuppTypeFormService.getInfoSuppTypeSig(this.editForm);
    if (infoSuppType.infoSuppTypeId !== null) {
      this.subscribeToSaveResponse(this.infoSuppTypeService.update(infoSuppType));
    } else {
      this.subscribeToSaveResponse(this.infoSuppTypeService.create(infoSuppType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInfoSuppTypeSig>>): void {
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

  protected updateForm(infoSuppType: IInfoSuppTypeSig): void {
    this.infoSuppType = infoSuppType;
    this.infoSuppTypeFormService.resetForm(this.editForm, infoSuppType);
  }
}
