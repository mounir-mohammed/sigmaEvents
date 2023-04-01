import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { InfoSuppTypeSigComponent } from './list/info-supp-type-sig.component';
import { InfoSuppTypeSigDetailComponent } from './detail/info-supp-type-sig-detail.component';
import { InfoSuppTypeSigUpdateComponent } from './update/info-supp-type-sig-update.component';
import { InfoSuppTypeSigDeleteDialogComponent } from './delete/info-supp-type-sig-delete-dialog.component';
import { InfoSuppTypeSigRoutingModule } from './route/info-supp-type-sig-routing.module';

@NgModule({
  imports: [SharedModule, InfoSuppTypeSigRoutingModule],
  declarations: [
    InfoSuppTypeSigComponent,
    InfoSuppTypeSigDetailComponent,
    InfoSuppTypeSigUpdateComponent,
    InfoSuppTypeSigDeleteDialogComponent,
  ],
})
export class InfoSuppTypeSigModule {}
