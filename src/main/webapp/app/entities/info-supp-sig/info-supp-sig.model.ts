import { IInfoSuppTypeSig } from 'app/entities/info-supp-type-sig/info-supp-type-sig.model';
import { IAccreditationSig } from 'app/entities/accreditation-sig/accreditation-sig.model';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';

export interface IInfoSuppSig {
  infoSuppId: number;
  infoSuppName?: string | null;
  infoSuppDescription?: string | null;
  infoSuppParams?: string | null;
  infoSuppAttributs?: string | null;
  infoSuppStat?: boolean | null;
  infoSuppType?: Pick<IInfoSuppTypeSig, 'infoSuppTypeId' | 'infoSuppTypeName' | 'infoSuppTypeStat'> | null;
  accreditation?: Pick<IAccreditationSig, 'accreditationId'> | null;
  event?: Pick<IEventSig, 'eventId' | 'eventName' | 'eventStat'> | null;
}

export type NewInfoSuppSig = Omit<IInfoSuppSig, 'infoSuppId'> & { infoSuppId: null };
