import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEventFieldSig } from '../event-field-sig.model';
import { EventFieldSigService } from '../service/event-field-sig.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './event-field-sig-delete-dialog.component.html',
})
export class EventFieldSigDeleteDialogComponent {
  eventField?: IEventFieldSig;

  constructor(protected eventFieldService: EventFieldSigService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.eventFieldService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
