import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AuthentificationTypeSigComponent } from './list/authentification-type-sig.component';
import { AuthentificationTypeSigDetailComponent } from './detail/authentification-type-sig-detail.component';
import { AuthentificationTypeSigUpdateComponent } from './update/authentification-type-sig-update.component';
import { AuthentificationTypeSigDeleteDialogComponent } from './delete/authentification-type-sig-delete-dialog.component';
import { AuthentificationTypeSigRoutingModule } from './route/authentification-type-sig-routing.module';

@NgModule({
  imports: [SharedModule, AuthentificationTypeSigRoutingModule],
  declarations: [
    AuthentificationTypeSigComponent,
    AuthentificationTypeSigDetailComponent,
    AuthentificationTypeSigUpdateComponent,
    AuthentificationTypeSigDeleteDialogComponent,
  ],
})
export class AuthentificationTypeSigModule {}
