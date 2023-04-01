import dayjs from 'dayjs/esm';

import { IDayPassInfoSig, NewDayPassInfoSig } from './day-pass-info-sig.model';

export const sampleWithRequiredData: IDayPassInfoSig = {
  dayPassInfoId: 32770,
};

export const sampleWithPartialData: IDayPassInfoSig = {
  dayPassInfoId: 93080,
  dayPassInfoName: 'backing Ergonomic',
  dayPassInfoDateStart: dayjs('2023-03-31T22:04'),
  dayPassInfoDateEnd: dayjs('2023-03-31T22:59'),
  dayPassInfoStat: true,
};

export const sampleWithFullData: IDayPassInfoSig = {
  dayPassInfoId: 16024,
  dayPassInfoName: 'product',
  dayPassDescription: 'Uganda communities Brazilian',
  dayPassLogo: '../fake-data/blob/hipster.png',
  dayPassLogoContentType: 'unknown',
  dayPassInfoCreationDate: dayjs('2023-04-01T01:37'),
  dayPassInfoUpdateDate: dayjs('2023-04-01T00:42'),
  dayPassInfoCreatedByuser: 'grey',
  dayPassInfoDateStart: dayjs('2023-03-31T06:27'),
  dayPassInfoDateEnd: dayjs('2023-03-31T11:23'),
  dayPassInfoNumber: 2652,
  dayPassParams: 'Gorgeous systems',
  dayPassAttributs: 'Chips invoice compressing',
  dayPassInfoStat: false,
};

export const sampleWithNewData: NewDayPassInfoSig = {
  dayPassInfoId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
