import { IOrganizSig, NewOrganizSig } from './organiz-sig.model';

export const sampleWithRequiredData: IOrganizSig = {
  organizId: 23124,
  organizName: 'Salad ROI compressing',
};

export const sampleWithPartialData: IOrganizSig = {
  organizId: 5747,
  organizName: 'panel connect',
  organizDescription: 'Trinidad deposit Cambridgeshire',
  organizLogo: '../fake-data/blob/hipster.png',
  organizLogoContentType: 'unknown',
  organizEmail: 'synergize',
};

export const sampleWithFullData: IOrganizSig = {
  organizId: 51641,
  organizName: 'withdrawal',
  organizDescription: 'Marketing Kansas',
  organizLogo: '../fake-data/blob/hipster.png',
  organizLogoContentType: 'unknown',
  organizTel: 'transmitting',
  organizFax: 'Pants bypass programming',
  organizEmail: 'Rubber firewall mobile',
  organizAdresse: 'reintermediate haptic',
  organizParams: 'action-items Rubber upward-trending',
  organizAttributs: 'violet magenta',
  organizStat: true,
};

export const sampleWithNewData: NewOrganizSig = {
  organizName: 'Principal Sharable',
  organizId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
