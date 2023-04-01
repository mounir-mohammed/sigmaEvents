import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISiteSig } from '../site-sig.model';
import { SiteSigService } from '../service/site-sig.service';

@Injectable({ providedIn: 'root' })
export class SiteSigRoutingResolveService implements Resolve<ISiteSig | null> {
  constructor(protected service: SiteSigService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISiteSig | null | never> {
    const id = route.params['siteId'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((site: HttpResponse<ISiteSig>) => {
          if (site.body) {
            return of(site.body);
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
