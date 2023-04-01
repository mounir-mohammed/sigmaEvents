import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICivilitySig, NewCivilitySig } from '../civility-sig.model';

export type PartialUpdateCivilitySig = Partial<ICivilitySig> & Pick<ICivilitySig, 'civilityId'>;

export type EntityResponseType = HttpResponse<ICivilitySig>;
export type EntityArrayResponseType = HttpResponse<ICivilitySig[]>;

@Injectable({ providedIn: 'root' })
export class CivilitySigService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/civilities');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(civility: NewCivilitySig): Observable<EntityResponseType> {
    return this.http.post<ICivilitySig>(this.resourceUrl, civility, { observe: 'response' });
  }

  update(civility: ICivilitySig): Observable<EntityResponseType> {
    return this.http.put<ICivilitySig>(`${this.resourceUrl}/${this.getCivilitySigIdentifier(civility)}`, civility, { observe: 'response' });
  }

  partialUpdate(civility: PartialUpdateCivilitySig): Observable<EntityResponseType> {
    return this.http.patch<ICivilitySig>(`${this.resourceUrl}/${this.getCivilitySigIdentifier(civility)}`, civility, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICivilitySig>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICivilitySig[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCivilitySigIdentifier(civility: Pick<ICivilitySig, 'civilityId'>): number {
    return civility.civilityId;
  }

  compareCivilitySig(o1: Pick<ICivilitySig, 'civilityId'> | null, o2: Pick<ICivilitySig, 'civilityId'> | null): boolean {
    return o1 && o2 ? this.getCivilitySigIdentifier(o1) === this.getCivilitySigIdentifier(o2) : o1 === o2;
  }

  addCivilitySigToCollectionIfMissing<Type extends Pick<ICivilitySig, 'civilityId'>>(
    civilityCollection: Type[],
    ...civilitiesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const civilities: Type[] = civilitiesToCheck.filter(isPresent);
    if (civilities.length > 0) {
      const civilityCollectionIdentifiers = civilityCollection.map(civilityItem => this.getCivilitySigIdentifier(civilityItem)!);
      const civilitiesToAdd = civilities.filter(civilityItem => {
        const civilityIdentifier = this.getCivilitySigIdentifier(civilityItem);
        if (civilityCollectionIdentifiers.includes(civilityIdentifier)) {
          return false;
        }
        civilityCollectionIdentifiers.push(civilityIdentifier);
        return true;
      });
      return [...civilitiesToAdd, ...civilityCollection];
    }
    return civilityCollection;
  }
}
