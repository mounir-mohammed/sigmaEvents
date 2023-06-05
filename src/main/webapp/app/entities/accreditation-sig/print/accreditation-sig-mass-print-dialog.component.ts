import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAccreditationSig } from '../accreditation-sig.model';
import { AccreditationSigService } from '../service/accreditation-sig.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { IStatusSig } from 'app/entities/status-sig/status-sig.model';
import { ActivatedRoute } from '@angular/router';
import { PrintingModelSigService } from 'app/entities/printing-model-sig/service/printing-model-sig.service';
import { DataUtils } from 'app/core/util/data-util.service';
import { PrintingType } from 'app/config/printingType.contants';
import { BadgeUtils } from 'app/badge/badge-utils';
import { ErrorModalUtil } from 'app/shared/util/errorModal.shared';
import { Authority } from 'app/config/authority.constants';
import dayjs from 'dayjs/esm';
import { ITEM_MASS_PRINTED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './accreditation-sig-mass-print-dialog.component.html',
})
export class AccreditationMassSigPrintDialogComponent implements OnInit {
  accreditations?: IAccreditationSig[];
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
    protected badgeUtils: BadgeUtils,
    protected errorModalUtil: ErrorModalUtil
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(async () => {
      console.log(this.accreditations);
      this.accountService.identity().subscribe(account => (this.currentAccount = account));
      for (let accreditation of this.accreditations!) {
        console.log(accreditation.accreditationId);
        await this.callGenerateBadge(this.currentAccount!, accreditation).then(() => {});
      }
      this.badgeGenerated = true;
    });
  }

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmPrint(accreditations: IAccreditationSig[], status?: IStatusSig): void {
    if (accreditations) {
      const promises: Promise<void>[] = [];
      let notPrintedIds: number[] = [];
      for (let accreditation of accreditations) {
        if (
          accreditation!.status!.statusUserCanPrint ||
          this.accountService.hasAnyAuthority([Authority.ADMIN]) ||
          this.accountService.hasAnyAuthority([Authority.EVENT_ADMIN]) ||
          this.accountService.hasAnyAuthority([Authority.CAN_ACC_REPRINT])
        ) {
          accreditation.accreditationPrintStat = true;
          accreditation.accreditationPrintNumber = accreditation.accreditationPrintNumber! + 1;
          accreditation.accreditationPrintedByuser = this.currentAccount?.login;
          accreditation.accreditationPrintDate = dayjs();
          accreditation.status = status;
          const promise = new Promise<void>((resolve, reject) => {
            this.accreditationService.update(accreditation).subscribe(async () => {
              var badgeId =
                accreditation.event?.eventAbreviation! + '_' + accreditation.event?.eventId! + '_' + accreditation.accreditationId!;
              this.badgeUtils
                .print(badgeId, accreditation.accreditationPrintingModel)
                .then(() => resolve())
                .catch(error => reject(error));
            });
          });
          promises.push(promise);
        } else {
          notPrintedIds.push(accreditation.accreditationId);
        }
      }
      Promise.all(promises)
        .then(() => {
          this.activeModal.close(ITEM_MASS_PRINTED_EVENT);
        })
        .catch(error => {
          this.errorModalUtil.throwAlertErrorUnauthorizedMassPrinting(notPrintedIds);
          this.activeModal.close(ITEM_MASS_PRINTED_EVENT);
        });
    }
  }

  private async callGenerateBadge(currentAccount: Account, accreditation?: IAccreditationSig): Promise<Boolean> {
    return new Promise(async resolve => {
      if (currentAccount?.printingCentre?.printingType?.printingTypeValue) {
        console.log(currentAccount?.printingCentre?.printingType?.printingTypeValue);
        if (currentAccount?.printingCentre?.printingType?.printingTypeValue == PrintingType.BY_EVENT) {
          if (accreditation?.event?.eventPrintingModelId!) {
            await this.printingModelSigService.getPrintingModelConfig(accreditation?.event?.eventPrintingModelId!).then(async modelData => {
              if (modelData) {
                accreditation!.accreditationPrintingModel = modelData!;
                await this.badgeUtils.generateBadge(accreditation, modelData, 'badge-container').finally(() => {
                  resolve(true);
                });
              } else {
                this.errorModalUtil.throwAlertErrorLoadingModel(accreditation?.accreditationId!);
                console.log('callGenerateBadge() => PRINTING MODEL IS EMPTY');
                this.cancel();
                resolve(false);
              }
            });
          } else {
            this.errorModalUtil.throwAlertErrorLoadingModel(accreditation?.accreditationId!);
            console.log('callGenerateBadge() => NO MODEL FOUND');
            this.cancel();
            resolve(false);
          }
        }

        if (currentAccount?.printingCentre?.printingType?.printingTypeValue == PrintingType.BY_CENTER) {
          if (currentAccount?.printingCentre?.printingModel?.printingModelId!) {
            await this.printingModelSigService
              .getPrintingModelConfig(currentAccount?.printingCentre?.printingModel?.printingModelId!)
              .then(async modelData => {
                if (modelData) {
                  accreditation!.accreditationPrintingModel = modelData!;
                  await this.badgeUtils.generateBadge(accreditation, modelData, 'badge-container').finally(() => {
                    resolve(true);
                  });
                } else {
                  this.errorModalUtil.throwAlertErrorLoadingModel(accreditation?.accreditationId!);
                  console.log('callGenerateBadge() => PRINTING MODEL IS EMPTY');
                  this.cancel();
                  resolve(false);
                }
              });
          } else {
            this.errorModalUtil.throwAlertErrorLoadingModel(accreditation?.accreditationId!);
            console.log('callGenerateBadge() => NO MODEL FOUND');
            this.cancel();
            resolve(false);
          }
        }
        if (currentAccount?.printingCentre?.printingType?.printingTypeValue == PrintingType.BY_ACCREDITATION_TYPE) {
          if (accreditation?.accreditationType?.printingModel?.printingModelId!) {
            await this.printingModelSigService
              .getPrintingModelConfig(accreditation?.accreditationType?.printingModel?.printingModelId!)
              .then(async modelData => {
                if (modelData) {
                  accreditation!.accreditationPrintingModel = modelData!;
                  await this.badgeUtils.generateBadge(accreditation, modelData, 'badge-container').finally(() => {
                    resolve(true);
                  });
                } else {
                  this.errorModalUtil.throwAlertErrorLoadingModel(accreditation?.accreditationId!);
                  console.log('callGenerateBadge() => PRINTING MODEL IS EMPTY');
                  this.cancel();
                  resolve(false);
                }
              });
          } else {
            this.errorModalUtil.throwAlertErrorLoadingModel(accreditation?.accreditationId!);
            console.log('callGenerateBadge() => NO MODEL FOUND');
            this.cancel();
            resolve(false);
          }
        }

        if (currentAccount?.printingCentre?.printingType?.printingTypeValue == PrintingType.BY_CATEGORY) {
          if (accreditation?.category?.printingModel?.printingModelId!) {
            await this.printingModelSigService
              .getPrintingModelConfig(accreditation?.category?.printingModel?.printingModelId!)
              .then(async modelData => {
                if (modelData) {
                  accreditation!.accreditationPrintingModel = modelData!;
                  await this.badgeUtils.generateBadge(accreditation, modelData, 'badge-container').finally(() => {
                    resolve(true);
                  });
                } else {
                  this.errorModalUtil.throwAlertErrorLoadingModel(accreditation?.accreditationId!);
                  console.log('callGenerateBadge() => PRINTING MODEL IS EMPTY');
                  this.cancel();
                  resolve(false);
                }
              });
          } else {
            this.errorModalUtil.throwAlertErrorLoadingModel(accreditation?.accreditationId!);
            console.log('callGenerateBadge() => NO MODEL FOUND');
            this.cancel();
            resolve(false);
          }
        }
      } else {
        this.errorModalUtil.throwAlertErrorLoadingModel(accreditation?.accreditationId!);
        console.log('callGenerateBadge() => NO PRINTINGTYPE FOUND!');
        this.cancel();
        resolve(false);
      }
    });
  }

  getSelectedAccreditationsIds(): number[] {
    const selectedAccreditations: number[] = [];
    for (const accreditation of this.accreditations!) {
      if (accreditation.selected) {
        selectedAccreditations.push(accreditation.accreditationId);
      }
    }
    return selectedAccreditations;
  }
}
