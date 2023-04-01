import { ICountrySig, NewCountrySig } from './country-sig.model';

export const sampleWithRequiredData: ICountrySig = {
  countryId: 4746,
  countryName: 'SCSI Configuration deposit',
  countryCodeAlpha2: 'Tuna',
};

export const sampleWithPartialData: ICountrySig = {
  countryId: 23601,
  countryName: 'knowledge deposit',
  countryCodeAlpha2: 'Progressive Martinique',
  countryFlag: '../fake-data/blob/hipster.png',
  countryFlagContentType: 'unknown',
  countryParams: 'Oklahoma',
};

export const sampleWithFullData: ICountrySig = {
  countryId: 77976,
  countryName: 'Open-architected Pre-emptive',
  countryCodeAlpha2: 'front-end North',
  countryCodeAlpha3: 'Buckinghamshire',
  countryTelCode: 'Fantastic JSON hack',
  countryDescription: 'orchestration',
  countryFlag: '../fake-data/blob/hipster.png',
  countryFlagContentType: 'unknown',
  countryParams: 'Outdoors',
  countryAttributs: 'middleware streamline',
  countryStat: true,
};

export const sampleWithNewData: NewCountrySig = {
  countryName: 'SDD',
  countryCodeAlpha2: 'CSS methodologies panel',
  countryId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
