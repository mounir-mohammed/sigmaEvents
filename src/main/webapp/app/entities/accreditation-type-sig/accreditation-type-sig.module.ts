import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AccreditationTypeSigComponent } from './list/accreditation-type-sig.component';
import { AccreditationTypeSigDetailComponent } from './detail/accreditation-type-sig-detail.component';
import { AccreditationTypeSigUpdateComponent } from './update/accreditation-type-sig-update.component';
import { AccreditationTypeSigDeleteDialogComponent } from './delete/accreditation-type-sig-delete-dialog.component';
import { AccreditationTypeSigRoutingModule } from './route/accreditation-type-sig-routing.module';

@NgModule({
  imports: [SharedModule, AccreditationTypeSigRoutingModule],
  declarations: [
    AccreditationTypeSigComponent,
    AccreditationTypeSigDetailComponent,
    AccreditationTypeSigUpdateComponent,
    AccreditationTypeSigDeleteDialogComponent,
  ],
})
export class AccreditationTypeSigModule {}
