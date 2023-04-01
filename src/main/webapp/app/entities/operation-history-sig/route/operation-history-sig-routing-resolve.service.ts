import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IOperationHistorySig } from '../operation-history-sig.model';
import { OperationHistorySigService } from '../service/operation-history-sig.service';

@Injectable({ providedIn: 'root' })
export class OperationHistorySigRoutingResolveService implements Resolve<IOperationHistorySig | null> {
  constructor(protected service: OperationHistorySigService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOperationHistorySig | null | never> {
    const id = route.params['operationHistoryId'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((operationHistory: HttpResponse<IOperationHistorySig>) => {
          if (operationHistory.body) {
            return of(operationHistory.body);
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
