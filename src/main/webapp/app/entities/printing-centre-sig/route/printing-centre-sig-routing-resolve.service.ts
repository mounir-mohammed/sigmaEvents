import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPrintingCentreSig } from '../printing-centre-sig.model';
import { PrintingCentreSigService } from '../service/printing-centre-sig.service';

@Injectable({ providedIn: 'root' })
export class PrintingCentreSigRoutingResolveService implements Resolve<IPrintingCentreSig | null> {
  constructor(protected service: PrintingCentreSigService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPrintingCentreSig | null | never> {
    const id = route.params['printingCentreId'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((printingCentre: HttpResponse<IPrintingCentreSig>) => {
          if (printingCentre.body) {
            return of(printingCentre.body);
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
