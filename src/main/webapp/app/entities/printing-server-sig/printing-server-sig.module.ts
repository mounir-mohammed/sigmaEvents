import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PrintingServerSigComponent } from './list/printing-server-sig.component';
import { PrintingServerSigDetailComponent } from './detail/printing-server-sig-detail.component';
import { PrintingServerSigUpdateComponent } from './update/printing-server-sig-update.component';
import { PrintingServerSigDeleteDialogComponent } from './delete/printing-server-sig-delete-dialog.component';
import { PrintingServerSigRoutingModule } from './route/printing-server-sig-routing.module';

@NgModule({
  imports: [SharedModule, PrintingServerSigRoutingModule],
  declarations: [
    PrintingServerSigComponent,
    PrintingServerSigDetailComponent,
    PrintingServerSigUpdateComponent,
    PrintingServerSigDeleteDialogComponent,
  ],
})
export class PrintingServerSigModule {}
