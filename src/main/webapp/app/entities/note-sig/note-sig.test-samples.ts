import { INoteSig, NewNoteSig } from './note-sig.model';

export const sampleWithRequiredData: INoteSig = {
  noteId: 79739,
  noteValue: 'deposit Liaison',
};

export const sampleWithPartialData: INoteSig = {
  noteId: 52340,
  noteValue: 'Garden Developer',
  noteTypeParams: 'value-added Convertible',
  noteTypeAttributs: 'Harbor Ouguiya cross-platform',
  noteStat: false,
};

export const sampleWithFullData: INoteSig = {
  noteId: 92085,
  noteValue: 'Loan',
  noteDescription: 'GB',
  noteTypeParams: 'BCEAO moratorium Ohio',
  noteTypeAttributs: 'Object-based end-to-end Future',
  noteStat: false,
};

export const sampleWithNewData: NewNoteSig = {
  noteValue: 'Electronics',
  noteId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
