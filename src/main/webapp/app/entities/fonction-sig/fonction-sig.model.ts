import { IAreaSig } from 'app/entities/area-sig/area-sig.model';
import { ICategorySig } from 'app/entities/category-sig/category-sig.model';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';

export interface IFonctionSig {
  fonctionId: number;
  fonctionName?: string | null;
  fonctionAbreviation?: string | null;
  fonctionColor?: string | null;
  fonctionDescription?: string | null;
  fonctionLogo?: string | null;
  fonctionLogoContentType?: string | null;
  fonctionParams?: string | null;
  fonctionAttributs?: string | null;
  fonctionStat?: boolean | null;
  areas?: Pick<IAreaSig, 'areaId' | 'areaName' | 'areaAbreviation' | 'areaStat'>[] | null;
  category?: Pick<ICategorySig, 'categoryId' | 'categoryName' | 'categoryStat'> | null;
  event?: Pick<IEventSig, 'eventId' | 'eventName' | 'eventStat'> | null;
}

export type NewFonctionSig = Omit<IFonctionSig, 'fonctionId'> & { fonctionId: null };
