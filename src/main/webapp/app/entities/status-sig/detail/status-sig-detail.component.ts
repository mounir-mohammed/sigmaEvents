import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStatusSig } from '../status-sig.model';

@Component({
  selector: 'sigma-status-sig-detail',
  templateUrl: './status-sig-detail.component.html',
})
export class StatusSigDetailComponent implements OnInit {
  status: IStatusSig | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ status }) => {
      this.status = status;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
