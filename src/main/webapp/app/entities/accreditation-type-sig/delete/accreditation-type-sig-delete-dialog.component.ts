import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAccreditationTypeSig } from '../accreditation-type-sig.model';
import { AccreditationTypeSigService } from '../service/accreditation-type-sig.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './accreditation-type-sig-delete-dialog.component.html',
})
export class AccreditationTypeSigDeleteDialogComponent {
  accreditationType?: IAccreditationTypeSig;

  constructor(protected accreditationTypeService: AccreditationTypeSigService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.accreditationTypeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
