import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICheckAccreditationReportSig } from '../check-accreditation-report-sig.model';
import { CheckAccreditationReportSigService } from '../service/check-accreditation-report-sig.service';

@Injectable({ providedIn: 'root' })
export class CheckAccreditationReportSigRoutingResolveService implements Resolve<ICheckAccreditationReportSig | null> {
  constructor(protected service: CheckAccreditationReportSigService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICheckAccreditationReportSig | null | never> {
    const id = route.params['checkAccreditationReportId'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((checkAccreditationReport: HttpResponse<ICheckAccreditationReportSig>) => {
          if (checkAccreditationReport.body) {
            return of(checkAccreditationReport.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
