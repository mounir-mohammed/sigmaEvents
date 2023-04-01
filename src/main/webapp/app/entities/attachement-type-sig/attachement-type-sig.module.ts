import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AttachementTypeSigComponent } from './list/attachement-type-sig.component';
import { AttachementTypeSigDetailComponent } from './detail/attachement-type-sig-detail.component';
import { AttachementTypeSigUpdateComponent } from './update/attachement-type-sig-update.component';
import { AttachementTypeSigDeleteDialogComponent } from './delete/attachement-type-sig-delete-dialog.component';
import { AttachementTypeSigRoutingModule } from './route/attachement-type-sig-routing.module';

@NgModule({
  imports: [SharedModule, AttachementTypeSigRoutingModule],
  declarations: [
    AttachementTypeSigComponent,
    AttachementTypeSigDetailComponent,
    AttachementTypeSigUpdateComponent,
    AttachementTypeSigDeleteDialogComponent,
  ],
})
export class AttachementTypeSigModule {}
