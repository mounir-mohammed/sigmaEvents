import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IOrganizSig } from '../organiz-sig.model';
import { OrganizSigService } from '../service/organiz-sig.service';

@Injectable({ providedIn: 'root' })
export class OrganizSigRoutingResolveService implements Resolve<IOrganizSig | null> {
  constructor(protected service: OrganizSigService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOrganizSig | null | never> {
    const id = route.params['organizId'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((organiz: HttpResponse<IOrganizSig>) => {
          if (organiz.body) {
            return of(organiz.body);
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
