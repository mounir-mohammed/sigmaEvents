export interface ICivilitySig {
  civilityId: number;
  civilityValue?: string | null;
  civilityDescription?: string | null;
  civilityCode?: string | null;
  civilityParams?: string | null;
  civilityAttributs?: string | null;
  civilityStat?: boolean | null;
}

export type NewCivilitySig = Omit<ICivilitySig, 'civilityId'> & { civilityId: null };
