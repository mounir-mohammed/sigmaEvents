import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { INationalitySig } from '../nationality-sig.model';
import { NationalitySigService } from '../service/nationality-sig.service';

@Injectable({ providedIn: 'root' })
export class NationalitySigRoutingResolveService implements Resolve<INationalitySig | null> {
  constructor(protected service: NationalitySigService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INationalitySig | null | never> {
    const id = route.params['nationalityId'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((nationality: HttpResponse<INationalitySig>) => {
          if (nationality.body) {
            return of(nationality.body);
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
