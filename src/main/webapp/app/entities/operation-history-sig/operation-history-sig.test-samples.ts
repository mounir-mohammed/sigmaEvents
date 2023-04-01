import dayjs from 'dayjs/esm';

import { IOperationHistorySig, NewOperationHistorySig } from './operation-history-sig.model';

export const sampleWithRequiredData: IOperationHistorySig = {
  operationHistoryId: 3762,
};

export const sampleWithPartialData: IOperationHistorySig = {
  operationHistoryId: 20973,
  operationHistoryDescription: 'Plains Inverse',
  operationHistoryDate: dayjs('2023-03-31T03:47'),
  operationHistoryUserID: 78596,
  operationHistoryOldValue: 'Manager Isle',
  operationHistoryParams: 'mobile',
  operationHistoryAttributs: 'IB Squares Mandatory',
};

export const sampleWithFullData: IOperationHistorySig = {
  operationHistoryId: 32869,
  operationHistoryDescription: 'Savings',
  operationHistoryDate: dayjs('2023-03-31T04:23'),
  operationHistoryUserID: 18263,
  operationHistoryOldValue: 'Rubber feed auxiliary',
  operationHistoryNewValue: 'Facilitator Dollar',
  operationHistoryOldId: 97570,
  operationHistoryNewId: 73109,
  operationHistoryImportedFile: 'calculating Factors',
  operationHistoryImportedFilePath: 'Forest',
  operationHistoryParams: 'Directives structure transmitting',
  operationHistoryAttributs: 'connect Loan',
  operationHistoryStat: false,
};

export const sampleWithNewData: NewOperationHistorySig = {
  operationHistoryId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
