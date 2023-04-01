import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEventFormSig, NewEventFormSig } from '../event-form-sig.model';

export type PartialUpdateEventFormSig = Partial<IEventFormSig> & Pick<IEventFormSig, 'formId'>;

export type EntityResponseType = HttpResponse<IEventFormSig>;
export type EntityArrayResponseType = HttpResponse<IEventFormSig[]>;

@Injectable({ providedIn: 'root' })
export class EventFormSigService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/event-forms');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(eventForm: NewEventFormSig): Observable<EntityResponseType> {
    return this.http.post<IEventFormSig>(this.resourceUrl, eventForm, { observe: 'response' });
  }

  update(eventForm: IEventFormSig): Observable<EntityResponseType> {
    return this.http.put<IEventFormSig>(`${this.resourceUrl}/${this.getEventFormSigIdentifier(eventForm)}`, eventForm, {
      observe: 'response',
    });
  }

  partialUpdate(eventForm: PartialUpdateEventFormSig): Observable<EntityResponseType> {
    return this.http.patch<IEventFormSig>(`${this.resourceUrl}/${this.getEventFormSigIdentifier(eventForm)}`, eventForm, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEventFormSig>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEventFormSig[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEventFormSigIdentifier(eventForm: Pick<IEventFormSig, 'formId'>): number {
    return eventForm.formId;
  }

  compareEventFormSig(o1: Pick<IEventFormSig, 'formId'> | null, o2: Pick<IEventFormSig, 'formId'> | null): boolean {
    return o1 && o2 ? this.getEventFormSigIdentifier(o1) === this.getEventFormSigIdentifier(o2) : o1 === o2;
  }

  addEventFormSigToCollectionIfMissing<Type extends Pick<IEventFormSig, 'formId'>>(
    eventFormCollection: Type[],
    ...eventFormsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const eventForms: Type[] = eventFormsToCheck.filter(isPresent);
    if (eventForms.length > 0) {
      const eventFormCollectionIdentifiers = eventFormCollection.map(eventFormItem => this.getEventFormSigIdentifier(eventFormItem)!);
      const eventFormsToAdd = eventForms.filter(eventFormItem => {
        const eventFormIdentifier = this.getEventFormSigIdentifier(eventFormItem);
        if (eventFormCollectionIdentifiers.includes(eventFormIdentifier)) {
          return false;
        }
        eventFormCollectionIdentifiers.push(eventFormIdentifier);
        return true;
      });
      return [...eventFormsToAdd, ...eventFormCollection];
    }
    return eventFormCollection;
  }
}
