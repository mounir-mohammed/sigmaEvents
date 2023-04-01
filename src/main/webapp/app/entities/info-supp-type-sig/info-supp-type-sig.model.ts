export interface IInfoSuppTypeSig {
  infoSuppTypeId: number;
  infoSuppTypeName?: string | null;
  infoSuppTypeDescription?: string | null;
  infoSuppTypeParams?: string | null;
  infoSuppTypeAttributs?: string | null;
  infoSuppTypeStat?: boolean | null;
}

export type NewInfoSuppTypeSig = Omit<IInfoSuppTypeSig, 'infoSuppTypeId'> & { infoSuppTypeId: null };
