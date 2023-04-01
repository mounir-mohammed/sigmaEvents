import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAttachementTypeSig } from '../attachement-type-sig.model';
import { AttachementTypeSigService } from '../service/attachement-type-sig.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './attachement-type-sig-delete-dialog.component.html',
})
export class AttachementTypeSigDeleteDialogComponent {
  attachementType?: IAttachementTypeSig;

  constructor(protected attachementTypeService: AttachementTypeSigService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.attachementTypeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
