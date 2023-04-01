import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEventControlSig } from '../event-control-sig.model';
import { EventControlSigService } from '../service/event-control-sig.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './event-control-sig-delete-dialog.component.html',
})
export class EventControlSigDeleteDialogComponent {
  eventControl?: IEventControlSig;

  constructor(protected eventControlService: EventControlSigService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.eventControlService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
