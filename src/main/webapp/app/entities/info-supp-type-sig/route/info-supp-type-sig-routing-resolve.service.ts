import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IInfoSuppTypeSig } from '../info-supp-type-sig.model';
import { InfoSuppTypeSigService } from '../service/info-supp-type-sig.service';

@Injectable({ providedIn: 'root' })
export class InfoSuppTypeSigRoutingResolveService implements Resolve<IInfoSuppTypeSig | null> {
  constructor(protected service: InfoSuppTypeSigService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInfoSuppTypeSig | null | never> {
    const id = route.params['infoSuppTypeId'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((infoSuppType: HttpResponse<IInfoSuppTypeSig>) => {
          if (infoSuppType.body) {
            return of(infoSuppType.body);
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
