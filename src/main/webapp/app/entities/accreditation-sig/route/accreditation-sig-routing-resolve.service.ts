import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAccreditationSig } from '../accreditation-sig.model';
import { AccreditationSigService } from '../service/accreditation-sig.service';

@Injectable({ providedIn: 'root' })
export class AccreditationSigRoutingResolveService implements Resolve<IAccreditationSig | null> {
  constructor(protected service: AccreditationSigService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAccreditationSig | null | never> {
    const id = route.params['accreditationId'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((accreditation: HttpResponse<IAccreditationSig>) => {
          if (accreditation.body) {
            return of(accreditation.body);
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
