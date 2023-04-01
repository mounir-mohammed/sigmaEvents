import dayjs from 'dayjs/esm';

import { IEventSig, NewEventSig } from './event-sig.model';

export const sampleWithRequiredData: IEventSig = {
  eventId: 63022,
  eventName: 'Cotton',
  eventdateStart: dayjs('2023-03-31T14:29'),
  eventdateEnd: dayjs('2023-03-31T23:24'),
};

export const sampleWithPartialData: IEventSig = {
  eventId: 3539,
  eventName: 'paradigm transmit generation',
  eventdateStart: dayjs('2023-03-31T22:12'),
  eventdateEnd: dayjs('2023-03-31T06:26'),
  eventBannerCenter: '../fake-data/blob/hipster.png',
  eventBannerCenterContentType: 'unknown',
  eventBannerRight: '../fake-data/blob/hipster.png',
  eventBannerRightContentType: 'unknown',
  eventWithPhoto: true,
  eventCodeByTypeCategorie: false,
  eventCodeByTypeFonction: true,
  eventDefaultPrintingLanguage: false,
  eventImported: true,
  eventArchived: false,
  eventArchiveFileName: 'Shirt',
  eventURL: 'input Court',
  eventSousDomaine: 'synergistic Account',
  eventStat: false,
};

export const sampleWithFullData: IEventSig = {
  eventId: 10836,
  eventName: 'Throughway',
  eventColor: 'Future-proofed',
  eventDescription: 'Stravenue Virtual Louisiana',
  eventAbreviation: 'Tuna Intel',
  eventdateStart: dayjs('2023-03-31T09:19'),
  eventdateEnd: dayjs('2023-04-01T02:01'),
  eventPrintingModelId: 30383,
  eventLogo: '../fake-data/blob/hipster.png',
  eventLogoContentType: 'unknown',
  eventBannerCenter: '../fake-data/blob/hipster.png',
  eventBannerCenterContentType: 'unknown',
  eventBannerRight: '../fake-data/blob/hipster.png',
  eventBannerRightContentType: 'unknown',
  eventBannerLeft: '../fake-data/blob/hipster.png',
  eventBannerLeftContentType: 'unknown',
  eventBannerBas: '../fake-data/blob/hipster.png',
  eventBannerBasContentType: 'unknown',
  eventWithPhoto: true,
  eventNoCode: true,
  eventCodeNoFilter: true,
  eventCodeByTypeAccreditation: false,
  eventCodeByTypeCategorie: false,
  eventCodeByTypeFonction: false,
  eventCodeByTypeOrganiz: false,
  eventDefaultPrintingLanguage: true,
  eventPCenterPrintingLanguage: true,
  eventImported: true,
  eventArchived: true,
  eventArchiveFileName: 'connecting',
  eventURL: 'tangible Architect',
  eventDomaine: 'Danish time-frame Pizza',
  eventSousDomaine: 'access',
  eventCloned: true,
  eventParams: 'transmitter',
  eventAttributs: 'e-markets',
  eventStat: true,
};

export const sampleWithNewData: NewEventSig = {
  eventName: 'monitor reinvent Pound',
  eventdateStart: dayjs('2023-03-31T05:47'),
  eventdateEnd: dayjs('2023-03-31T07:12'),
  eventId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
