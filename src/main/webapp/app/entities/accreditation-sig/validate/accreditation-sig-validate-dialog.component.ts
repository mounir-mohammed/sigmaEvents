import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAccreditationSig } from '../accreditation-sig.model';
import { AccreditationSigService } from '../service/accreditation-sig.service';
import { ITEM_VALIDATED_EVENT } from 'app/config/navigation.constants';
import { AccountService } from 'app/core/auth/account.service';
import { Authority } from 'app/config/authority.constants';
import { IStatusSig } from 'app/entities/status-sig/status-sig.model';

@Component({
  templateUrl: './accreditation-sig-validate-dialog.component.html',
})
export class AccreditationSigValidateDialogComponent {
  accreditation?: IAccreditationSig;
  status?: IStatusSig;

  constructor(
    protected accreditationService: AccreditationSigService,
    protected activeModal: NgbActiveModal,
    protected accountService: AccountService
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmValidate(accreditation: IAccreditationSig, status?: IStatusSig): void {
    accreditation.status = status;
    this.accreditationService.validate(accreditation.accreditationId, status!.statusId!).subscribe(() => {
      this.activeModal.close(ITEM_VALIDATED_EVENT);
    });
  }
}
