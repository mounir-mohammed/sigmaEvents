import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISettingSig } from '../setting-sig.model';
import { SettingSigService } from '../service/setting-sig.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './setting-sig-delete-dialog.component.html',
})
export class SettingSigDeleteDialogComponent {
  setting?: ISettingSig;

  constructor(protected settingService: SettingSigService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.settingService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
