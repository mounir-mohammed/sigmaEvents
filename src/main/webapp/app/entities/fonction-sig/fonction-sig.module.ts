import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FonctionSigComponent } from './list/fonction-sig.component';
import { FonctionSigDetailComponent } from './detail/fonction-sig-detail.component';
import { FonctionSigUpdateComponent } from './update/fonction-sig-update.component';
import { FonctionSigDeleteDialogComponent } from './delete/fonction-sig-delete-dialog.component';
import { FonctionSigRoutingModule } from './route/fonction-sig-routing.module';
import { ColorPickerModule } from 'ngx-color-picker';

@NgModule({
  imports: [SharedModule, FonctionSigRoutingModule, ColorPickerModule],
  declarations: [FonctionSigComponent, FonctionSigDetailComponent, FonctionSigUpdateComponent, FonctionSigDeleteDialogComponent],
})
export class FonctionSigModule {}
