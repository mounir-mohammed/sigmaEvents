import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ISexeSig, NewSexeSig } from '../sexe-sig.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { sexeId: unknown }> = Partial<Omit<T, 'sexeId'>> & { sexeId: T['sexeId'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISexeSig for edit and NewSexeSigFormGroupInput for create.
 */
type SexeSigFormGroupInput = ISexeSig | PartialWithRequiredKeyOf<NewSexeSig>;

type SexeSigFormDefaults = Pick<NewSexeSig, 'sexeId' | 'sexeStat'>;

type SexeSigFormGroupContent = {
  sexeId: FormControl<ISexeSig['sexeId'] | NewSexeSig['sexeId']>;
  sexeValue: FormControl<ISexeSig['sexeValue']>;
  sexeDescription: FormControl<ISexeSig['sexeDescription']>;
  sexeTypeParams: FormControl<ISexeSig['sexeTypeParams']>;
  sexeTypeAttributs: FormControl<ISexeSig['sexeTypeAttributs']>;
  sexeStat: FormControl<ISexeSig['sexeStat']>;
};

export type SexeSigFormGroup = FormGroup<SexeSigFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SexeSigFormService {
  createSexeSigFormGroup(sexe: SexeSigFormGroupInput = { sexeId: null }): SexeSigFormGroup {
    const sexeRawValue = {
      ...this.getFormDefaults(),
      ...sexe,
    };
    return new FormGroup<SexeSigFormGroupContent>({
      sexeId: new FormControl(
        { value: sexeRawValue.sexeId, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      sexeValue: new FormControl(sexeRawValue.sexeValue, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      sexeDescription: new FormControl(sexeRawValue.sexeDescription, {
        validators: [Validators.maxLength(200)],
      }),
      sexeTypeParams: new FormControl(sexeRawValue.sexeTypeParams),
      sexeTypeAttributs: new FormControl(sexeRawValue.sexeTypeAttributs),
      sexeStat: new FormControl(sexeRawValue.sexeStat),
    });
  }

  getSexeSig(form: SexeSigFormGroup): ISexeSig | NewSexeSig {
    return form.getRawValue() as ISexeSig | NewSexeSig;
  }

  resetForm(form: SexeSigFormGroup, sexe: SexeSigFormGroupInput): void {
    const sexeRawValue = { ...this.getFormDefaults(), ...sexe };
    form.reset(
      {
        ...sexeRawValue,
        sexeId: { value: sexeRawValue.sexeId, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): SexeSigFormDefaults {
    return {
      sexeId: null,
      sexeStat: false,
    };
  }
}
