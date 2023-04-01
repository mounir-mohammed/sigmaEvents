import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IOrganizSig } from '../organiz-sig.model';
import { OrganizSigService } from '../service/organiz-sig.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './organiz-sig-delete-dialog.component.html',
})
export class OrganizSigDeleteDialogComponent {
  organiz?: IOrganizSig;

  constructor(protected organizService: OrganizSigService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.organizService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
