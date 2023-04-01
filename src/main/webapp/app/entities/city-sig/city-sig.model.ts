import { ICountrySig } from 'app/entities/country-sig/country-sig.model';

export interface ICitySig {
  cityId: number;
  cityName?: string | null;
  cityZipCode?: string | null;
  cityAbreviation?: string | null;
  cityDescription?: string | null;
  cityParams?: string | null;
  cityAttributs?: string | null;
  cityStat?: boolean | null;
  country?: Pick<ICountrySig, 'countryId'> | null;
}

export type NewCitySig = Omit<ICitySig, 'cityId'> & { cityId: null };
