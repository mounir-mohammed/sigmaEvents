import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICountrySig, NewCountrySig } from '../country-sig.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { countryId: unknown }> = Partial<Omit<T, 'countryId'>> & { countryId: T['countryId'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICountrySig for edit and NewCountrySigFormGroupInput for create.
 */
type CountrySigFormGroupInput = ICountrySig | PartialWithRequiredKeyOf<NewCountrySig>;

type CountrySigFormDefaults = Pick<NewCountrySig, 'countryId' | 'countryStat'>;

type CountrySigFormGroupContent = {
  countryId: FormControl<ICountrySig['countryId'] | NewCountrySig['countryId']>;
  countryName: FormControl<ICountrySig['countryName']>;
  countryCodeAlpha2: FormControl<ICountrySig['countryCodeAlpha2']>;
  countryCodeAlpha3: FormControl<ICountrySig['countryCodeAlpha3']>;
  countryTelCode: FormControl<ICountrySig['countryTelCode']>;
  countryDescription: FormControl<ICountrySig['countryDescription']>;
  countryFlag: FormControl<ICountrySig['countryFlag']>;
  countryFlagContentType: FormControl<ICountrySig['countryFlagContentType']>;
  countryParams: FormControl<ICountrySig['countryParams']>;
  countryAttributs: FormControl<ICountrySig['countryAttributs']>;
  countryStat: FormControl<ICountrySig['countryStat']>;
};

export type CountrySigFormGroup = FormGroup<CountrySigFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CountrySigFormService {
  createCountrySigFormGroup(country: CountrySigFormGroupInput = { countryId: null }): CountrySigFormGroup {
    const countryRawValue = {
      ...this.getFormDefaults(),
      ...country,
    };
    return new FormGroup<CountrySigFormGroupContent>({
      countryId: new FormControl(
        { value: countryRawValue.countryId, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      countryName: new FormControl(countryRawValue.countryName, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      countryCodeAlpha2: new FormControl(countryRawValue.countryCodeAlpha2, {
        validators: [Validators.required],
      }),
      countryCodeAlpha3: new FormControl(countryRawValue.countryCodeAlpha3),
      countryTelCode: new FormControl(countryRawValue.countryTelCode),
      countryDescription: new FormControl(countryRawValue.countryDescription, {
        validators: [Validators.maxLength(200)],
      }),
      countryFlag: new FormControl(countryRawValue.countryFlag),
      countryFlagContentType: new FormControl(countryRawValue.countryFlagContentType),
      countryParams: new FormControl(countryRawValue.countryParams),
      countryAttributs: new FormControl(countryRawValue.countryAttributs),
      countryStat: new FormControl(countryRawValue.countryStat),
    });
  }

  getCountrySig(form: CountrySigFormGroup): ICountrySig | NewCountrySig {
    return form.getRawValue() as ICountrySig | NewCountrySig;
  }

  resetForm(form: CountrySigFormGroup, country: CountrySigFormGroupInput): void {
    const countryRawValue = { ...this.getFormDefaults(), ...country };
    form.reset(
      {
        ...countryRawValue,
        countryId: { value: countryRawValue.countryId, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CountrySigFormDefaults {
    return {
      countryId: null,
      countryStat: false,
    };
  }
}
