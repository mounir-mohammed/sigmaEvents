import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IInfoSuppTypeSig, NewInfoSuppTypeSig } from '../info-supp-type-sig.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { infoSuppTypeId: unknown }> = Partial<Omit<T, 'infoSuppTypeId'>> & {
  infoSuppTypeId: T['infoSuppTypeId'];
};

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IInfoSuppTypeSig for edit and NewInfoSuppTypeSigFormGroupInput for create.
 */
type InfoSuppTypeSigFormGroupInput = IInfoSuppTypeSig | PartialWithRequiredKeyOf<NewInfoSuppTypeSig>;

type InfoSuppTypeSigFormDefaults = Pick<NewInfoSuppTypeSig, 'infoSuppTypeId' | 'infoSuppTypeStat'>;

type InfoSuppTypeSigFormGroupContent = {
  infoSuppTypeId: FormControl<IInfoSuppTypeSig['infoSuppTypeId'] | NewInfoSuppTypeSig['infoSuppTypeId']>;
  infoSuppTypeName: FormControl<IInfoSuppTypeSig['infoSuppTypeName']>;
  infoSuppTypeDescription: FormControl<IInfoSuppTypeSig['infoSuppTypeDescription']>;
  infoSuppTypeParams: FormControl<IInfoSuppTypeSig['infoSuppTypeParams']>;
  infoSuppTypeAttributs: FormControl<IInfoSuppTypeSig['infoSuppTypeAttributs']>;
  infoSuppTypeStat: FormControl<IInfoSuppTypeSig['infoSuppTypeStat']>;
};

export type InfoSuppTypeSigFormGroup = FormGroup<InfoSuppTypeSigFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class InfoSuppTypeSigFormService {
  createInfoSuppTypeSigFormGroup(infoSuppType: InfoSuppTypeSigFormGroupInput = { infoSuppTypeId: null }): InfoSuppTypeSigFormGroup {
    const infoSuppTypeRawValue = {
      ...this.getFormDefaults(),
      ...infoSuppType,
    };
    return new FormGroup<InfoSuppTypeSigFormGroupContent>({
      infoSuppTypeId: new FormControl(
        { value: infoSuppTypeRawValue.infoSuppTypeId, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      infoSuppTypeName: new FormControl(infoSuppTypeRawValue.infoSuppTypeName, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      infoSuppTypeDescription: new FormControl(infoSuppTypeRawValue.infoSuppTypeDescription, {
        validators: [Validators.maxLength(200)],
      }),
      infoSuppTypeParams: new FormControl(infoSuppTypeRawValue.infoSuppTypeParams),
      infoSuppTypeAttributs: new FormControl(infoSuppTypeRawValue.infoSuppTypeAttributs),
      infoSuppTypeStat: new FormControl(infoSuppTypeRawValue.infoSuppTypeStat),
    });
  }

  getInfoSuppTypeSig(form: InfoSuppTypeSigFormGroup): IInfoSuppTypeSig | NewInfoSuppTypeSig {
    return form.getRawValue() as IInfoSuppTypeSig | NewInfoSuppTypeSig;
  }

  resetForm(form: InfoSuppTypeSigFormGroup, infoSuppType: InfoSuppTypeSigFormGroupInput): void {
    const infoSuppTypeRawValue = { ...this.getFormDefaults(), ...infoSuppType };
    form.reset(
      {
        ...infoSuppTypeRawValue,
        infoSuppTypeId: { value: infoSuppTypeRawValue.infoSuppTypeId, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): InfoSuppTypeSigFormDefaults {
    return {
      infoSuppTypeId: null,
      infoSuppTypeStat: false,
    };
  }
}
