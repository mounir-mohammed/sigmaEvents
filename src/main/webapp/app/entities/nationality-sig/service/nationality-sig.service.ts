import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { INationalitySig, NewNationalitySig } from '../nationality-sig.model';

export type PartialUpdateNationalitySig = Partial<INationalitySig> & Pick<INationalitySig, 'nationalityId'>;

export type EntityResponseType = HttpResponse<INationalitySig>;
export type EntityArrayResponseType = HttpResponse<INationalitySig[]>;

@Injectable({ providedIn: 'root' })
export class NationalitySigService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/nationalities');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(nationality: NewNationalitySig): Observable<EntityResponseType> {
    return this.http.post<INationalitySig>(this.resourceUrl, nationality, { observe: 'response' });
  }

  update(nationality: INationalitySig): Observable<EntityResponseType> {
    return this.http.put<INationalitySig>(`${this.resourceUrl}/${this.getNationalitySigIdentifier(nationality)}`, nationality, {
      observe: 'response',
    });
  }

  partialUpdate(nationality: PartialUpdateNationalitySig): Observable<EntityResponseType> {
    return this.http.patch<INationalitySig>(`${this.resourceUrl}/${this.getNationalitySigIdentifier(nationality)}`, nationality, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INationalitySig>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INationalitySig[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getNationalitySigIdentifier(nationality: Pick<INationalitySig, 'nationalityId'>): number {
    return nationality.nationalityId;
  }

  compareNationalitySig(o1: Pick<INationalitySig, 'nationalityId'> | null, o2: Pick<INationalitySig, 'nationalityId'> | null): boolean {
    return o1 && o2 ? this.getNationalitySigIdentifier(o1) === this.getNationalitySigIdentifier(o2) : o1 === o2;
  }

  addNationalitySigToCollectionIfMissing<Type extends Pick<INationalitySig, 'nationalityId'>>(
    nationalityCollection: Type[],
    ...nationalitiesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const nationalities: Type[] = nationalitiesToCheck.filter(isPresent);
    if (nationalities.length > 0) {
      const nationalityCollectionIdentifiers = nationalityCollection.map(
        nationalityItem => this.getNationalitySigIdentifier(nationalityItem)!
      );
      const nationalitiesToAdd = nationalities.filter(nationalityItem => {
        const nationalityIdentifier = this.getNationalitySigIdentifier(nationalityItem);
        if (nationalityCollectionIdentifiers.includes(nationalityIdentifier)) {
          return false;
        }
        nationalityCollectionIdentifiers.push(nationalityIdentifier);
        return true;
      });
      return [...nationalitiesToAdd, ...nationalityCollection];
    }
    return nationalityCollection;
  }
}
