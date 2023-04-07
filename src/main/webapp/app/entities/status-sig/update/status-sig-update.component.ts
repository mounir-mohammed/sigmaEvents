import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { StatusSigFormService, StatusSigFormGroup } from './status-sig-form.service';
import { IStatusSig } from '../status-sig.model';
import { StatusSigService } from '../service/status-sig.service';

@Component({
  selector: 'sigma-status-sig-update',
  templateUrl: './status-sig-update.component.html',
})
export class StatusSigUpdateComponent implements OnInit {
  isSaving = false;
  status: IStatusSig | null = null;

  editForm: StatusSigFormGroup = this.statusFormService.createStatusSigFormGroup();
  public color = '#cccccc';

  constructor(
    protected statusService: StatusSigService,
    protected statusFormService: StatusSigFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ status }) => {
      this.status = status;
      if (status) {
        this.color = this.status?.statusColor!;
        this.updateForm(status);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const status = this.statusFormService.getStatusSig(this.editForm);
    if (status.statusId !== null) {
      this.subscribeToSaveResponse(this.statusService.update(status));
    } else {
      this.subscribeToSaveResponse(this.statusService.create(status));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStatusSig>>): void {
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

  protected updateForm(status: IStatusSig): void {
    this.status = status;
    this.statusFormService.resetForm(this.editForm, status);
  }

  public onChangeColor(color: string): void {
    this.color = color;
    this.editForm.patchValue({ statusColor: color });
  }
}
