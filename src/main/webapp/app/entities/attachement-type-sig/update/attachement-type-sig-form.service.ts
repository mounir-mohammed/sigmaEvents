import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAttachementTypeSig, NewAttachementTypeSig } from '../attachement-type-sig.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { attachementTypeId: unknown }> = Partial<Omit<T, 'attachementTypeId'>> & {
  attachementTypeId: T['attachementTypeId'];
};

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAttachementTypeSig for edit and NewAttachementTypeSigFormGroupInput for create.
 */
type AttachementTypeSigFormGroupInput = IAttachementTypeSig | PartialWithRequiredKeyOf<NewAttachementTypeSig>;

type AttachementTypeSigFormDefaults = Pick<NewAttachementTypeSig, 'attachementTypeId' | 'attachementTypeStat'>;

type AttachementTypeSigFormGroupContent = {
  attachementTypeId: FormControl<IAttachementTypeSig['attachementTypeId'] | NewAttachementTypeSig['attachementTypeId']>;
  attachementTypeName: FormControl<IAttachementTypeSig['attachementTypeName']>;
  attachementTypeDescription: FormControl<IAttachementTypeSig['attachementTypeDescription']>;
  attachementTypeParams: FormControl<IAttachementTypeSig['attachementTypeParams']>;
  attachementTypeAttributs: FormControl<IAttachementTypeSig['attachementTypeAttributs']>;
  attachementTypeStat: FormControl<IAttachementTypeSig['attachementTypeStat']>;
};

export type AttachementTypeSigFormGroup = FormGroup<AttachementTypeSigFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AttachementTypeSigFormService {
  createAttachementTypeSigFormGroup(
    attachementType: AttachementTypeSigFormGroupInput = { attachementTypeId: null }
  ): AttachementTypeSigFormGroup {
    const attachementTypeRawValue = {
      ...this.getFormDefaults(),
      ...attachementType,
    };
    return new FormGroup<AttachementTypeSigFormGroupContent>({
      attachementTypeId: new FormControl(
        { value: attachementTypeRawValue.attachementTypeId, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      attachementTypeName: new FormControl(attachementTypeRawValue.attachementTypeName, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      attachementTypeDescription: new FormControl(attachementTypeRawValue.attachementTypeDescription, {
        validators: [Validators.maxLength(200)],
      }),
      attachementTypeParams: new FormControl(attachementTypeRawValue.attachementTypeParams),
      attachementTypeAttributs: new FormControl(attachementTypeRawValue.attachementTypeAttributs),
      attachementTypeStat: new FormControl(attachementTypeRawValue.attachementTypeStat),
    });
  }

  getAttachementTypeSig(form: AttachementTypeSigFormGroup): IAttachementTypeSig | NewAttachementTypeSig {
    return form.getRawValue() as IAttachementTypeSig | NewAttachementTypeSig;
  }

  resetForm(form: AttachementTypeSigFormGroup, attachementType: AttachementTypeSigFormGroupInput): void {
    const attachementTypeRawValue = { ...this.getFormDefaults(), ...attachementType };
    form.reset(
      {
        ...attachementTypeRawValue,
        attachementTypeId: { value: attachementTypeRawValue.attachementTypeId, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AttachementTypeSigFormDefaults {
    return {
      attachementTypeId: null,
      attachementTypeStat: false,
    };
  }
}
