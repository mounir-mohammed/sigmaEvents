import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAreaSig } from '../area-sig.model';
import { AreaSigService } from '../service/area-sig.service';

@Injectable({ providedIn: 'root' })
export class AreaSigRoutingResolveService implements Resolve<IAreaSig | null> {
  constructor(protected service: AreaSigService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAreaSig | null | never> {
    const id = route.params['areaId'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((area: HttpResponse<IAreaSig>) => {
          if (area.body) {
            return of(area.body);
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
