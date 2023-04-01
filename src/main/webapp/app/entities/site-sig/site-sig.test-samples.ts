import { ISiteSig, NewSiteSig } from './site-sig.model';

export const sampleWithRequiredData: ISiteSig = {
  siteId: 70275,
  siteName: 'Loop',
};

export const sampleWithPartialData: ISiteSig = {
  siteId: 77311,
  siteName: 'turn-key Optional indexing',
  siteDescription: 'Home Rubber Beauty',
  siteLogo: '../fake-data/blob/hipster.png',
  siteLogoContentType: 'unknown',
  siteEmail: 'Ball Solutions Way',
  siteTel: 'synthesize scale',
};

export const sampleWithFullData: ISiteSig = {
  siteId: 93355,
  siteName: 'Program',
  siteColor: 'real-time Pennsylvania',
  siteAbreviation: 'compress c',
  siteDescription: 'plum Data Cheese',
  siteLogo: '../fake-data/blob/hipster.png',
  siteLogoContentType: 'unknown',
  siteAdresse: 'monitor',
  siteEmail: 'Assistant hack',
  siteTel: 'array',
  siteFax: 'Accountability AI online',
  siteResponsableName: 'AI productize Utah',
  siteParams: 'Investment',
  siteAttributs: 'Facilitator Handcrafted grow',
  siteStat: false,
};

export const sampleWithNewData: NewSiteSig = {
  siteName: 'hack Chicken calculate',
  siteId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
