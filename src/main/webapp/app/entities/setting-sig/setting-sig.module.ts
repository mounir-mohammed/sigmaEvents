import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SettingSigComponent } from './list/setting-sig.component';
import { SettingSigDetailComponent } from './detail/setting-sig-detail.component';
import { SettingSigUpdateComponent } from './update/setting-sig-update.component';
import { SettingSigDeleteDialogComponent } from './delete/setting-sig-delete-dialog.component';
import { SettingSigRoutingModule } from './route/setting-sig-routing.module';

@NgModule({
  imports: [SharedModule, SettingSigRoutingModule],
  declarations: [SettingSigComponent, SettingSigDetailComponent, SettingSigUpdateComponent, SettingSigDeleteDialogComponent],
})
export class SettingSigModule {}
