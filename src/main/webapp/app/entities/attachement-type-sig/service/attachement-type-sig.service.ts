import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAttachementTypeSig, NewAttachementTypeSig } from '../attachement-type-sig.model';

export type PartialUpdateAttachementTypeSig = Partial<IAttachementTypeSig> & Pick<IAttachementTypeSig, 'attachementTypeId'>;

export type EntityResponseType = HttpResponse<IAttachementTypeSig>;
export type EntityArrayResponseType = HttpResponse<IAttachementTypeSig[]>;

@Injectable({ providedIn: 'root' })
export class AttachementTypeSigService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/attachement-types');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(attachementType: NewAttachementTypeSig): Observable<EntityResponseType> {
    return this.http.post<IAttachementTypeSig>(this.resourceUrl, attachementType, { observe: 'response' });
  }

  update(attachementType: IAttachementTypeSig): Observable<EntityResponseType> {
    return this.http.put<IAttachementTypeSig>(
      `${this.resourceUrl}/${this.getAttachementTypeSigIdentifier(attachementType)}`,
      attachementType,
      { observe: 'response' }
    );
  }

  partialUpdate(attachementType: PartialUpdateAttachementTypeSig): Observable<EntityResponseType> {
    return this.http.patch<IAttachementTypeSig>(
      `${this.resourceUrl}/${this.getAttachementTypeSigIdentifier(attachementType)}`,
      attachementType,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAttachementTypeSig>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAttachementTypeSig[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAttachementTypeSigIdentifier(attachementType: Pick<IAttachementTypeSig, 'attachementTypeId'>): number {
    return attachementType.attachementTypeId;
  }

  compareAttachementTypeSig(
    o1: Pick<IAttachementTypeSig, 'attachementTypeId'> | null,
    o2: Pick<IAttachementTypeSig, 'attachementTypeId'> | null
  ): boolean {
    return o1 && o2 ? this.getAttachementTypeSigIdentifier(o1) === this.getAttachementTypeSigIdentifier(o2) : o1 === o2;
  }

  addAttachementTypeSigToCollectionIfMissing<Type extends Pick<IAttachementTypeSig, 'attachementTypeId'>>(
    attachementTypeCollection: Type[],
    ...attachementTypesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const attachementTypes: Type[] = attachementTypesToCheck.filter(isPresent);
    if (attachementTypes.length > 0) {
      const attachementTypeCollectionIdentifiers = attachementTypeCollection.map(
        attachementTypeItem => this.getAttachementTypeSigIdentifier(attachementTypeItem)!
      );
      const attachementTypesToAdd = attachementTypes.filter(attachementTypeItem => {
        const attachementTypeIdentifier = this.getAttachementTypeSigIdentifier(attachementTypeItem);
        if (attachementTypeCollectionIdentifiers.includes(attachementTypeIdentifier)) {
          return false;
        }
        attachementTypeCollectionIdentifiers.push(attachementTypeIdentifier);
        return true;
      });
      return [...attachementTypesToAdd, ...attachementTypeCollection];
    }
    return attachementTypeCollection;
  }
}
