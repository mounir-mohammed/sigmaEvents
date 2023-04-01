import { ILanguageSig, NewLanguageSig } from './language-sig.model';

export const sampleWithRequiredData: ILanguageSig = {
  languageId: 6445,
  languageCode: 'Unbranded Granite Facilitator',
  languageName: 'Argentina Sleek copying',
};

export const sampleWithPartialData: ILanguageSig = {
  languageId: 75251,
  languageCode: 'Jewelery Money Metal',
  languageName: 'TCP',
  languageParams: 'time-frame protocol',
  languageAttributs: 'global Producer',
  languageStat: false,
};

export const sampleWithFullData: ILanguageSig = {
  languageId: 89102,
  languageCode: 'program',
  languageName: 'payment',
  languageDescription: 'bluetooth ivory',
  languageParams: 'Tennessee deposit extend',
  languageAttributs: 'Checking',
  languageStat: true,
};

export const sampleWithNewData: NewLanguageSig = {
  languageCode: 'Steel invoice Web',
  languageName: 'Island Electronics',
  languageId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
