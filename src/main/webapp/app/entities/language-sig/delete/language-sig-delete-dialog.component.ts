import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ILanguageSig } from '../language-sig.model';
import { LanguageSigService } from '../service/language-sig.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './language-sig-delete-dialog.component.html',
})
export class LanguageSigDeleteDialogComponent {
  language?: ILanguageSig;

  constructor(protected languageService: LanguageSigService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.languageService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
