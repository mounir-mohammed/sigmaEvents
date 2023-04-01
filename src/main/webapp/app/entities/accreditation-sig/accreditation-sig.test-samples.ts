import dayjs from 'dayjs/esm';

import { IAccreditationSig, NewAccreditationSig } from './accreditation-sig.model';

export const sampleWithRequiredData: IAccreditationSig = {
  accreditationId: 87463,
  accreditationFirstName: 'Cheese Human Berkshire',
  accreditationLastName: 'monetize',
  accreditationBirthDay: dayjs('2023-03-31'),
};

export const sampleWithPartialData: IAccreditationSig = {
  accreditationId: 4427,
  accreditationFirstName: 'real-time deposit infrastructures',
  accreditationLastName: 'Kuwait silver',
  accreditationBirthDay: dayjs('2023-03-31'),
  accreditationSexe: 'auxiliary microchip envisioneer',
  accreditationDescription: 'District Functionality customized',
  accreditationEmail: 'Angola Metal Licensed',
  accreditationTel: 'Handmade',
  accreditationPhoto: '../fake-data/blob/hipster.png',
  accreditationPhotoContentType: 'unknown',
  accreditationPasseportId: 'quantifying Throughway',
  accreditationCartePresseId: 'Montana Loan',
  accreditationCarteProfessionnelleId: 'virtual',
  accreditationCreationDate: dayjs('2023-03-31T12:29'),
  accreditationUpdateDate: dayjs('2023-03-31T19:24'),
  accreditationDateStart: dayjs('2023-04-01T01:17'),
  accreditationParams: 'Senior Gloves',
  accreditationAttributs: 'Ergonomic Rustic whiteboard',
  accreditationStat: false,
};

export const sampleWithFullData: IAccreditationSig = {
  accreditationId: 32536,
  accreditationFirstName: 'Investment Hat Forward',
  accreditationSecondName: 'indexing Supervisor',
  accreditationLastName: 'reboot',
  accreditationBirthDay: dayjs('2023-03-31'),
  accreditationSexe: 'Architect',
  accreditationOccupation: 'indexing',
  accreditationDescription: '1080p',
  accreditationEmail: 'Cocos',
  accreditationTel: 'Jewelery drive Lesotho',
  accreditationPhoto: '../fake-data/blob/hipster.png',
  accreditationPhotoContentType: 'unknown',
  accreditationCinId: 'Fresh Cambridgeshire Oregon',
  accreditationPasseportId: 'Kansas',
  accreditationCartePresseId: 'global systems connecting',
  accreditationCarteProfessionnelleId: 'Ohio Dynamic compress',
  accreditationCreationDate: dayjs('2023-03-31T20:13'),
  accreditationUpdateDate: dayjs('2023-03-31T21:24'),
  accreditationCreatedByuser: 'driver',
  accreditationDateStart: dayjs('2023-03-31T04:22'),
  accreditationDateEnd: dayjs('2023-03-31T07:04'),
  accreditationPrintStat: false,
  accreditationPrintNumber: 92575,
  accreditationParams: 'protocol',
  accreditationAttributs: 'vortals Granite',
  accreditationStat: false,
};

export const sampleWithNewData: NewAccreditationSig = {
  accreditationFirstName: 'Virginia Internal Pennsylvania',
  accreditationLastName: 'Tennessee Planner',
  accreditationBirthDay: dayjs('2023-03-31'),
  accreditationId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
