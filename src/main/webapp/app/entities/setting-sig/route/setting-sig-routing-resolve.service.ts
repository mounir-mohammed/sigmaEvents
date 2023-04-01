import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISettingSig } from '../setting-sig.model';
import { SettingSigService } from '../service/setting-sig.service';

@Injectable({ providedIn: 'root' })
export class SettingSigRoutingResolveService implements Resolve<ISettingSig | null> {
  constructor(protected service: SettingSigService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISettingSig | null | never> {
    const id = route.params['settingId'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((setting: HttpResponse<ISettingSig>) => {
          if (setting.body) {
            return of(setting.body);
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
