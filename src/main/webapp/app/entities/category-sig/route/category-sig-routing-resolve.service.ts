import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICategorySig } from '../category-sig.model';
import { CategorySigService } from '../service/category-sig.service';

@Injectable({ providedIn: 'root' })
export class CategorySigRoutingResolveService implements Resolve<ICategorySig | null> {
  constructor(protected service: CategorySigService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICategorySig | null | never> {
    const id = route.params['categoryId'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((category: HttpResponse<ICategorySig>) => {
          if (category.body) {
            return of(category.body);
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
