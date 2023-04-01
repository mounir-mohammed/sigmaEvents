import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAttachementSig } from '../attachement-sig.model';
import { AttachementSigService } from '../service/attachement-sig.service';

@Injectable({ providedIn: 'root' })
export class AttachementSigRoutingResolveService implements Resolve<IAttachementSig | null> {
  constructor(protected service: AttachementSigService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAttachementSig | null | never> {
    const id = route.params['attachementId'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((attachement: HttpResponse<IAttachementSig>) => {
          if (attachement.body) {
            return of(attachement.body);
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
