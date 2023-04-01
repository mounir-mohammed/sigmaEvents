import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILanguageSig } from '../language-sig.model';
import { LanguageSigService } from '../service/language-sig.service';

@Injectable({ providedIn: 'root' })
export class LanguageSigRoutingResolveService implements Resolve<ILanguageSig | null> {
  constructor(protected service: LanguageSigService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILanguageSig | null | never> {
    const id = route.params['languageId'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((language: HttpResponse<ILanguageSig>) => {
          if (language.body) {
            return of(language.body);
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
