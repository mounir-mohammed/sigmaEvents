import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPrintingTypeSig } from '../printing-type-sig.model';
import { PrintingTypeSigService } from '../service/printing-type-sig.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './printing-type-sig-delete-dialog.component.html',
})
export class PrintingTypeSigDeleteDialogComponent {
  printingType?: IPrintingTypeSig;

  constructor(protected printingTypeService: PrintingTypeSigService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.printingTypeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
