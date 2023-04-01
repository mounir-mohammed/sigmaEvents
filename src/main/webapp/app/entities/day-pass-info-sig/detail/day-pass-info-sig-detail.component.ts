import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDayPassInfoSig } from '../day-pass-info-sig.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'sigma-day-pass-info-sig-detail',
  templateUrl: './day-pass-info-sig-detail.component.html',
})
export class DayPassInfoSigDetailComponent implements OnInit {
  dayPassInfo: IDayPassInfoSig | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dayPassInfo }) => {
      this.dayPassInfo = dayPassInfo;
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
