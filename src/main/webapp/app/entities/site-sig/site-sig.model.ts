import { ICitySig } from 'app/entities/city-sig/city-sig.model';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';
import { IAccreditationSig } from 'app/entities/accreditation-sig/accreditation-sig.model';

export interface ISiteSig {
  siteId: number;
  siteName?: string | null;
  siteColor?: string | null;
  siteAbreviation?: string | null;
  siteDescription?: string | null;
  siteLogo?: string | null;
  siteLogoContentType?: string | null;
  siteAdresse?: string | null;
  siteEmail?: string | null;
  siteTel?: string | null;
  siteFax?: string | null;
  siteResponsableName?: string | null;
  siteParams?: string | null;
  siteAttributs?: string | null;
  siteStat?: boolean | null;
  city?: Pick<ICitySig, 'cityId' | 'cityName' | 'cityStat'> | null;
  event?: Pick<IEventSig, 'eventId' | 'eventName' | 'eventStat'> | null;
  accreditations?: Pick<IAccreditationSig, 'accreditationId'>[] | null;
}

export type NewSiteSig = Omit<ISiteSig, 'siteId'> & { siteId: null };
