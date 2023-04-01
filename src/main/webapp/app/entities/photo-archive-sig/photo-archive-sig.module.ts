import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PhotoArchiveSigComponent } from './list/photo-archive-sig.component';
import { PhotoArchiveSigDetailComponent } from './detail/photo-archive-sig-detail.component';
import { PhotoArchiveSigUpdateComponent } from './update/photo-archive-sig-update.component';
import { PhotoArchiveSigDeleteDialogComponent } from './delete/photo-archive-sig-delete-dialog.component';
import { PhotoArchiveSigRoutingModule } from './route/photo-archive-sig-routing.module';

@NgModule({
  imports: [SharedModule, PhotoArchiveSigRoutingModule],
  declarations: [
    PhotoArchiveSigComponent,
    PhotoArchiveSigDetailComponent,
    PhotoArchiveSigUpdateComponent,
    PhotoArchiveSigDeleteDialogComponent,
  ],
})
export class PhotoArchiveSigModule {}
