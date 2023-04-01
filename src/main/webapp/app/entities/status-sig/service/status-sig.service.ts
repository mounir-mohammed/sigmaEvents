import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IStatusSig, NewStatusSig } from '../status-sig.model';

export type PartialUpdateStatusSig = Partial<IStatusSig> & Pick<IStatusSig, 'statusId'>;

export type EntityResponseType = HttpResponse<IStatusSig>;
export type EntityArrayResponseType = HttpResponse<IStatusSig[]>;

@Injectable({ providedIn: 'root' })
export class StatusSigService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/statuses');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(status: NewStatusSig): Observable<EntityResponseType> {
    return this.http.post<IStatusSig>(this.resourceUrl, status, { observe: 'response' });
  }

  update(status: IStatusSig): Observable<EntityResponseType> {
    return this.http.put<IStatusSig>(`${this.resourceUrl}/${this.getStatusSigIdentifier(status)}`, status, { observe: 'response' });
  }

  partialUpdate(status: PartialUpdateStatusSig): Observable<EntityResponseType> {
    return this.http.patch<IStatusSig>(`${this.resourceUrl}/${this.getStatusSigIdentifier(status)}`, status, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IStatusSig>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IStatusSig[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getStatusSigIdentifier(status: Pick<IStatusSig, 'statusId'>): number {
    return status.statusId;
  }

  compareStatusSig(o1: Pick<IStatusSig, 'statusId'> | null, o2: Pick<IStatusSig, 'statusId'> | null): boolean {
    return o1 && o2 ? this.getStatusSigIdentifier(o1) === this.getStatusSigIdentifier(o2) : o1 === o2;
  }

  addStatusSigToCollectionIfMissing<Type extends Pick<IStatusSig, 'statusId'>>(
    statusCollection: Type[],
    ...statusesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const statuses: Type[] = statusesToCheck.filter(isPresent);
    if (statuses.length > 0) {
      const statusCollectionIdentifiers = statusCollection.map(statusItem => this.getStatusSigIdentifier(statusItem)!);
      const statusesToAdd = statuses.filter(statusItem => {
        const statusIdentifier = this.getStatusSigIdentifier(statusItem);
        if (statusCollectionIdentifiers.includes(statusIdentifier)) {
          return false;
        }
        statusCollectionIdentifiers.push(statusIdentifier);
        return true;
      });
      return [...statusesToAdd, ...statusCollection];
    }
    return statusCollection;
  }
}
