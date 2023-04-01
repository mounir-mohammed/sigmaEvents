import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAuthentificationTypeSig } from '../authentification-type-sig.model';
import { AuthentificationTypeSigService } from '../service/authentification-type-sig.service';

@Injectable({ providedIn: 'root' })
export class AuthentificationTypeSigRoutingResolveService implements Resolve<IAuthentificationTypeSig | null> {
  constructor(protected service: AuthentificationTypeSigService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAuthentificationTypeSig | null | never> {
    const id = route.params['authentificationTypeId'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((authentificationType: HttpResponse<IAuthentificationTypeSig>) => {
          if (authentificationType.body) {
            return of(authentificationType.body);
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
