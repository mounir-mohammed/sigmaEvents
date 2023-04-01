import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICitySig } from '../city-sig.model';

@Component({
  selector: 'sigma-city-sig-detail',
  templateUrl: './city-sig-detail.component.html',
})
export class CitySigDetailComponent implements OnInit {
  city: ICitySig | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ city }) => {
      this.city = city;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
