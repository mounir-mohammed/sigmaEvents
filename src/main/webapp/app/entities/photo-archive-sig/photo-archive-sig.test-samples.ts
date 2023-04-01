import { IPhotoArchiveSig, NewPhotoArchiveSig } from './photo-archive-sig.model';

export const sampleWithRequiredData: IPhotoArchiveSig = {
  photoArchiveId: 67251,
  photoArchiveName: 'Mission Generic TCP',
};

export const sampleWithPartialData: IPhotoArchiveSig = {
  photoArchiveId: 38712,
  photoArchiveName: 'pixel synthesizing',
  photoArchivePhoto: '../fake-data/blob/hipster.png',
  photoArchivePhotoContentType: 'unknown',
  photoArchiveDescription: 'Mauritius proactive',
};

export const sampleWithFullData: IPhotoArchiveSig = {
  photoArchiveId: 25013,
  photoArchiveName: 'cutting-edge Eritrea',
  photoArchivePath: 'systematic',
  photoArchivePhoto: '../fake-data/blob/hipster.png',
  photoArchivePhotoContentType: 'unknown',
  photoArchiveDescription: 'Integration Loan',
  photoArchiveParams: 'digital',
  photoArchiveAttributs: 'Borders multi-byte Health',
  photoArchiveStat: false,
};

export const sampleWithNewData: NewPhotoArchiveSig = {
  photoArchiveName: 'deposit Handmade Rustic',
  photoArchiveId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
