import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IInfoSuppTypeSig } from '../info-supp-type-sig.model';
import { InfoSuppTypeSigService } from '../service/info-supp-type-sig.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './info-supp-type-sig-delete-dialog.component.html',
})
export class InfoSuppTypeSigDeleteDialogComponent {
  infoSuppType?: IInfoSuppTypeSig;

  constructor(protected infoSuppTypeService: InfoSuppTypeSigService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.infoSuppTypeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
