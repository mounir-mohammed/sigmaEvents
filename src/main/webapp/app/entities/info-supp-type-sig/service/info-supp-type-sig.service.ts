import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IInfoSuppTypeSig, NewInfoSuppTypeSig } from '../info-supp-type-sig.model';

export type PartialUpdateInfoSuppTypeSig = Partial<IInfoSuppTypeSig> & Pick<IInfoSuppTypeSig, 'infoSuppTypeId'>;

export type EntityResponseType = HttpResponse<IInfoSuppTypeSig>;
export type EntityArrayResponseType = HttpResponse<IInfoSuppTypeSig[]>;

@Injectable({ providedIn: 'root' })
export class InfoSuppTypeSigService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/info-supp-types');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(infoSuppType: NewInfoSuppTypeSig): Observable<EntityResponseType> {
    return this.http.post<IInfoSuppTypeSig>(this.resourceUrl, infoSuppType, { observe: 'response' });
  }

  update(infoSuppType: IInfoSuppTypeSig): Observable<EntityResponseType> {
    return this.http.put<IInfoSuppTypeSig>(`${this.resourceUrl}/${this.getInfoSuppTypeSigIdentifier(infoSuppType)}`, infoSuppType, {
      observe: 'response',
    });
  }

  partialUpdate(infoSuppType: PartialUpdateInfoSuppTypeSig): Observable<EntityResponseType> {
    return this.http.patch<IInfoSuppTypeSig>(`${this.resourceUrl}/${this.getInfoSuppTypeSigIdentifier(infoSuppType)}`, infoSuppType, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IInfoSuppTypeSig>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IInfoSuppTypeSig[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getInfoSuppTypeSigIdentifier(infoSuppType: Pick<IInfoSuppTypeSig, 'infoSuppTypeId'>): number {
    return infoSuppType.infoSuppTypeId;
  }

  compareInfoSuppTypeSig(
    o1: Pick<IInfoSuppTypeSig, 'infoSuppTypeId'> | null,
    o2: Pick<IInfoSuppTypeSig, 'infoSuppTypeId'> | null
  ): boolean {
    return o1 && o2 ? this.getInfoSuppTypeSigIdentifier(o1) === this.getInfoSuppTypeSigIdentifier(o2) : o1 === o2;
  }

  addInfoSuppTypeSigToCollectionIfMissing<Type extends Pick<IInfoSuppTypeSig, 'infoSuppTypeId'>>(
    infoSuppTypeCollection: Type[],
    ...infoSuppTypesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const infoSuppTypes: Type[] = infoSuppTypesToCheck.filter(isPresent);
    if (infoSuppTypes.length > 0) {
      const infoSuppTypeCollectionIdentifiers = infoSuppTypeCollection.map(
        infoSuppTypeItem => this.getInfoSuppTypeSigIdentifier(infoSuppTypeItem)!
      );
      const infoSuppTypesToAdd = infoSuppTypes.filter(infoSuppTypeItem => {
        const infoSuppTypeIdentifier = this.getInfoSuppTypeSigIdentifier(infoSuppTypeItem);
        if (infoSuppTypeCollectionIdentifiers.includes(infoSuppTypeIdentifier)) {
          return false;
        }
        infoSuppTypeCollectionIdentifiers.push(infoSuppTypeIdentifier);
        return true;
      });
      return [...infoSuppTypesToAdd, ...infoSuppTypeCollection];
    }
    return infoSuppTypeCollection;
  }
}
