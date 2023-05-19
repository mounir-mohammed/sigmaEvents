import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAccreditationSig } from '../accreditation-sig.model';
import { AccreditationSigService } from '../service/accreditation-sig.service';
import { ITEM_PRINTED_EVENT } from 'app/config/navigation.constants';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import dayjs from 'dayjs/esm';
import { IStatusSig } from 'app/entities/status-sig/status-sig.model';
import { IPrintingModelSig } from 'app/entities/printing-model-sig/printing-model-sig.model';
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
  printingModel: IPrintingModelSig | null = null;
  dataLoaded: boolean = false;
  badgeGenerated: boolean = false;
  modelData: any;

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
    this.activatedRoute.data.subscribe(() => {
      this.accountService.identity().subscribe(account => (this.currentAccount = account));
      if (this.currentAccount?.printingCentre?.printingType?.printingTypeValue) {
        if (this.currentAccount?.printingCentre?.printingType?.printingTypeValue == PrintingType.BY_EVENT) {
          if (this.accreditation?.event?.eventPrintingModelId!) {
            this.getConfig(this.accreditation?.event?.eventPrintingModelId!).finally(() => {
              this.badgeUtils.generateadge(this.accreditation, this.modelData, 'badge-container').finally(() => {
                this.badgeGenerated = true;
              });
            });
          } else {
            this.throwAlertErrorLoadingModel();
            console.log('ngOnInit() => NO MODEL FOUND');
          }
        }

        if (this.currentAccount?.printingCentre?.printingType?.printingTypeValue == PrintingType.BY_CENTER) {
          if (this.currentAccount?.printingCentre?.printingModel?.printingModelId!) {
            this.getConfig(this.currentAccount?.printingCentre?.printingModel?.printingModelId!).finally(() => {
              this.badgeUtils.generateadge(this.accreditation, this.modelData, 'badge-container').finally(() => {
                this.badgeGenerated = true;
              });
            });
          } else {
            this.throwAlertErrorLoadingModel();
            console.log('ngOnInit() => NO MODEL FOUND');
          }
        }

        if (this.currentAccount?.printingCentre?.printingType?.printingTypeValue == PrintingType.BY_ACCREDITATION_TYPE) {
          if (this.accreditation?.accreditationType?.printingModel?.printingModelId!) {
            this.getConfig(this.accreditation?.accreditationType?.printingModel?.printingModelId!).finally(() => {
              this.badgeUtils.generateadge(this.accreditation, this.modelData, 'badge-container').finally(() => {
                this.badgeGenerated = true;
              });
            });
          } else {
            this.throwAlertErrorLoadingModel();
            console.log('ngOnInit() => NO MODEL FOUND');
          }
        }

        if (this.currentAccount?.printingCentre?.printingType?.printingTypeValue == PrintingType.BY_CATEGORY) {
          if (this.accreditation?.category?.printingModel?.printingModelId!) {
            this.getConfig(this.accreditation?.category?.printingModel?.printingModelId!).finally(() => {
              this.badgeUtils.generateadge(this.accreditation, this.modelData, 'badge-container').finally(() => {
                this.badgeGenerated = true;
              });
            });
          } else {
            this.throwAlertErrorLoadingModel();
            console.log('ngOnInit() => NO MODEL FOUND');
          }
        }
      } else {
        this.throwAlertErrorLoadingModel();
        console.log('ngOnInit() => NO PRINTINGTYPE FOUND!');
      }
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
      this.badgeUtils.print(badgeId, this.modelData);
    });
  }

  getConfig(modelId: number): Promise<Boolean> {
    return new Promise(resolve => {
      this.printingModelSigService.find(modelId).subscribe(resp => {
        this.printingModel = resp.body;
        if (this.printingModel?.printingModelStat) {
          this.modelData = this.dataUtils.base64ToJson(this.printingModel?.printingModelData!);
          resolve(true);
        } else {
          this.throwAlertErrorLoadingModel();
          console.log('getConfig() => PRINTING MODEL STATE NOT ACTIVATED');
          resolve(false);
        }
      });
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
}
