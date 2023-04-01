import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPrintingCentreSig } from '../printing-centre-sig.model';
import { PrintingCentreSigService } from '../service/printing-centre-sig.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './printing-centre-sig-delete-dialog.component.html',
})
export class PrintingCentreSigDeleteDialogComponent {
  printingCentre?: IPrintingCentreSig;

  constructor(protected printingCentreService: PrintingCentreSigService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.printingCentreService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
