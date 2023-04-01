import { IAttachementTypeSig, NewAttachementTypeSig } from './attachement-type-sig.model';

export const sampleWithRequiredData: IAttachementTypeSig = {
  attachementTypeId: 65914,
  attachementTypeName: 'Cliff',
};

export const sampleWithPartialData: IAttachementTypeSig = {
  attachementTypeId: 70018,
  attachementTypeName: 'Franc Account quantifying',
  attachementTypeDescription: 'PNG',
  attachementTypeAttributs: 'quantifying Towels',
  attachementTypeStat: true,
};

export const sampleWithFullData: IAttachementTypeSig = {
  attachementTypeId: 87341,
  attachementTypeName: 'Branding Refined maroon',
  attachementTypeDescription: 'Beauty',
  attachementTypeParams: 'Home',
  attachementTypeAttributs: '24/365 Loan parsing',
  attachementTypeStat: false,
};

export const sampleWithNewData: NewAttachementTypeSig = {
  attachementTypeName: 'Tunnel architect New',
  attachementTypeId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
