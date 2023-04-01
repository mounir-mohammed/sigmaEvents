import { ICivilitySig, NewCivilitySig } from './civility-sig.model';

export const sampleWithRequiredData: ICivilitySig = {
  civilityId: 24031,
  civilityValue: 'calculating visualize',
};

export const sampleWithPartialData: ICivilitySig = {
  civilityId: 34807,
  civilityValue: 'Comoro',
  civilityCode: 'programming',
  civilityAttributs: 'programming',
};

export const sampleWithFullData: ICivilitySig = {
  civilityId: 36309,
  civilityValue: 'Berkshire systems deposit',
  civilityDescription: 'Alabama',
  civilityCode: 'firewall',
  civilityParams: 'back-end Metal',
  civilityAttributs: 'violet deposit',
  civilityStat: false,
};

export const sampleWithNewData: NewCivilitySig = {
  civilityValue: 'Market',
  civilityId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
