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
  badgeGenerated: boolean = false;
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
    console.log('Start ngOnInit()');
    this.activatedRoute.data.subscribe(() => {
      this.accountService.identity().subscribe(account => (this.currentAccount = account));
      console.log(this.currentAccount?.printingCentre?.printingType?.printingTypeValue);
      if (this.currentAccount?.printingCentre?.printingType?.printingTypeValue) {
        if (this.currentAccount?.printingCentre?.printingType?.printingTypeValue == 'BY_EVENT') {
          if (this.accreditation?.event?.eventPrintingModelId!) {
            this.getConfig(this.accreditation?.event?.eventPrintingModelId!).finally(() => {
              this.dataLoaded = true;
              console.log('this.dataLoaded = true;');
              this.generateadge();
            });
          } else {
            this.throwAlertErrorLoadingModel();
            console.log('ngOnInit() => NO MODEL FOUND');
          }
        }

        if (this.currentAccount?.printingCentre?.printingType?.printingTypeValue == 'BY_CENTER') {
          if (this.currentAccount?.printingCentre?.printingModel?.printingModelId!) {
            this.getConfig(this.currentAccount?.printingCentre?.printingModel?.printingModelId!).finally(() => {
              this.dataLoaded = true;
              console.log('this.dataLoaded = true;');
              this.generateadge();
            });
          } else {
            this.throwAlertErrorLoadingModel();
            console.log('ngOnInit() => NO MODEL FOUND');
          }
        }

        if (this.currentAccount?.printingCentre?.printingType?.printingTypeValue == 'BY_ACCREDITATION_TYPE') {
          if (this.accreditation?.accreditationType?.printingModel?.printingModelId!) {
            this.getConfigFromPrintingModel(this.accreditation?.accreditationType?.printingModel).finally(() => {
              this.dataLoaded = true;
              console.log('this.dataLoaded = true;');
              this.generateadge();
            });
          } else {
            this.throwAlertErrorLoadingModel();
            console.log('ngOnInit() => NO MODEL FOUND');
          }
        }

        if (this.currentAccount?.printingCentre?.printingType?.printingTypeValue == 'BY_CATEGORY') {
          if (this.accreditation?.category?.printingModel?.printingModelId!) {
            this.getConfigFromPrintingModel(this.accreditation?.category?.printingModel).finally(() => {
              this.dataLoaded = true;
              console.log('this.dataLoaded = true;');
              this.generateadge();
            });
          } else {
            this.throwAlertErrorLoadingModel();
            console.log('ngOnInit() => NO MODEL FOUND');
          }
        }
        console.log(this.modelData);
      } else {
        this.throwAlertErrorLoadingModel();
        console.log('ngOnInit() => NO PRINTINGTYPE FOUND!');
      }
    });
    console.log('END ngOnInit()');
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
      this.exportAsPDF(accreditation.event?.eventAbreviation! + '_' + accreditation.event?.eventId! + '_' + accreditation.accreditationId!);
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
          let fileName = divId + '_' + new Date().toUTCString();
          console.log(fileName);
          pdf.save(fileName);
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
          this.throwAlertErrorLoadingModel();
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
          this.throwAlertErrorLoadingModel();
          console.log('getConfigFromPrintingModel() => PRINTING MODEL STATE NOT ACTIVATED');
          resolve(false);
        }
      } else {
        this.throwAlertErrorLoadingModel();
        console.log('getConfigFromPrintingModel() => NO PRINTING MODEL DATA FOUND');
      }
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

  generateadge(): void {
    console.log('generate badge');
    console.log(this.dataLoaded);
    if (this.dataLoaded && this.accreditation) {
      var accreditationJson = JSON.stringify(this.accreditation);
      var data = JSON.parse(accreditationJson);
      console.log(accreditationJson);
      console.log('IN generateadge()');
      var badgeContainer = document.getElementById('badge-container');
      console.log(badgeContainer);
      //generate badge
      var badge = document.createElement('div');
      badge.id =
        this.accreditation?.event?.eventAbreviation + '_' + this.accreditation?.event?.eventId + '_' + this.accreditation?.accreditationId;
      badge.style.width = this.modelData.printingModel.page.width;
      badge.style.height = this.modelData.printingModel.page.height;
      badge.style.margin = this.modelData.printingModel.page.margin;
      badge.style.border = this.modelData.printingModel.page.border;

      //fields
      this.modelData.printingModel.fields.forEach((element: any) => {
        console.log(element.name);
        var field = document.createElement('div');
        field.id =
          this.accreditation?.event?.eventAbreviation +
          '_' +
          this.accreditation?.event?.eventId +
          '_' +
          this.accreditation?.accreditationId +
          '_' +
          element.name;
        field.textContent = this.dataUtils.searchElementFromJson(element.path, data);
        field.style.display = element.display;
        field.style.position = element.position;
        field.style.left = element.x;
        field.style.top = element.y;
        field.style.zIndex = element.z;
        field.style.backgroundColor = this.dataUtils.searchElementFromJson(element.DynamicBackgroundColor, data)
          ? this.dataUtils.searchElementFromJson(element.DynamicBackgroundColor, data)
          : element.backgroundColor;
        field.style.color = this.dataUtils.searchElementFromJson(element.DynamicColor, data)
          ? this.dataUtils.searchElementFromJson(element.DynamicColor, data)
          : element.color;
        field.style.textAlign = element.textAlign;
        field.style.fontFamily = element.fontFamily;
        field.style.fontStyle = element.fontStyle;
        field.style.fontSize = element.fontSize;
        field.style.border = element.border;
        badge?.appendChild(field);
      });

      badgeContainer?.appendChild(badge);
      this.badgeGenerated = true;
    } else {
      console.log('NOT generateadge()');
      this.badgeGenerated = false;
    }
  }
}
