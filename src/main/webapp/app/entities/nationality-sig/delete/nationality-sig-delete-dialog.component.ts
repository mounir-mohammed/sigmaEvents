import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { INationalitySig } from '../nationality-sig.model';
import { NationalitySigService } from '../service/nationality-sig.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './nationality-sig-delete-dialog.component.html',
})
export class NationalitySigDeleteDialogComponent {
  nationality?: INationalitySig;

  constructor(protected nationalityService: NationalitySigService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.nationalityService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
