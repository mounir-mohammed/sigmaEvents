import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IInfoSuppSig } from '../info-supp-sig.model';
import { InfoSuppSigService } from '../service/info-supp-sig.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './info-supp-sig-delete-dialog.component.html',
})
export class InfoSuppSigDeleteDialogComponent {
  infoSupp?: IInfoSuppSig;

  constructor(protected infoSuppService: InfoSuppSigService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.infoSuppService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
