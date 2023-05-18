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
import { PrintingType } from 'app/config/printingType.contants';
import { FieldType } from 'app/config/fieldType';

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
    this.activatedRoute.data.subscribe(() => {
      this.accountService.identity().subscribe(account => (this.currentAccount = account));
      if (this.currentAccount?.printingCentre?.printingType?.printingTypeValue) {
        if (this.currentAccount?.printingCentre?.printingType?.printingTypeValue == PrintingType.BY_EVENT) {
          if (this.accreditation?.event?.eventPrintingModelId!) {
            this.getConfig(this.accreditation?.event?.eventPrintingModelId!).finally(() => {
              this.dataLoaded = true;
              this.generateadge();
            });
          } else {
            this.throwAlertErrorLoadingModel();
            console.log('ngOnInit() => NO MODEL FOUND');
          }
        }

        if (this.currentAccount?.printingCentre?.printingType?.printingTypeValue == PrintingType.BY_CENTER) {
          if (this.currentAccount?.printingCentre?.printingModel?.printingModelId!) {
            this.getConfig(this.currentAccount?.printingCentre?.printingModel?.printingModelId!).finally(() => {
              this.dataLoaded = true;
              this.generateadge();
            });
          } else {
            this.throwAlertErrorLoadingModel();
            console.log('ngOnInit() => NO MODEL FOUND');
          }
        }

        if (this.currentAccount?.printingCentre?.printingType?.printingTypeValue == PrintingType.BY_ACCREDITATION_TYPE) {
          if (this.accreditation?.accreditationType?.printingModel?.printingModelId!) {
            this.getConfig(this.accreditation?.accreditationType?.printingModel?.printingModelId!).finally(() => {
              this.dataLoaded = true;
              this.generateadge();
            });
          } else {
            this.throwAlertErrorLoadingModel();
            console.log('ngOnInit() => NO MODEL FOUND');
          }
        }

        if (this.currentAccount?.printingCentre?.printingType?.printingTypeValue == PrintingType.BY_CATEGORY) {
          if (this.accreditation?.category?.printingModel?.printingModelId!) {
            this.getConfig(this.accreditation?.category?.printingModel?.printingModelId!).finally(() => {
              this.dataLoaded = true;
              this.generateadge();
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
      this.exportAsPDF(accreditation.event?.eventAbreviation! + '_' + accreditation.event?.eventId! + '_' + accreditation.accreditationId!);
    });
  }

  exportAsPDF(divId: string) {
    let data = document.getElementById(divId);
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
    var groupDivs: Array<any> = [];
    if (this.dataLoaded && this.accreditation) {
      var accreditationJson = JSON.stringify(this.accreditation);
      var data = JSON.parse(accreditationJson);
      var badgeContainer = document.getElementById('badge-container');
      //generate badge
      var badge = document.createElement('div');
      badge.id =
        this.accreditation?.event?.eventAbreviation + '_' + this.accreditation?.event?.eventId + '_' + this.accreditation?.accreditationId;
      badge.style.width = this.modelData.printingModel.page.width;
      badge.style.height = this.modelData.printingModel.page.height;
      badge.style.margin = this.modelData.printingModel.page.margin;
      badge.style.border = this.modelData.printingModel.page.border;
      badge.style.position = this.modelData.printingModel.page.position;

      //add groups
      this.modelData.printingModel.groups.forEach((group: any) => {
        var groupDiv = document.createElement('div');
        groupDiv.id = group.name;
        groupDiv.style.position = group.position;
        groupDiv.style.left = group.x;
        groupDiv.style.top = group.y;
        groupDiv.style.zIndex = group.z;
        groupDiv.style.backgroundColor = this.dataUtils.searchElementFromJson(group.DynamicBackgroundColor, data)
          ? this.dataUtils.searchElementFromJson(group.DynamicBackgroundColor, data)
          : group.backgroundColor;
        groupDiv.style.color = this.dataUtils.searchElementFromJson(group.DynamicColor, data)
          ? this.dataUtils.searchElementFromJson(group.DynamicColor, data)
          : group.color;
        groupDiv.style.border = group.border;
        groupDiv.style.display = group.display;
        groupDiv.style.verticalAlign = group.verticalAlign;
        groupDiv.style.tableLayout = group.tableLayout;
        groupDiv.style.width = group.width;
        groupDiv.style.height = group.height;
        groupDiv.style.maxWidth = group.maxWidth;
        groupDiv.style.maxHeight = group.maxHeight;
        groupDiv.style.borderSpacing = group.borderSpacing;
        groupDivs.push(groupDiv);
      });

      //add fields
      this.modelData.printingModel.fields.forEach((element: any) => {
        console.log(element.name);
        console.log(element.fontWeight);
        var field = document.createElement('div');
        field.id =
          this.accreditation?.event?.eventAbreviation +
          '_' +
          this.accreditation?.event?.eventId +
          '_' +
          this.accreditation?.accreditationId +
          '_' +
          element.name;
        var text = '';
        if (element.type == FieldType.TEXT) {
          text = this.dataUtils.searchElementFromJson(element.path, data);
          if (element.toUpperCase) {
            if (text) {
              text = text.toString().toUpperCase().trim();
            }
          }
        } else if (element.type == FieldType.CONCAT) {
          var text = '';
          element.childFields.forEach((childField: any) => {
            if (this.dataUtils.searchElementFromJson(childField.path, data) !== null) {
              text = text + this.dataUtils.searchElementFromJson(childField.path, data);
            }
            if (childField.separator) {
              text = text + childField.separator;
            }
          });
        } else if (element.type == FieldType.LIST) {
          console.log(element.name);
        }

        if (element.toUpperCase) {
          if (text) {
            text = text.toString().toUpperCase();
          }
        }
        if (element.code) {
          console.log(element.name);
          console.log(text);
        } else {
          field.textContent = text;
        }
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
        field.style.fontWeight = element.fontWeight;
        field.style.border = element.border;
        field.style.whiteSpace = element.whiteSpace;
        field.style.verticalAlign = element.verticalAlign;
        field.style.width = element.width;
        field.style.height = element.height;
        field.style.maxWidth = element.maxWidth;
        field.style.maxHeight = element.maxHeight;
        if (element.groupName == null) {
          badge?.appendChild(field);
        } else {
          groupDivs.forEach((groupDiv: any) => {
            if (groupDiv.id === element.groupName) {
              groupDiv?.appendChild(field);
            }
          });
        }
      });

      // add images
      this.modelData.printingModel.images.forEach((image: any) => {
        var img = document.createElement('img');
        img.id =
          this.accreditation?.event?.eventAbreviation +
          '_' +
          this.accreditation?.event?.eventId +
          '_' +
          this.accreditation?.accreditationId +
          '_' +
          image.name;
        img.src =
          'data:' +
          this.dataUtils.searchElementFromJson(image.type, data) +
          ';base64,' +
          this.dataUtils.searchElementFromJson(image.path, data);
        img.style.display = image.display;
        img.style.position = image.position;
        img.style.left = image.x;
        img.style.top = image.y;
        img.style.zIndex = image.z;
        img.style.border = image.border;
        img.style.verticalAlign = image.verticalAlign;
        img.style.width = image.width;
        img.style.height = image.height;
        img.style.maxWidth = image.maxWidth;
        img.style.maxHeight = image.maxHeight;
        badge?.appendChild(img);
      });

      //add cadres
      this.modelData.printingModel.cadres.forEach((cadre: any) => {
        var cadreDiv = document.createElement('div');
        cadreDiv.id = cadre.name;
        cadreDiv.style.display = cadre.display;
        cadreDiv.style.position = cadre.position;
        cadreDiv.style.left = cadre.x;
        cadreDiv.style.top = cadre.y;
        cadreDiv.style.zIndex = cadre.z;
        cadreDiv.style.backgroundColor = this.dataUtils.searchElementFromJson(cadre.DynamicBackgroundColor, data)
          ? this.dataUtils.searchElementFromJson(cadre.DynamicBackgroundColor, data)
          : cadre.backgroundColor;
        cadreDiv.style.color = this.dataUtils.searchElementFromJson(cadre.DynamicColor, data)
          ? this.dataUtils.searchElementFromJson(cadre.DynamicColor, data)
          : cadre.color;
        cadreDiv.style.width = cadre.width;
        cadreDiv.style.height = cadre.height;
        cadreDiv.style.maxWidth = cadre.maxWidth;
        cadreDiv.style.maxHeight = cadre.maxHeight;
        cadreDiv.style.border = cadre.border;
        cadreDiv.style.verticalAlign = cadre.verticalAlign;
        badge?.appendChild(cadreDiv);
      });

      groupDivs.forEach((groupDiv: any) => {
        badge?.appendChild(groupDiv);
      });

      badgeContainer?.appendChild(badge);
      this.badgeGenerated = true;
    } else {
      this.badgeGenerated = false;
    }
  }
}
