import { IStatusSig, NewStatusSig } from './status-sig.model';

export const sampleWithRequiredData: IStatusSig = {
  statusId: 34266,
  statusName: 'transitional implementation',
};

export const sampleWithPartialData: IStatusSig = {
  statusId: 97421,
  statusName: 'Account copying Trail',
  statusAbreviation: 'optical Be',
  statusUserCanPrint: false,
  statusUserCanUpdate: false,
  statusUserCanValidate: false,
  statusParams: 'leading-edge payment Bedfordshire',
  statusAttributs: 'Towels Rustic',
  statusStat: false,
};

export const sampleWithFullData: IStatusSig = {
  statusId: 30747,
  statusName: 'Ferry indexing',
  statusAbreviation: 'maximize c',
  statusColor: 'Mauritania',
  statusDescription: 'Practical',
  statusUserCanPrint: false,
  statusUserCanUpdate: false,
  statusUserCanValidate: false,
  statusParams: 'Ohio',
  statusAttributs: 'Dynamic',
  statusStat: false,
};

export const sampleWithNewData: NewStatusSig = {
  statusName: 'virtual Shoes',
  statusId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
