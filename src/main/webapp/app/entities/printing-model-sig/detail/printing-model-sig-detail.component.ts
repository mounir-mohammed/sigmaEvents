import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPrintingModelSig } from '../printing-model-sig.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'sigma-printing-model-sig-detail',
  templateUrl: './printing-model-sig-detail.component.html',
})
export class PrintingModelSigDetailComponent implements OnInit {
  printingModel: IPrintingModelSig | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ printingModel }) => {
      this.printingModel = printingModel;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}
