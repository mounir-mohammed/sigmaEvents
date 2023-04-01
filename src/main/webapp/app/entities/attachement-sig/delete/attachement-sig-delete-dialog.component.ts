import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAttachementSig } from '../attachement-sig.model';
import { AttachementSigService } from '../service/attachement-sig.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './attachement-sig-delete-dialog.component.html',
})
export class AttachementSigDeleteDialogComponent {
  attachement?: IAttachementSig;

  constructor(protected attachementService: AttachementSigService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.attachementService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
