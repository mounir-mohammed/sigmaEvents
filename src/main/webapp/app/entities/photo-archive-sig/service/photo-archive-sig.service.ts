import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPhotoArchiveSig, NewPhotoArchiveSig } from '../photo-archive-sig.model';

export type PartialUpdatePhotoArchiveSig = Partial<IPhotoArchiveSig> & Pick<IPhotoArchiveSig, 'photoArchiveId'>;

export type EntityResponseType = HttpResponse<IPhotoArchiveSig>;
export type EntityArrayResponseType = HttpResponse<IPhotoArchiveSig[]>;

@Injectable({ providedIn: 'root' })
export class PhotoArchiveSigService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/photo-archives');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(photoArchive: NewPhotoArchiveSig): Observable<EntityResponseType> {
    return this.http.post<IPhotoArchiveSig>(this.resourceUrl, photoArchive, { observe: 'response' });
  }

  update(photoArchive: IPhotoArchiveSig): Observable<EntityResponseType> {
    return this.http.put<IPhotoArchiveSig>(`${this.resourceUrl}/${this.getPhotoArchiveSigIdentifier(photoArchive)}`, photoArchive, {
      observe: 'response',
    });
  }

  partialUpdate(photoArchive: PartialUpdatePhotoArchiveSig): Observable<EntityResponseType> {
    return this.http.patch<IPhotoArchiveSig>(`${this.resourceUrl}/${this.getPhotoArchiveSigIdentifier(photoArchive)}`, photoArchive, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPhotoArchiveSig>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPhotoArchiveSig[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPhotoArchiveSigIdentifier(photoArchive: Pick<IPhotoArchiveSig, 'photoArchiveId'>): number {
    return photoArchive.photoArchiveId;
  }

  comparePhotoArchiveSig(
    o1: Pick<IPhotoArchiveSig, 'photoArchiveId'> | null,
    o2: Pick<IPhotoArchiveSig, 'photoArchiveId'> | null
  ): boolean {
    return o1 && o2 ? this.getPhotoArchiveSigIdentifier(o1) === this.getPhotoArchiveSigIdentifier(o2) : o1 === o2;
  }

  addPhotoArchiveSigToCollectionIfMissing<Type extends Pick<IPhotoArchiveSig, 'photoArchiveId'>>(
    photoArchiveCollection: Type[],
    ...photoArchivesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const photoArchives: Type[] = photoArchivesToCheck.filter(isPresent);
    if (photoArchives.length > 0) {
      const photoArchiveCollectionIdentifiers = photoArchiveCollection.map(
        photoArchiveItem => this.getPhotoArchiveSigIdentifier(photoArchiveItem)!
      );
      const photoArchivesToAdd = photoArchives.filter(photoArchiveItem => {
        const photoArchiveIdentifier = this.getPhotoArchiveSigIdentifier(photoArchiveItem);
        if (photoArchiveCollectionIdentifiers.includes(photoArchiveIdentifier)) {
          return false;
        }
        photoArchiveCollectionIdentifiers.push(photoArchiveIdentifier);
        return true;
      });
      return [...photoArchivesToAdd, ...photoArchiveCollection];
    }
    return photoArchiveCollection;
  }
}
