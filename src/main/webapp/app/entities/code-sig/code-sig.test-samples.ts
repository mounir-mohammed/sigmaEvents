import { ICodeSig, NewCodeSig } from './code-sig.model';

export const sampleWithRequiredData: ICodeSig = {
  codeId: 16252,
  codeValue: 'leverage Corporate',
};

export const sampleWithPartialData: ICodeSig = {
  codeId: 77045,
  codeValue: 'Lead magenta Arabia',
  codeUsed: false,
  codeParams: 'productize',
  codeAttributs: 'ivory Multi-channelled of',
  codeStat: false,
};

export const sampleWithFullData: ICodeSig = {
  codeId: 53102,
  codeForEntity: 'Jewelery infrastructures convergence',
  codeEntityValue: 'Mouse monitor',
  codeValue: 'deliverables Reduced',
  codeUsed: true,
  codeParams: 'benchmark Assistant Creative',
  codeAttributs: 'Soft Concrete',
  codeStat: true,
};

export const sampleWithNewData: NewCodeSig = {
  codeValue: 'monitor',
  codeId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
