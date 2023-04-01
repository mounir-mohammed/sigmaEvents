import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEventControlSig } from '../event-control-sig.model';

@Component({
  selector: 'sigma-event-control-sig-detail',
  templateUrl: './event-control-sig-detail.component.html',
})
export class EventControlSigDetailComponent implements OnInit {
  eventControl: IEventControlSig | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eventControl }) => {
      this.eventControl = eventControl;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
