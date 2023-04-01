import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEventFormSig } from '../event-form-sig.model';
import { EventFormSigService } from '../service/event-form-sig.service';

@Injectable({ providedIn: 'root' })
export class EventFormSigRoutingResolveService implements Resolve<IEventFormSig | null> {
  constructor(protected service: EventFormSigService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEventFormSig | null | never> {
    const id = route.params['formId'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((eventForm: HttpResponse<IEventFormSig>) => {
          if (eventForm.body) {
            return of(eventForm.body);
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
