import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICloningSig } from '../cloning-sig.model';
import { CloningSigService } from '../service/cloning-sig.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './cloning-sig-delete-dialog.component.html',
})
export class CloningSigDeleteDialogComponent {
  cloning?: ICloningSig;

  constructor(protected cloningService: CloningSigService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cloningService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
