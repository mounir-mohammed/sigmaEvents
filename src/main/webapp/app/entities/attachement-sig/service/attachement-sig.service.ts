import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAttachementSig, NewAttachementSig } from '../attachement-sig.model';

export type PartialUpdateAttachementSig = Partial<IAttachementSig> & Pick<IAttachementSig, 'attachementId'>;

export type EntityResponseType = HttpResponse<IAttachementSig>;
export type EntityArrayResponseType = HttpResponse<IAttachementSig[]>;

@Injectable({ providedIn: 'root' })
export class AttachementSigService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/attachements');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(attachement: NewAttachementSig): Observable<EntityResponseType> {
    return this.http.post<IAttachementSig>(this.resourceUrl, attachement, { observe: 'response' });
  }

  update(attachement: IAttachementSig): Observable<EntityResponseType> {
    return this.http.put<IAttachementSig>(`${this.resourceUrl}/${this.getAttachementSigIdentifier(attachement)}`, attachement, {
      observe: 'response',
    });
  }

  partialUpdate(attachement: PartialUpdateAttachementSig): Observable<EntityResponseType> {
    return this.http.patch<IAttachementSig>(`${this.resourceUrl}/${this.getAttachementSigIdentifier(attachement)}`, attachement, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAttachementSig>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAttachementSig[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAttachementSigIdentifier(attachement: Pick<IAttachementSig, 'attachementId'>): number {
    return attachement.attachementId;
  }

  compareAttachementSig(o1: Pick<IAttachementSig, 'attachementId'> | null, o2: Pick<IAttachementSig, 'attachementId'> | null): boolean {
    return o1 && o2 ? this.getAttachementSigIdentifier(o1) === this.getAttachementSigIdentifier(o2) : o1 === o2;
  }

  addAttachementSigToCollectionIfMissing<Type extends Pick<IAttachementSig, 'attachementId'>>(
    attachementCollection: Type[],
    ...attachementsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const attachements: Type[] = attachementsToCheck.filter(isPresent);
    if (attachements.length > 0) {
      const attachementCollectionIdentifiers = attachementCollection.map(
        attachementItem => this.getAttachementSigIdentifier(attachementItem)!
      );
      const attachementsToAdd = attachements.filter(attachementItem => {
        const attachementIdentifier = this.getAttachementSigIdentifier(attachementItem);
        if (attachementCollectionIdentifiers.includes(attachementIdentifier)) {
          return false;
        }
        attachementCollectionIdentifiers.push(attachementIdentifier);
        return true;
      });
      return [...attachementsToAdd, ...attachementCollection];
    }
    return attachementCollection;
  }
}
