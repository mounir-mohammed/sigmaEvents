import dayjs from 'dayjs/esm';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';
import { IEventFieldSig } from 'app/entities/event-field-sig/event-field-sig.model';

export interface IEventControlSig {
  controlId: number;
  controlName?: string | null;
  controlDescription?: string | null;
  controlType?: string | null;
  controlValueString?: string | null;
  controlValueLong?: number | null;
  controlValueDate?: dayjs.Dayjs | null;
  controlParams?: string | null;
  controlAttributs?: string | null;
  controlValueStat?: boolean | null;
  event?: Pick<IEventSig, 'eventId'> | null;
  eventField?: Pick<IEventFieldSig, 'fieldId'> | null;
}

export type NewEventControlSig = Omit<IEventControlSig, 'controlId'> & { controlId: null };
