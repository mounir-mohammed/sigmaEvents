import dayjs from 'dayjs/esm';
import { ILanguageSig } from 'app/entities/language-sig/language-sig.model';

export interface IEventSig {
  eventId: number;
  eventName?: string | null;
  eventColor?: string | null;
  eventDescription?: string | null;
  eventAbreviation?: string | null;
  eventdateStart?: dayjs.Dayjs | null;
  eventdateEnd?: dayjs.Dayjs | null;
  eventPrintingModelId?: number | null;
  eventLogo?: string | null;
  eventLogoContentType?: string | null;
  eventBannerCenter?: string | null;
  eventBannerCenterContentType?: string | null;
  eventBannerRight?: string | null;
  eventBannerRightContentType?: string | null;
  eventBannerLeft?: string | null;
  eventBannerLeftContentType?: string | null;
  eventBannerBas?: string | null;
  eventBannerBasContentType?: string | null;
  eventWithPhoto?: boolean | null;
  eventNoCode?: boolean | null;
  eventCodeNoFilter?: boolean | null;
  eventCodeByTypeAccreditation?: boolean | null;
  eventCodeByTypeCategorie?: boolean | null;
  eventCodeByTypeFonction?: boolean | null;
  eventCodeByTypeOrganiz?: boolean | null;
  eventDefaultPrintingLanguage?: boolean | null;
  eventPCenterPrintingLanguage?: boolean | null;
  eventImported?: boolean | null;
  eventArchived?: boolean | null;
  eventArchiveFileName?: string | null;
  eventURL?: string | null;
  eventDomaine?: string | null;
  eventSousDomaine?: string | null;
  eventCloned?: boolean | null;
  eventParams?: string | null;
  eventAttributs?: string | null;
  eventStat?: boolean | null;
  language?: Pick<ILanguageSig, 'languageId'> | null;
}

export type NewEventSig = Omit<IEventSig, 'eventId'> & { eventId: null };
