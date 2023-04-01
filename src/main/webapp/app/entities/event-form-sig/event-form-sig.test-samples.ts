import { IEventFormSig, NewEventFormSig } from './event-form-sig.model';

export const sampleWithRequiredData: IEventFormSig = {
  formId: 10626,
};

export const sampleWithPartialData: IEventFormSig = {
  formId: 2272,
  formName: 'Lilangeni',
  formDescription: 'Unit',
  formAttributs: 'utilize Handcrafted',
  formStat: false,
};

export const sampleWithFullData: IEventFormSig = {
  formId: 88487,
  formName: 'product',
  formDescription: 'Account payment',
  formParams: 'Table withdrawal Overpass',
  formAttributs: 'Communications Health up',
  formStat: true,
};

export const sampleWithNewData: NewEventFormSig = {
  formId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
