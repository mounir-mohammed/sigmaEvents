import { IEventSig } from 'app/entities/event-sig/event-sig.model';
import { IEventFormSig } from 'app/entities/event-form-sig/event-form-sig.model';

export interface IEventFieldSig {
  fieldId: number;
  fieldName?: string | null;
  fieldCategorie?: string | null;
  fieldDescription?: string | null;
  fieldType?: string | null;
  fieldParams?: string | null;
  fieldAttributs?: string | null;
  fieldStat?: boolean | null;
  event?: Pick<IEventSig, 'eventId' | 'eventName' | 'eventStat'> | null;
  eventForm?: Pick<IEventFormSig, 'formId' | 'formName' | 'formStat'> | null;
}

export type NewEventFieldSig = Omit<IEventFieldSig, 'fieldId'> & { fieldId: null };
