import { IPrintingModelSig } from 'app/entities/printing-model-sig/printing-model-sig.model';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';

export interface IAccreditationTypeSig {
  accreditationTypeId: number;
  accreditationTypeValue?: string | null;
  accreditationTypeAbreviation?: string | null;
  accreditationTypeDescription?: string | null;
  accreditationTypeParams?: string | null;
  accreditationTypeAttributs?: string | null;
  accreditationTypeStat?: boolean | null;
  printingModel?: Pick<IPrintingModelSig, 'printingModelId' | 'printingModelName' | 'printingModelStat'> | null;
  event?: Pick<IEventSig, 'eventId' | 'eventName' | 'eventStat'> | null;
}

export type NewAccreditationTypeSig = Omit<IAccreditationTypeSig, 'accreditationTypeId'> & { accreditationTypeId: null };
