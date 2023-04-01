import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICloningSig } from '../cloning-sig.model';
import { CloningSigService } from '../service/cloning-sig.service';

@Injectable({ providedIn: 'root' })
export class CloningSigRoutingResolveService implements Resolve<ICloningSig | null> {
  constructor(protected service: CloningSigService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICloningSig | null | never> {
    const id = route.params['cloningId'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((cloning: HttpResponse<ICloningSig>) => {
          if (cloning.body) {
            return of(cloning.body);
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
