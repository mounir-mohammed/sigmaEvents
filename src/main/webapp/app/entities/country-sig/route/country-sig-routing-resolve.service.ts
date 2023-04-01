import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICountrySig } from '../country-sig.model';
import { CountrySigService } from '../service/country-sig.service';

@Injectable({ providedIn: 'root' })
export class CountrySigRoutingResolveService implements Resolve<ICountrySig | null> {
  constructor(protected service: CountrySigService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICountrySig | null | never> {
    const id = route.params['countryId'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((country: HttpResponse<ICountrySig>) => {
          if (country.body) {
            return of(country.body);
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
