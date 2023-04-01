import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILanguageSig } from '../language-sig.model';

@Component({
  selector: 'sigma-language-sig-detail',
  templateUrl: './language-sig-detail.component.html',
})
export class LanguageSigDetailComponent implements OnInit {
  language: ILanguageSig | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ language }) => {
      this.language = language;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
