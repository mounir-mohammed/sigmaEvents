import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { CloningSigFormService, CloningSigFormGroup } from './cloning-sig-form.service';
import { ICloningSig } from '../cloning-sig.model';
import { CloningSigService } from '../service/cloning-sig.service';

@Component({
  selector: 'sigma-cloning-sig-update',
  templateUrl: './cloning-sig-update.component.html',
})
export class CloningSigUpdateComponent implements OnInit {
  isSaving = false;
  cloning: ICloningSig | null = null;

  editForm: CloningSigFormGroup = this.cloningFormService.createCloningSigFormGroup();

  constructor(
    protected cloningService: CloningSigService,
    protected cloningFormService: CloningSigFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cloning }) => {
      this.cloning = cloning;
      if (cloning) {
        this.updateForm(cloning);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cloning = this.cloningFormService.getCloningSig(this.editForm);
    if (cloning.cloningId !== null) {
      this.subscribeToSaveResponse(this.cloningService.update(cloning));
    } else {
      this.subscribeToSaveResponse(this.cloningService.create(cloning));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICloningSig>>): void {
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

  protected updateForm(cloning: ICloningSig): void {
    this.cloning = cloning;
    this.cloningFormService.resetForm(this.editForm, cloning);
  }
}
