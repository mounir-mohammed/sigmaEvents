import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPrintingServerSig } from '../printing-server-sig.model';
import { PrintingServerSigService } from '../service/printing-server-sig.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './printing-server-sig-delete-dialog.component.html',
})
export class PrintingServerSigDeleteDialogComponent {
  printingServer?: IPrintingServerSig;

  constructor(protected printingServerService: PrintingServerSigService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.printingServerService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
