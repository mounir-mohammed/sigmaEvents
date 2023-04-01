import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CodeTypeSigComponent } from './list/code-type-sig.component';
import { CodeTypeSigDetailComponent } from './detail/code-type-sig-detail.component';
import { CodeTypeSigUpdateComponent } from './update/code-type-sig-update.component';
import { CodeTypeSigDeleteDialogComponent } from './delete/code-type-sig-delete-dialog.component';
import { CodeTypeSigRoutingModule } from './route/code-type-sig-routing.module';

@NgModule({
  imports: [SharedModule, CodeTypeSigRoutingModule],
  declarations: [CodeTypeSigComponent, CodeTypeSigDetailComponent, CodeTypeSigUpdateComponent, CodeTypeSigDeleteDialogComponent],
})
export class CodeTypeSigModule {}
