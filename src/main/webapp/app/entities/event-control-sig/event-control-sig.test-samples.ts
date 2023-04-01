import dayjs from 'dayjs/esm';

import { IEventControlSig, NewEventControlSig } from './event-control-sig.model';

export const sampleWithRequiredData: IEventControlSig = {
  controlId: 14803,
};

export const sampleWithPartialData: IEventControlSig = {
  controlId: 11301,
  controlDescription: '1080p engineer',
  controlType: 'sensor',
  controlAttributs: 'Tennessee bandwidth Lucia',
  controlValueStat: false,
};

export const sampleWithFullData: IEventControlSig = {
  controlId: 97098,
  controlName: 'Fantastic Analyst global',
  controlDescription: 'transmit deposit payment',
  controlType: 'Unbranded Dynamic partnerships',
  controlValueString: 'Wooden reboot systemic',
  controlValueLong: 38157,
  controlValueDate: dayjs('2023-03-31T19:30'),
  controlParams: 'Alley Multi-lateral grow',
  controlAttributs: 'Virginia SQL',
  controlValueStat: true,
};

export const sampleWithNewData: NewEventControlSig = {
  controlId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
