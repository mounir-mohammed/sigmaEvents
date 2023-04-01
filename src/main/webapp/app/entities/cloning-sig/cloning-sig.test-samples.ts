import dayjs from 'dayjs/esm';

import { ICloningSig, NewCloningSig } from './cloning-sig.model';

export const sampleWithRequiredData: ICloningSig = {
  cloningId: 38108,
};

export const sampleWithPartialData: ICloningSig = {
  cloningId: 47332,
  cloningOldEventId: 69792,
  cloningDate: dayjs('2023-03-31T16:27'),
  clonedEntitys: 'Unbranded Pass',
  clonedParams: 'azure database',
  clonedStat: false,
};

export const sampleWithFullData: ICloningSig = {
  cloningId: 52479,
  cloningDescription: 'Internal',
  cloningOldEventId: 57748,
  cloningNewEventId: 42304,
  cloningUserId: 6341,
  cloningDate: dayjs('2023-03-31T11:33'),
  clonedEntitys: 'Hat copy multi-byte',
  clonedParams: 'back-end alarm',
  clonedAttributs: 'input Chair digital',
  clonedStat: true,
};

export const sampleWithNewData: NewCloningSig = {
  cloningId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
