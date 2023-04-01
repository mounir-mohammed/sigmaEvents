import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDayPassInfoSig, NewDayPassInfoSig } from '../day-pass-info-sig.model';

export type PartialUpdateDayPassInfoSig = Partial<IDayPassInfoSig> & Pick<IDayPassInfoSig, 'dayPassInfoId'>;

type RestOf<T extends IDayPassInfoSig | NewDayPassInfoSig> = Omit<
  T,
  'dayPassInfoCreationDate' | 'dayPassInfoUpdateDate' | 'dayPassInfoDateStart' | 'dayPassInfoDateEnd'
> & {
  dayPassInfoCreationDate?: string | null;
  dayPassInfoUpdateDate?: string | null;
  dayPassInfoDateStart?: string | null;
  dayPassInfoDateEnd?: string | null;
};

export type RestDayPassInfoSig = RestOf<IDayPassInfoSig>;

export type NewRestDayPassInfoSig = RestOf<NewDayPassInfoSig>;

export type PartialUpdateRestDayPassInfoSig = RestOf<PartialUpdateDayPassInfoSig>;

export type EntityResponseType = HttpResponse<IDayPassInfoSig>;
export type EntityArrayResponseType = HttpResponse<IDayPassInfoSig[]>;

@Injectable({ providedIn: 'root' })
export class DayPassInfoSigService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/day-pass-infos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(dayPassInfo: NewDayPassInfoSig): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dayPassInfo);
    return this.http
      .post<RestDayPassInfoSig>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(dayPassInfo: IDayPassInfoSig): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dayPassInfo);
    return this.http
      .put<RestDayPassInfoSig>(`${this.resourceUrl}/${this.getDayPassInfoSigIdentifier(dayPassInfo)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(dayPassInfo: PartialUpdateDayPassInfoSig): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dayPassInfo);
    return this.http
      .patch<RestDayPassInfoSig>(`${this.resourceUrl}/${this.getDayPassInfoSigIdentifier(dayPassInfo)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestDayPassInfoSig>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestDayPassInfoSig[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDayPassInfoSigIdentifier(dayPassInfo: Pick<IDayPassInfoSig, 'dayPassInfoId'>): number {
    return dayPassInfo.dayPassInfoId;
  }

  compareDayPassInfoSig(o1: Pick<IDayPassInfoSig, 'dayPassInfoId'> | null, o2: Pick<IDayPassInfoSig, 'dayPassInfoId'> | null): boolean {
    return o1 && o2 ? this.getDayPassInfoSigIdentifier(o1) === this.getDayPassInfoSigIdentifier(o2) : o1 === o2;
  }

  addDayPassInfoSigToCollectionIfMissing<Type extends Pick<IDayPassInfoSig, 'dayPassInfoId'>>(
    dayPassInfoCollection: Type[],
    ...dayPassInfosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const dayPassInfos: Type[] = dayPassInfosToCheck.filter(isPresent);
    if (dayPassInfos.length > 0) {
      const dayPassInfoCollectionIdentifiers = dayPassInfoCollection.map(
        dayPassInfoItem => this.getDayPassInfoSigIdentifier(dayPassInfoItem)!
      );
      const dayPassInfosToAdd = dayPassInfos.filter(dayPassInfoItem => {
        const dayPassInfoIdentifier = this.getDayPassInfoSigIdentifier(dayPassInfoItem);
        if (dayPassInfoCollectionIdentifiers.includes(dayPassInfoIdentifier)) {
          return false;
        }
        dayPassInfoCollectionIdentifiers.push(dayPassInfoIdentifier);
        return true;
      });
      return [...dayPassInfosToAdd, ...dayPassInfoCollection];
    }
    return dayPassInfoCollection;
  }

  protected convertDateFromClient<T extends IDayPassInfoSig | NewDayPassInfoSig | PartialUpdateDayPassInfoSig>(dayPassInfo: T): RestOf<T> {
    return {
      ...dayPassInfo,
      dayPassInfoCreationDate: dayPassInfo.dayPassInfoCreationDate?.toJSON() ?? null,
      dayPassInfoUpdateDate: dayPassInfo.dayPassInfoUpdateDate?.toJSON() ?? null,
      dayPassInfoDateStart: dayPassInfo.dayPassInfoDateStart?.toJSON() ?? null,
      dayPassInfoDateEnd: dayPassInfo.dayPassInfoDateEnd?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restDayPassInfoSig: RestDayPassInfoSig): IDayPassInfoSig {
    return {
      ...restDayPassInfoSig,
      dayPassInfoCreationDate: restDayPassInfoSig.dayPassInfoCreationDate ? dayjs(restDayPassInfoSig.dayPassInfoCreationDate) : undefined,
      dayPassInfoUpdateDate: restDayPassInfoSig.dayPassInfoUpdateDate ? dayjs(restDayPassInfoSig.dayPassInfoUpdateDate) : undefined,
      dayPassInfoDateStart: restDayPassInfoSig.dayPassInfoDateStart ? dayjs(restDayPassInfoSig.dayPassInfoDateStart) : undefined,
      dayPassInfoDateEnd: restDayPassInfoSig.dayPassInfoDateEnd ? dayjs(restDayPassInfoSig.dayPassInfoDateEnd) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestDayPassInfoSig>): HttpResponse<IDayPassInfoSig> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestDayPassInfoSig[]>): HttpResponse<IDayPassInfoSig[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
