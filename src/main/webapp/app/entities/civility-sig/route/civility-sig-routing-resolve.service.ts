import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICivilitySig } from '../civility-sig.model';
import { CivilitySigService } from '../service/civility-sig.service';

@Injectable({ providedIn: 'root' })
export class CivilitySigRoutingResolveService implements Resolve<ICivilitySig | null> {
  constructor(protected service: CivilitySigService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICivilitySig | null | never> {
    const id = route.params['civilityId'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((civility: HttpResponse<ICivilitySig>) => {
          if (civility.body) {
            return of(civility.body);
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
