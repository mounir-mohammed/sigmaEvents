import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPrintingServerSig } from '../printing-server-sig.model';
import { PrintingServerSigService } from '../service/printing-server-sig.service';

@Injectable({ providedIn: 'root' })
export class PrintingServerSigRoutingResolveService implements Resolve<IPrintingServerSig | null> {
  constructor(protected service: PrintingServerSigService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPrintingServerSig | null | never> {
    const id = route.params['printingServerId'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((printingServer: HttpResponse<IPrintingServerSig>) => {
          if (printingServer.body) {
            return of(printingServer.body);
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
