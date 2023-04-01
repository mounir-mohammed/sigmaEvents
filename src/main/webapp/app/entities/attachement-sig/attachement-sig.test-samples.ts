import { IAttachementSig, NewAttachementSig } from './attachement-sig.model';

export const sampleWithRequiredData: IAttachementSig = {
  attachementId: 37309,
  attachementName: 'Administrator Granite Camp',
};

export const sampleWithPartialData: IAttachementSig = {
  attachementId: 13834,
  attachementName: 'open-source hard',
  attachementPath: 'green Koruna Island',
  attachementDescription: 'primary Trace Louisiana',
  attachementParams: 'compress gold',
  attachementAttributs: 'Markets',
};

export const sampleWithFullData: IAttachementSig = {
  attachementId: 30593,
  attachementName: 'Metal digital',
  attachementPath: 'Turks dedicated Garden',
  attachementBlob: '../fake-data/blob/hipster.png',
  attachementBlobContentType: 'unknown',
  attachementDescription: 'Vanuatu',
  attachementPhoto: '../fake-data/blob/hipster.png',
  attachementPhotoContentType: 'unknown',
  attachementParams: 'up Chief cross-platform',
  attachementAttributs: 'Cuba',
  attachementStat: false,
};

export const sampleWithNewData: NewAttachementSig = {
  attachementName: 'Account Handmade',
  attachementId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
