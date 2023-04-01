import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPhotoArchiveSig } from '../photo-archive-sig.model';
import { PhotoArchiveSigService } from '../service/photo-archive-sig.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './photo-archive-sig-delete-dialog.component.html',
})
export class PhotoArchiveSigDeleteDialogComponent {
  photoArchive?: IPhotoArchiveSig;

  constructor(protected photoArchiveService: PhotoArchiveSigService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.photoArchiveService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
