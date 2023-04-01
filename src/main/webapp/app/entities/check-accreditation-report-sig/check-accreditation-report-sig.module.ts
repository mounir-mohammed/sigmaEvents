import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CheckAccreditationReportSigComponent } from './list/check-accreditation-report-sig.component';
import { CheckAccreditationReportSigDetailComponent } from './detail/check-accreditation-report-sig-detail.component';
import { CheckAccreditationReportSigUpdateComponent } from './update/check-accreditation-report-sig-update.component';
import { CheckAccreditationReportSigDeleteDialogComponent } from './delete/check-accreditation-report-sig-delete-dialog.component';
import { CheckAccreditationReportSigRoutingModule } from './route/check-accreditation-report-sig-routing.module';

@NgModule({
  imports: [SharedModule, CheckAccreditationReportSigRoutingModule],
  declarations: [
    CheckAccreditationReportSigComponent,
    CheckAccreditationReportSigDetailComponent,
    CheckAccreditationReportSigUpdateComponent,
    CheckAccreditationReportSigDeleteDialogComponent,
  ],
})
export class CheckAccreditationReportSigModule {}
