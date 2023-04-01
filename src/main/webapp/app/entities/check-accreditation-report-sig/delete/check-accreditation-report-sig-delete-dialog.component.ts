import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICheckAccreditationReportSig } from '../check-accreditation-report-sig.model';
import { CheckAccreditationReportSigService } from '../service/check-accreditation-report-sig.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './check-accreditation-report-sig-delete-dialog.component.html',
})
export class CheckAccreditationReportSigDeleteDialogComponent {
  checkAccreditationReport?: ICheckAccreditationReportSig;

  constructor(protected checkAccreditationReportService: CheckAccreditationReportSigService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.checkAccreditationReportService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
