import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICitySig, NewCitySig } from '../city-sig.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { cityId: unknown }> = Partial<Omit<T, 'cityId'>> & { cityId: T['cityId'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICitySig for edit and NewCitySigFormGroupInput for create.
 */
type CitySigFormGroupInput = ICitySig | PartialWithRequiredKeyOf<NewCitySig>;

type CitySigFormDefaults = Pick<NewCitySig, 'cityId' | 'cityStat'>;

type CitySigFormGroupContent = {
  cityId: FormControl<ICitySig['cityId'] | NewCitySig['cityId']>;
  cityName: FormControl<ICitySig['cityName']>;
  cityZipCode: FormControl<ICitySig['cityZipCode']>;
  cityAbreviation: FormControl<ICitySig['cityAbreviation']>;
  cityDescription: FormControl<ICitySig['cityDescription']>;
  cityParams: FormControl<ICitySig['cityParams']>;
  cityAttributs: FormControl<ICitySig['cityAttributs']>;
  cityStat: FormControl<ICitySig['cityStat']>;
  country: FormControl<ICitySig['country']>;
};

export type CitySigFormGroup = FormGroup<CitySigFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CitySigFormService {
  createCitySigFormGroup(city: CitySigFormGroupInput = { cityId: null }): CitySigFormGroup {
    const cityRawValue = {
      ...this.getFormDefaults(),
      ...city,
    };
    return new FormGroup<CitySigFormGroupContent>({
      cityId: new FormControl(
        { value: cityRawValue.cityId, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      cityName: new FormControl(cityRawValue.cityName, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      cityZipCode: new FormControl(cityRawValue.cityZipCode, {
        validators: [Validators.required],
      }),
      cityAbreviation: new FormControl(cityRawValue.cityAbreviation, {
        validators: [Validators.maxLength(10)],
      }),
      cityDescription: new FormControl(cityRawValue.cityDescription, {
        validators: [Validators.maxLength(200)],
      }),
      cityParams: new FormControl(cityRawValue.cityParams),
      cityAttributs: new FormControl(cityRawValue.cityAttributs),
      cityStat: new FormControl(cityRawValue.cityStat),
      country: new FormControl(cityRawValue.country),
    });
  }

  getCitySig(form: CitySigFormGroup): ICitySig | NewCitySig {
    return form.getRawValue() as ICitySig | NewCitySig;
  }

  resetForm(form: CitySigFormGroup, city: CitySigFormGroupInput): void {
    const cityRawValue = { ...this.getFormDefaults(), ...city };
    form.reset(
      {
        ...cityRawValue,
        cityId: { value: cityRawValue.cityId, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CitySigFormDefaults {
    return {
      cityId: null,
      cityStat: false,
    };
  }
}
