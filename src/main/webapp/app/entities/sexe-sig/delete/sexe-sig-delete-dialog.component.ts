import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISexeSig } from '../sexe-sig.model';
import { SexeSigService } from '../service/sexe-sig.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './sexe-sig-delete-dialog.component.html',
})
export class SexeSigDeleteDialogComponent {
  sexe?: ISexeSig;

  constructor(protected sexeService: SexeSigService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sexeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
