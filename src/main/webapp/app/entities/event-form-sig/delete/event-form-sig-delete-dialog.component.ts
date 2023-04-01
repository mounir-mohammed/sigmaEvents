import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEventFormSig } from '../event-form-sig.model';
import { EventFormSigService } from '../service/event-form-sig.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './event-form-sig-delete-dialog.component.html',
})
export class EventFormSigDeleteDialogComponent {
  eventForm?: IEventFormSig;

  constructor(protected eventFormService: EventFormSigService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.eventFormService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
