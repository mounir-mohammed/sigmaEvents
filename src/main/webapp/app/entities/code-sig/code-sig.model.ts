import { ICodeTypeSig } from 'app/entities/code-type-sig/code-type-sig.model';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';

export interface ICodeSig {
  codeId: number;
  codeForEntity?: string | null;
  codeEntityValue?: string | null;
  codeValue?: string | null;
  codeUsed?: boolean | null;
  codeParams?: string | null;
  codeAttributs?: string | null;
  codeStat?: boolean | null;
  codeType?: Pick<ICodeTypeSig, 'codeTypeId' | 'codeTypeValue' | 'codeTypeStat'> | null;
  event?: Pick<IEventSig, 'eventId' | 'eventName' | 'eventStat'> | null;
}

export type NewCodeSig = Omit<ICodeSig, 'codeId'> & { codeId: null };
