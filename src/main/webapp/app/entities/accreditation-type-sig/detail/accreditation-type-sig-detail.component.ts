import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAccreditationTypeSig } from '../accreditation-type-sig.model';

@Component({
  selector: 'sigma-accreditation-type-sig-detail',
  templateUrl: './accreditation-type-sig-detail.component.html',
})
export class AccreditationTypeSigDetailComponent implements OnInit {
  accreditationType: IAccreditationTypeSig | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ accreditationType }) => {
      this.accreditationType = accreditationType;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
