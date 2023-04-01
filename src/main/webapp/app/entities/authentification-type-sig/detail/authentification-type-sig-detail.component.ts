import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAuthentificationTypeSig } from '../authentification-type-sig.model';

@Component({
  selector: 'sigma-authentification-type-sig-detail',
  templateUrl: './authentification-type-sig-detail.component.html',
})
export class AuthentificationTypeSigDetailComponent implements OnInit {
  authentificationType: IAuthentificationTypeSig | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ authentificationType }) => {
      this.authentificationType = authentificationType;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
