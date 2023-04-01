import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IOperationHistorySig } from '../operation-history-sig.model';
import { OperationHistorySigService } from '../service/operation-history-sig.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './operation-history-sig-delete-dialog.component.html',
})
export class OperationHistorySigDeleteDialogComponent {
  operationHistory?: IOperationHistorySig;

  constructor(protected operationHistoryService: OperationHistorySigService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.operationHistoryService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
