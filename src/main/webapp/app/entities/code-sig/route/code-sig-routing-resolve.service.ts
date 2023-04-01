import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICodeSig } from '../code-sig.model';
import { CodeSigService } from '../service/code-sig.service';

@Injectable({ providedIn: 'root' })
export class CodeSigRoutingResolveService implements Resolve<ICodeSig | null> {
  constructor(protected service: CodeSigService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICodeSig | null | never> {
    const id = route.params['codeId'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((code: HttpResponse<ICodeSig>) => {
          if (code.body) {
            return of(code.body);
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
