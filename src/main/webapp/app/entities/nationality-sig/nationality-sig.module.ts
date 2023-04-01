import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { NationalitySigComponent } from './list/nationality-sig.component';
import { NationalitySigDetailComponent } from './detail/nationality-sig-detail.component';
import { NationalitySigUpdateComponent } from './update/nationality-sig-update.component';
import { NationalitySigDeleteDialogComponent } from './delete/nationality-sig-delete-dialog.component';
import { NationalitySigRoutingModule } from './route/nationality-sig-routing.module';

@NgModule({
  imports: [SharedModule, NationalitySigRoutingModule],
  declarations: [
    NationalitySigComponent,
    NationalitySigDetailComponent,
    NationalitySigUpdateComponent,
    NationalitySigDeleteDialogComponent,
  ],
})
export class NationalitySigModule {}
