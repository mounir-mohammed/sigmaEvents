import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SiteSigComponent } from './list/site-sig.component';
import { SiteSigDetailComponent } from './detail/site-sig-detail.component';
import { SiteSigUpdateComponent } from './update/site-sig-update.component';
import { SiteSigDeleteDialogComponent } from './delete/site-sig-delete-dialog.component';
import { SiteSigRoutingModule } from './route/site-sig-routing.module';

@NgModule({
  imports: [SharedModule, SiteSigRoutingModule],
  declarations: [SiteSigComponent, SiteSigDetailComponent, SiteSigUpdateComponent, SiteSigDeleteDialogComponent],
})
export class SiteSigModule {}
