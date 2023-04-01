import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEventFormSig } from '../event-form-sig.model';

@Component({
  selector: 'sigma-event-form-sig-detail',
  templateUrl: './event-form-sig-detail.component.html',
})
export class EventFormSigDetailComponent implements OnInit {
  eventForm: IEventFormSig | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eventForm }) => {
      this.eventForm = eventForm;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
