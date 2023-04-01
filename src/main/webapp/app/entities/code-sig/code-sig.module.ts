import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CodeSigComponent } from './list/code-sig.component';
import { CodeSigDetailComponent } from './detail/code-sig-detail.component';
import { CodeSigUpdateComponent } from './update/code-sig-update.component';
import { CodeSigDeleteDialogComponent } from './delete/code-sig-delete-dialog.component';
import { CodeSigRoutingModule } from './route/code-sig-routing.module';

@NgModule({
  imports: [SharedModule, CodeSigRoutingModule],
  declarations: [CodeSigComponent, CodeSigDetailComponent, CodeSigUpdateComponent, CodeSigDeleteDialogComponent],
})
export class CodeSigModule {}
