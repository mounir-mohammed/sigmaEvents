import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILogHistorySig, NewLogHistorySig } from '../log-history-sig.model';

export type PartialUpdateLogHistorySig = Partial<ILogHistorySig> & Pick<ILogHistorySig, 'logHistory'>;

export type EntityResponseType = HttpResponse<ILogHistorySig>;
export type EntityArrayResponseType = HttpResponse<ILogHistorySig[]>;

@Injectable({ providedIn: 'root' })
export class LogHistorySigService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/log-histories');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(logHistory: NewLogHistorySig): Observable<EntityResponseType> {
    return this.http.post<ILogHistorySig>(this.resourceUrl, logHistory, { observe: 'response' });
  }

  update(logHistory: ILogHistorySig): Observable<EntityResponseType> {
    return this.http.put<ILogHistorySig>(`${this.resourceUrl}/${this.getLogHistorySigIdentifier(logHistory)}`, logHistory, {
      observe: 'response',
    });
  }

  partialUpdate(logHistory: PartialUpdateLogHistorySig): Observable<EntityResponseType> {
    return this.http.patch<ILogHistorySig>(`${this.resourceUrl}/${this.getLogHistorySigIdentifier(logHistory)}`, logHistory, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILogHistorySig>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILogHistorySig[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getLogHistorySigIdentifier(logHistory: Pick<ILogHistorySig, 'logHistory'>): number {
    return logHistory.logHistory;
  }

  compareLogHistorySig(o1: Pick<ILogHistorySig, 'logHistory'> | null, o2: Pick<ILogHistorySig, 'logHistory'> | null): boolean {
    return o1 && o2 ? this.getLogHistorySigIdentifier(o1) === this.getLogHistorySigIdentifier(o2) : o1 === o2;
  }

  addLogHistorySigToCollectionIfMissing<Type extends Pick<ILogHistorySig, 'logHistory'>>(
    logHistoryCollection: Type[],
    ...logHistoriesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const logHistories: Type[] = logHistoriesToCheck.filter(isPresent);
    if (logHistories.length > 0) {
      const logHistoryCollectionIdentifiers = logHistoryCollection.map(logHistoryItem => this.getLogHistorySigIdentifier(logHistoryItem)!);
      const logHistoriesToAdd = logHistories.filter(logHistoryItem => {
        const logHistoryIdentifier = this.getLogHistorySigIdentifier(logHistoryItem);
        if (logHistoryCollectionIdentifiers.includes(logHistoryIdentifier)) {
          return false;
        }
        logHistoryCollectionIdentifiers.push(logHistoryIdentifier);
        return true;
      });
      return [...logHistoriesToAdd, ...logHistoryCollection];
    }
    return logHistoryCollection;
  }
}
