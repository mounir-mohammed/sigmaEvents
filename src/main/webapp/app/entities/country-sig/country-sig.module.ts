import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CountrySigComponent } from './list/country-sig.component';
import { CountrySigDetailComponent } from './detail/country-sig-detail.component';
import { CountrySigUpdateComponent } from './update/country-sig-update.component';
import { CountrySigDeleteDialogComponent } from './delete/country-sig-delete-dialog.component';
import { CountrySigRoutingModule } from './route/country-sig-routing.module';

@NgModule({
  imports: [SharedModule, CountrySigRoutingModule],
  declarations: [CountrySigComponent, CountrySigDetailComponent, CountrySigUpdateComponent, CountrySigDeleteDialogComponent],
})
export class CountrySigModule {}
