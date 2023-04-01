import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { LogHistorySigComponent } from './list/log-history-sig.component';
import { LogHistorySigDetailComponent } from './detail/log-history-sig-detail.component';
import { LogHistorySigUpdateComponent } from './update/log-history-sig-update.component';
import { LogHistorySigDeleteDialogComponent } from './delete/log-history-sig-delete-dialog.component';
import { LogHistorySigRoutingModule } from './route/log-history-sig-routing.module';

@NgModule({
  imports: [SharedModule, LogHistorySigRoutingModule],
  declarations: [LogHistorySigComponent, LogHistorySigDetailComponent, LogHistorySigUpdateComponent, LogHistorySigDeleteDialogComponent],
})
export class LogHistorySigModule {}
