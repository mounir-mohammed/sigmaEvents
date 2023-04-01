import { IPrintingTypeSig, NewPrintingTypeSig } from './printing-type-sig.model';

export const sampleWithRequiredData: IPrintingTypeSig = {
  printingTypeId: 75539,
  printingTypeValue: 'vertical',
};

export const sampleWithPartialData: IPrintingTypeSig = {
  printingTypeId: 43196,
  printingTypeValue: 'Cheese',
  printingTypeDescription: 'generating strategy Borders',
  printingTypeAttributs: 'driver',
  printingTypeStat: false,
};

export const sampleWithFullData: IPrintingTypeSig = {
  printingTypeId: 90756,
  printingTypeValue: 'killer Buckinghamshire distributed',
  printingTypeDescription: 'TCP payment User-centric',
  printingTypeParams: 'Chief transmitting Bike',
  printingTypeAttributs: 'Dynamic parsing Shoes',
  printingTypeStat: false,
};

export const sampleWithNewData: NewPrintingTypeSig = {
  printingTypeValue: 'GB primary',
  printingTypeId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
