import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { firstValueFrom, Observable } from 'rxjs';
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

  async save(): Promise<void> {
    this.isSaving = true;
    const cloning = this.cloningFormService.getCloningSig(this.editForm);

    try {
      if (cloning.cloningId !== null) {
        // Regular synchronous update
        await firstValueFrom(this.cloningService.update(cloning));
        this.onSaveSuccess();
      } else {
        // Async create with job
        const jobResponse = (
          await firstValueFrom(
            this.cloningService.create(cloning as any) // NewCloningSig cast
          )
        ).body!;

        const jobId = jobResponse.jobId;
        if (!jobId) {
          throw new Error('Job ID not returned from server');
        }

        // Poll until job completes
        await this.pollJobStatus(jobId);
        this.onSaveSuccess();
      }
    } catch (error) {
      console.error('Save error', error);
      this.onSaveError();
    } finally {
      this.onSaveFinalize();
    }
  }

  private async pollJobStatus(jobId: string): Promise<void> {
    const intervalMs = 2000; // poll every 2 seconds
    let isCompleted = false;

    while (!isCompleted) {
      const statusResponse = await firstValueFrom(this.cloningService.getJobStatus(jobId));
      isCompleted = statusResponse.status === 'COMPLETED';

      if (!isCompleted) {
        await new Promise(resolve => setTimeout(resolve, intervalMs));
      }
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICloningSig>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    alert('Cloning process completed successfully!');
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
