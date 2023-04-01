import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICloningSig } from '../cloning-sig.model';

@Component({
  selector: 'sigma-cloning-sig-detail',
  templateUrl: './cloning-sig-detail.component.html',
})
export class CloningSigDetailComponent implements OnInit {
  cloning: ICloningSig | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cloning }) => {
      this.cloning = cloning;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
