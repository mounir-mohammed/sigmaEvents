import dayjs from 'dayjs/esm';
import { ISiteSig } from 'app/entities/site-sig/site-sig.model';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';
import { ICivilitySig } from 'app/entities/civility-sig/civility-sig.model';
import { ISexeSig } from 'app/entities/sexe-sig/sexe-sig.model';
import { INationalitySig } from 'app/entities/nationality-sig/nationality-sig.model';
import { ICountrySig } from 'app/entities/country-sig/country-sig.model';
import { ICitySig } from 'app/entities/city-sig/city-sig.model';
import { ICategorySig } from 'app/entities/category-sig/category-sig.model';
import { IFonctionSig } from 'app/entities/fonction-sig/fonction-sig.model';
import { IOrganizSig } from 'app/entities/organiz-sig/organiz-sig.model';
import { IAccreditationTypeSig } from 'app/entities/accreditation-type-sig/accreditation-type-sig.model';
import { IStatusSig } from 'app/entities/status-sig/status-sig.model';
import { IAttachementSig } from 'app/entities/attachement-sig/attachement-sig.model';
import { ICodeSig } from 'app/entities/code-sig/code-sig.model';
import { IDayPassInfoSig } from 'app/entities/day-pass-info-sig/day-pass-info-sig.model';

export interface IAccreditationSig {
  accreditationId: number;
  accreditationFirstName?: string | null;
  accreditationSecondName?: string | null;
  accreditationLastName?: string | null;
  accreditationBirthDay?: dayjs.Dayjs | null;
  accreditationSexe?: string | null;
  accreditationOccupation?: string | null;
  accreditationDescription?: string | null;
  accreditationEmail?: string | null;
  accreditationTel?: string | null;
  accreditationPhoto?: string | null;
  accreditationPhotoContentType?: string | null;
  accreditationCinId?: string | null;
  accreditationPasseportId?: string | null;
  accreditationCartePresseId?: string | null;
  accreditationCarteProfessionnelleId?: string | null;
  accreditationCreationDate?: dayjs.Dayjs | null;
  accreditationUpdateDate?: dayjs.Dayjs | null;
  accreditationCreatedByuser?: string | null;
  accreditationUpdatedByuser?: string | null;
  accreditationPrintedByuser?: string | null;
  accreditationPrintDate?: dayjs.Dayjs | null;
  accreditationDateStart?: dayjs.Dayjs | null;
  accreditationDateEnd?: dayjs.Dayjs | null;
  accreditationPrintStat?: boolean | null;
  accreditationPrintNumber?: number | null;
  accreditationParams?: string | null;
  accreditationAttributs?: string | null;
  accreditationStat?: boolean | null;
  accreditationActivated?: boolean | null;
  accreditationPrintingModel?: any;
  sites?:
    | Pick<ISiteSig, 'siteId' | 'siteName' | 'siteAbreviation' | 'siteColor' | 'siteStat' | 'siteLogo' | 'siteLogoContentType'>[]
    | null;
  event?: Pick<
    IEventSig,
    | 'eventId'
    | 'eventName'
    | 'eventAbreviation'
    | 'eventStat'
    | 'eventPrintingModelId'
    | 'eventWithPhoto'
    | 'eventNoCode'
    | 'eventCodeNoFilter'
    | 'eventCodeByTypeAccreditation'
    | 'eventCodeByTypeCategorie'
    | 'eventCodeByTypeFonction'
    | 'eventCodeByTypeOrganiz'
    | 'eventDefaultPrintingLanguage'
    | 'eventPCenterPrintingLanguage'
    | 'eventLogo'
    | 'eventLogoContentType'
  > | null;
  civility?: Pick<ICivilitySig, 'civilityId' | 'civilityValue' | 'civilityStat'> | null;
  sexe?: Pick<ISexeSig, 'sexeId' | 'sexeValue' | 'sexeStat'> | null;
  nationality?: Pick<
    INationalitySig,
    'nationalityId' | 'nationalityValue' | 'nationalityFlag' | 'nationalityFlagContentType' | 'nationalityStat'
  > | null;
  country?: Pick<ICountrySig, 'countryId' | 'countryName' | 'countryStat'> | null;
  city?: Pick<ICitySig, 'cityId' | 'cityName' | 'cityStat'> | null;
  category?: Pick<
    ICategorySig,
    'categoryId' | 'categoryName' | 'categoryAbreviation' | 'categoryStat' | 'categoryLogo' | 'categoryLogoContentType' | 'printingModel'
  > | null;
  fonction?: Pick<
    IFonctionSig,
    'fonctionId' | 'fonctionName' | 'fonctionAbreviation' | 'fonctionStat' | 'fonctionLogo' | 'fonctionLogoContentType' | 'areas'
  > | null;
  organiz?: Pick<IOrganizSig, 'organizId' | 'organizName' | 'organizStat' | 'organizLogo' | 'organizLogoContentType'> | null;
  accreditationType?: Pick<
    IAccreditationTypeSig,
    'accreditationTypeId' | 'accreditationTypeValue' | 'accreditationTypeStat' | 'printingModel'
  > | null;
  status?: Pick<IStatusSig, 'statusId' | 'statusName' | 'statusColor'> | null;
  attachement?: Pick<IAttachementSig, 'attachementId' | 'attachementName' | 'attachementStat'> | null;
  code?: Pick<ICodeSig, 'codeId' | 'codeValue' | 'codeStat'> | null;
  dayPassInfo?: Pick<IDayPassInfoSig, 'dayPassInfoId' | 'dayPassInfoName' | 'dayPassInfoStat'> | null;
}

export type NewAccreditationSig = Omit<IAccreditationSig, 'accreditationId'> & { accreditationId: null };
