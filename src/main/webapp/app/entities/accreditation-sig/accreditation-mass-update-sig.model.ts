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

export interface IAccreditationMassUpdateSig {
  accreditationIds: number[];
  accreditationOccupation?: string | null;
  accreditationDescription?: string | null;
  accreditationPhoto?: string | null;
  accreditationPhotoContentType?: string | null;
  accreditationDateStart?: dayjs.Dayjs | null;
  accreditationDateEnd?: dayjs.Dayjs | null;
  accreditationParams?: string | null;
  accreditationAttributs?: string | null;
  accreditationStat?: boolean | null;
  accreditationActivated?: boolean | null;
  sites?: ISiteSig;
  event?: IEventSig;
  civility?: ICivilitySig;
  sexe?: ISexeSig;
  nationality?: INationalitySig;
  country?: ICountrySig;
  city?: ICitySig;
  category?: ICategorySig;
  fonction?: IFonctionSig;
  organiz?: IOrganizSig;
  accreditationType?: IAccreditationTypeSig;
  status?: IStatusSig;
  attachement?: IAttachementSig;
  code?: ICodeSig;
  dayPassInfo?: IDayPassInfoSig;
  accreditationUpdateSites?: boolean | null;
}

export type NewAccreditationMassUpdateSig = Omit<IAccreditationMassUpdateSig, 'accreditationIds'> & { accreditationIds: null };
