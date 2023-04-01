import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICivilitySig, NewCivilitySig } from '../civility-sig.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { civilityId: unknown }> = Partial<Omit<T, 'civilityId'>> & { civilityId: T['civilityId'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICivilitySig for edit and NewCivilitySigFormGroupInput for create.
 */
type CivilitySigFormGroupInput = ICivilitySig | PartialWithRequiredKeyOf<NewCivilitySig>;

type CivilitySigFormDefaults = Pick<NewCivilitySig, 'civilityId' | 'civilityStat'>;

type CivilitySigFormGroupContent = {
  civilityId: FormControl<ICivilitySig['civilityId'] | NewCivilitySig['civilityId']>;
  civilityValue: FormControl<ICivilitySig['civilityValue']>;
  civilityDescription: FormControl<ICivilitySig['civilityDescription']>;
  civilityCode: FormControl<ICivilitySig['civilityCode']>;
  civilityParams: FormControl<ICivilitySig['civilityParams']>;
  civilityAttributs: FormControl<ICivilitySig['civilityAttributs']>;
  civilityStat: FormControl<ICivilitySig['civilityStat']>;
};

export type CivilitySigFormGroup = FormGroup<CivilitySigFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CivilitySigFormService {
  createCivilitySigFormGroup(civility: CivilitySigFormGroupInput = { civilityId: null }): CivilitySigFormGroup {
    const civilityRawValue = {
      ...this.getFormDefaults(),
      ...civility,
    };
    return new FormGroup<CivilitySigFormGroupContent>({
      civilityId: new FormControl(
        { value: civilityRawValue.civilityId, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      civilityValue: new FormControl(civilityRawValue.civilityValue, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      civilityDescription: new FormControl(civilityRawValue.civilityDescription, {
        validators: [Validators.maxLength(200)],
      }),
      civilityCode: new FormControl(civilityRawValue.civilityCode),
      civilityParams: new FormControl(civilityRawValue.civilityParams),
      civilityAttributs: new FormControl(civilityRawValue.civilityAttributs),
      civilityStat: new FormControl(civilityRawValue.civilityStat),
    });
  }

  getCivilitySig(form: CivilitySigFormGroup): ICivilitySig | NewCivilitySig {
    return form.getRawValue() as ICivilitySig | NewCivilitySig;
  }

  resetForm(form: CivilitySigFormGroup, civility: CivilitySigFormGroupInput): void {
    const civilityRawValue = { ...this.getFormDefaults(), ...civility };
    form.reset(
      {
        ...civilityRawValue,
        civilityId: { value: civilityRawValue.civilityId, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CivilitySigFormDefaults {
    return {
      civilityId: null,
      civilityStat: false,
    };
  }
}
