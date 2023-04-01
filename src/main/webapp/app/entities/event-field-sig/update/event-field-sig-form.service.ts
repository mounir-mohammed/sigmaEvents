import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IEventFieldSig, NewEventFieldSig } from '../event-field-sig.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { fieldId: unknown }> = Partial<Omit<T, 'fieldId'>> & { fieldId: T['fieldId'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEventFieldSig for edit and NewEventFieldSigFormGroupInput for create.
 */
type EventFieldSigFormGroupInput = IEventFieldSig | PartialWithRequiredKeyOf<NewEventFieldSig>;

type EventFieldSigFormDefaults = Pick<NewEventFieldSig, 'fieldId' | 'fieldStat'>;

type EventFieldSigFormGroupContent = {
  fieldId: FormControl<IEventFieldSig['fieldId'] | NewEventFieldSig['fieldId']>;
  fieldName: FormControl<IEventFieldSig['fieldName']>;
  fieldCategorie: FormControl<IEventFieldSig['fieldCategorie']>;
  fieldDescription: FormControl<IEventFieldSig['fieldDescription']>;
  fieldType: FormControl<IEventFieldSig['fieldType']>;
  fieldParams: FormControl<IEventFieldSig['fieldParams']>;
  fieldAttributs: FormControl<IEventFieldSig['fieldAttributs']>;
  fieldStat: FormControl<IEventFieldSig['fieldStat']>;
  event: FormControl<IEventFieldSig['event']>;
  eventForm: FormControl<IEventFieldSig['eventForm']>;
};

export type EventFieldSigFormGroup = FormGroup<EventFieldSigFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EventFieldSigFormService {
  createEventFieldSigFormGroup(eventField: EventFieldSigFormGroupInput = { fieldId: null }): EventFieldSigFormGroup {
    const eventFieldRawValue = {
      ...this.getFormDefaults(),
      ...eventField,
    };
    return new FormGroup<EventFieldSigFormGroupContent>({
      fieldId: new FormControl(
        { value: eventFieldRawValue.fieldId, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      fieldName: new FormControl(eventFieldRawValue.fieldName),
      fieldCategorie: new FormControl(eventFieldRawValue.fieldCategorie),
      fieldDescription: new FormControl(eventFieldRawValue.fieldDescription),
      fieldType: new FormControl(eventFieldRawValue.fieldType),
      fieldParams: new FormControl(eventFieldRawValue.fieldParams),
      fieldAttributs: new FormControl(eventFieldRawValue.fieldAttributs),
      fieldStat: new FormControl(eventFieldRawValue.fieldStat),
      event: new FormControl(eventFieldRawValue.event),
      eventForm: new FormControl(eventFieldRawValue.eventForm),
    });
  }

  getEventFieldSig(form: EventFieldSigFormGroup): IEventFieldSig | NewEventFieldSig {
    return form.getRawValue() as IEventFieldSig | NewEventFieldSig;
  }

  resetForm(form: EventFieldSigFormGroup, eventField: EventFieldSigFormGroupInput): void {
    const eventFieldRawValue = { ...this.getFormDefaults(), ...eventField };
    form.reset(
      {
        ...eventFieldRawValue,
        fieldId: { value: eventFieldRawValue.fieldId, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): EventFieldSigFormDefaults {
    return {
      fieldId: null,
      fieldStat: false,
    };
  }
}
