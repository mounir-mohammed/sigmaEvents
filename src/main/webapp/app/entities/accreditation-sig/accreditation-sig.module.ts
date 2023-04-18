import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AccreditationSigComponent } from './list/accreditation-sig.component';
import { AccreditationSigDetailComponent } from './detail/accreditation-sig-detail.component';
import { AccreditationSigUpdateComponent } from './update/accreditation-sig-update.component';
import { AccreditationSigDeleteDialogComponent } from './delete/accreditation-sig-delete-dialog.component';
import { AccreditationSigValidateDialogComponent } from './validate/accreditation-sig-validate-dialog.component';
import { AccreditationSigRoutingModule } from './route/accreditation-sig-routing.module';

@NgModule({
  imports: [SharedModule, AccreditationSigRoutingModule],
  declarations: [
    AccreditationSigComponent,
    AccreditationSigDetailComponent,
    AccreditationSigUpdateComponent,
    AccreditationSigDeleteDialogComponent,
    AccreditationSigValidateDialogComponent,
  ],
})
export class AccreditationSigModule {}
