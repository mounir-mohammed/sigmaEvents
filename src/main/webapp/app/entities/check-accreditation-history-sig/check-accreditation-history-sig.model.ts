import dayjs from 'dayjs/esm';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';
import { IAccreditationSig } from 'app/entities/accreditation-sig/accreditation-sig.model';

export interface ICheckAccreditationHistorySig {
  checkAccreditationHistoryId: number;
  checkAccreditationHistoryReadedCode?: string | null;
  checkAccreditationHistoryUserId?: number | null;
  checkAccreditationHistoryResult?: boolean | null;
  checkAccreditationHistoryError?: string | null;
  checkAccreditationHistoryDate?: dayjs.Dayjs | null;
  checkAccreditationHistoryLocalisation?: string | null;
  checkAccreditationHistoryIpAdresse?: string | null;
  checkAccreditationParams?: string | null;
  checkAccreditationAttributs?: string | null;
  checkAccreditationHistoryStat?: boolean | null;
  event?: Pick<IEventSig, 'eventId' | 'eventName' | 'eventStat'> | null;
  accreditation?: Pick<IAccreditationSig, 'accreditationId'> | null;
}

export type NewCheckAccreditationHistorySig = Omit<ICheckAccreditationHistorySig, 'checkAccreditationHistoryId'> & {
  checkAccreditationHistoryId: null;
};
