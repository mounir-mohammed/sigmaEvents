import { ICountrySig } from 'app/entities/country-sig/country-sig.model';
import { ICitySig } from 'app/entities/city-sig/city-sig.model';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';

export interface IOrganizSig {
  organizId: number;
  organizName?: string | null;
  organizDescription?: string | null;
  organizLogo?: string | null;
  organizLogoContentType?: string | null;
  organizTel?: string | null;
  organizFax?: string | null;
  organizEmail?: string | null;
  organizAdresse?: string | null;
  organizParams?: string | null;
  organizAttributs?: string | null;
  organizStat?: boolean | null;
  country?: Pick<ICountrySig, 'countryId' | 'countryName' | 'countryStat'> | null;
  city?: Pick<ICitySig, 'cityId' | 'cityName' | 'cityStat'> | null;
  event?: Pick<IEventSig, 'eventId' | 'eventName' | 'eventStat'> | null;
}

export type NewOrganizSig = Omit<IOrganizSig, 'organizId'> & { organizId: null };
