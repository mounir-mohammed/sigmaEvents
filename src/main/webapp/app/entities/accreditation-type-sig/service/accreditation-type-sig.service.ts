import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAccreditationTypeSig, NewAccreditationTypeSig } from '../accreditation-type-sig.model';

export type PartialUpdateAccreditationTypeSig = Partial<IAccreditationTypeSig> & Pick<IAccreditationTypeSig, 'accreditationTypeId'>;

export type EntityResponseType = HttpResponse<IAccreditationTypeSig>;
export type EntityArrayResponseType = HttpResponse<IAccreditationTypeSig[]>;

@Injectable({ providedIn: 'root' })
export class AccreditationTypeSigService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/accreditation-types');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(accreditationType: NewAccreditationTypeSig): Observable<EntityResponseType> {
    return this.http.post<IAccreditationTypeSig>(this.resourceUrl, accreditationType, { observe: 'response' });
  }

  update(accreditationType: IAccreditationTypeSig): Observable<EntityResponseType> {
    return this.http.put<IAccreditationTypeSig>(
      `${this.resourceUrl}/${this.getAccreditationTypeSigIdentifier(accreditationType)}`,
      accreditationType,
      { observe: 'response' }
    );
  }

  partialUpdate(accreditationType: PartialUpdateAccreditationTypeSig): Observable<EntityResponseType> {
    return this.http.patch<IAccreditationTypeSig>(
      `${this.resourceUrl}/${this.getAccreditationTypeSigIdentifier(accreditationType)}`,
      accreditationType,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAccreditationTypeSig>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAccreditationTypeSig[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAccreditationTypeSigIdentifier(accreditationType: Pick<IAccreditationTypeSig, 'accreditationTypeId'>): number {
    return accreditationType.accreditationTypeId;
  }

  compareAccreditationTypeSig(
    o1: Pick<IAccreditationTypeSig, 'accreditationTypeId'> | null,
    o2: Pick<IAccreditationTypeSig, 'accreditationTypeId'> | null
  ): boolean {
    return o1 && o2 ? this.getAccreditationTypeSigIdentifier(o1) === this.getAccreditationTypeSigIdentifier(o2) : o1 === o2;
  }

  addAccreditationTypeSigToCollectionIfMissing<Type extends Pick<IAccreditationTypeSig, 'accreditationTypeId'>>(
    accreditationTypeCollection: Type[],
    ...accreditationTypesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const accreditationTypes: Type[] = accreditationTypesToCheck.filter(isPresent);
    if (accreditationTypes.length > 0) {
      const accreditationTypeCollectionIdentifiers = accreditationTypeCollection.map(
        accreditationTypeItem => this.getAccreditationTypeSigIdentifier(accreditationTypeItem)!
      );
      const accreditationTypesToAdd = accreditationTypes.filter(accreditationTypeItem => {
        const accreditationTypeIdentifier = this.getAccreditationTypeSigIdentifier(accreditationTypeItem);
        if (accreditationTypeCollectionIdentifiers.includes(accreditationTypeIdentifier)) {
          return false;
        }
        accreditationTypeCollectionIdentifiers.push(accreditationTypeIdentifier);
        return true;
      });
      return [...accreditationTypesToAdd, ...accreditationTypeCollection];
    }
    return accreditationTypeCollection;
  }
}
