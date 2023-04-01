import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICitySig, NewCitySig } from '../city-sig.model';

export type PartialUpdateCitySig = Partial<ICitySig> & Pick<ICitySig, 'cityId'>;

export type EntityResponseType = HttpResponse<ICitySig>;
export type EntityArrayResponseType = HttpResponse<ICitySig[]>;

@Injectable({ providedIn: 'root' })
export class CitySigService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/cities');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(city: NewCitySig): Observable<EntityResponseType> {
    return this.http.post<ICitySig>(this.resourceUrl, city, { observe: 'response' });
  }

  update(city: ICitySig): Observable<EntityResponseType> {
    return this.http.put<ICitySig>(`${this.resourceUrl}/${this.getCitySigIdentifier(city)}`, city, { observe: 'response' });
  }

  partialUpdate(city: PartialUpdateCitySig): Observable<EntityResponseType> {
    return this.http.patch<ICitySig>(`${this.resourceUrl}/${this.getCitySigIdentifier(city)}`, city, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICitySig>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICitySig[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCitySigIdentifier(city: Pick<ICitySig, 'cityId'>): number {
    return city.cityId;
  }

  compareCitySig(o1: Pick<ICitySig, 'cityId'> | null, o2: Pick<ICitySig, 'cityId'> | null): boolean {
    return o1 && o2 ? this.getCitySigIdentifier(o1) === this.getCitySigIdentifier(o2) : o1 === o2;
  }

  addCitySigToCollectionIfMissing<Type extends Pick<ICitySig, 'cityId'>>(
    cityCollection: Type[],
    ...citiesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const cities: Type[] = citiesToCheck.filter(isPresent);
    if (cities.length > 0) {
      const cityCollectionIdentifiers = cityCollection.map(cityItem => this.getCitySigIdentifier(cityItem)!);
      const citiesToAdd = cities.filter(cityItem => {
        const cityIdentifier = this.getCitySigIdentifier(cityItem);
        if (cityCollectionIdentifiers.includes(cityIdentifier)) {
          return false;
        }
        cityCollectionIdentifiers.push(cityIdentifier);
        return true;
      });
      return [...citiesToAdd, ...cityCollection];
    }
    return cityCollection;
  }
}
