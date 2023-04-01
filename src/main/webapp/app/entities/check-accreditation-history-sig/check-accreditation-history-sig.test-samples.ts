import dayjs from 'dayjs/esm';

import { ICheckAccreditationHistorySig, NewCheckAccreditationHistorySig } from './check-accreditation-history-sig.model';

export const sampleWithRequiredData: ICheckAccreditationHistorySig = {
  checkAccreditationHistoryId: 75098,
};

export const sampleWithPartialData: ICheckAccreditationHistorySig = {
  checkAccreditationHistoryId: 61938,
  checkAccreditationHistoryReadedCode: 'Car',
  checkAccreditationHistoryUserId: 41048,
  checkAccreditationHistoryError: 'Bedfordshire programming Metal',
  checkAccreditationHistoryLocalisation: 'Avon',
  checkAccreditationHistoryIpAdresse: 'Account Buckinghamshire open-source',
  checkAccreditationParams: 'PNG',
  checkAccreditationHistoryStat: true,
};

export const sampleWithFullData: ICheckAccreditationHistorySig = {
  checkAccreditationHistoryId: 1758,
  checkAccreditationHistoryReadedCode: 'Division azure',
  checkAccreditationHistoryUserId: 40681,
  checkAccreditationHistoryResult: true,
  checkAccreditationHistoryError: 'Drive Market',
  checkAccreditationHistoryDate: dayjs('2023-03-31T14:20'),
  checkAccreditationHistoryLocalisation: 'Executive Computer',
  checkAccreditationHistoryIpAdresse: 'programming',
  checkAccreditationParams: 'Indiana expedite',
  checkAccreditationAttributs: 'Account',
  checkAccreditationHistoryStat: false,
};

export const sampleWithNewData: NewCheckAccreditationHistorySig = {
  checkAccreditationHistoryId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
