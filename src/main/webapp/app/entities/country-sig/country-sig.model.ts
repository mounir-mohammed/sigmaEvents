export interface ICountrySig {
  countryId: number;
  countryName?: string | null;
  countryCodeAlpha2?: string | null;
  countryCodeAlpha3?: string | null;
  countryTelCode?: string | null;
  countryDescription?: string | null;
  countryFlag?: string | null;
  countryFlagContentType?: string | null;
  countryParams?: string | null;
  countryAttributs?: string | null;
  countryStat?: boolean | null;
}

export type NewCountrySig = Omit<ICountrySig, 'countryId'> & { countryId: null };
