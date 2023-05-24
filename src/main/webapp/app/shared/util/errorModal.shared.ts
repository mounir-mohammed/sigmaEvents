import { Injectable } from '@angular/core';
import { AlertService } from 'app/core/util/alert.service';

@Injectable({
  providedIn: 'root',
})
export class ErrorModalUtil {
  constructor(protected alertService: AlertService) {}

  throwAlertErrorLoadingModel(id: number) {
    this.alertService.get().push(
      this.alertService.addAlert(
        {
          type: 'danger',
          message: 'Error on loading printing model for Accreditation {{ id }}, Please contact administrator',
          translationKey: 'sigmaEventsApp.accreditation.print.modelError',
          translationParams: { id: id },
          timeout: 1000,
        },
        this.alertService.get()
      )
    );
  }

  throwAlertErrorUnauthorizedPrinting(id: number) {
    this.alertService.get().push(
      this.alertService.addAlert(
        {
          type: 'danger',
          message: 'Unauthorized Printing Accreditation {{ id }}, Please contact administrator',
          translationKey: 'sigmaEventsApp.accreditation.print.unauthorizedPrintingError',
          translationParams: { id: id },
          timeout: 1000,
        },
        this.alertService.get()
      )
    );
  }
}
