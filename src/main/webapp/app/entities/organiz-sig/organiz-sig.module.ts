import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { OrganizSigComponent } from './list/organiz-sig.component';
import { OrganizSigDetailComponent } from './detail/organiz-sig-detail.component';
import { OrganizSigUpdateComponent } from './update/organiz-sig-update.component';
import { OrganizSigDeleteDialogComponent } from './delete/organiz-sig-delete-dialog.component';
import { OrganizSigRoutingModule } from './route/organiz-sig-routing.module';

@NgModule({
  imports: [SharedModule, OrganizSigRoutingModule],
  declarations: [OrganizSigComponent, OrganizSigDetailComponent, OrganizSigUpdateComponent, OrganizSigDeleteDialogComponent],
})
export class OrganizSigModule {}
