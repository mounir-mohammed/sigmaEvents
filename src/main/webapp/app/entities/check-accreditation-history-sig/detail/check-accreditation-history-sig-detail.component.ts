import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICheckAccreditationHistorySig } from '../check-accreditation-history-sig.model';

@Component({
  selector: 'sigma-check-accreditation-history-sig-detail',
  templateUrl: './check-accreditation-history-sig-detail.component.html',
})
export class CheckAccreditationHistorySigDetailComponent implements OnInit {
  checkAccreditationHistory: ICheckAccreditationHistorySig | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ checkAccreditationHistory }) => {
      this.checkAccreditationHistory = checkAccreditationHistory;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
