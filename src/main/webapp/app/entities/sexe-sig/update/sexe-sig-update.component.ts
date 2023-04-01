import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { SexeSigFormService, SexeSigFormGroup } from './sexe-sig-form.service';
import { ISexeSig } from '../sexe-sig.model';
import { SexeSigService } from '../service/sexe-sig.service';

@Component({
  selector: 'sigma-sexe-sig-update',
  templateUrl: './sexe-sig-update.component.html',
})
export class SexeSigUpdateComponent implements OnInit {
  isSaving = false;
  sexe: ISexeSig | null = null;

  editForm: SexeSigFormGroup = this.sexeFormService.createSexeSigFormGroup();

  constructor(
    protected sexeService: SexeSigService,
    protected sexeFormService: SexeSigFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sexe }) => {
      this.sexe = sexe;
      if (sexe) {
        this.updateForm(sexe);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sexe = this.sexeFormService.getSexeSig(this.editForm);
    if (sexe.sexeId !== null) {
      this.subscribeToSaveResponse(this.sexeService.update(sexe));
    } else {
      this.subscribeToSaveResponse(this.sexeService.create(sexe));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISexeSig>>): void {
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

  protected updateForm(sexe: ISexeSig): void {
    this.sexe = sexe;
    this.sexeFormService.resetForm(this.editForm, sexe);
  }
}
