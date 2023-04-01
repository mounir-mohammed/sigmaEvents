import { IPrintingCentreSig, NewPrintingCentreSig } from './printing-centre-sig.model';

export const sampleWithRequiredData: IPrintingCentreSig = {
  printingCentreId: 48064,
  printingCentreName: 'Frozen state',
};

export const sampleWithPartialData: IPrintingCentreSig = {
  printingCentreId: 16084,
  printingCentreDescription: 'Dram interactive',
  printingCentreName: 'Granite payment',
  printingCentreAdresse: 'Technician',
  printingCentreEmail: '4th Decentralized',
  printingCentreTel: 'generating clicks-and-mortar invoice',
  printingCentreResponsableName: 'Manager',
};

export const sampleWithFullData: IPrintingCentreSig = {
  printingCentreId: 7071,
  printingCentreDescription: 'Republic',
  printingCentreName: 'Licensed',
  printingCentreLogo: '../fake-data/blob/hipster.png',
  printingCentreLogoContentType: 'unknown',
  printingCentreAdresse: 'workforce auxiliary',
  printingCentreEmail: 'application Berkshire',
  printingCentreTel: 'rich Programmable USB',
  printingCentreFax: 'incremental Mobility quantifying',
  printingCentreResponsableName: 'Account',
  printingParams: 'orchestration',
  printingAttributs: 'microchip',
  printingCentreStat: false,
};

export const sampleWithNewData: NewPrintingCentreSig = {
  printingCentreName: 'Home homogeneous Developer',
  printingCentreId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
