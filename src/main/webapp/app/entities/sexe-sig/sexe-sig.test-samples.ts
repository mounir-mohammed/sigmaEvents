import { ISexeSig, NewSexeSig } from './sexe-sig.model';

export const sampleWithRequiredData: ISexeSig = {
  sexeId: 34229,
  sexeValue: 'payment teal',
};

export const sampleWithPartialData: ISexeSig = {
  sexeId: 28755,
  sexeValue: 'invoice Home',
  sexeTypeParams: 'virtual',
  sexeTypeAttributs: 'transform',
  sexeStat: false,
};

export const sampleWithFullData: ISexeSig = {
  sexeId: 82566,
  sexeValue: 'IB hack',
  sexeDescription: 'Integration',
  sexeTypeParams: 'Granite Virginia Ariary',
  sexeTypeAttributs: 'Account Specialist paradigms',
  sexeStat: false,
};

export const sampleWithNewData: NewSexeSig = {
  sexeValue: 'capacitor auxiliary Cambridgeshire',
  sexeId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
