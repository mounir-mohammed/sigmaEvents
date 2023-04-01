import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFonctionSig } from '../fonction-sig.model';
import { FonctionSigService } from '../service/fonction-sig.service';

@Injectable({ providedIn: 'root' })
export class FonctionSigRoutingResolveService implements Resolve<IFonctionSig | null> {
  constructor(protected service: FonctionSigService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFonctionSig | null | never> {
    const id = route.params['fonctionId'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((fonction: HttpResponse<IFonctionSig>) => {
          if (fonction.body) {
            return of(fonction.body);
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
