import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEventFieldSig } from '../event-field-sig.model';
import { EventFieldSigService } from '../service/event-field-sig.service';

@Injectable({ providedIn: 'root' })
export class EventFieldSigRoutingResolveService implements Resolve<IEventFieldSig | null> {
  constructor(protected service: EventFieldSigService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEventFieldSig | null | never> {
    const id = route.params['fieldId'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((eventField: HttpResponse<IEventFieldSig>) => {
          if (eventField.body) {
            return of(eventField.body);
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
