import { IFonctionSig, NewFonctionSig } from './fonction-sig.model';

export const sampleWithRequiredData: IFonctionSig = {
  fonctionId: 57026,
  fonctionName: 'deposit',
};

export const sampleWithPartialData: IFonctionSig = {
  fonctionId: 64276,
  fonctionName: 'Corporate',
  fonctionColor: 'Gorgeous',
};

export const sampleWithFullData: IFonctionSig = {
  fonctionId: 52676,
  fonctionName: 'cyan Towels content',
  fonctionAbreviation: 'Mexico Acc',
  fonctionColor: 'Research SSL quantify',
  fonctionDescription: 'compressing Cotton',
  fonctionLogo: '../fake-data/blob/hipster.png',
  fonctionLogoContentType: 'unknown',
  fonctionParams: 'monetize forecast',
  fonctionAttributs: 'Account',
  fonctionStat: true,
};

export const sampleWithNewData: NewFonctionSig = {
  fonctionName: 'invoice Account Plastic',
  fonctionId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
