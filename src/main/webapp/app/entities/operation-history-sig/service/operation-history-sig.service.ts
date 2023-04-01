import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IOperationHistorySig, NewOperationHistorySig } from '../operation-history-sig.model';

export type PartialUpdateOperationHistorySig = Partial<IOperationHistorySig> & Pick<IOperationHistorySig, 'operationHistoryId'>;

type RestOf<T extends IOperationHistorySig | NewOperationHistorySig> = Omit<T, 'operationHistoryDate'> & {
  operationHistoryDate?: string | null;
};

export type RestOperationHistorySig = RestOf<IOperationHistorySig>;

export type NewRestOperationHistorySig = RestOf<NewOperationHistorySig>;

export type PartialUpdateRestOperationHistorySig = RestOf<PartialUpdateOperationHistorySig>;

export type EntityResponseType = HttpResponse<IOperationHistorySig>;
export type EntityArrayResponseType = HttpResponse<IOperationHistorySig[]>;

@Injectable({ providedIn: 'root' })
export class OperationHistorySigService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/operation-histories');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(operationHistory: NewOperationHistorySig): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(operationHistory);
    return this.http
      .post<RestOperationHistorySig>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(operationHistory: IOperationHistorySig): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(operationHistory);
    return this.http
      .put<RestOperationHistorySig>(`${this.resourceUrl}/${this.getOperationHistorySigIdentifier(operationHistory)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(operationHistory: PartialUpdateOperationHistorySig): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(operationHistory);
    return this.http
      .patch<RestOperationHistorySig>(`${this.resourceUrl}/${this.getOperationHistorySigIdentifier(operationHistory)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestOperationHistorySig>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestOperationHistorySig[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getOperationHistorySigIdentifier(operationHistory: Pick<IOperationHistorySig, 'operationHistoryId'>): number {
    return operationHistory.operationHistoryId;
  }

  compareOperationHistorySig(
    o1: Pick<IOperationHistorySig, 'operationHistoryId'> | null,
    o2: Pick<IOperationHistorySig, 'operationHistoryId'> | null
  ): boolean {
    return o1 && o2 ? this.getOperationHistorySigIdentifier(o1) === this.getOperationHistorySigIdentifier(o2) : o1 === o2;
  }

  addOperationHistorySigToCollectionIfMissing<Type extends Pick<IOperationHistorySig, 'operationHistoryId'>>(
    operationHistoryCollection: Type[],
    ...operationHistoriesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const operationHistories: Type[] = operationHistoriesToCheck.filter(isPresent);
    if (operationHistories.length > 0) {
      const operationHistoryCollectionIdentifiers = operationHistoryCollection.map(
        operationHistoryItem => this.getOperationHistorySigIdentifier(operationHistoryItem)!
      );
      const operationHistoriesToAdd = operationHistories.filter(operationHistoryItem => {
        const operationHistoryIdentifier = this.getOperationHistorySigIdentifier(operationHistoryItem);
        if (operationHistoryCollectionIdentifiers.includes(operationHistoryIdentifier)) {
          return false;
        }
        operationHistoryCollectionIdentifiers.push(operationHistoryIdentifier);
        return true;
      });
      return [...operationHistoriesToAdd, ...operationHistoryCollection];
    }
    return operationHistoryCollection;
  }

  protected convertDateFromClient<T extends IOperationHistorySig | NewOperationHistorySig | PartialUpdateOperationHistorySig>(
    operationHistory: T
  ): RestOf<T> {
    return {
      ...operationHistory,
      operationHistoryDate: operationHistory.operationHistoryDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restOperationHistorySig: RestOperationHistorySig): IOperationHistorySig {
    return {
      ...restOperationHistorySig,
      operationHistoryDate: restOperationHistorySig.operationHistoryDate ? dayjs(restOperationHistorySig.operationHistoryDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestOperationHistorySig>): HttpResponse<IOperationHistorySig> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestOperationHistorySig[]>): HttpResponse<IOperationHistorySig[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
