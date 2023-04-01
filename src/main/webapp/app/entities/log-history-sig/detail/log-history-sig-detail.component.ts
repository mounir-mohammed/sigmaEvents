import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILogHistorySig } from '../log-history-sig.model';

@Component({
  selector: 'sigma-log-history-sig-detail',
  templateUrl: './log-history-sig-detail.component.html',
})
export class LogHistorySigDetailComponent implements OnInit {
  logHistory: ILogHistorySig | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ logHistory }) => {
      this.logHistory = logHistory;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
