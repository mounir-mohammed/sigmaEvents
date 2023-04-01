import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEventControlSig, NewEventControlSig } from '../event-control-sig.model';

export type PartialUpdateEventControlSig = Partial<IEventControlSig> & Pick<IEventControlSig, 'controlId'>;

type RestOf<T extends IEventControlSig | NewEventControlSig> = Omit<T, 'controlValueDate'> & {
  controlValueDate?: string | null;
};

export type RestEventControlSig = RestOf<IEventControlSig>;

export type NewRestEventControlSig = RestOf<NewEventControlSig>;

export type PartialUpdateRestEventControlSig = RestOf<PartialUpdateEventControlSig>;

export type EntityResponseType = HttpResponse<IEventControlSig>;
export type EntityArrayResponseType = HttpResponse<IEventControlSig[]>;

@Injectable({ providedIn: 'root' })
export class EventControlSigService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/event-controls');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(eventControl: NewEventControlSig): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(eventControl);
    return this.http
      .post<RestEventControlSig>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(eventControl: IEventControlSig): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(eventControl);
    return this.http
      .put<RestEventControlSig>(`${this.resourceUrl}/${this.getEventControlSigIdentifier(eventControl)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(eventControl: PartialUpdateEventControlSig): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(eventControl);
    return this.http
      .patch<RestEventControlSig>(`${this.resourceUrl}/${this.getEventControlSigIdentifier(eventControl)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestEventControlSig>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestEventControlSig[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEventControlSigIdentifier(eventControl: Pick<IEventControlSig, 'controlId'>): number {
    return eventControl.controlId;
  }

  compareEventControlSig(o1: Pick<IEventControlSig, 'controlId'> | null, o2: Pick<IEventControlSig, 'controlId'> | null): boolean {
    return o1 && o2 ? this.getEventControlSigIdentifier(o1) === this.getEventControlSigIdentifier(o2) : o1 === o2;
  }

  addEventControlSigToCollectionIfMissing<Type extends Pick<IEventControlSig, 'controlId'>>(
    eventControlCollection: Type[],
    ...eventControlsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const eventControls: Type[] = eventControlsToCheck.filter(isPresent);
    if (eventControls.length > 0) {
      const eventControlCollectionIdentifiers = eventControlCollection.map(
        eventControlItem => this.getEventControlSigIdentifier(eventControlItem)!
      );
      const eventControlsToAdd = eventControls.filter(eventControlItem => {
        const eventControlIdentifier = this.getEventControlSigIdentifier(eventControlItem);
        if (eventControlCollectionIdentifiers.includes(eventControlIdentifier)) {
          return false;
        }
        eventControlCollectionIdentifiers.push(eventControlIdentifier);
        return true;
      });
      return [...eventControlsToAdd, ...eventControlCollection];
    }
    return eventControlCollection;
  }

  protected convertDateFromClient<T extends IEventControlSig | NewEventControlSig | PartialUpdateEventControlSig>(
    eventControl: T
  ): RestOf<T> {
    return {
      ...eventControl,
      controlValueDate: eventControl.controlValueDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restEventControlSig: RestEventControlSig): IEventControlSig {
    return {
      ...restEventControlSig,
      controlValueDate: restEventControlSig.controlValueDate ? dayjs(restEventControlSig.controlValueDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestEventControlSig>): HttpResponse<IEventControlSig> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestEventControlSig[]>): HttpResponse<IEventControlSig[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
