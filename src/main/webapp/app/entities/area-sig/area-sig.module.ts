import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AreaSigComponent } from './list/area-sig.component';
import { AreaSigDetailComponent } from './detail/area-sig-detail.component';
import { AreaSigUpdateComponent } from './update/area-sig-update.component';
import { AreaSigDeleteDialogComponent } from './delete/area-sig-delete-dialog.component';
import { AreaSigRoutingModule } from './route/area-sig-routing.module';

@NgModule({
  imports: [SharedModule, AreaSigRoutingModule],
  declarations: [AreaSigComponent, AreaSigDetailComponent, AreaSigUpdateComponent, AreaSigDeleteDialogComponent],
})
export class AreaSigModule {}
