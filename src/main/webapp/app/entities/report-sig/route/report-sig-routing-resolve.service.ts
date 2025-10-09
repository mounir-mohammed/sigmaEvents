import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

@Injectable({ providedIn: 'root' })
export class ReportSigRoutingResolveService implements Resolve<null> {
  constructor(protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<null | never> {
    return of(null);
  }
}
