import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDayPassInfoSig } from '../day-pass-info-sig.model';
import { DayPassInfoSigService } from '../service/day-pass-info-sig.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './day-pass-info-sig-delete-dialog.component.html',
})
export class DayPassInfoSigDeleteDialogComponent {
  dayPassInfo?: IDayPassInfoSig;

  constructor(protected dayPassInfoService: DayPassInfoSigService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dayPassInfoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
