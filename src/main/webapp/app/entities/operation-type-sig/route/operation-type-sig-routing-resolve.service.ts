import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IOperationTypeSig } from '../operation-type-sig.model';
import { OperationTypeSigService } from '../service/operation-type-sig.service';

@Injectable({ providedIn: 'root' })
export class OperationTypeSigRoutingResolveService implements Resolve<IOperationTypeSig | null> {
  constructor(protected service: OperationTypeSigService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOperationTypeSig | null | never> {
    const id = route.params['operationTypeId'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((operationType: HttpResponse<IOperationTypeSig>) => {
          if (operationType.body) {
            return of(operationType.body);
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
