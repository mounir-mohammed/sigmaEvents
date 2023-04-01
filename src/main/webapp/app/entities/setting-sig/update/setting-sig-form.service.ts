import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ISettingSig, NewSettingSig } from '../setting-sig.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { settingId: unknown }> = Partial<Omit<T, 'settingId'>> & { settingId: T['settingId'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISettingSig for edit and NewSettingSigFormGroupInput for create.
 */
type SettingSigFormGroupInput = ISettingSig | PartialWithRequiredKeyOf<NewSettingSig>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ISettingSig | NewSettingSig> = Omit<T, 'settingValueDate'> & {
  settingValueDate?: string | null;
};

type SettingSigFormRawValue = FormValueOf<ISettingSig>;

type NewSettingSigFormRawValue = FormValueOf<NewSettingSig>;

type SettingSigFormDefaults = Pick<NewSettingSig, 'settingId' | 'settingValueDate' | 'settingValueBoolean' | 'settingStat'>;

type SettingSigFormGroupContent = {
  settingId: FormControl<SettingSigFormRawValue['settingId'] | NewSettingSig['settingId']>;
  settingParentId: FormControl<SettingSigFormRawValue['settingParentId']>;
  settingType: FormControl<SettingSigFormRawValue['settingType']>;
  settingNameClass: FormControl<SettingSigFormRawValue['settingNameClass']>;
  settingDataType: FormControl<SettingSigFormRawValue['settingDataType']>;
  settingDescription: FormControl<SettingSigFormRawValue['settingDescription']>;
  settingValueString: FormControl<SettingSigFormRawValue['settingValueString']>;
  settingValueLong: FormControl<SettingSigFormRawValue['settingValueLong']>;
  settingValueDate: FormControl<SettingSigFormRawValue['settingValueDate']>;
  settingValueBoolean: FormControl<SettingSigFormRawValue['settingValueBoolean']>;
  settingValueBlob: FormControl<SettingSigFormRawValue['settingValueBlob']>;
  settingValueBlobContentType: FormControl<SettingSigFormRawValue['settingValueBlobContentType']>;
  settingParams: FormControl<SettingSigFormRawValue['settingParams']>;
  settingAttributs: FormControl<SettingSigFormRawValue['settingAttributs']>;
  settingStat: FormControl<SettingSigFormRawValue['settingStat']>;
  language: FormControl<SettingSigFormRawValue['language']>;
  event: FormControl<SettingSigFormRawValue['event']>;
};

export type SettingSigFormGroup = FormGroup<SettingSigFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SettingSigFormService {
  createSettingSigFormGroup(setting: SettingSigFormGroupInput = { settingId: null }): SettingSigFormGroup {
    const settingRawValue = this.convertSettingSigToSettingSigRawValue({
      ...this.getFormDefaults(),
      ...setting,
    });
    return new FormGroup<SettingSigFormGroupContent>({
      settingId: new FormControl(
        { value: settingRawValue.settingId, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      settingParentId: new FormControl(settingRawValue.settingParentId),
      settingType: new FormControl(settingRawValue.settingType, {
        validators: [Validators.required, Validators.minLength(2), Validators.maxLength(50)],
      }),
      settingNameClass: new FormControl(settingRawValue.settingNameClass, {
        validators: [Validators.required, Validators.minLength(2), Validators.maxLength(50)],
      }),
      settingDataType: new FormControl(settingRawValue.settingDataType, {
        validators: [Validators.required, Validators.minLength(2), Validators.maxLength(50)],
      }),
      settingDescription: new FormControl(settingRawValue.settingDescription, {
        validators: [Validators.maxLength(200)],
      }),
      settingValueString: new FormControl(settingRawValue.settingValueString),
      settingValueLong: new FormControl(settingRawValue.settingValueLong),
      settingValueDate: new FormControl(settingRawValue.settingValueDate),
      settingValueBoolean: new FormControl(settingRawValue.settingValueBoolean),
      settingValueBlob: new FormControl(settingRawValue.settingValueBlob),
      settingValueBlobContentType: new FormControl(settingRawValue.settingValueBlobContentType),
      settingParams: new FormControl(settingRawValue.settingParams),
      settingAttributs: new FormControl(settingRawValue.settingAttributs),
      settingStat: new FormControl(settingRawValue.settingStat),
      language: new FormControl(settingRawValue.language),
      event: new FormControl(settingRawValue.event),
    });
  }

  getSettingSig(form: SettingSigFormGroup): ISettingSig | NewSettingSig {
    return this.convertSettingSigRawValueToSettingSig(form.getRawValue() as SettingSigFormRawValue | NewSettingSigFormRawValue);
  }

  resetForm(form: SettingSigFormGroup, setting: SettingSigFormGroupInput): void {
    const settingRawValue = this.convertSettingSigToSettingSigRawValue({ ...this.getFormDefaults(), ...setting });
    form.reset(
      {
        ...settingRawValue,
        settingId: { value: settingRawValue.settingId, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): SettingSigFormDefaults {
    const currentTime = dayjs();

    return {
      settingId: null,
      settingValueDate: currentTime,
      settingValueBoolean: false,
      settingStat: false,
    };
  }

  private convertSettingSigRawValueToSettingSig(
    rawSettingSig: SettingSigFormRawValue | NewSettingSigFormRawValue
  ): ISettingSig | NewSettingSig {
    return {
      ...rawSettingSig,
      settingValueDate: dayjs(rawSettingSig.settingValueDate, DATE_TIME_FORMAT),
    };
  }

  private convertSettingSigToSettingSigRawValue(
    setting: ISettingSig | (Partial<NewSettingSig> & SettingSigFormDefaults)
  ): SettingSigFormRawValue | PartialWithRequiredKeyOf<NewSettingSigFormRawValue> {
    return {
      ...setting,
      settingValueDate: setting.settingValueDate ? setting.settingValueDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
