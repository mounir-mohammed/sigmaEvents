import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { OperationHistorySigComponent } from './list/operation-history-sig.component';
import { OperationHistorySigDetailComponent } from './detail/operation-history-sig-detail.component';
import { OperationHistorySigUpdateComponent } from './update/operation-history-sig-update.component';
import { OperationHistorySigDeleteDialogComponent } from './delete/operation-history-sig-delete-dialog.component';
import { OperationHistorySigRoutingModule } from './route/operation-history-sig-routing.module';

@NgModule({
  imports: [SharedModule, OperationHistorySigRoutingModule],
  declarations: [
    OperationHistorySigComponent,
    OperationHistorySigDetailComponent,
    OperationHistorySigUpdateComponent,
    OperationHistorySigDeleteDialogComponent,
  ],
})
export class OperationHistorySigModule {}
