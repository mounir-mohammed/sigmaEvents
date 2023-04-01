import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAuthentificationTypeSig, NewAuthentificationTypeSig } from '../authentification-type-sig.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { authentificationTypeId: unknown }> = Partial<Omit<T, 'authentificationTypeId'>> & {
  authentificationTypeId: T['authentificationTypeId'];
};

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAuthentificationTypeSig for edit and NewAuthentificationTypeSigFormGroupInput for create.
 */
type AuthentificationTypeSigFormGroupInput = IAuthentificationTypeSig | PartialWithRequiredKeyOf<NewAuthentificationTypeSig>;

type AuthentificationTypeSigFormDefaults = Pick<NewAuthentificationTypeSig, 'authentificationTypeId' | 'authentificationTypeStat'>;

type AuthentificationTypeSigFormGroupContent = {
  authentificationTypeId: FormControl<
    IAuthentificationTypeSig['authentificationTypeId'] | NewAuthentificationTypeSig['authentificationTypeId']
  >;
  authentificationTypeValue: FormControl<IAuthentificationTypeSig['authentificationTypeValue']>;
  authentificationTypeDescription: FormControl<IAuthentificationTypeSig['authentificationTypeDescription']>;
  authentificationTypeParams: FormControl<IAuthentificationTypeSig['authentificationTypeParams']>;
  authentificationTypeAttributs: FormControl<IAuthentificationTypeSig['authentificationTypeAttributs']>;
  authentificationTypeStat: FormControl<IAuthentificationTypeSig['authentificationTypeStat']>;
};

export type AuthentificationTypeSigFormGroup = FormGroup<AuthentificationTypeSigFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AuthentificationTypeSigFormService {
  createAuthentificationTypeSigFormGroup(
    authentificationType: AuthentificationTypeSigFormGroupInput = { authentificationTypeId: null }
  ): AuthentificationTypeSigFormGroup {
    const authentificationTypeRawValue = {
      ...this.getFormDefaults(),
      ...authentificationType,
    };
    return new FormGroup<AuthentificationTypeSigFormGroupContent>({
      authentificationTypeId: new FormControl(
        { value: authentificationTypeRawValue.authentificationTypeId, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      authentificationTypeValue: new FormControl(authentificationTypeRawValue.authentificationTypeValue, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      authentificationTypeDescription: new FormControl(authentificationTypeRawValue.authentificationTypeDescription, {
        validators: [Validators.maxLength(200)],
      }),
      authentificationTypeParams: new FormControl(authentificationTypeRawValue.authentificationTypeParams),
      authentificationTypeAttributs: new FormControl(authentificationTypeRawValue.authentificationTypeAttributs),
      authentificationTypeStat: new FormControl(authentificationTypeRawValue.authentificationTypeStat),
    });
  }

  getAuthentificationTypeSig(form: AuthentificationTypeSigFormGroup): IAuthentificationTypeSig | NewAuthentificationTypeSig {
    return form.getRawValue() as IAuthentificationTypeSig | NewAuthentificationTypeSig;
  }

  resetForm(form: AuthentificationTypeSigFormGroup, authentificationType: AuthentificationTypeSigFormGroupInput): void {
    const authentificationTypeRawValue = { ...this.getFormDefaults(), ...authentificationType };
    form.reset(
      {
        ...authentificationTypeRawValue,
        authentificationTypeId: { value: authentificationTypeRawValue.authentificationTypeId, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AuthentificationTypeSigFormDefaults {
    return {
      authentificationTypeId: null,
      authentificationTypeStat: false,
    };
  }
}
