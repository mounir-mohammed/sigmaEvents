import { IEventSig } from 'app/entities/event-sig/event-sig.model';
import { IFonctionSig } from 'app/entities/fonction-sig/fonction-sig.model';

export interface IAreaSig {
  areaId: number;
  areaName?: string | null;
  areaAbreviation?: string | null;
  areaColor?: string | null;
  areaDescription?: string | null;
  areaLogo?: string | null;
  areaLogoContentType?: string | null;
  areaParams?: string | null;
  areaAttributs?: string | null;
  areaStat?: boolean | null;
  event?: Pick<IEventSig, 'eventId'> | null;
  fonctions?: Pick<IFonctionSig, 'fonctionId'>[] | null;
}

export type NewAreaSig = Omit<IAreaSig, 'areaId'> & { areaId: null };
