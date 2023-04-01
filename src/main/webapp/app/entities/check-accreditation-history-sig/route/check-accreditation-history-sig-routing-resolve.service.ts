import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICheckAccreditationHistorySig } from '../check-accreditation-history-sig.model';
import { CheckAccreditationHistorySigService } from '../service/check-accreditation-history-sig.service';

@Injectable({ providedIn: 'root' })
export class CheckAccreditationHistorySigRoutingResolveService implements Resolve<ICheckAccreditationHistorySig | null> {
  constructor(protected service: CheckAccreditationHistorySigService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICheckAccreditationHistorySig | null | never> {
    const id = route.params['checkAccreditationHistoryId'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((checkAccreditationHistory: HttpResponse<ICheckAccreditationHistorySig>) => {
          if (checkAccreditationHistory.body) {
            return of(checkAccreditationHistory.body);
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
