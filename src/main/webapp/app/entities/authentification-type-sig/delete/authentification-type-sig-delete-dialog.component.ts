import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAuthentificationTypeSig } from '../authentification-type-sig.model';
import { AuthentificationTypeSigService } from '../service/authentification-type-sig.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './authentification-type-sig-delete-dialog.component.html',
})
export class AuthentificationTypeSigDeleteDialogComponent {
  authentificationType?: IAuthentificationTypeSig;

  constructor(protected authentificationTypeService: AuthentificationTypeSigService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.authentificationTypeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
