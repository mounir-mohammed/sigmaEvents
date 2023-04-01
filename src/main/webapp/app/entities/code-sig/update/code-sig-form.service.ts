import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICodeSig, NewCodeSig } from '../code-sig.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { codeId: unknown }> = Partial<Omit<T, 'codeId'>> & { codeId: T['codeId'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICodeSig for edit and NewCodeSigFormGroupInput for create.
 */
type CodeSigFormGroupInput = ICodeSig | PartialWithRequiredKeyOf<NewCodeSig>;

type CodeSigFormDefaults = Pick<NewCodeSig, 'codeId' | 'codeUsed' | 'codeStat'>;

type CodeSigFormGroupContent = {
  codeId: FormControl<ICodeSig['codeId'] | NewCodeSig['codeId']>;
  codeForEntity: FormControl<ICodeSig['codeForEntity']>;
  codeEntityValue: FormControl<ICodeSig['codeEntityValue']>;
  codeValue: FormControl<ICodeSig['codeValue']>;
  codeUsed: FormControl<ICodeSig['codeUsed']>;
  codeParams: FormControl<ICodeSig['codeParams']>;
  codeAttributs: FormControl<ICodeSig['codeAttributs']>;
  codeStat: FormControl<ICodeSig['codeStat']>;
  codeType: FormControl<ICodeSig['codeType']>;
  event: FormControl<ICodeSig['event']>;
};

export type CodeSigFormGroup = FormGroup<CodeSigFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CodeSigFormService {
  createCodeSigFormGroup(code: CodeSigFormGroupInput = { codeId: null }): CodeSigFormGroup {
    const codeRawValue = {
      ...this.getFormDefaults(),
      ...code,
    };
    return new FormGroup<CodeSigFormGroupContent>({
      codeId: new FormControl(
        { value: codeRawValue.codeId, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      codeForEntity: new FormControl(codeRawValue.codeForEntity),
      codeEntityValue: new FormControl(codeRawValue.codeEntityValue),
      codeValue: new FormControl(codeRawValue.codeValue, {
        validators: [Validators.required],
      }),
      codeUsed: new FormControl(codeRawValue.codeUsed),
      codeParams: new FormControl(codeRawValue.codeParams),
      codeAttributs: new FormControl(codeRawValue.codeAttributs),
      codeStat: new FormControl(codeRawValue.codeStat),
      codeType: new FormControl(codeRawValue.codeType),
      event: new FormControl(codeRawValue.event),
    });
  }

  getCodeSig(form: CodeSigFormGroup): ICodeSig | NewCodeSig {
    return form.getRawValue() as ICodeSig | NewCodeSig;
  }

  resetForm(form: CodeSigFormGroup, code: CodeSigFormGroupInput): void {
    const codeRawValue = { ...this.getFormDefaults(), ...code };
    form.reset(
      {
        ...codeRawValue,
        codeId: { value: codeRawValue.codeId, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CodeSigFormDefaults {
    return {
      codeId: null,
      codeUsed: false,
      codeStat: false,
    };
  }
}
