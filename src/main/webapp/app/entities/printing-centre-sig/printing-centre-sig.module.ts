import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PrintingCentreSigComponent } from './list/printing-centre-sig.component';
import { PrintingCentreSigDetailComponent } from './detail/printing-centre-sig-detail.component';
import { PrintingCentreSigUpdateComponent } from './update/printing-centre-sig-update.component';
import { PrintingCentreSigDeleteDialogComponent } from './delete/printing-centre-sig-delete-dialog.component';
import { PrintingCentreSigRoutingModule } from './route/printing-centre-sig-routing.module';

@NgModule({
  imports: [SharedModule, PrintingCentreSigRoutingModule],
  declarations: [
    PrintingCentreSigComponent,
    PrintingCentreSigDetailComponent,
    PrintingCentreSigUpdateComponent,
    PrintingCentreSigDeleteDialogComponent,
  ],
})
export class PrintingCentreSigModule {}
