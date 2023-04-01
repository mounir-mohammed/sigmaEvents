import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PrintingTypeSigComponent } from './list/printing-type-sig.component';
import { PrintingTypeSigDetailComponent } from './detail/printing-type-sig-detail.component';
import { PrintingTypeSigUpdateComponent } from './update/printing-type-sig-update.component';
import { PrintingTypeSigDeleteDialogComponent } from './delete/printing-type-sig-delete-dialog.component';
import { PrintingTypeSigRoutingModule } from './route/printing-type-sig-routing.module';

@NgModule({
  imports: [SharedModule, PrintingTypeSigRoutingModule],
  declarations: [
    PrintingTypeSigComponent,
    PrintingTypeSigDetailComponent,
    PrintingTypeSigUpdateComponent,
    PrintingTypeSigDeleteDialogComponent,
  ],
})
export class PrintingTypeSigModule {}
