import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICheckAccreditationHistorySig } from '../check-accreditation-history-sig.model';
import { CheckAccreditationHistorySigService } from '../service/check-accreditation-history-sig.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './check-accreditation-history-sig-delete-dialog.component.html',
})
export class CheckAccreditationHistorySigDeleteDialogComponent {
  checkAccreditationHistory?: ICheckAccreditationHistorySig;

  constructor(protected checkAccreditationHistoryService: CheckAccreditationHistorySigService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.checkAccreditationHistoryService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
