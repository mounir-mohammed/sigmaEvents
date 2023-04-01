import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICitySig } from '../city-sig.model';
import { CitySigService } from '../service/city-sig.service';

@Injectable({ providedIn: 'root' })
export class CitySigRoutingResolveService implements Resolve<ICitySig | null> {
  constructor(protected service: CitySigService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICitySig | null | never> {
    const id = route.params['cityId'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((city: HttpResponse<ICitySig>) => {
          if (city.body) {
            return of(city.body);
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
