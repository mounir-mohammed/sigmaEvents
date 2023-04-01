import { IInfoSuppSig, NewInfoSuppSig } from './info-supp-sig.model';

export const sampleWithRequiredData: IInfoSuppSig = {
  infoSuppId: 86932,
  infoSuppName: 'bus',
};

export const sampleWithPartialData: IInfoSuppSig = {
  infoSuppId: 11120,
  infoSuppName: 'Lead dedicated Handcrafted',
  infoSuppDescription: 'Massachusetts Accounts Books',
  infoSuppParams: 'product',
  infoSuppAttributs: 'Facilitator efficient',
};

export const sampleWithFullData: IInfoSuppSig = {
  infoSuppId: 84562,
  infoSuppName: 'Florida Fresh Denar',
  infoSuppDescription: 'Metal Business-focused',
  infoSuppParams: 'core Ecuador',
  infoSuppAttributs: 'Licensed Principal pixel',
  infoSuppStat: true,
};

export const sampleWithNewData: NewInfoSuppSig = {
  infoSuppName: 'Outdoors leading-edge optical',
  infoSuppId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
