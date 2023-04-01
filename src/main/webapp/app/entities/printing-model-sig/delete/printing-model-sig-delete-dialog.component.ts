import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPrintingModelSig } from '../printing-model-sig.model';
import { PrintingModelSigService } from '../service/printing-model-sig.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './printing-model-sig-delete-dialog.component.html',
})
export class PrintingModelSigDeleteDialogComponent {
  printingModel?: IPrintingModelSig;

  constructor(protected printingModelService: PrintingModelSigService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.printingModelService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
