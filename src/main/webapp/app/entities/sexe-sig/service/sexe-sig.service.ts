import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISexeSig, NewSexeSig } from '../sexe-sig.model';

export type PartialUpdateSexeSig = Partial<ISexeSig> & Pick<ISexeSig, 'sexeId'>;

export type EntityResponseType = HttpResponse<ISexeSig>;
export type EntityArrayResponseType = HttpResponse<ISexeSig[]>;

@Injectable({ providedIn: 'root' })
export class SexeSigService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/sexes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(sexe: NewSexeSig): Observable<EntityResponseType> {
    return this.http.post<ISexeSig>(this.resourceUrl, sexe, { observe: 'response' });
  }

  update(sexe: ISexeSig): Observable<EntityResponseType> {
    return this.http.put<ISexeSig>(`${this.resourceUrl}/${this.getSexeSigIdentifier(sexe)}`, sexe, { observe: 'response' });
  }

  partialUpdate(sexe: PartialUpdateSexeSig): Observable<EntityResponseType> {
    return this.http.patch<ISexeSig>(`${this.resourceUrl}/${this.getSexeSigIdentifier(sexe)}`, sexe, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISexeSig>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISexeSig[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSexeSigIdentifier(sexe: Pick<ISexeSig, 'sexeId'>): number {
    return sexe.sexeId;
  }

  compareSexeSig(o1: Pick<ISexeSig, 'sexeId'> | null, o2: Pick<ISexeSig, 'sexeId'> | null): boolean {
    return o1 && o2 ? this.getSexeSigIdentifier(o1) === this.getSexeSigIdentifier(o2) : o1 === o2;
  }

  addSexeSigToCollectionIfMissing<Type extends Pick<ISexeSig, 'sexeId'>>(
    sexeCollection: Type[],
    ...sexesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const sexes: Type[] = sexesToCheck.filter(isPresent);
    if (sexes.length > 0) {
      const sexeCollectionIdentifiers = sexeCollection.map(sexeItem => this.getSexeSigIdentifier(sexeItem)!);
      const sexesToAdd = sexes.filter(sexeItem => {
        const sexeIdentifier = this.getSexeSigIdentifier(sexeItem);
        if (sexeCollectionIdentifiers.includes(sexeIdentifier)) {
          return false;
        }
        sexeCollectionIdentifiers.push(sexeIdentifier);
        return true;
      });
      return [...sexesToAdd, ...sexeCollection];
    }
    return sexeCollection;
  }
}
