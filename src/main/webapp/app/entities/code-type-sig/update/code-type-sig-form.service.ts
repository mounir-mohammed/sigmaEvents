import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICodeTypeSig, NewCodeTypeSig } from '../code-type-sig.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { codeTypeId: unknown }> = Partial<Omit<T, 'codeTypeId'>> & { codeTypeId: T['codeTypeId'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICodeTypeSig for edit and NewCodeTypeSigFormGroupInput for create.
 */
type CodeTypeSigFormGroupInput = ICodeTypeSig | PartialWithRequiredKeyOf<NewCodeTypeSig>;

type CodeTypeSigFormDefaults = Pick<NewCodeTypeSig, 'codeTypeId' | 'codeTypeStat'>;

type CodeTypeSigFormGroupContent = {
  codeTypeId: FormControl<ICodeTypeSig['codeTypeId'] | NewCodeTypeSig['codeTypeId']>;
  codeTypeValue: FormControl<ICodeTypeSig['codeTypeValue']>;
  codeTypeDescription: FormControl<ICodeTypeSig['codeTypeDescription']>;
  codeTypeParams: FormControl<ICodeTypeSig['codeTypeParams']>;
  codeTypeAttributs: FormControl<ICodeTypeSig['codeTypeAttributs']>;
  codeTypeStat: FormControl<ICodeTypeSig['codeTypeStat']>;
};

export type CodeTypeSigFormGroup = FormGroup<CodeTypeSigFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CodeTypeSigFormService {
  createCodeTypeSigFormGroup(codeType: CodeTypeSigFormGroupInput = { codeTypeId: null }): CodeTypeSigFormGroup {
    const codeTypeRawValue = {
      ...this.getFormDefaults(),
      ...codeType,
    };
    return new FormGroup<CodeTypeSigFormGroupContent>({
      codeTypeId: new FormControl(
        { value: codeTypeRawValue.codeTypeId, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      codeTypeValue: new FormControl(codeTypeRawValue.codeTypeValue, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      codeTypeDescription: new FormControl(codeTypeRawValue.codeTypeDescription, {
        validators: [Validators.maxLength(200)],
      }),
      codeTypeParams: new FormControl(codeTypeRawValue.codeTypeParams),
      codeTypeAttributs: new FormControl(codeTypeRawValue.codeTypeAttributs),
      codeTypeStat: new FormControl(codeTypeRawValue.codeTypeStat),
    });
  }

  getCodeTypeSig(form: CodeTypeSigFormGroup): ICodeTypeSig | NewCodeTypeSig {
    return form.getRawValue() as ICodeTypeSig | NewCodeTypeSig;
  }

  resetForm(form: CodeTypeSigFormGroup, codeType: CodeTypeSigFormGroupInput): void {
    const codeTypeRawValue = { ...this.getFormDefaults(), ...codeType };
    form.reset(
      {
        ...codeTypeRawValue,
        codeTypeId: { value: codeTypeRawValue.codeTypeId, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CodeTypeSigFormDefaults {
    return {
      codeTypeId: null,
      codeTypeStat: false,
    };
  }
}
