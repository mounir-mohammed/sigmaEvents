import { IAccreditationSig } from 'app/entities/accreditation-sig/accreditation-sig.model';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';

export interface INoteSig {
  noteId: number;
  noteValue?: string | null;
  noteDescription?: string | null;
  noteTypeParams?: string | null;
  noteTypeAttributs?: string | null;
  noteStat?: boolean | null;
  accreditation?: Pick<IAccreditationSig, 'accreditationId'> | null;
  event?: Pick<IEventSig, 'eventId' | 'eventName' | 'eventStat'> | null;
}

export type NewNoteSig = Omit<INoteSig, 'noteId'> & { noteId: null };
