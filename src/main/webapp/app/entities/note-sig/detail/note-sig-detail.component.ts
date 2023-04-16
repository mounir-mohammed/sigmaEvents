import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INoteSig } from '../note-sig.model';
import { Authority } from 'app/config/authority.constants';

@Component({
  selector: 'sigma-note-sig-detail',
  templateUrl: './note-sig-detail.component.html',
})
export class NoteSigDetailComponent implements OnInit {
  note: INoteSig | null = null;
  authority = Authority;
  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ note }) => {
      this.note = note;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
