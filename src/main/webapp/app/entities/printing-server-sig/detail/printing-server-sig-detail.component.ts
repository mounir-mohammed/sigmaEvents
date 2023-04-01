import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPrintingServerSig } from '../printing-server-sig.model';

@Component({
  selector: 'sigma-printing-server-sig-detail',
  templateUrl: './printing-server-sig-detail.component.html',
})
export class PrintingServerSigDetailComponent implements OnInit {
  printingServer: IPrintingServerSig | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ printingServer }) => {
      this.printingServer = printingServer;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
