import dayjs from 'dayjs/esm';
import { ILanguageSig } from 'app/entities/language-sig/language-sig.model';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';

export interface ISettingSig {
  settingId: number;
  settingParentId?: number | null;
  settingType?: string | null;
  settingNameClass?: string | null;
  settingDataType?: string | null;
  settingDescription?: string | null;
  settingValueString?: string | null;
  settingValueLong?: number | null;
  settingValueDate?: dayjs.Dayjs | null;
  settingValueBoolean?: boolean | null;
  settingValueBlob?: string | null;
  settingValueBlobContentType?: string | null;
  settingParams?: string | null;
  settingAttributs?: string | null;
  settingStat?: boolean | null;
  language?: Pick<ILanguageSig, 'languageId'> | null;
  event?: Pick<IEventSig, 'eventId'> | null;
}

export type NewSettingSig = Omit<ISettingSig, 'settingId'> & { settingId: null };
