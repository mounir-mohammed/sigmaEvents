import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { NoteSigComponent } from './list/note-sig.component';
import { NoteSigDetailComponent } from './detail/note-sig-detail.component';
import { NoteSigUpdateComponent } from './update/note-sig-update.component';
import { NoteSigDeleteDialogComponent } from './delete/note-sig-delete-dialog.component';
import { NoteSigRoutingModule } from './route/note-sig-routing.module';

@NgModule({
  imports: [SharedModule, NoteSigRoutingModule],
  declarations: [NoteSigComponent, NoteSigDetailComponent, NoteSigUpdateComponent, NoteSigDeleteDialogComponent],
})
export class NoteSigModule {}
