import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPrintingTypeSig } from '../printing-type-sig.model';
import { PrintingTypeSigService } from '../service/printing-type-sig.service';

@Injectable({ providedIn: 'root' })
export class PrintingTypeSigRoutingResolveService implements Resolve<IPrintingTypeSig | null> {
  constructor(protected service: PrintingTypeSigService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPrintingTypeSig | null | never> {
    const id = route.params['printingTypeId'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((printingType: HttpResponse<IPrintingTypeSig>) => {
          if (printingType.body) {
            return of(printingType.body);
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
