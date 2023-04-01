import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDayPassInfoSig } from '../day-pass-info-sig.model';
import { DayPassInfoSigService } from '../service/day-pass-info-sig.service';

@Injectable({ providedIn: 'root' })
export class DayPassInfoSigRoutingResolveService implements Resolve<IDayPassInfoSig | null> {
  constructor(protected service: DayPassInfoSigService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDayPassInfoSig | null | never> {
    const id = route.params['dayPassInfoId'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((dayPassInfo: HttpResponse<IDayPassInfoSig>) => {
          if (dayPassInfo.body) {
            return of(dayPassInfo.body);
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
