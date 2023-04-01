import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISiteSig, NewSiteSig } from '../site-sig.model';

export type PartialUpdateSiteSig = Partial<ISiteSig> & Pick<ISiteSig, 'siteId'>;

export type EntityResponseType = HttpResponse<ISiteSig>;
export type EntityArrayResponseType = HttpResponse<ISiteSig[]>;

@Injectable({ providedIn: 'root' })
export class SiteSigService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/sites');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(site: NewSiteSig): Observable<EntityResponseType> {
    return this.http.post<ISiteSig>(this.resourceUrl, site, { observe: 'response' });
  }

  update(site: ISiteSig): Observable<EntityResponseType> {
    return this.http.put<ISiteSig>(`${this.resourceUrl}/${this.getSiteSigIdentifier(site)}`, site, { observe: 'response' });
  }

  partialUpdate(site: PartialUpdateSiteSig): Observable<EntityResponseType> {
    return this.http.patch<ISiteSig>(`${this.resourceUrl}/${this.getSiteSigIdentifier(site)}`, site, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISiteSig>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISiteSig[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSiteSigIdentifier(site: Pick<ISiteSig, 'siteId'>): number {
    return site.siteId;
  }

  compareSiteSig(o1: Pick<ISiteSig, 'siteId'> | null, o2: Pick<ISiteSig, 'siteId'> | null): boolean {
    return o1 && o2 ? this.getSiteSigIdentifier(o1) === this.getSiteSigIdentifier(o2) : o1 === o2;
  }

  addSiteSigToCollectionIfMissing<Type extends Pick<ISiteSig, 'siteId'>>(
    siteCollection: Type[],
    ...sitesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const sites: Type[] = sitesToCheck.filter(isPresent);
    if (sites.length > 0) {
      const siteCollectionIdentifiers = siteCollection.map(siteItem => this.getSiteSigIdentifier(siteItem)!);
      const sitesToAdd = sites.filter(siteItem => {
        const siteIdentifier = this.getSiteSigIdentifier(siteItem);
        if (siteCollectionIdentifiers.includes(siteIdentifier)) {
          return false;
        }
        siteCollectionIdentifiers.push(siteIdentifier);
        return true;
      });
      return [...sitesToAdd, ...siteCollection];
    }
    return siteCollection;
  }
}
