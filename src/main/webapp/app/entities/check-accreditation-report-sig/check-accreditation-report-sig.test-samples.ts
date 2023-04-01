import { ICheckAccreditationReportSig, NewCheckAccreditationReportSig } from './check-accreditation-report-sig.model';

export const sampleWithRequiredData: ICheckAccreditationReportSig = {
  checkAccreditationReportId: 2469,
};

export const sampleWithPartialData: ICheckAccreditationReportSig = {
  checkAccreditationReportId: 72001,
  checkAccreditationReportCINPhoto: '../fake-data/blob/hipster.png',
  checkAccreditationReportCINPhotoContentType: 'unknown',
  checkAccreditationReportParams: 'bypass Shoes',
  checkAccreditationReportStat: false,
};

export const sampleWithFullData: ICheckAccreditationReportSig = {
  checkAccreditationReportId: 60090,
  checkAccreditationReportDescription: 'systematic Rubber',
  checkAccreditationReportPersonPhoto: '../fake-data/blob/hipster.png',
  checkAccreditationReportPersonPhotoContentType: 'unknown',
  checkAccreditationReportCINPhoto: '../fake-data/blob/hipster.png',
  checkAccreditationReportCINPhotoContentType: 'unknown',
  checkAccreditationReportAttachment: '../fake-data/blob/hipster.png',
  checkAccreditationReportAttachmentContentType: 'unknown',
  checkAccreditationReportParams: 'invoice Grocery quantify',
  checkAccreditationReportAttributs: 'synthesizing',
  checkAccreditationReportStat: true,
};

export const sampleWithNewData: NewCheckAccreditationReportSig = {
  checkAccreditationReportId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
