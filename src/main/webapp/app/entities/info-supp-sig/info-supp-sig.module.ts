import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { InfoSuppSigComponent } from './list/info-supp-sig.component';
import { InfoSuppSigDetailComponent } from './detail/info-supp-sig-detail.component';
import { InfoSuppSigUpdateComponent } from './update/info-supp-sig-update.component';
import { InfoSuppSigDeleteDialogComponent } from './delete/info-supp-sig-delete-dialog.component';
import { InfoSuppSigRoutingModule } from './route/info-supp-sig-routing.module';

@NgModule({
  imports: [SharedModule, InfoSuppSigRoutingModule],
  declarations: [InfoSuppSigComponent, InfoSuppSigDetailComponent, InfoSuppSigUpdateComponent, InfoSuppSigDeleteDialogComponent],
})
export class InfoSuppSigModule {}
