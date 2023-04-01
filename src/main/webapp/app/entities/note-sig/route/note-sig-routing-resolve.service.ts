import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { INoteSig } from '../note-sig.model';
import { NoteSigService } from '../service/note-sig.service';

@Injectable({ providedIn: 'root' })
export class NoteSigRoutingResolveService implements Resolve<INoteSig | null> {
  constructor(protected service: NoteSigService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INoteSig | null | never> {
    const id = route.params['noteId'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((note: HttpResponse<INoteSig>) => {
          if (note.body) {
            return of(note.body);
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
