import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AttachementSigComponent } from './list/attachement-sig.component';
import { AttachementSigDetailComponent } from './detail/attachement-sig-detail.component';
import { AttachementSigUpdateComponent } from './update/attachement-sig-update.component';
import { AttachementSigDeleteDialogComponent } from './delete/attachement-sig-delete-dialog.component';
import { AttachementSigRoutingModule } from './route/attachement-sig-routing.module';

@NgModule({
  imports: [SharedModule, AttachementSigRoutingModule],
  declarations: [
    AttachementSigComponent,
    AttachementSigDetailComponent,
    AttachementSigUpdateComponent,
    AttachementSigDeleteDialogComponent,
  ],
})
export class AttachementSigModule {}
