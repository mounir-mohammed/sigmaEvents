import dayjs from 'dayjs/esm';

export interface ICloningSig {
  cloningId: number;
  cloningDescription?: string | null;
  cloningOldEventId?: number | null;
  cloningNewEventId?: number | null;
  cloningUserId?: number | null;
  cloningDate?: dayjs.Dayjs | null;
  clonedEntitys?: string | null;
  clonedParams?: string | null;
  clonedAttributs?: string | null;
  clonedStat?: boolean | null;
}

export type NewCloningSig = Omit<ICloningSig, 'cloningId'> & { cloningId: null };
