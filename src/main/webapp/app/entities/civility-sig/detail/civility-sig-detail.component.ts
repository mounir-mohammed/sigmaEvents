import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICivilitySig } from '../civility-sig.model';

@Component({
  selector: 'sigma-civility-sig-detail',
  templateUrl: './civility-sig-detail.component.html',
})
export class CivilitySigDetailComponent implements OnInit {
  civility: ICivilitySig | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ civility }) => {
      this.civility = civility;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
