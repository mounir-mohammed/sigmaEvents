import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFonctionSig } from '../fonction-sig.model';
import { FonctionSigService } from '../service/fonction-sig.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './fonction-sig-delete-dialog.component.html',
})
export class FonctionSigDeleteDialogComponent {
  fonction?: IFonctionSig;

  constructor(protected fonctionService: FonctionSigService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fonctionService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
