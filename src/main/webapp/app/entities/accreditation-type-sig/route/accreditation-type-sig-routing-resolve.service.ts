import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAccreditationTypeSig } from '../accreditation-type-sig.model';
import { AccreditationTypeSigService } from '../service/accreditation-type-sig.service';

@Injectable({ providedIn: 'root' })
export class AccreditationTypeSigRoutingResolveService implements Resolve<IAccreditationTypeSig | null> {
  constructor(protected service: AccreditationTypeSigService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAccreditationTypeSig | null | never> {
    const id = route.params['accreditationTypeId'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((accreditationType: HttpResponse<IAccreditationTypeSig>) => {
          if (accreditationType.body) {
            return of(accreditationType.body);
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
