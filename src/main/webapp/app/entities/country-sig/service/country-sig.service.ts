import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICountrySig, NewCountrySig } from '../country-sig.model';

export type PartialUpdateCountrySig = Partial<ICountrySig> & Pick<ICountrySig, 'countryId'>;

export type EntityResponseType = HttpResponse<ICountrySig>;
export type EntityArrayResponseType = HttpResponse<ICountrySig[]>;

@Injectable({ providedIn: 'root' })
export class CountrySigService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/countries');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(country: NewCountrySig): Observable<EntityResponseType> {
    return this.http.post<ICountrySig>(this.resourceUrl, country, { observe: 'response' });
  }

  update(country: ICountrySig): Observable<EntityResponseType> {
    return this.http.put<ICountrySig>(`${this.resourceUrl}/${this.getCountrySigIdentifier(country)}`, country, { observe: 'response' });
  }

  partialUpdate(country: PartialUpdateCountrySig): Observable<EntityResponseType> {
    return this.http.patch<ICountrySig>(`${this.resourceUrl}/${this.getCountrySigIdentifier(country)}`, country, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICountrySig>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICountrySig[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCountrySigIdentifier(country: Pick<ICountrySig, 'countryId'>): number {
    return country.countryId;
  }

  compareCountrySig(o1: Pick<ICountrySig, 'countryId'> | null, o2: Pick<ICountrySig, 'countryId'> | null): boolean {
    return o1 && o2 ? this.getCountrySigIdentifier(o1) === this.getCountrySigIdentifier(o2) : o1 === o2;
  }

  addCountrySigToCollectionIfMissing<Type extends Pick<ICountrySig, 'countryId'>>(
    countryCollection: Type[],
    ...countriesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const countries: Type[] = countriesToCheck.filter(isPresent);
    if (countries.length > 0) {
      const countryCollectionIdentifiers = countryCollection.map(countryItem => this.getCountrySigIdentifier(countryItem)!);
      const countriesToAdd = countries.filter(countryItem => {
        const countryIdentifier = this.getCountrySigIdentifier(countryItem);
        if (countryCollectionIdentifiers.includes(countryIdentifier)) {
          return false;
        }
        countryCollectionIdentifiers.push(countryIdentifier);
        return true;
      });
      return [...countriesToAdd, ...countryCollection];
    }
    return countryCollection;
  }
}
