import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SexeSigComponent } from './list/sexe-sig.component';
import { SexeSigDetailComponent } from './detail/sexe-sig-detail.component';
import { SexeSigUpdateComponent } from './update/sexe-sig-update.component';
import { SexeSigDeleteDialogComponent } from './delete/sexe-sig-delete-dialog.component';
import { SexeSigRoutingModule } from './route/sexe-sig-routing.module';

@NgModule({
  imports: [SharedModule, SexeSigRoutingModule],
  declarations: [SexeSigComponent, SexeSigDetailComponent, SexeSigUpdateComponent, SexeSigDeleteDialogComponent],
})
export class SexeSigModule {}
