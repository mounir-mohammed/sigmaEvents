import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPhotoArchiveSig } from '../photo-archive-sig.model';
import { PhotoArchiveSigService } from '../service/photo-archive-sig.service';

@Injectable({ providedIn: 'root' })
export class PhotoArchiveSigRoutingResolveService implements Resolve<IPhotoArchiveSig | null> {
  constructor(protected service: PhotoArchiveSigService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPhotoArchiveSig | null | never> {
    const id = route.params['photoArchiveId'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((photoArchive: HttpResponse<IPhotoArchiveSig>) => {
          if (photoArchive.body) {
            return of(photoArchive.body);
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
