import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IInfoSuppSig } from '../info-supp-sig.model';
import { InfoSuppSigService } from '../service/info-supp-sig.service';

@Injectable({ providedIn: 'root' })
export class InfoSuppSigRoutingResolveService implements Resolve<IInfoSuppSig | null> {
  constructor(protected service: InfoSuppSigService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInfoSuppSig | null | never> {
    const id = route.params['infoSuppId'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((infoSupp: HttpResponse<IInfoSuppSig>) => {
          if (infoSupp.body) {
            return of(infoSupp.body);
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
