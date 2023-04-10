import dayjs from 'dayjs/esm';
import { IOperationTypeSig } from 'app/entities/operation-type-sig/operation-type-sig.model';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';

export interface IOperationHistorySig {
  operationHistoryId: number;
  operationHistoryDescription?: string | null;
  operationHistoryDate?: dayjs.Dayjs | null;
  operationHistoryUserID?: number | null;
  operationHistoryOldValue?: string | null;
  operationHistoryNewValue?: string | null;
  operationHistoryOldId?: number | null;
  operationHistoryNewId?: number | null;
  operationHistoryImportedFile?: string | null;
  operationHistoryImportedFilePath?: string | null;
  operationHistoryParams?: string | null;
  operationHistoryAttributs?: string | null;
  operationHistoryStat?: boolean | null;
  typeoperation?: Pick<IOperationTypeSig, 'operationTypeId' | 'operationTypeValue' | 'operationTypeStat'> | null;
  event?: Pick<IEventSig, 'eventId' | 'eventName' | 'eventStat'> | null;
}

export type NewOperationHistorySig = Omit<IOperationHistorySig, 'operationHistoryId'> & { operationHistoryId: null };
