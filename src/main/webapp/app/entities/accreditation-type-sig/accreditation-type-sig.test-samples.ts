import { IAccreditationTypeSig, NewAccreditationTypeSig } from './accreditation-type-sig.model';

export const sampleWithRequiredData: IAccreditationTypeSig = {
  accreditationTypeId: 81994,
  accreditationTypeValue: 'redundant Pizza',
};

export const sampleWithPartialData: IAccreditationTypeSig = {
  accreditationTypeId: 92186,
  accreditationTypeValue: 'brand Internal Shoes',
  accreditationTypeAbreviation: 'Directives',
  accreditationTypeDescription: 'Money disintermediate',
  accreditationTypeAttributs: 'pink Georgia cross-media',
};

export const sampleWithFullData: IAccreditationTypeSig = {
  accreditationTypeId: 885,
  accreditationTypeValue: 'Refined Bike solid',
  accreditationTypeAbreviation: 'Identity S',
  accreditationTypeDescription: 'Agent synthesize',
  accreditationTypeParams: 'Loan',
  accreditationTypeAttributs: 'navigate',
  accreditationTypeStat: true,
};

export const sampleWithNewData: NewAccreditationTypeSig = {
  accreditationTypeValue: 'embrace',
  accreditationTypeId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
