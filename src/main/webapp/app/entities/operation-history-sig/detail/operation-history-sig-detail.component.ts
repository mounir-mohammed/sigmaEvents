import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOperationHistorySig } from '../operation-history-sig.model';

@Component({
  selector: 'sigma-operation-history-sig-detail',
  templateUrl: './operation-history-sig-detail.component.html',
})
export class OperationHistorySigDetailComponent implements OnInit {
  operationHistory: IOperationHistorySig | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ operationHistory }) => {
      this.operationHistory = operationHistory;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
