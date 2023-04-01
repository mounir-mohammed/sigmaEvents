import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOperationTypeSig } from '../operation-type-sig.model';

@Component({
  selector: 'sigma-operation-type-sig-detail',
  templateUrl: './operation-type-sig-detail.component.html',
})
export class OperationTypeSigDetailComponent implements OnInit {
  operationType: IOperationTypeSig | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ operationType }) => {
      this.operationType = operationType;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
