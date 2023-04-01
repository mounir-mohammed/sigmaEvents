import { ICategorySig, NewCategorySig } from './category-sig.model';

export const sampleWithRequiredData: ICategorySig = {
  categoryId: 2529,
  categoryName: 'Namibia Sausages',
};

export const sampleWithPartialData: ICategorySig = {
  categoryId: 21394,
  categoryName: 'Principal transparent task-force',
  categoryAbreviation: 'service-de',
  categoryDescription: 'Salvador SDD',
  categoryLogo: '../fake-data/blob/hipster.png',
  categoryLogoContentType: 'unknown',
  categoryParams: 'hack Rustic interface',
};

export const sampleWithFullData: ICategorySig = {
  categoryId: 81542,
  categoryName: 'Account Utah',
  categoryAbreviation: 'Jewelery m',
  categoryColor: 'synthesize Valleys',
  categoryDescription: 'bifurcated Checking',
  categoryLogo: '../fake-data/blob/hipster.png',
  categoryLogoContentType: 'unknown',
  categoryParams: 'Balanced',
  categoryAttributs: 'Practical composite transmitting',
  categoryStat: true,
};

export const sampleWithNewData: NewCategorySig = {
  categoryName: 'Account global analyzing',
  categoryId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
