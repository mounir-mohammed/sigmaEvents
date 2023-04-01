import dayjs from 'dayjs/esm';

import { ISettingSig, NewSettingSig } from './setting-sig.model';

export const sampleWithRequiredData: ISettingSig = {
  settingId: 51292,
  settingType: 'composite',
  settingNameClass: 'Aruban Table 1080p',
  settingDataType: 'indigo',
};

export const sampleWithPartialData: ISettingSig = {
  settingId: 49059,
  settingType: 'SCSI vortals Movies',
  settingNameClass: 'Berkshire models',
  settingDataType: 'bluetooth',
  settingDescription: 'red application',
  settingValueString: 'Mandatory Pound encryption',
  settingAttributs: 'Program Usability Progressive',
};

export const sampleWithFullData: ISettingSig = {
  settingId: 28285,
  settingParentId: 2354,
  settingType: 'primary',
  settingNameClass: 'connecting deposit',
  settingDataType: 'Station Designer',
  settingDescription: 'Borders auxiliary quantifying',
  settingValueString: 'programming',
  settingValueLong: 12285,
  settingValueDate: dayjs('2023-03-31T09:02'),
  settingValueBoolean: true,
  settingValueBlob: '../fake-data/blob/hipster.png',
  settingValueBlobContentType: 'unknown',
  settingParams: 'Concrete Account driver',
  settingAttributs: 'Direct Buckinghamshire',
  settingStat: true,
};

export const sampleWithNewData: NewSettingSig = {
  settingType: 'Wooden wireless Papua',
  settingNameClass: 'Berkshire web-enabled Rapid',
  settingDataType: 'Clothing Progressive',
  settingId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
