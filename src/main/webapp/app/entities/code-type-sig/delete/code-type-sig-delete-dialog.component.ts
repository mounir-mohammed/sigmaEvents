import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICodeTypeSig } from '../code-type-sig.model';
import { CodeTypeSigService } from '../service/code-type-sig.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './code-type-sig-delete-dialog.component.html',
})
export class CodeTypeSigDeleteDialogComponent {
  codeType?: ICodeTypeSig;

  constructor(protected codeTypeService: CodeTypeSigService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.codeTypeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
