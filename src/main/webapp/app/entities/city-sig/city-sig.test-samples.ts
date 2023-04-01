import { ICitySig, NewCitySig } from './city-sig.model';

export const sampleWithRequiredData: ICitySig = {
  cityId: 79135,
  cityName: 'up multi-byte',
  cityZipCode: 'Movies',
};

export const sampleWithPartialData: ICitySig = {
  cityId: 54860,
  cityName: 'Soap Metrics',
  cityZipCode: 'Product Health',
  cityAbreviation: 'District',
  cityDescription: 'Junction',
  cityParams: 'zero open-source',
  cityStat: true,
};

export const sampleWithFullData: ICitySig = {
  cityId: 63924,
  cityName: 'Movies',
  cityZipCode: 'program',
  cityAbreviation: 'Internatio',
  cityDescription: 'bandwidth Investor Ways',
  cityParams: 'invoice payment',
  cityAttributs: 'back',
  cityStat: false,
};

export const sampleWithNewData: NewCitySig = {
  cityName: 'Re-engineered Rupiah compressing',
  cityZipCode: 'mobile',
  cityId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
