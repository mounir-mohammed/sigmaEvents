import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAccreditationSig } from '../accreditation-sig.model';
import { AccreditationSigService } from '../service/accreditation-sig.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { AccountService } from 'app/core/auth/account.service';
import { Authority } from 'app/config/authority.constants';

@Component({
  templateUrl: './accreditation-sig-delete-dialog.component.html',
})
export class AccreditationSigDeleteDialogComponent {
  accreditation?: IAccreditationSig;

  constructor(
    protected accreditationService: AccreditationSigService,
    protected activeModal: NgbActiveModal,
    protected accountService: AccountService
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(accreditation?: IAccreditationSig): void {
    if (this.accountService.hasAnyAuthority([Authority.ADMIN])) {
      this.confirmDeleteAdmin(accreditation);
    } else {
      this.confirmDeleteUser(accreditation);
    }
  }

  confirmDeleteUser(accreditation?: IAccreditationSig): void {
    if (accreditation) {
      accreditation!.accreditationStat = false;
      accreditation!.accreditationActivated = false;
      this.accreditationService.update(accreditation).subscribe(() => {
        this.activeModal.close(ITEM_DELETED_EVENT);
      });
    }
  }

  confirmDeleteAdmin(accreditation?: IAccreditationSig): void {
    if (accreditation) {
      accreditation!.accreditationStat = false;
      accreditation!.accreditationActivated = false;
      this.accreditationService.delete(accreditation.accreditationId).subscribe(() => {
        this.activeModal.close(ITEM_DELETED_EVENT);
      });
    }
  }
}
