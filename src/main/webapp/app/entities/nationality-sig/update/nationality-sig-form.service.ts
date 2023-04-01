import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { INationalitySig, NewNationalitySig } from '../nationality-sig.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { nationalityId: unknown }> = Partial<Omit<T, 'nationalityId'>> & {
  nationalityId: T['nationalityId'];
};

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts INationalitySig for edit and NewNationalitySigFormGroupInput for create.
 */
type NationalitySigFormGroupInput = INationalitySig | PartialWithRequiredKeyOf<NewNationalitySig>;

type NationalitySigFormDefaults = Pick<NewNationalitySig, 'nationalityId' | 'nationalityStat'>;

type NationalitySigFormGroupContent = {
  nationalityId: FormControl<INationalitySig['nationalityId'] | NewNationalitySig['nationalityId']>;
  nationalityValue: FormControl<INationalitySig['nationalityValue']>;
  nationalityAbreviation: FormControl<INationalitySig['nationalityAbreviation']>;
  nationalityDescription: FormControl<INationalitySig['nationalityDescription']>;
  nationalityFlag: FormControl<INationalitySig['nationalityFlag']>;
  nationalityFlagContentType: FormControl<INationalitySig['nationalityFlagContentType']>;
  nationalityParams: FormControl<INationalitySig['nationalityParams']>;
  nationalityAttributs: FormControl<INationalitySig['nationalityAttributs']>;
  nationalityStat: FormControl<INationalitySig['nationalityStat']>;
};

export type NationalitySigFormGroup = FormGroup<NationalitySigFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class NationalitySigFormService {
  createNationalitySigFormGroup(nationality: NationalitySigFormGroupInput = { nationalityId: null }): NationalitySigFormGroup {
    const nationalityRawValue = {
      ...this.getFormDefaults(),
      ...nationality,
    };
    return new FormGroup<NationalitySigFormGroupContent>({
      nationalityId: new FormControl(
        { value: nationalityRawValue.nationalityId, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      nationalityValue: new FormControl(nationalityRawValue.nationalityValue, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      nationalityAbreviation: new FormControl(nationalityRawValue.nationalityAbreviation, {
        validators: [Validators.maxLength(10)],
      }),
      nationalityDescription: new FormControl(nationalityRawValue.nationalityDescription, {
        validators: [Validators.maxLength(200)],
      }),
      nationalityFlag: new FormControl(nationalityRawValue.nationalityFlag),
      nationalityFlagContentType: new FormControl(nationalityRawValue.nationalityFlagContentType),
      nationalityParams: new FormControl(nationalityRawValue.nationalityParams),
      nationalityAttributs: new FormControl(nationalityRawValue.nationalityAttributs),
      nationalityStat: new FormControl(nationalityRawValue.nationalityStat),
    });
  }

  getNationalitySig(form: NationalitySigFormGroup): INationalitySig | NewNationalitySig {
    return form.getRawValue() as INationalitySig | NewNationalitySig;
  }

  resetForm(form: NationalitySigFormGroup, nationality: NationalitySigFormGroupInput): void {
    const nationalityRawValue = { ...this.getFormDefaults(), ...nationality };
    form.reset(
      {
        ...nationalityRawValue,
        nationalityId: { value: nationalityRawValue.nationalityId, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): NationalitySigFormDefaults {
    return {
      nationalityId: null,
      nationalityStat: false,
    };
  }
}
