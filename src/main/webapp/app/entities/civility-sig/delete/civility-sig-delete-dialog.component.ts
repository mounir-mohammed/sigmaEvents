import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICivilitySig } from '../civility-sig.model';
import { CivilitySigService } from '../service/civility-sig.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './civility-sig-delete-dialog.component.html',
})
export class CivilitySigDeleteDialogComponent {
  civility?: ICivilitySig;

  constructor(protected civilityService: CivilitySigService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.civilityService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
