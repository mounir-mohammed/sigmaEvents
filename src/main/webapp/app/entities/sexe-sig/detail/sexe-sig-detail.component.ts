import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISexeSig } from '../sexe-sig.model';

@Component({
  selector: 'sigma-sexe-sig-detail',
  templateUrl: './sexe-sig-detail.component.html',
})
export class SexeSigDetailComponent implements OnInit {
  sexe: ISexeSig | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sexe }) => {
      this.sexe = sexe;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
