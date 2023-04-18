import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAccreditationSig } from '../accreditation-sig.model';
import { AccreditationSigService } from '../service/accreditation-sig.service';
import { ITEM_VALIDATED_EVENT } from 'app/config/navigation.constants';
import { AccountService } from 'app/core/auth/account.service';
import { Authority } from 'app/config/authority.constants';

@Component({
  templateUrl: './accreditation-sig-validate-dialog.component.html',
})
export class AccreditationSigValidateDialogComponent {
  accreditation?: IAccreditationSig;

  constructor(
    protected accreditationService: AccreditationSigService,
    protected activeModal: NgbActiveModal,
    protected accountService: AccountService
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmValidate(accreditation: IAccreditationSig): void {
    this.accreditationService.update(accreditation).subscribe(() => {
      this.activeModal.close(ITEM_VALIDATED_EVENT);
    });
  }
}
