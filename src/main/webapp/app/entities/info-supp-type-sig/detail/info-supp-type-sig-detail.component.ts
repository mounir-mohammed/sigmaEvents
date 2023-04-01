import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInfoSuppTypeSig } from '../info-supp-type-sig.model';

@Component({
  selector: 'sigma-info-supp-type-sig-detail',
  templateUrl: './info-supp-type-sig-detail.component.html',
})
export class InfoSuppTypeSigDetailComponent implements OnInit {
  infoSuppType: IInfoSuppTypeSig | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ infoSuppType }) => {
      this.infoSuppType = infoSuppType;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
