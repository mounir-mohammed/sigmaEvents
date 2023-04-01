import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IInfoSuppSig, NewInfoSuppSig } from '../info-supp-sig.model';

export type PartialUpdateInfoSuppSig = Partial<IInfoSuppSig> & Pick<IInfoSuppSig, 'infoSuppId'>;

export type EntityResponseType = HttpResponse<IInfoSuppSig>;
export type EntityArrayResponseType = HttpResponse<IInfoSuppSig[]>;

@Injectable({ providedIn: 'root' })
export class InfoSuppSigService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/info-supps');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(infoSupp: NewInfoSuppSig): Observable<EntityResponseType> {
    return this.http.post<IInfoSuppSig>(this.resourceUrl, infoSupp, { observe: 'response' });
  }

  update(infoSupp: IInfoSuppSig): Observable<EntityResponseType> {
    return this.http.put<IInfoSuppSig>(`${this.resourceUrl}/${this.getInfoSuppSigIdentifier(infoSupp)}`, infoSupp, { observe: 'response' });
  }

  partialUpdate(infoSupp: PartialUpdateInfoSuppSig): Observable<EntityResponseType> {
    return this.http.patch<IInfoSuppSig>(`${this.resourceUrl}/${this.getInfoSuppSigIdentifier(infoSupp)}`, infoSupp, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IInfoSuppSig>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IInfoSuppSig[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getInfoSuppSigIdentifier(infoSupp: Pick<IInfoSuppSig, 'infoSuppId'>): number {
    return infoSupp.infoSuppId;
  }

  compareInfoSuppSig(o1: Pick<IInfoSuppSig, 'infoSuppId'> | null, o2: Pick<IInfoSuppSig, 'infoSuppId'> | null): boolean {
    return o1 && o2 ? this.getInfoSuppSigIdentifier(o1) === this.getInfoSuppSigIdentifier(o2) : o1 === o2;
  }

  addInfoSuppSigToCollectionIfMissing<Type extends Pick<IInfoSuppSig, 'infoSuppId'>>(
    infoSuppCollection: Type[],
    ...infoSuppsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const infoSupps: Type[] = infoSuppsToCheck.filter(isPresent);
    if (infoSupps.length > 0) {
      const infoSuppCollectionIdentifiers = infoSuppCollection.map(infoSuppItem => this.getInfoSuppSigIdentifier(infoSuppItem)!);
      const infoSuppsToAdd = infoSupps.filter(infoSuppItem => {
        const infoSuppIdentifier = this.getInfoSuppSigIdentifier(infoSuppItem);
        if (infoSuppCollectionIdentifiers.includes(infoSuppIdentifier)) {
          return false;
        }
        infoSuppCollectionIdentifiers.push(infoSuppIdentifier);
        return true;
      });
      return [...infoSuppsToAdd, ...infoSuppCollection];
    }
    return infoSuppCollection;
  }
}
