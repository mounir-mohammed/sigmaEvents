import { ICodeTypeSig, NewCodeTypeSig } from './code-type-sig.model';

export const sampleWithRequiredData: ICodeTypeSig = {
  codeTypeId: 49140,
  codeTypeValue: 'background',
};

export const sampleWithPartialData: ICodeTypeSig = {
  codeTypeId: 64,
  codeTypeValue: 'Stream',
  codeTypeDescription: 'hard',
  codeTypeParams: 'SAS Stand-alone Representative',
  codeTypeStat: true,
};

export const sampleWithFullData: ICodeTypeSig = {
  codeTypeId: 2667,
  codeTypeValue: 'SDR engineer ivory',
  codeTypeDescription: 'Ergonomic Uzbekistan application',
  codeTypeParams: 'Peso',
  codeTypeAttributs: 'brand',
  codeTypeStat: true,
};

export const sampleWithNewData: NewCodeTypeSig = {
  codeTypeValue: 'Sausages',
  codeTypeId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
