import { ICitySig } from 'app/entities/city-sig/city-sig.model';
import { ICountrySig } from 'app/entities/country-sig/country-sig.model';
import { IOrganizSig } from 'app/entities/organiz-sig/organiz-sig.model';
import { IPrintingTypeSig } from 'app/entities/printing-type-sig/printing-type-sig.model';
import { IPrintingServerSig } from 'app/entities/printing-server-sig/printing-server-sig.model';
import { IPrintingModelSig } from 'app/entities/printing-model-sig/printing-model-sig.model';
import { ILanguageSig } from 'app/entities/language-sig/language-sig.model';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';

export interface IPrintingCentreSig {
  printingCentreId: number;
  printingCentreDescription?: string | null;
  printingCentreName?: string | null;
  printingCentreLogo?: string | null;
  printingCentreLogoContentType?: string | null;
  printingCentreAdresse?: string | null;
  printingCentreEmail?: string | null;
  printingCentreTel?: string | null;
  printingCentreFax?: string | null;
  printingCentreResponsableName?: string | null;
  printingParams?: string | null;
  printingAttributs?: string | null;
  printingCentreStat?: boolean | null;
  city?: Pick<ICitySig, 'cityId' | 'cityName' | 'cityStat'> | null;
  country?: Pick<ICountrySig, 'countryId' | 'countryName' | 'countryStat'> | null;
  organiz?: Pick<IOrganizSig, 'organizId' | 'organizName' | 'organizStat'> | null;
  printingType?: Pick<IPrintingTypeSig, 'printingTypeId' | 'printingTypeValue' | 'printingTypeStat'> | null;
  printingServer?: Pick<IPrintingServerSig, 'printingServerId' | 'printingServerName' | 'printingServerStat'> | null;
  printingModel?: Pick<IPrintingModelSig, 'printingModelId' | 'printingModelName' | 'printingModelStat'> | null;
  language?: Pick<ILanguageSig, 'languageId' | 'languageName' | 'languageStat'> | null;
  event?: Pick<IEventSig, 'eventId' | 'eventName' | 'eventStat' | 'eventdateStart' | 'eventdateEnd'> | null;
}

export type NewPrintingCentreSig = Omit<IPrintingCentreSig, 'printingCentreId'> & { printingCentreId: null };
