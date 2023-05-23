import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAccreditationSig } from '../accreditation-sig.model';
import { AccreditationSigService } from '../service/accreditation-sig.service';
import { ITEM_PRINTED_EVENT } from 'app/config/navigation.constants';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import dayjs from 'dayjs/esm';
import { IStatusSig } from 'app/entities/status-sig/status-sig.model';
import { ActivatedRoute } from '@angular/router';
import { PrintingModelSigService } from 'app/entities/printing-model-sig/service/printing-model-sig.service';
import { DataUtils } from 'app/core/util/data-util.service';
import { AlertService } from 'app/core/util/alert.service';
import { PrintingType } from 'app/config/printingType.contants';
import { BadgeUtils } from 'app/badge/badge-utils';

@Component({
  templateUrl: './accreditation-sig-print-dialog.component.html',
})
export class AccreditationSigPrintDialogComponent implements OnInit {
  accreditation?: IAccreditationSig;
  currentAccount: Account | null = null;
  status?: IStatusSig;
  badgeGenerated: boolean = false;

  constructor(
    protected accreditationService: AccreditationSigService,
    protected activeModal: NgbActiveModal,
    protected accountService: AccountService,
    protected activatedRoute: ActivatedRoute,
    protected printingModelSigService: PrintingModelSigService,
    protected dataUtils: DataUtils,
    protected alertService: AlertService,
    protected badgeUtils: BadgeUtils
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(async () => {
      this.accountService.identity().subscribe(account => (this.currentAccount = account));
      await this.callGenerateBadge(this.currentAccount!, this.accreditation).then(() => {
        this.badgeGenerated = true;
      });
    });
  }

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmPrint(accreditation: IAccreditationSig, status?: IStatusSig): void {
    accreditation.accreditationPrintedByuser = this.currentAccount?.login;
    accreditation.accreditationPrintDate = dayjs();
    accreditation.status = status;
    this.accreditationService.update(accreditation).subscribe(() => {
      this.activeModal.close(ITEM_PRINTED_EVENT);
      var badgeId = accreditation.event?.eventAbreviation! + '_' + accreditation.event?.eventId! + '_' + accreditation.accreditationId!;
      this.badgeUtils.print(badgeId, accreditation.accreditationPrintingModel);
    });
  }

  throwAlertErrorLoadingModel() {
    this.alertService.get().push(
      this.alertService.addAlert(
        {
          type: 'danger',
          message: 'modelError',
          translationKey: 'sigmaEventsApp.accreditation.print.modelError',
          timeout: 1000,
        },
        this.alertService.get()
      )
    );
    this.cancel();
  }

  private async callGenerateBadge(currentAccount: Account, accreditation?: IAccreditationSig): Promise<Boolean> {
    return new Promise(async resolve => {
      if (currentAccount?.printingCentre?.printingType?.printingTypeValue) {
        if (currentAccount?.printingCentre?.printingType?.printingTypeValue == PrintingType.BY_EVENT) {
          if (accreditation?.event?.eventPrintingModelId!) {
            await this.badgeUtils.getConfig(accreditation?.event?.eventPrintingModelId!).then(async modelData => {
              this.accreditation!.accreditationPrintingModel = modelData!;
              await this.badgeUtils.generateBadge(accreditation, modelData, 'badge-container').finally(() => {
                resolve(true);
              });
            });
          } else {
            resolve(false);
            console.log('ngOnInit() => NO MODEL FOUND');
          }
        }

        if (currentAccount?.printingCentre?.printingType?.printingTypeValue == PrintingType.BY_CENTER) {
          if (currentAccount?.printingCentre?.printingModel?.printingModelId!) {
            await this.badgeUtils.getConfig(currentAccount?.printingCentre?.printingModel?.printingModelId!).then(async modelData => {
              this.accreditation!.accreditationPrintingModel = modelData!;
              await this.badgeUtils.generateBadge(accreditation, modelData, 'badge-container').finally(() => {
                resolve(true);
              });
            });
          } else {
            resolve(false);
            console.log('ngOnInit() => NO MODEL FOUND');
          }
        }
        if (currentAccount?.printingCentre?.printingType?.printingTypeValue == PrintingType.BY_ACCREDITATION_TYPE) {
          if (accreditation?.accreditationType?.printingModel?.printingModelId!) {
            await this.badgeUtils.getConfig(accreditation?.accreditationType?.printingModel?.printingModelId!).then(async modelData => {
              this.accreditation!.accreditationPrintingModel = modelData!;
              await this.badgeUtils.generateBadge(accreditation, modelData, 'badge-container').finally(() => {
                resolve(true);
              });
            });
          } else {
            resolve(false);
            console.log('ngOnInit() => NO MODEL FOUND');
          }
        }

        if (currentAccount?.printingCentre?.printingType?.printingTypeValue == PrintingType.BY_CATEGORY) {
          if (accreditation?.category?.printingModel?.printingModelId!) {
            await this.badgeUtils.getConfig(accreditation?.category?.printingModel?.printingModelId!).then(async modelData => {
              this.accreditation!.accreditationPrintingModel = modelData!;
              await this.badgeUtils.generateBadge(accreditation, modelData, 'badge-container').finally(() => {
                resolve(true);
              });
            });
          } else {
            resolve(false);
            console.log('ngOnInit() => NO MODEL FOUND');
          }
        }
      } else {
        resolve(false);
        console.log('ngOnInit() => NO PRINTINGTYPE FOUND!');
      }
    });
  }
}
