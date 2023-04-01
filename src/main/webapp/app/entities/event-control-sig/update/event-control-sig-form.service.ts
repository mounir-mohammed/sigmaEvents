import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IEventControlSig, NewEventControlSig } from '../event-control-sig.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { controlId: unknown }> = Partial<Omit<T, 'controlId'>> & { controlId: T['controlId'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEventControlSig for edit and NewEventControlSigFormGroupInput for create.
 */
type EventControlSigFormGroupInput = IEventControlSig | PartialWithRequiredKeyOf<NewEventControlSig>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IEventControlSig | NewEventControlSig> = Omit<T, 'controlValueDate'> & {
  controlValueDate?: string | null;
};

type EventControlSigFormRawValue = FormValueOf<IEventControlSig>;

type NewEventControlSigFormRawValue = FormValueOf<NewEventControlSig>;

type EventControlSigFormDefaults = Pick<NewEventControlSig, 'controlId' | 'controlValueDate' | 'controlValueStat'>;

type EventControlSigFormGroupContent = {
  controlId: FormControl<EventControlSigFormRawValue['controlId'] | NewEventControlSig['controlId']>;
  controlName: FormControl<EventControlSigFormRawValue['controlName']>;
  controlDescription: FormControl<EventControlSigFormRawValue['controlDescription']>;
  controlType: FormControl<EventControlSigFormRawValue['controlType']>;
  controlValueString: FormControl<EventControlSigFormRawValue['controlValueString']>;
  controlValueLong: FormControl<EventControlSigFormRawValue['controlValueLong']>;
  controlValueDate: FormControl<EventControlSigFormRawValue['controlValueDate']>;
  controlParams: FormControl<EventControlSigFormRawValue['controlParams']>;
  controlAttributs: FormControl<EventControlSigFormRawValue['controlAttributs']>;
  controlValueStat: FormControl<EventControlSigFormRawValue['controlValueStat']>;
  event: FormControl<EventControlSigFormRawValue['event']>;
  eventField: FormControl<EventControlSigFormRawValue['eventField']>;
};

export type EventControlSigFormGroup = FormGroup<EventControlSigFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EventControlSigFormService {
  createEventControlSigFormGroup(eventControl: EventControlSigFormGroupInput = { controlId: null }): EventControlSigFormGroup {
    const eventControlRawValue = this.convertEventControlSigToEventControlSigRawValue({
      ...this.getFormDefaults(),
      ...eventControl,
    });
    return new FormGroup<EventControlSigFormGroupContent>({
      controlId: new FormControl(
        { value: eventControlRawValue.controlId, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      controlName: new FormControl(eventControlRawValue.controlName),
      controlDescription: new FormControl(eventControlRawValue.controlDescription),
      controlType: new FormControl(eventControlRawValue.controlType),
      controlValueString: new FormControl(eventControlRawValue.controlValueString),
      controlValueLong: new FormControl(eventControlRawValue.controlValueLong),
      controlValueDate: new FormControl(eventControlRawValue.controlValueDate),
      controlParams: new FormControl(eventControlRawValue.controlParams),
      controlAttributs: new FormControl(eventControlRawValue.controlAttributs),
      controlValueStat: new FormControl(eventControlRawValue.controlValueStat),
      event: new FormControl(eventControlRawValue.event),
      eventField: new FormControl(eventControlRawValue.eventField),
    });
  }

  getEventControlSig(form: EventControlSigFormGroup): IEventControlSig | NewEventControlSig {
    return this.convertEventControlSigRawValueToEventControlSig(
      form.getRawValue() as EventControlSigFormRawValue | NewEventControlSigFormRawValue
    );
  }

  resetForm(form: EventControlSigFormGroup, eventControl: EventControlSigFormGroupInput): void {
    const eventControlRawValue = this.convertEventControlSigToEventControlSigRawValue({ ...this.getFormDefaults(), ...eventControl });
    form.reset(
      {
        ...eventControlRawValue,
        controlId: { value: eventControlRawValue.controlId, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): EventControlSigFormDefaults {
    const currentTime = dayjs();

    return {
      controlId: null,
      controlValueDate: currentTime,
      controlValueStat: false,
    };
  }

  private convertEventControlSigRawValueToEventControlSig(
    rawEventControlSig: EventControlSigFormRawValue | NewEventControlSigFormRawValue
  ): IEventControlSig | NewEventControlSig {
    return {
      ...rawEventControlSig,
      controlValueDate: dayjs(rawEventControlSig.controlValueDate, DATE_TIME_FORMAT),
    };
  }

  private convertEventControlSigToEventControlSigRawValue(
    eventControl: IEventControlSig | (Partial<NewEventControlSig> & EventControlSigFormDefaults)
  ): EventControlSigFormRawValue | PartialWithRequiredKeyOf<NewEventControlSigFormRawValue> {
    return {
      ...eventControl,
      controlValueDate: eventControl.controlValueDate ? eventControl.controlValueDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
