import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPrintingModelSig } from '../printing-model-sig.model';
import { PrintingModelSigService } from '../service/printing-model-sig.service';

@Injectable({ providedIn: 'root' })
export class PrintingModelSigRoutingResolveService implements Resolve<IPrintingModelSig | null> {
  constructor(protected service: PrintingModelSigService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPrintingModelSig | null | never> {
    const id = route.params['printingModelId'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((printingModel: HttpResponse<IPrintingModelSig>) => {
          if (printingModel.body) {
            return of(printingModel.body);
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
