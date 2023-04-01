import { IAuthentificationTypeSig, NewAuthentificationTypeSig } from './authentification-type-sig.model';

export const sampleWithRequiredData: IAuthentificationTypeSig = {
  authentificationTypeId: 92619,
  authentificationTypeValue: 'Leu Sports Compatible',
};

export const sampleWithPartialData: IAuthentificationTypeSig = {
  authentificationTypeId: 10958,
  authentificationTypeValue: 'Massachusetts navigating calculate',
  authentificationTypeDescription: 'Market Portugal',
  authentificationTypeAttributs: 'human-resource',
};

export const sampleWithFullData: IAuthentificationTypeSig = {
  authentificationTypeId: 81885,
  authentificationTypeValue: 'Chair',
  authentificationTypeDescription: 'quantifying TCP invoice',
  authentificationTypeParams: 'IB supply-chains',
  authentificationTypeAttributs: 'recontextualize Cordoba AI',
  authentificationTypeStat: false,
};

export const sampleWithNewData: NewAuthentificationTypeSig = {
  authentificationTypeValue: 'magenta',
  authentificationTypeId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
