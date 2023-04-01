import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEventFieldSig } from '../event-field-sig.model';

@Component({
  selector: 'sigma-event-field-sig-detail',
  templateUrl: './event-field-sig-detail.component.html',
})
export class EventFieldSigDetailComponent implements OnInit {
  eventField: IEventFieldSig | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eventField }) => {
      this.eventField = eventField;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
