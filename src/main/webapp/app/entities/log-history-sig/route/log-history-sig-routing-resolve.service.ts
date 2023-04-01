import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILogHistorySig } from '../log-history-sig.model';
import { LogHistorySigService } from '../service/log-history-sig.service';

@Injectable({ providedIn: 'root' })
export class LogHistorySigRoutingResolveService implements Resolve<ILogHistorySig | null> {
  constructor(protected service: LogHistorySigService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILogHistorySig | null | never> {
    const id = route.params['logHistory'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((logHistory: HttpResponse<ILogHistorySig>) => {
          if (logHistory.body) {
            return of(logHistory.body);
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
