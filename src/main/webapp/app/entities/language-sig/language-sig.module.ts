import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { LanguageSigComponent } from './list/language-sig.component';
import { LanguageSigDetailComponent } from './detail/language-sig-detail.component';
import { LanguageSigUpdateComponent } from './update/language-sig-update.component';
import { LanguageSigDeleteDialogComponent } from './delete/language-sig-delete-dialog.component';
import { LanguageSigRoutingModule } from './route/language-sig-routing.module';

@NgModule({
  imports: [SharedModule, LanguageSigRoutingModule],
  declarations: [LanguageSigComponent, LanguageSigDetailComponent, LanguageSigUpdateComponent, LanguageSigDeleteDialogComponent],
})
export class LanguageSigModule {}
