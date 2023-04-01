import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CheckAccreditationHistorySigComponent } from './list/check-accreditation-history-sig.component';
import { CheckAccreditationHistorySigDetailComponent } from './detail/check-accreditation-history-sig-detail.component';
import { CheckAccreditationHistorySigUpdateComponent } from './update/check-accreditation-history-sig-update.component';
import { CheckAccreditationHistorySigDeleteDialogComponent } from './delete/check-accreditation-history-sig-delete-dialog.component';
import { CheckAccreditationHistorySigRoutingModule } from './route/check-accreditation-history-sig-routing.module';

@NgModule({
  imports: [SharedModule, CheckAccreditationHistorySigRoutingModule],
  declarations: [
    CheckAccreditationHistorySigComponent,
    CheckAccreditationHistorySigDetailComponent,
    CheckAccreditationHistorySigUpdateComponent,
    CheckAccreditationHistorySigDeleteDialogComponent,
  ],
})
export class CheckAccreditationHistorySigModule {}
