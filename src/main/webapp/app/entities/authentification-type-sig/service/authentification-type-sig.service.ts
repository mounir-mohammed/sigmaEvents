import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAuthentificationTypeSig, NewAuthentificationTypeSig } from '../authentification-type-sig.model';

export type PartialUpdateAuthentificationTypeSig = Partial<IAuthentificationTypeSig> &
  Pick<IAuthentificationTypeSig, 'authentificationTypeId'>;

export type EntityResponseType = HttpResponse<IAuthentificationTypeSig>;
export type EntityArrayResponseType = HttpResponse<IAuthentificationTypeSig[]>;

@Injectable({ providedIn: 'root' })
export class AuthentificationTypeSigService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/authentification-types');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(authentificationType: NewAuthentificationTypeSig): Observable<EntityResponseType> {
    return this.http.post<IAuthentificationTypeSig>(this.resourceUrl, authentificationType, { observe: 'response' });
  }

  update(authentificationType: IAuthentificationTypeSig): Observable<EntityResponseType> {
    return this.http.put<IAuthentificationTypeSig>(
      `${this.resourceUrl}/${this.getAuthentificationTypeSigIdentifier(authentificationType)}`,
      authentificationType,
      { observe: 'response' }
    );
  }

  partialUpdate(authentificationType: PartialUpdateAuthentificationTypeSig): Observable<EntityResponseType> {
    return this.http.patch<IAuthentificationTypeSig>(
      `${this.resourceUrl}/${this.getAuthentificationTypeSigIdentifier(authentificationType)}`,
      authentificationType,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAuthentificationTypeSig>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAuthentificationTypeSig[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAuthentificationTypeSigIdentifier(authentificationType: Pick<IAuthentificationTypeSig, 'authentificationTypeId'>): number {
    return authentificationType.authentificationTypeId;
  }

  compareAuthentificationTypeSig(
    o1: Pick<IAuthentificationTypeSig, 'authentificationTypeId'> | null,
    o2: Pick<IAuthentificationTypeSig, 'authentificationTypeId'> | null
  ): boolean {
    return o1 && o2 ? this.getAuthentificationTypeSigIdentifier(o1) === this.getAuthentificationTypeSigIdentifier(o2) : o1 === o2;
  }

  addAuthentificationTypeSigToCollectionIfMissing<Type extends Pick<IAuthentificationTypeSig, 'authentificationTypeId'>>(
    authentificationTypeCollection: Type[],
    ...authentificationTypesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const authentificationTypes: Type[] = authentificationTypesToCheck.filter(isPresent);
    if (authentificationTypes.length > 0) {
      const authentificationTypeCollectionIdentifiers = authentificationTypeCollection.map(
        authentificationTypeItem => this.getAuthentificationTypeSigIdentifier(authentificationTypeItem)!
      );
      const authentificationTypesToAdd = authentificationTypes.filter(authentificationTypeItem => {
        const authentificationTypeIdentifier = this.getAuthentificationTypeSigIdentifier(authentificationTypeItem);
        if (authentificationTypeCollectionIdentifiers.includes(authentificationTypeIdentifier)) {
          return false;
        }
        authentificationTypeCollectionIdentifiers.push(authentificationTypeIdentifier);
        return true;
      });
      return [...authentificationTypesToAdd, ...authentificationTypeCollection];
    }
    return authentificationTypeCollection;
  }
}
