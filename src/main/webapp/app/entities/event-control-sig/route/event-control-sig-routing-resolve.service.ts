import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEventControlSig } from '../event-control-sig.model';
import { EventControlSigService } from '../service/event-control-sig.service';

@Injectable({ providedIn: 'root' })
export class EventControlSigRoutingResolveService implements Resolve<IEventControlSig | null> {
  constructor(protected service: EventControlSigService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEventControlSig | null | never> {
    const id = route.params['controlId'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((eventControl: HttpResponse<IEventControlSig>) => {
          if (eventControl.body) {
            return of(eventControl.body);
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
