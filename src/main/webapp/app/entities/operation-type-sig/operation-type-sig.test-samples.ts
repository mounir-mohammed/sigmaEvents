import { IOperationTypeSig, NewOperationTypeSig } from './operation-type-sig.model';

export const sampleWithRequiredData: IOperationTypeSig = {
  operationTypeId: 21620,
  operationTypeValue: 'SSL',
};

export const sampleWithPartialData: IOperationTypeSig = {
  operationTypeId: 84585,
  operationTypeValue: 'Frozen withdrawal',
  operationTypeAttributs: 'Visionary',
  operationTypeStat: false,
};

export const sampleWithFullData: IOperationTypeSig = {
  operationTypeId: 42627,
  operationTypeValue: 'Bedfordshire solid Maryland',
  operationTypeDescription: 'navigating Response bus',
  operationTypeParams: 'programming Account Assistant',
  operationTypeAttributs: 'Bedfordshire',
  operationTypeStat: true,
};

export const sampleWithNewData: NewOperationTypeSig = {
  operationTypeValue: 'Dynamic Sleek',
  operationTypeId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
