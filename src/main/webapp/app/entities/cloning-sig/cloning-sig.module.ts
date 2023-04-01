import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CloningSigComponent } from './list/cloning-sig.component';
import { CloningSigDetailComponent } from './detail/cloning-sig-detail.component';
import { CloningSigUpdateComponent } from './update/cloning-sig-update.component';
import { CloningSigDeleteDialogComponent } from './delete/cloning-sig-delete-dialog.component';
import { CloningSigRoutingModule } from './route/cloning-sig-routing.module';

@NgModule({
  imports: [SharedModule, CloningSigRoutingModule],
  declarations: [CloningSigComponent, CloningSigDetailComponent, CloningSigUpdateComponent, CloningSigDeleteDialogComponent],
})
export class CloningSigModule {}
