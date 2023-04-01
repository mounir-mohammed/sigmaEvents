import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { OperationTypeSigComponent } from './list/operation-type-sig.component';
import { OperationTypeSigDetailComponent } from './detail/operation-type-sig-detail.component';
import { OperationTypeSigUpdateComponent } from './update/operation-type-sig-update.component';
import { OperationTypeSigDeleteDialogComponent } from './delete/operation-type-sig-delete-dialog.component';
import { OperationTypeSigRoutingModule } from './route/operation-type-sig-routing.module';

@NgModule({
  imports: [SharedModule, OperationTypeSigRoutingModule],
  declarations: [
    OperationTypeSigComponent,
    OperationTypeSigDetailComponent,
    OperationTypeSigUpdateComponent,
    OperationTypeSigDeleteDialogComponent,
  ],
})
export class OperationTypeSigModule {}
