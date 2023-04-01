import { IAreaSig, NewAreaSig } from './area-sig.model';

export const sampleWithRequiredData: IAreaSig = {
  areaId: 11565,
  areaName: 'Steel African',
};

export const sampleWithPartialData: IAreaSig = {
  areaId: 78257,
  areaName: 'attitude-oriented',
  areaColor: 'Sleek',
  areaLogo: '../fake-data/blob/hipster.png',
  areaLogoContentType: 'unknown',
  areaStat: true,
};

export const sampleWithFullData: IAreaSig = {
  areaId: 53059,
  areaName: 'compress Balanced',
  areaAbreviation: 'Checking G',
  areaColor: 'National Wooden Incredible',
  areaDescription: 'Account B2B index',
  areaLogo: '../fake-data/blob/hipster.png',
  areaLogoContentType: 'unknown',
  areaParams: 'auxiliary Extended Rand',
  areaAttributs: 'Soap Fish',
  areaStat: false,
};

export const sampleWithNewData: NewAreaSig = {
  areaName: 'Dollar Technician',
  areaId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
