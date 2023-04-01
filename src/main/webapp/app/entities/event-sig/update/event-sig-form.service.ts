import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IEventSig, NewEventSig } from '../event-sig.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { eventId: unknown }> = Partial<Omit<T, 'eventId'>> & { eventId: T['eventId'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEventSig for edit and NewEventSigFormGroupInput for create.
 */
type EventSigFormGroupInput = IEventSig | PartialWithRequiredKeyOf<NewEventSig>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IEventSig | NewEventSig> = Omit<T, 'eventdateStart' | 'eventdateEnd'> & {
  eventdateStart?: string | null;
  eventdateEnd?: string | null;
};

type EventSigFormRawValue = FormValueOf<IEventSig>;

type NewEventSigFormRawValue = FormValueOf<NewEventSig>;

type EventSigFormDefaults = Pick<
  NewEventSig,
  | 'eventId'
  | 'eventdateStart'
  | 'eventdateEnd'
  | 'eventWithPhoto'
  | 'eventNoCode'
  | 'eventCodeNoFilter'
  | 'eventCodeByTypeAccreditation'
  | 'eventCodeByTypeCategorie'
  | 'eventCodeByTypeFonction'
  | 'eventCodeByTypeOrganiz'
  | 'eventDefaultPrintingLanguage'
  | 'eventPCenterPrintingLanguage'
  | 'eventImported'
  | 'eventArchived'
  | 'eventCloned'
  | 'eventStat'
>;

type EventSigFormGroupContent = {
  eventId: FormControl<EventSigFormRawValue['eventId'] | NewEventSig['eventId']>;
  eventName: FormControl<EventSigFormRawValue['eventName']>;
  eventColor: FormControl<EventSigFormRawValue['eventColor']>;
  eventDescription: FormControl<EventSigFormRawValue['eventDescription']>;
  eventAbreviation: FormControl<EventSigFormRawValue['eventAbreviation']>;
  eventdateStart: FormControl<EventSigFormRawValue['eventdateStart']>;
  eventdateEnd: FormControl<EventSigFormRawValue['eventdateEnd']>;
  eventPrintingModelId: FormControl<EventSigFormRawValue['eventPrintingModelId']>;
  eventLogo: FormControl<EventSigFormRawValue['eventLogo']>;
  eventLogoContentType: FormControl<EventSigFormRawValue['eventLogoContentType']>;
  eventBannerCenter: FormControl<EventSigFormRawValue['eventBannerCenter']>;
  eventBannerCenterContentType: FormControl<EventSigFormRawValue['eventBannerCenterContentType']>;
  eventBannerRight: FormControl<EventSigFormRawValue['eventBannerRight']>;
  eventBannerRightContentType: FormControl<EventSigFormRawValue['eventBannerRightContentType']>;
  eventBannerLeft: FormControl<EventSigFormRawValue['eventBannerLeft']>;
  eventBannerLeftContentType: FormControl<EventSigFormRawValue['eventBannerLeftContentType']>;
  eventBannerBas: FormControl<EventSigFormRawValue['eventBannerBas']>;
  eventBannerBasContentType: FormControl<EventSigFormRawValue['eventBannerBasContentType']>;
  eventWithPhoto: FormControl<EventSigFormRawValue['eventWithPhoto']>;
  eventNoCode: FormControl<EventSigFormRawValue['eventNoCode']>;
  eventCodeNoFilter: FormControl<EventSigFormRawValue['eventCodeNoFilter']>;
  eventCodeByTypeAccreditation: FormControl<EventSigFormRawValue['eventCodeByTypeAccreditation']>;
  eventCodeByTypeCategorie: FormControl<EventSigFormRawValue['eventCodeByTypeCategorie']>;
  eventCodeByTypeFonction: FormControl<EventSigFormRawValue['eventCodeByTypeFonction']>;
  eventCodeByTypeOrganiz: FormControl<EventSigFormRawValue['eventCodeByTypeOrganiz']>;
  eventDefaultPrintingLanguage: FormControl<EventSigFormRawValue['eventDefaultPrintingLanguage']>;
  eventPCenterPrintingLanguage: FormControl<EventSigFormRawValue['eventPCenterPrintingLanguage']>;
  eventImported: FormControl<EventSigFormRawValue['eventImported']>;
  eventArchived: FormControl<EventSigFormRawValue['eventArchived']>;
  eventArchiveFileName: FormControl<EventSigFormRawValue['eventArchiveFileName']>;
  eventURL: FormControl<EventSigFormRawValue['eventURL']>;
  eventDomaine: FormControl<EventSigFormRawValue['eventDomaine']>;
  eventSousDomaine: FormControl<EventSigFormRawValue['eventSousDomaine']>;
  eventCloned: FormControl<EventSigFormRawValue['eventCloned']>;
  eventParams: FormControl<EventSigFormRawValue['eventParams']>;
  eventAttributs: FormControl<EventSigFormRawValue['eventAttributs']>;
  eventStat: FormControl<EventSigFormRawValue['eventStat']>;
  language: FormControl<EventSigFormRawValue['language']>;
};

export type EventSigFormGroup = FormGroup<EventSigFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EventSigFormService {
  createEventSigFormGroup(event: EventSigFormGroupInput = { eventId: null }): EventSigFormGroup {
    const eventRawValue = this.convertEventSigToEventSigRawValue({
      ...this.getFormDefaults(),
      ...event,
    });
    return new FormGroup<EventSigFormGroupContent>({
      eventId: new FormControl(
        { value: eventRawValue.eventId, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      eventName: new FormControl(eventRawValue.eventName, {
        validators: [Validators.required, Validators.minLength(5), Validators.maxLength(100)],
      }),
      eventColor: new FormControl(eventRawValue.eventColor, {
        validators: [Validators.maxLength(100)],
      }),
      eventDescription: new FormControl(eventRawValue.eventDescription, {
        validators: [Validators.maxLength(200)],
      }),
      eventAbreviation: new FormControl(eventRawValue.eventAbreviation, {
        validators: [Validators.maxLength(10)],
      }),
      eventdateStart: new FormControl(eventRawValue.eventdateStart, {
        validators: [Validators.required],
      }),
      eventdateEnd: new FormControl(eventRawValue.eventdateEnd, {
        validators: [Validators.required],
      }),
      eventPrintingModelId: new FormControl(eventRawValue.eventPrintingModelId),
      eventLogo: new FormControl(eventRawValue.eventLogo),
      eventLogoContentType: new FormControl(eventRawValue.eventLogoContentType),
      eventBannerCenter: new FormControl(eventRawValue.eventBannerCenter),
      eventBannerCenterContentType: new FormControl(eventRawValue.eventBannerCenterContentType),
      eventBannerRight: new FormControl(eventRawValue.eventBannerRight),
      eventBannerRightContentType: new FormControl(eventRawValue.eventBannerRightContentType),
      eventBannerLeft: new FormControl(eventRawValue.eventBannerLeft),
      eventBannerLeftContentType: new FormControl(eventRawValue.eventBannerLeftContentType),
      eventBannerBas: new FormControl(eventRawValue.eventBannerBas),
      eventBannerBasContentType: new FormControl(eventRawValue.eventBannerBasContentType),
      eventWithPhoto: new FormControl(eventRawValue.eventWithPhoto),
      eventNoCode: new FormControl(eventRawValue.eventNoCode),
      eventCodeNoFilter: new FormControl(eventRawValue.eventCodeNoFilter),
      eventCodeByTypeAccreditation: new FormControl(eventRawValue.eventCodeByTypeAccreditation),
      eventCodeByTypeCategorie: new FormControl(eventRawValue.eventCodeByTypeCategorie),
      eventCodeByTypeFonction: new FormControl(eventRawValue.eventCodeByTypeFonction),
      eventCodeByTypeOrganiz: new FormControl(eventRawValue.eventCodeByTypeOrganiz),
      eventDefaultPrintingLanguage: new FormControl(eventRawValue.eventDefaultPrintingLanguage),
      eventPCenterPrintingLanguage: new FormControl(eventRawValue.eventPCenterPrintingLanguage),
      eventImported: new FormControl(eventRawValue.eventImported),
      eventArchived: new FormControl(eventRawValue.eventArchived),
      eventArchiveFileName: new FormControl(eventRawValue.eventArchiveFileName),
      eventURL: new FormControl(eventRawValue.eventURL),
      eventDomaine: new FormControl(eventRawValue.eventDomaine),
      eventSousDomaine: new FormControl(eventRawValue.eventSousDomaine),
      eventCloned: new FormControl(eventRawValue.eventCloned),
      eventParams: new FormControl(eventRawValue.eventParams),
      eventAttributs: new FormControl(eventRawValue.eventAttributs),
      eventStat: new FormControl(eventRawValue.eventStat),
      language: new FormControl(eventRawValue.language),
    });
  }

  getEventSig(form: EventSigFormGroup): IEventSig | NewEventSig {
    return this.convertEventSigRawValueToEventSig(form.getRawValue() as EventSigFormRawValue | NewEventSigFormRawValue);
  }

  resetForm(form: EventSigFormGroup, event: EventSigFormGroupInput): void {
    const eventRawValue = this.convertEventSigToEventSigRawValue({ ...this.getFormDefaults(), ...event });
    form.reset(
      {
        ...eventRawValue,
        eventId: { value: eventRawValue.eventId, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): EventSigFormDefaults {
    const currentTime = dayjs();

    return {
      eventId: null,
      eventdateStart: currentTime,
      eventdateEnd: currentTime,
      eventWithPhoto: false,
      eventNoCode: false,
      eventCodeNoFilter: false,
      eventCodeByTypeAccreditation: false,
      eventCodeByTypeCategorie: false,
      eventCodeByTypeFonction: false,
      eventCodeByTypeOrganiz: false,
      eventDefaultPrintingLanguage: false,
      eventPCenterPrintingLanguage: false,
      eventImported: false,
      eventArchived: false,
      eventCloned: false,
      eventStat: false,
    };
  }

  private convertEventSigRawValueToEventSig(rawEventSig: EventSigFormRawValue | NewEventSigFormRawValue): IEventSig | NewEventSig {
    return {
      ...rawEventSig,
      eventdateStart: dayjs(rawEventSig.eventdateStart, DATE_TIME_FORMAT),
      eventdateEnd: dayjs(rawEventSig.eventdateEnd, DATE_TIME_FORMAT),
    };
  }

  private convertEventSigToEventSigRawValue(
    event: IEventSig | (Partial<NewEventSig> & EventSigFormDefaults)
  ): EventSigFormRawValue | PartialWithRequiredKeyOf<NewEventSigFormRawValue> {
    return {
      ...event,
      eventdateStart: event.eventdateStart ? event.eventdateStart.format(DATE_TIME_FORMAT) : undefined,
      eventdateEnd: event.eventdateEnd ? event.eventdateEnd.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
