import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICheckAccreditationReportSig } from '../check-accreditation-report-sig.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'sigma-check-accreditation-report-sig-detail',
  templateUrl: './check-accreditation-report-sig-detail.component.html',
})
export class CheckAccreditationReportSigDetailComponent implements OnInit {
  checkAccreditationReport: ICheckAccreditationReportSig | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ checkAccreditationReport }) => {
      this.checkAccreditationReport = checkAccreditationReport;
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
