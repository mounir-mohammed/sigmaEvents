import dayjs from 'dayjs/esm';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';

export interface IDayPassInfoSig {
  dayPassInfoId: number;
  dayPassInfoName?: string | null;
  dayPassDescription?: string | null;
  dayPassLogo?: string | null;
  dayPassLogoContentType?: string | null;
  dayPassInfoCreationDate?: dayjs.Dayjs | null;
  dayPassInfoUpdateDate?: dayjs.Dayjs | null;
  dayPassInfoCreatedByuser?: string | null;
  dayPassInfoDateStart?: dayjs.Dayjs | null;
  dayPassInfoDateEnd?: dayjs.Dayjs | null;
  dayPassInfoNumber?: number | null;
  dayPassParams?: string | null;
  dayPassAttributs?: string | null;
  dayPassInfoStat?: boolean | null;
  event?: Pick<IEventSig, 'eventId' | 'eventName' | 'eventStat'> | null;
}

export type NewDayPassInfoSig = Omit<IDayPassInfoSig, 'dayPassInfoId'> & { dayPassInfoId: null };
