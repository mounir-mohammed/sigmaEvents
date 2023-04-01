import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IOperationTypeSig } from '../operation-type-sig.model';
import { OperationTypeSigService } from '../service/operation-type-sig.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './operation-type-sig-delete-dialog.component.html',
})
export class OperationTypeSigDeleteDialogComponent {
  operationType?: IOperationTypeSig;

  constructor(protected operationTypeService: OperationTypeSigService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.operationTypeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
