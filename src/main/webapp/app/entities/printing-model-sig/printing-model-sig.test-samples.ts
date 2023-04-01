import { IPrintingModelSig, NewPrintingModelSig } from './printing-model-sig.model';

export const sampleWithRequiredData: IPrintingModelSig = {
  printingModelId: 97913,
  printingModelName: 'capacitor',
  printingModelFile: 'Industrial Car Underpass',
  printingModelPath: 'innovate connecting',
};

export const sampleWithPartialData: IPrintingModelSig = {
  printingModelId: 49163,
  printingModelName: 'Internal well-modulated Solutions',
  printingModelFile: 'Pants forecast Berkshire',
  printingModelPath: 'Cordoba Montserrat indexing',
  printingModelDescription: 'Grove',
  printingModelData: '../fake-data/blob/hipster.png',
  printingModelDataContentType: 'unknown',
  printingModelParams: 'Chair up indigo',
  printingModelAttributs: 'neural-net',
  printingModelStat: false,
};

export const sampleWithFullData: IPrintingModelSig = {
  printingModelId: 57049,
  printingModelName: 'sensor array Garden',
  printingModelFile: 'SMTP',
  printingModelPath: 'Rial mobile',
  printingModelDescription: 'frame Account',
  printingModelData: '../fake-data/blob/hipster.png',
  printingModelDataContentType: 'unknown',
  printingModelParams: 'Directives Producer',
  printingModelAttributs: 'Developer',
  printingModelStat: true,
};

export const sampleWithNewData: NewPrintingModelSig = {
  printingModelName: 'quantifying Steel',
  printingModelFile: 'Generic Future',
  printingModelPath: 'Soft Ameliorated',
  printingModelId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
