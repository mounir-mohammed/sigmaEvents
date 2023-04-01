import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPrintingTypeSig } from '../printing-type-sig.model';

@Component({
  selector: 'sigma-printing-type-sig-detail',
  templateUrl: './printing-type-sig-detail.component.html',
})
export class PrintingTypeSigDetailComponent implements OnInit {
  printingType: IPrintingTypeSig | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ printingType }) => {
      this.printingType = printingType;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
