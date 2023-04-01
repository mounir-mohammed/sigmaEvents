import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CitySigComponent } from './list/city-sig.component';
import { CitySigDetailComponent } from './detail/city-sig-detail.component';
import { CitySigUpdateComponent } from './update/city-sig-update.component';
import { CitySigDeleteDialogComponent } from './delete/city-sig-delete-dialog.component';
import { CitySigRoutingModule } from './route/city-sig-routing.module';

@NgModule({
  imports: [SharedModule, CitySigRoutingModule],
  declarations: [CitySigComponent, CitySigDetailComponent, CitySigUpdateComponent, CitySigDeleteDialogComponent],
})
export class CitySigModule {}
