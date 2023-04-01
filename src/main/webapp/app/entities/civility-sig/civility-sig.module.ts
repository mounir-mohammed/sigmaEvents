import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CivilitySigComponent } from './list/civility-sig.component';
import { CivilitySigDetailComponent } from './detail/civility-sig-detail.component';
import { CivilitySigUpdateComponent } from './update/civility-sig-update.component';
import { CivilitySigDeleteDialogComponent } from './delete/civility-sig-delete-dialog.component';
import { CivilitySigRoutingModule } from './route/civility-sig-routing.module';

@NgModule({
  imports: [SharedModule, CivilitySigRoutingModule],
  declarations: [CivilitySigComponent, CivilitySigDetailComponent, CivilitySigUpdateComponent, CivilitySigDeleteDialogComponent],
})
export class CivilitySigModule {}
