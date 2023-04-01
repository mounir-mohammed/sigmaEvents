import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEventSig } from '../event-sig.model';
import { EventSigService } from '../service/event-sig.service';

@Injectable({ providedIn: 'root' })
export class EventSigRoutingResolveService implements Resolve<IEventSig | null> {
  constructor(protected service: EventSigService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEventSig | null | never> {
    const id = route.params['eventId'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((event: HttpResponse<IEventSig>) => {
          if (event.body) {
            return of(event.body);
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
