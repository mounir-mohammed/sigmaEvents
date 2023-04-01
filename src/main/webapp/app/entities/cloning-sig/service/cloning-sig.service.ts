import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICloningSig, NewCloningSig } from '../cloning-sig.model';

export type PartialUpdateCloningSig = Partial<ICloningSig> & Pick<ICloningSig, 'cloningId'>;

type RestOf<T extends ICloningSig | NewCloningSig> = Omit<T, 'cloningDate'> & {
  cloningDate?: string | null;
};

export type RestCloningSig = RestOf<ICloningSig>;

export type NewRestCloningSig = RestOf<NewCloningSig>;

export type PartialUpdateRestCloningSig = RestOf<PartialUpdateCloningSig>;

export type EntityResponseType = HttpResponse<ICloningSig>;
export type EntityArrayResponseType = HttpResponse<ICloningSig[]>;

@Injectable({ providedIn: 'root' })
export class CloningSigService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/clonings');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(cloning: NewCloningSig): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cloning);
    return this.http
      .post<RestCloningSig>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(cloning: ICloningSig): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cloning);
    return this.http
      .put<RestCloningSig>(`${this.resourceUrl}/${this.getCloningSigIdentifier(cloning)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(cloning: PartialUpdateCloningSig): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cloning);
    return this.http
      .patch<RestCloningSig>(`${this.resourceUrl}/${this.getCloningSigIdentifier(cloning)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestCloningSig>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCloningSig[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCloningSigIdentifier(cloning: Pick<ICloningSig, 'cloningId'>): number {
    return cloning.cloningId;
  }

  compareCloningSig(o1: Pick<ICloningSig, 'cloningId'> | null, o2: Pick<ICloningSig, 'cloningId'> | null): boolean {
    return o1 && o2 ? this.getCloningSigIdentifier(o1) === this.getCloningSigIdentifier(o2) : o1 === o2;
  }

  addCloningSigToCollectionIfMissing<Type extends Pick<ICloningSig, 'cloningId'>>(
    cloningCollection: Type[],
    ...cloningsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const clonings: Type[] = cloningsToCheck.filter(isPresent);
    if (clonings.length > 0) {
      const cloningCollectionIdentifiers = cloningCollection.map(cloningItem => this.getCloningSigIdentifier(cloningItem)!);
      const cloningsToAdd = clonings.filter(cloningItem => {
        const cloningIdentifier = this.getCloningSigIdentifier(cloningItem);
        if (cloningCollectionIdentifiers.includes(cloningIdentifier)) {
          return false;
        }
        cloningCollectionIdentifiers.push(cloningIdentifier);
        return true;
      });
      return [...cloningsToAdd, ...cloningCollection];
    }
    return cloningCollection;
  }

  protected convertDateFromClient<T extends ICloningSig | NewCloningSig | PartialUpdateCloningSig>(cloning: T): RestOf<T> {
    return {
      ...cloning,
      cloningDate: cloning.cloningDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restCloningSig: RestCloningSig): ICloningSig {
    return {
      ...restCloningSig,
      cloningDate: restCloningSig.cloningDate ? dayjs(restCloningSig.cloningDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestCloningSig>): HttpResponse<ICloningSig> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestCloningSig[]>): HttpResponse<ICloningSig[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
