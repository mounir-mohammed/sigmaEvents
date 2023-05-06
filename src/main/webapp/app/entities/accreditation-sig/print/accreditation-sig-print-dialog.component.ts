import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAccreditationSig } from '../accreditation-sig.model';
import { AccreditationSigService } from '../service/accreditation-sig.service';
import { ITEM_PRINTED_EVENT } from 'app/config/navigation.constants';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import dayjs from 'dayjs/esm';
import { IStatusSig } from 'app/entities/status-sig/status-sig.model';

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
    });
  }
}
