import { IEventFieldSig, NewEventFieldSig } from './event-field-sig.model';

export const sampleWithRequiredData: IEventFieldSig = {
  fieldId: 61676,
};

export const sampleWithPartialData: IEventFieldSig = {
  fieldId: 95409,
  fieldName: 'Manager Franc',
  fieldCategorie: 'algorithm wireless',
  fieldDescription: 'Wooden parse',
  fieldParams: 'optical Health payment',
  fieldStat: false,
};

export const sampleWithFullData: IEventFieldSig = {
  fieldId: 6447,
  fieldName: 'Direct Knoll',
  fieldCategorie: 'Tuna Lock',
  fieldDescription: 'Villages system Product',
  fieldType: 'Wisconsin Rubber Chair',
  fieldParams: 'Frozen',
  fieldAttributs: 'Intranet',
  fieldStat: true,
};

export const sampleWithNewData: NewEventFieldSig = {
  fieldId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
