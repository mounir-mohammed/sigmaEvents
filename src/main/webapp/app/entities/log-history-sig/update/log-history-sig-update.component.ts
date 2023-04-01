import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { LogHistorySigFormService, LogHistorySigFormGroup } from './log-history-sig-form.service';
import { ILogHistorySig } from '../log-history-sig.model';
import { LogHistorySigService } from '../service/log-history-sig.service';

@Component({
  selector: 'sigma-log-history-sig-update',
  templateUrl: './log-history-sig-update.component.html',
})
export class LogHistorySigUpdateComponent implements OnInit {
  isSaving = false;
  logHistory: ILogHistorySig | null = null;

  editForm: LogHistorySigFormGroup = this.logHistoryFormService.createLogHistorySigFormGroup();

  constructor(
    protected logHistoryService: LogHistorySigService,
    protected logHistoryFormService: LogHistorySigFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ logHistory }) => {
      this.logHistory = logHistory;
      if (logHistory) {
        this.updateForm(logHistory);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const logHistory = this.logHistoryFormService.getLogHistorySig(this.editForm);
    if (logHistory.logHistory !== null) {
      this.subscribeToSaveResponse(this.logHistoryService.update(logHistory));
    } else {
      this.subscribeToSaveResponse(this.logHistoryService.create(logHistory));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILogHistorySig>>): void {
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

  protected updateForm(logHistory: ILogHistorySig): void {
    this.logHistory = logHistory;
    this.logHistoryFormService.resetForm(this.editForm, logHistory);
  }
}
