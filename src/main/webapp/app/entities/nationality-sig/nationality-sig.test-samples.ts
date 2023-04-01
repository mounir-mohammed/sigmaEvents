import { INationalitySig, NewNationalitySig } from './nationality-sig.model';

export const sampleWithRequiredData: INationalitySig = {
  nationalityId: 30424,
  nationalityValue: 'Louisiana National',
};

export const sampleWithPartialData: INationalitySig = {
  nationalityId: 92283,
  nationalityValue: 'partnerships Intelligent',
  nationalityAbreviation: 'Soap azure',
  nationalityDescription: 'bluetooth Virtual',
  nationalityFlag: '../fake-data/blob/hipster.png',
  nationalityFlagContentType: 'unknown',
  nationalityAttributs: 'value-added green Consultant',
};

export const sampleWithFullData: INationalitySig = {
  nationalityId: 20415,
  nationalityValue: 'Organic',
  nationalityAbreviation: 'Response H',
  nationalityDescription: 'framework',
  nationalityFlag: '../fake-data/blob/hipster.png',
  nationalityFlagContentType: 'unknown',
  nationalityParams: 'Enterprise-wide',
  nationalityAttributs: 'Personal',
  nationalityStat: false,
};

export const sampleWithNewData: NewNationalitySig = {
  nationalityValue: 'drive Practical',
  nationalityId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
