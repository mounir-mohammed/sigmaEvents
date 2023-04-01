import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEventFieldSig, NewEventFieldSig } from '../event-field-sig.model';

export type PartialUpdateEventFieldSig = Partial<IEventFieldSig> & Pick<IEventFieldSig, 'fieldId'>;

export type EntityResponseType = HttpResponse<IEventFieldSig>;
export type EntityArrayResponseType = HttpResponse<IEventFieldSig[]>;

@Injectable({ providedIn: 'root' })
export class EventFieldSigService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/event-fields');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(eventField: NewEventFieldSig): Observable<EntityResponseType> {
    return this.http.post<IEventFieldSig>(this.resourceUrl, eventField, { observe: 'response' });
  }

  update(eventField: IEventFieldSig): Observable<EntityResponseType> {
    return this.http.put<IEventFieldSig>(`${this.resourceUrl}/${this.getEventFieldSigIdentifier(eventField)}`, eventField, {
      observe: 'response',
    });
  }

  partialUpdate(eventField: PartialUpdateEventFieldSig): Observable<EntityResponseType> {
    return this.http.patch<IEventFieldSig>(`${this.resourceUrl}/${this.getEventFieldSigIdentifier(eventField)}`, eventField, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEventFieldSig>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEventFieldSig[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEventFieldSigIdentifier(eventField: Pick<IEventFieldSig, 'fieldId'>): number {
    return eventField.fieldId;
  }

  compareEventFieldSig(o1: Pick<IEventFieldSig, 'fieldId'> | null, o2: Pick<IEventFieldSig, 'fieldId'> | null): boolean {
    return o1 && o2 ? this.getEventFieldSigIdentifier(o1) === this.getEventFieldSigIdentifier(o2) : o1 === o2;
  }

  addEventFieldSigToCollectionIfMissing<Type extends Pick<IEventFieldSig, 'fieldId'>>(
    eventFieldCollection: Type[],
    ...eventFieldsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const eventFields: Type[] = eventFieldsToCheck.filter(isPresent);
    if (eventFields.length > 0) {
      const eventFieldCollectionIdentifiers = eventFieldCollection.map(eventFieldItem => this.getEventFieldSigIdentifier(eventFieldItem)!);
      const eventFieldsToAdd = eventFields.filter(eventFieldItem => {
        const eventFieldIdentifier = this.getEventFieldSigIdentifier(eventFieldItem);
        if (eventFieldCollectionIdentifiers.includes(eventFieldIdentifier)) {
          return false;
        }
        eventFieldCollectionIdentifiers.push(eventFieldIdentifier);
        return true;
      });
      return [...eventFieldsToAdd, ...eventFieldCollection];
    }
    return eventFieldCollection;
  }
}
