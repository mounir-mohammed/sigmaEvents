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
    protected dataUtils: DataUtils
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(() => {
      this.getConfig(this.accreditation?.event?.eventPrintingModelId!).finally(() => {
        console.log(this.modelData);
      });
    });
  }

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmPrint(accreditation: IAccreditationSig, status?: IStatusSig): void {
    this.accountService.identity().subscribe(account => (this.currentAccount = account));
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
    if (data) {
      html2canvas(data, { scale: 4 }).then(canvas => {
        const contentDataURL = canvas.toDataURL('image/jpeg', 1.0); // 'image/jpeg' for lower quality output.
        let pdf = new jspdf('l', 'cm', 'a4'); //Generates PDF in landscape mode
        //let pdf = new jspdf('p', 'cm', 'a4');
        pdf.addImage(contentDataURL, 'jpeg', 0, 0, 29.7, 21.0);
        pdf.autoPrint();
        //This is a key for printing
        pdf.output('dataurlnewwindow');
        //pdf.save(divId+'.pdf');
      });
    }
  }

  getConfig(modelId: number): Promise<Boolean> {
    return new Promise(resolve => {
      this.printingModelSigService.find(modelId).subscribe(resp => {
        this.printingModel = resp.body;
        this.modelData = this.dataUtils.base64ToJson(this.printingModel?.printingModelData!);
        resolve(true);
      });
    });
  }
}
