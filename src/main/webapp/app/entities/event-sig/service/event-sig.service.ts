import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEventSig, NewEventSig } from '../event-sig.model';

export type PartialUpdateEventSig = Partial<IEventSig> & Pick<IEventSig, 'eventId'>;

type RestOf<T extends IEventSig | NewEventSig> = Omit<T, 'eventdateStart' | 'eventdateEnd'> & {
  eventdateStart?: string | null;
  eventdateEnd?: string | null;
};

export type RestEventSig = RestOf<IEventSig>;

export type NewRestEventSig = RestOf<NewEventSig>;

export type PartialUpdateRestEventSig = RestOf<PartialUpdateEventSig>;

export type EntityResponseType = HttpResponse<IEventSig>;
export type EntityArrayResponseType = HttpResponse<IEventSig[]>;

@Injectable({ providedIn: 'root' })
export class EventSigService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/events');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(event: NewEventSig): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(event);
    return this.http
      .post<RestEventSig>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(event: IEventSig): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(event);
    return this.http
      .put<RestEventSig>(`${this.resourceUrl}/${this.getEventSigIdentifier(event)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(event: PartialUpdateEventSig): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(event);
    return this.http
      .patch<RestEventSig>(`${this.resourceUrl}/${this.getEventSigIdentifier(event)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestEventSig>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestEventSig[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEventSigIdentifier(event: Pick<IEventSig, 'eventId'>): number {
    return event.eventId;
  }

  compareEventSig(o1: Pick<IEventSig, 'eventId'> | null, o2: Pick<IEventSig, 'eventId'> | null): boolean {
    return o1 && o2 ? this.getEventSigIdentifier(o1) === this.getEventSigIdentifier(o2) : o1 === o2;
  }

  addEventSigToCollectionIfMissing<Type extends Pick<IEventSig, 'eventId'>>(
    eventCollection: Type[],
    ...eventsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const events: Type[] = eventsToCheck.filter(isPresent);
    if (events.length > 0) {
      const eventCollectionIdentifiers = eventCollection.map(eventItem => this.getEventSigIdentifier(eventItem)!);
      const eventsToAdd = events.filter(eventItem => {
        const eventIdentifier = this.getEventSigIdentifier(eventItem);
        if (eventCollectionIdentifiers.includes(eventIdentifier)) {
          return false;
        }
        eventCollectionIdentifiers.push(eventIdentifier);
        return true;
      });
      return [...eventsToAdd, ...eventCollection];
    }
    return eventCollection;
  }

  protected convertDateFromClient<T extends IEventSig | NewEventSig | PartialUpdateEventSig>(event: T): RestOf<T> {
    return {
      ...event,
      eventdateStart: event.eventdateStart?.toJSON() ?? null,
      eventdateEnd: event.eventdateEnd?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restEventSig: RestEventSig): IEventSig {
    return {
      ...restEventSig,
      eventdateStart: restEventSig.eventdateStart ? dayjs(restEventSig.eventdateStart) : undefined,
      eventdateEnd: restEventSig.eventdateEnd ? dayjs(restEventSig.eventdateEnd) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestEventSig>): HttpResponse<IEventSig> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestEventSig[]>): HttpResponse<IEventSig[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
