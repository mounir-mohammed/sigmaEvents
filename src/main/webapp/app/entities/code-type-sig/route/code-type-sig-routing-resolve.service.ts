import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICodeTypeSig } from '../code-type-sig.model';
import { CodeTypeSigService } from '../service/code-type-sig.service';

@Injectable({ providedIn: 'root' })
export class CodeTypeSigRoutingResolveService implements Resolve<ICodeTypeSig | null> {
  constructor(protected service: CodeTypeSigService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICodeTypeSig | null | never> {
    const id = route.params['codeTypeId'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((codeType: HttpResponse<ICodeTypeSig>) => {
          if (codeType.body) {
            return of(codeType.body);
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
