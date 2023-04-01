import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PrintingModelSigComponent } from './list/printing-model-sig.component';
import { PrintingModelSigDetailComponent } from './detail/printing-model-sig-detail.component';
import { PrintingModelSigUpdateComponent } from './update/printing-model-sig-update.component';
import { PrintingModelSigDeleteDialogComponent } from './delete/printing-model-sig-delete-dialog.component';
import { PrintingModelSigRoutingModule } from './route/printing-model-sig-routing.module';

@NgModule({
  imports: [SharedModule, PrintingModelSigRoutingModule],
  declarations: [
    PrintingModelSigComponent,
    PrintingModelSigDetailComponent,
    PrintingModelSigUpdateComponent,
    PrintingModelSigDeleteDialogComponent,
  ],
})
export class PrintingModelSigModule {}
