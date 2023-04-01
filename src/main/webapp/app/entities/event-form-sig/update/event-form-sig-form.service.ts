import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IEventFormSig, NewEventFormSig } from '../event-form-sig.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { formId: unknown }> = Partial<Omit<T, 'formId'>> & { formId: T['formId'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEventFormSig for edit and NewEventFormSigFormGroupInput for create.
 */
type EventFormSigFormGroupInput = IEventFormSig | PartialWithRequiredKeyOf<NewEventFormSig>;

type EventFormSigFormDefaults = Pick<NewEventFormSig, 'formId' | 'formStat'>;

type EventFormSigFormGroupContent = {
  formId: FormControl<IEventFormSig['formId'] | NewEventFormSig['formId']>;
  formName: FormControl<IEventFormSig['formName']>;
  formDescription: FormControl<IEventFormSig['formDescription']>;
  formParams: FormControl<IEventFormSig['formParams']>;
  formAttributs: FormControl<IEventFormSig['formAttributs']>;
  formStat: FormControl<IEventFormSig['formStat']>;
  event: FormControl<IEventFormSig['event']>;
};

export type EventFormSigFormGroup = FormGroup<EventFormSigFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EventFormSigFormService {
  createEventFormSigFormGroup(eventForm: EventFormSigFormGroupInput = { formId: null }): EventFormSigFormGroup {
    const eventFormRawValue = {
      ...this.getFormDefaults(),
      ...eventForm,
    };
    return new FormGroup<EventFormSigFormGroupContent>({
      formId: new FormControl(
        { value: eventFormRawValue.formId, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      formName: new FormControl(eventFormRawValue.formName),
      formDescription: new FormControl(eventFormRawValue.formDescription),
      formParams: new FormControl(eventFormRawValue.formParams),
      formAttributs: new FormControl(eventFormRawValue.formAttributs),
      formStat: new FormControl(eventFormRawValue.formStat),
      event: new FormControl(eventFormRawValue.event),
    });
  }

  getEventFormSig(form: EventFormSigFormGroup): IEventFormSig | NewEventFormSig {
    return form.getRawValue() as IEventFormSig | NewEventFormSig;
  }

  resetForm(form: EventFormSigFormGroup, eventForm: EventFormSigFormGroupInput): void {
    const eventFormRawValue = { ...this.getFormDefaults(), ...eventForm };
    form.reset(
      {
        ...eventFormRawValue,
        formId: { value: eventFormRawValue.formId, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): EventFormSigFormDefaults {
    return {
      formId: null,
      formStat: false,
    };
  }
}
