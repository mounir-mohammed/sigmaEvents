import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAttachementTypeSig } from '../attachement-type-sig.model';
import { AttachementTypeSigService } from '../service/attachement-type-sig.service';

@Injectable({ providedIn: 'root' })
export class AttachementTypeSigRoutingResolveService implements Resolve<IAttachementTypeSig | null> {
  constructor(protected service: AttachementTypeSigService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAttachementTypeSig | null | never> {
    const id = route.params['attachementTypeId'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((attachementType: HttpResponse<IAttachementTypeSig>) => {
          if (attachementType.body) {
            return of(attachementType.body);
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
