import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISexeSig } from '../sexe-sig.model';
import { SexeSigService } from '../service/sexe-sig.service';

@Injectable({ providedIn: 'root' })
export class SexeSigRoutingResolveService implements Resolve<ISexeSig | null> {
  constructor(protected service: SexeSigService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISexeSig | null | never> {
    const id = route.params['sexeId'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((sexe: HttpResponse<ISexeSig>) => {
          if (sexe.body) {
            return of(sexe.body);
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
