import { ILogHistorySig, NewLogHistorySig } from './log-history-sig.model';

export const sampleWithRequiredData: ILogHistorySig = {
  logHistory: 95409,
};

export const sampleWithPartialData: ILogHistorySig = {
  logHistory: 71107,
  logHistoryParams: 'Lane',
  logHistoryAttributs: 'index',
};

export const sampleWithFullData: ILogHistorySig = {
  logHistory: 20931,
  logHistoryDescription: 'Syrian',
  logHistoryParams: 'Liaison',
  logHistoryAttributs: 'killer',
  logHistoryStat: false,
};

export const sampleWithNewData: NewLogHistorySig = {
  logHistory: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
