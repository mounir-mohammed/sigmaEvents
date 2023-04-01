import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICodeSig } from '../code-sig.model';

@Component({
  selector: 'sigma-code-sig-detail',
  templateUrl: './code-sig-detail.component.html',
})
export class CodeSigDetailComponent implements OnInit {
  code: ICodeSig | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ code }) => {
      this.code = code;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
