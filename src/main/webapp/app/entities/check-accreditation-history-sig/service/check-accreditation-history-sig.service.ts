import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICheckAccreditationHistorySig, NewCheckAccreditationHistorySig } from '../check-accreditation-history-sig.model';

export type PartialUpdateCheckAccreditationHistorySig = Partial<ICheckAccreditationHistorySig> &
  Pick<ICheckAccreditationHistorySig, 'checkAccreditationHistoryId'>;

type RestOf<T extends ICheckAccreditationHistorySig | NewCheckAccreditationHistorySig> = Omit<T, 'checkAccreditationHistoryDate'> & {
  checkAccreditationHistoryDate?: string | null;
};

export type RestCheckAccreditationHistorySig = RestOf<ICheckAccreditationHistorySig>;

export type NewRestCheckAccreditationHistorySig = RestOf<NewCheckAccreditationHistorySig>;

export type PartialUpdateRestCheckAccreditationHistorySig = RestOf<PartialUpdateCheckAccreditationHistorySig>;

export type EntityResponseType = HttpResponse<ICheckAccreditationHistorySig>;
export type EntityArrayResponseType = HttpResponse<ICheckAccreditationHistorySig[]>;

@Injectable({ providedIn: 'root' })
export class CheckAccreditationHistorySigService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/check-accreditation-histories');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(checkAccreditationHistory: NewCheckAccreditationHistorySig): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(checkAccreditationHistory);
    return this.http
      .post<RestCheckAccreditationHistorySig>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(checkAccreditationHistory: ICheckAccreditationHistorySig): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(checkAccreditationHistory);
    return this.http
      .put<RestCheckAccreditationHistorySig>(
        `${this.resourceUrl}/${this.getCheckAccreditationHistorySigIdentifier(checkAccreditationHistory)}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(checkAccreditationHistory: PartialUpdateCheckAccreditationHistorySig): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(checkAccreditationHistory);
    return this.http
      .patch<RestCheckAccreditationHistorySig>(
        `${this.resourceUrl}/${this.getCheckAccreditationHistorySigIdentifier(checkAccreditationHistory)}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestCheckAccreditationHistorySig>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCheckAccreditationHistorySig[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCheckAccreditationHistorySigIdentifier(
    checkAccreditationHistory: Pick<ICheckAccreditationHistorySig, 'checkAccreditationHistoryId'>
  ): number {
    return checkAccreditationHistory.checkAccreditationHistoryId;
  }

  compareCheckAccreditationHistorySig(
    o1: Pick<ICheckAccreditationHistorySig, 'checkAccreditationHistoryId'> | null,
    o2: Pick<ICheckAccreditationHistorySig, 'checkAccreditationHistoryId'> | null
  ): boolean {
    return o1 && o2 ? this.getCheckAccreditationHistorySigIdentifier(o1) === this.getCheckAccreditationHistorySigIdentifier(o2) : o1 === o2;
  }

  addCheckAccreditationHistorySigToCollectionIfMissing<Type extends Pick<ICheckAccreditationHistorySig, 'checkAccreditationHistoryId'>>(
    checkAccreditationHistoryCollection: Type[],
    ...checkAccreditationHistoriesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const checkAccreditationHistories: Type[] = checkAccreditationHistoriesToCheck.filter(isPresent);
    if (checkAccreditationHistories.length > 0) {
      const checkAccreditationHistoryCollectionIdentifiers = checkAccreditationHistoryCollection.map(
        checkAccreditationHistoryItem => this.getCheckAccreditationHistorySigIdentifier(checkAccreditationHistoryItem)!
      );
      const checkAccreditationHistoriesToAdd = checkAccreditationHistories.filter(checkAccreditationHistoryItem => {
        const checkAccreditationHistoryIdentifier = this.getCheckAccreditationHistorySigIdentifier(checkAccreditationHistoryItem);
        if (checkAccreditationHistoryCollectionIdentifiers.includes(checkAccreditationHistoryIdentifier)) {
          return false;
        }
        checkAccreditationHistoryCollectionIdentifiers.push(checkAccreditationHistoryIdentifier);
        return true;
      });
      return [...checkAccreditationHistoriesToAdd, ...checkAccreditationHistoryCollection];
    }
    return checkAccreditationHistoryCollection;
  }

  protected convertDateFromClient<
    T extends ICheckAccreditationHistorySig | NewCheckAccreditationHistorySig | PartialUpdateCheckAccreditationHistorySig
  >(checkAccreditationHistory: T): RestOf<T> {
    return {
      ...checkAccreditationHistory,
      checkAccreditationHistoryDate: checkAccreditationHistory.checkAccreditationHistoryDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restCheckAccreditationHistorySig: RestCheckAccreditationHistorySig): ICheckAccreditationHistorySig {
    return {
      ...restCheckAccreditationHistorySig,
      checkAccreditationHistoryDate: restCheckAccreditationHistorySig.checkAccreditationHistoryDate
        ? dayjs(restCheckAccreditationHistorySig.checkAccreditationHistoryDate)
        : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestCheckAccreditationHistorySig>): HttpResponse<ICheckAccreditationHistorySig> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(
    res: HttpResponse<RestCheckAccreditationHistorySig[]>
  ): HttpResponse<ICheckAccreditationHistorySig[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
