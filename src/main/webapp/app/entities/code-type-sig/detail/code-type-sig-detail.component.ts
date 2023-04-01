import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICodeTypeSig } from '../code-type-sig.model';

@Component({
  selector: 'sigma-code-type-sig-detail',
  templateUrl: './code-type-sig-detail.component.html',
})
export class CodeTypeSigDetailComponent implements OnInit {
  codeType: ICodeTypeSig | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ codeType }) => {
      this.codeType = codeType;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
