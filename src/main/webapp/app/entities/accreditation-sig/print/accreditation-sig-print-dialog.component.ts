import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAccreditationSig } from '../accreditation-sig.model';
import { AccreditationSigService } from '../service/accreditation-sig.service';
import { ITEM_PRINTED_EVENT } from 'app/config/navigation.constants';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import dayjs from 'dayjs/esm';
import { IStatusSig } from 'app/entities/status-sig/status-sig.model';
import jspdf from 'jspdf';
import html2canvas from 'html2canvas';

@Component({
  templateUrl: './accreditation-sig-print-dialog.component.html',
})
export class AccreditationSigPrintDialogComponent {
  accreditation?: IAccreditationSig;
  currentAccount: Account | null = null;
  status?: IStatusSig;

  constructor(
    protected accreditationService: AccreditationSigService,
    protected activeModal: NgbActiveModal,
    protected accountService: AccountService
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmPrint(accreditation: IAccreditationSig, status?: IStatusSig): void {
    this.accountService.identity().subscribe(account => (this.currentAccount = account));
    accreditation.accreditationPrintedByuser = this.currentAccount?.login;
    accreditation.accreditationPrintDate = dayjs();
    accreditation.status = status;
    this.accreditationService.update(accreditation).subscribe(() => {
      this.activeModal.close(ITEM_PRINTED_EVENT);
      this.exportAsPDF('ACC-' + accreditation.event?.eventId + '-' + accreditation.accreditationId);
    });
  }

  exportAsPDF(divId: string) {
    let data = document.getElementById(divId);
    if (data) {
      html2canvas(data).then(canvas => {
        const contentDataURL = canvas.toDataURL('image/jpeg', 1.0); // 'image/jpeg' for lower quality output.
        let pdf = new jspdf('l', 'cm', 'a4'); //Generates PDF in landscape mode
        //let pdf = new jspdf('p', 'cm', 'a4');
        pdf.addImage(contentDataURL, 'jpeg', 0, 0, 29.7, 21.0);
        pdf.autoPrint();
        //This is a key for printing
        pdf.output('dataurlnewwindow');
        //pdf.save(divId+'.pdf');
      });
    }
  }
}
