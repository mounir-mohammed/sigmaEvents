import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ILogHistorySig } from '../log-history-sig.model';
import { LogHistorySigService } from '../service/log-history-sig.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './log-history-sig-delete-dialog.component.html',
})
export class LogHistorySigDeleteDialogComponent {
  logHistory?: ILogHistorySig;

  constructor(protected logHistoryService: LogHistorySigService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.logHistoryService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
