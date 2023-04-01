import { IEventSig } from 'app/entities/event-sig/event-sig.model';

export interface IEventFormSig {
  formId: number;
  formName?: string | null;
  formDescription?: string | null;
  formParams?: string | null;
  formAttributs?: string | null;
  formStat?: boolean | null;
  event?: Pick<IEventSig, 'eventId'> | null;
}

export type NewEventFormSig = Omit<IEventFormSig, 'formId'> & { formId: null };
