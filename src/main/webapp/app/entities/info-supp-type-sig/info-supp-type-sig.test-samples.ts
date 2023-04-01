import { IInfoSuppTypeSig, NewInfoSuppTypeSig } from './info-supp-type-sig.model';

export const sampleWithRequiredData: IInfoSuppTypeSig = {
  infoSuppTypeId: 69303,
  infoSuppTypeName: 'Executive Account',
};

export const sampleWithPartialData: IInfoSuppTypeSig = {
  infoSuppTypeId: 66682,
  infoSuppTypeName: 'Franc',
  infoSuppTypeDescription: 'leverage brand',
  infoSuppTypeParams: 'Kwacha Cotton Accountability',
};

export const sampleWithFullData: IInfoSuppTypeSig = {
  infoSuppTypeId: 74468,
  infoSuppTypeName: 'capacitor Dinar Knoll',
  infoSuppTypeDescription: 'Rubber Frozen Account',
  infoSuppTypeParams: 'Sleek',
  infoSuppTypeAttributs: 'port',
  infoSuppTypeStat: false,
};

export const sampleWithNewData: NewInfoSuppTypeSig = {
  infoSuppTypeName: 'transmitting',
  infoSuppTypeId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
