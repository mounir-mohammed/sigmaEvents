import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAccreditationSig } from '../accreditation-sig.model';
import { AccreditationSigService } from '../service/accreditation-sig.service';
import { ITEM_PRINTED_EVENT } from 'app/config/navigation.constants';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import dayjs from 'dayjs/esm';
import { IStatusSig } from 'app/entities/status-sig/status-sig.model';
import jspdf from 'jspdf';
import html2canvas from 'html2canvas';
import { IPrintingModelSig } from 'app/entities/printing-model-sig/printing-model-sig.model';
import { ActivatedRoute } from '@angular/router';
import { PrintingModelSigService } from 'app/entities/printing-model-sig/service/printing-model-sig.service';
import { DataUtils } from 'app/core/util/data-util.service';
import { AlertService } from 'app/core/util/alert.service';

@Component({
  templateUrl: './accreditation-sig-print-dialog.component.html',
})
export class AccreditationSigPrintDialogComponent implements OnInit {
  accreditation?: IAccreditationSig;
  currentAccount: Account | null = null;
  status?: IStatusSig;
  printingModel: IPrintingModelSig | null = null;
  dataLoaded: boolean = false;
  modelData: any;

  constructor(
    protected accreditationService: AccreditationSigService,
    protected activeModal: NgbActiveModal,
    protected accountService: AccountService,
    protected activatedRoute: ActivatedRoute,
    protected printingModelSigService: PrintingModelSigService,
    protected dataUtils: DataUtils,
    protected alertService: AlertService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(() => {
      this.accountService.identity().subscribe(account => (this.currentAccount = account));
      console.log(this.currentAccount?.printingCentre?.printingType?.printingTypeValue);
      if (this.currentAccount?.printingCentre?.printingType?.printingTypeValue) {
        if (this.currentAccount?.printingCentre?.printingType?.printingTypeValue == 'BY_EVENT') {
          if (this.accreditation?.event?.eventPrintingModelId!) {
            this.getConfig(this.accreditation?.event?.eventPrintingModelId!).finally(() => {
              this.dataLoaded = true;
            });
          } else {
            this.throwAlertModel();
            console.log('ngOnInit() => NO MODEL FOUND');
          }
        }

        if (this.currentAccount?.printingCentre?.printingType?.printingTypeValue == 'BY_CENTER') {
          if (this.currentAccount?.printingCentre?.printingModel?.printingModelId!) {
            this.getConfig(this.currentAccount?.printingCentre?.printingModel?.printingModelId!).finally(() => {
              this.dataLoaded = true;
            });
          } else {
            this.throwAlertModel();
            console.log('ngOnInit() => NO MODEL FOUND');
          }
        }

        if (this.currentAccount?.printingCentre?.printingType?.printingTypeValue == 'BY_CATEGORY') {
          if (this.accreditation?.category?.printingModel?.printingModelId!) {
            this.getConfigFromPrintingModel(this.accreditation?.category?.printingModel).finally(() => {
              this.dataLoaded = true;
            });
          } else {
            this.throwAlertModel();
            console.log('ngOnInit() => NO MODEL FOUND');
          }
        }
        console.log(this.modelData);
      } else {
        this.throwAlertModel();
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
      this.exportAsPDF('ACC-' + accreditation.event?.eventId + '-' + accreditation.accreditationId);
    });
  }

  exportAsPDF(divId: string) {
    let data = document.getElementById(divId);
    console.log(divId);
    if (data && this.modelData) {
      html2canvas(data, { scale: this.modelData.printingModel.model.scale }).then(canvas => {
        const contentDataURL = canvas.toDataURL(this.modelData.printingModel.model.type, this.modelData.printingModel.model.quality);
        let pdf = new jspdf(
          this.modelData.printingModel.model.landScape,
          this.modelData.printingModel.model.unite,
          this.modelData.printingModel.model.format
        );
        //landscape values p/l
        pdf.addImage(
          contentDataURL,
          this.modelData.printingModel.model.typeImage,
          this.modelData.printingModel.model.x,
          this.modelData.printingModel.model.y,
          this.modelData.printingModel.model.w,
          this.modelData.printingModel.model.h
        );
        pdf.autoPrint();
        if (this.modelData.printingModel.model.autoPrint == true) {
          pdf.output('dataurlnewwindow');
        } else {
          pdf.save(divId + '.pdf');
        }
      });
    }
  }

  getConfig(modelId: number): Promise<Boolean> {
    return new Promise(resolve => {
      this.printingModelSigService.find(modelId).subscribe(resp => {
        this.printingModel = resp.body;
        if (this.printingModel?.printingModelStat) {
          this.modelData = this.dataUtils.base64ToJson(this.printingModel?.printingModelData!);
          resolve(true);
        } else {
          this.throwAlertModel();
          console.log('getConfig() => PRINTING MODEL STATE NOT ACTIVATED');
          resolve(false);
        }
      });
    });
  }

  getConfigFromPrintingModel(printingModel: IPrintingModelSig | null): Promise<Boolean> {
    return new Promise(resolve => {
      if (printingModel?.printingModelData) {
        this.printingModel = printingModel;
        if (this.printingModel?.printingModelStat) {
          this.modelData = this.dataUtils.base64ToJson(this.printingModel?.printingModelData!);
          resolve(true);
        } else {
          this.throwAlertModel();
          console.log('getConfigFromPrintingModel() => PRINTING MODEL STATE NOT ACTIVATED');
          resolve(false);
        }
      } else {
        this.throwAlertModel();
        console.log('getConfigFromPrintingModel() => NO PRINTING MODEL DATA FOUND');
      }
    });
  }

  throwAlertModel() {
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
