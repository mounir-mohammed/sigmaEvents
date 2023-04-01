export interface ISexeSig {
  sexeId: number;
  sexeValue?: string | null;
  sexeDescription?: string | null;
  sexeTypeParams?: string | null;
  sexeTypeAttributs?: string | null;
  sexeStat?: boolean | null;
}

export type NewSexeSig = Omit<ISexeSig, 'sexeId'> & { sexeId: null };
