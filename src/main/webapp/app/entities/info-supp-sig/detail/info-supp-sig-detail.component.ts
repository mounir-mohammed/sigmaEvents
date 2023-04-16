import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInfoSuppSig } from '../info-supp-sig.model';
import { Authority } from 'app/config/authority.constants';

@Component({
  selector: 'sigma-info-supp-sig-detail',
  templateUrl: './info-supp-sig-detail.component.html',
})
export class InfoSuppSigDetailComponent implements OnInit {
  infoSupp: IInfoSuppSig | null = null;
  authority = Authority;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ infoSupp }) => {
      this.infoSupp = infoSupp;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
