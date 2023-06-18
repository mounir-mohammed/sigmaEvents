import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { TranslateService } from '@ngx-translate/core';
import { Authority } from 'app/config/authority.constants';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';
import { IOrganizSig, NewOrganizSig } from 'app/entities/organiz-sig/organiz-sig.model';
import { OrganizSigService } from 'app/entities/organiz-sig/service/organiz-sig.service';

@Component({
  templateUrl: './accreditation-sig-organiz-dialog.component.html',
})
export class AccreditationSigOrganizDialogComponent {
  organisations: IOrganizSig[] | undefined;
  addOrganizForm = new FormGroup({
    organizName: new FormControl('', {
      validators: [Validators.required, Validators.maxLength(50)],
    }),
    organizDescription: new FormControl('', {
      validators: [Validators.required, Validators.maxLength(200)],
    }),
  });

  currentAccount: Account | null = null;

  constructor(
    protected activeModal: NgbActiveModal,
    protected organizService: OrganizSigService,
    protected accountService: AccountService,
    protected translateService: TranslateService
  ) {}

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => (this.currentAccount = account));
  }

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmAddOrganiz(): void {
    console.log(this.organisations);
    if (this.addOrganizForm.get('organizName')?.value && this.addOrganizForm.get('organizDescription')?.value) {
      const org = this.organisations!.find(organiz => organiz.organizName === this.addOrganizForm.get('organizName')?.value);
      if (org) {
        alert(this.translateService.instant('sigmaEventsApp.organiz.organizAlreadyExists'));
        return;
      }

      let userEvent: IEventSig | undefined;

      if (this.accountService.hasAnyAuthority([Authority.ADMIN])) {
        if (this.currentAccount?.printingCentre?.event) {
          userEvent = this.currentAccount?.printingCentre?.event;
        }
      } else {
        if (this.currentAccount?.printingCentre?.event) {
          userEvent = this.currentAccount?.printingCentre?.event;
        }
      }
      if (userEvent) {
        const newOrganiz: NewOrganizSig = {
          organizId: null, // Provide the default or null value based on your requirement
          organizName: this.addOrganizForm.get('organizName')?.value,
          organizDescription: this.addOrganizForm.get('organizDescription')?.value,
          organizStat: true,
          event: userEvent,
        };
        this.organizService.create(newOrganiz).subscribe(response => {
          const organiz: IOrganizSig = response.body!;
          this.activeModal.close(organiz);
        });
      }
    }
  }
}
