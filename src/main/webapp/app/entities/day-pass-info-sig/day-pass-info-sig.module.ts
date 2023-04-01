import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DayPassInfoSigComponent } from './list/day-pass-info-sig.component';
import { DayPassInfoSigDetailComponent } from './detail/day-pass-info-sig-detail.component';
import { DayPassInfoSigUpdateComponent } from './update/day-pass-info-sig-update.component';
import { DayPassInfoSigDeleteDialogComponent } from './delete/day-pass-info-sig-delete-dialog.component';
import { DayPassInfoSigRoutingModule } from './route/day-pass-info-sig-routing.module';

@NgModule({
  imports: [SharedModule, DayPassInfoSigRoutingModule],
  declarations: [
    DayPassInfoSigComponent,
    DayPassInfoSigDetailComponent,
    DayPassInfoSigUpdateComponent,
    DayPassInfoSigDeleteDialogComponent,
  ],
})
export class DayPassInfoSigModule {}
