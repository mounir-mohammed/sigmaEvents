import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { CivilitySigFormService, CivilitySigFormGroup } from './civility-sig-form.service';
import { ICivilitySig } from '../civility-sig.model';
import { CivilitySigService } from '../service/civility-sig.service';

@Component({
  selector: 'sigma-civility-sig-update',
  templateUrl: './civility-sig-update.component.html',
})
export class CivilitySigUpdateComponent implements OnInit {
  isSaving = false;
  civility: ICivilitySig | null = null;

  editForm: CivilitySigFormGroup = this.civilityFormService.createCivilitySigFormGroup();

  constructor(
    protected civilityService: CivilitySigService,
    protected civilityFormService: CivilitySigFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ civility }) => {
      this.civility = civility;
      if (civility) {
        this.updateForm(civility);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const civility = this.civilityFormService.getCivilitySig(this.editForm);
    if (civility.civilityId !== null) {
      this.subscribeToSaveResponse(this.civilityService.update(civility));
    } else {
      this.subscribeToSaveResponse(this.civilityService.create(civility));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICivilitySig>>): void {
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

  protected updateForm(civility: ICivilitySig): void {
    this.civility = civility;
    this.civilityFormService.resetForm(this.editForm, civility);
  }
}
