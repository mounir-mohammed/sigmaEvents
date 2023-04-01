import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IOrganizSig, NewOrganizSig } from '../organiz-sig.model';

export type PartialUpdateOrganizSig = Partial<IOrganizSig> & Pick<IOrganizSig, 'organizId'>;

export type EntityResponseType = HttpResponse<IOrganizSig>;
export type EntityArrayResponseType = HttpResponse<IOrganizSig[]>;

@Injectable({ providedIn: 'root' })
export class OrganizSigService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/organizs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(organiz: NewOrganizSig): Observable<EntityResponseType> {
    return this.http.post<IOrganizSig>(this.resourceUrl, organiz, { observe: 'response' });
  }

  update(organiz: IOrganizSig): Observable<EntityResponseType> {
    return this.http.put<IOrganizSig>(`${this.resourceUrl}/${this.getOrganizSigIdentifier(organiz)}`, organiz, { observe: 'response' });
  }

  partialUpdate(organiz: PartialUpdateOrganizSig): Observable<EntityResponseType> {
    return this.http.patch<IOrganizSig>(`${this.resourceUrl}/${this.getOrganizSigIdentifier(organiz)}`, organiz, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOrganizSig>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOrganizSig[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getOrganizSigIdentifier(organiz: Pick<IOrganizSig, 'organizId'>): number {
    return organiz.organizId;
  }

  compareOrganizSig(o1: Pick<IOrganizSig, 'organizId'> | null, o2: Pick<IOrganizSig, 'organizId'> | null): boolean {
    return o1 && o2 ? this.getOrganizSigIdentifier(o1) === this.getOrganizSigIdentifier(o2) : o1 === o2;
  }

  addOrganizSigToCollectionIfMissing<Type extends Pick<IOrganizSig, 'organizId'>>(
    organizCollection: Type[],
    ...organizsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const organizs: Type[] = organizsToCheck.filter(isPresent);
    if (organizs.length > 0) {
      const organizCollectionIdentifiers = organizCollection.map(organizItem => this.getOrganizSigIdentifier(organizItem)!);
      const organizsToAdd = organizs.filter(organizItem => {
        const organizIdentifier = this.getOrganizSigIdentifier(organizItem);
        if (organizCollectionIdentifiers.includes(organizIdentifier)) {
          return false;
        }
        organizCollectionIdentifiers.push(organizIdentifier);
        return true;
      });
      return [...organizsToAdd, ...organizCollection];
    }
    return organizCollection;
  }
}
