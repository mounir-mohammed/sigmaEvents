import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IStatusSig } from '../status-sig.model';
import { StatusSigService } from '../service/status-sig.service';

@Injectable({ providedIn: 'root' })
export class StatusSigRoutingResolveService implements Resolve<IStatusSig | null> {
  constructor(protected service: StatusSigService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IStatusSig | null | never> {
    const id = route.params['statusId'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((status: HttpResponse<IStatusSig>) => {
          if (status.body) {
            return of(status.body);
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
