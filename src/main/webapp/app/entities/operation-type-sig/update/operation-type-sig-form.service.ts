import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IOperationTypeSig, NewOperationTypeSig } from '../operation-type-sig.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { operationTypeId: unknown }> = Partial<Omit<T, 'operationTypeId'>> & {
  operationTypeId: T['operationTypeId'];
};

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IOperationTypeSig for edit and NewOperationTypeSigFormGroupInput for create.
 */
type OperationTypeSigFormGroupInput = IOperationTypeSig | PartialWithRequiredKeyOf<NewOperationTypeSig>;

type OperationTypeSigFormDefaults = Pick<NewOperationTypeSig, 'operationTypeId' | 'operationTypeStat'>;

type OperationTypeSigFormGroupContent = {
  operationTypeId: FormControl<IOperationTypeSig['operationTypeId'] | NewOperationTypeSig['operationTypeId']>;
  operationTypeValue: FormControl<IOperationTypeSig['operationTypeValue']>;
  operationTypeDescription: FormControl<IOperationTypeSig['operationTypeDescription']>;
  operationTypeParams: FormControl<IOperationTypeSig['operationTypeParams']>;
  operationTypeAttributs: FormControl<IOperationTypeSig['operationTypeAttributs']>;
  operationTypeStat: FormControl<IOperationTypeSig['operationTypeStat']>;
};

export type OperationTypeSigFormGroup = FormGroup<OperationTypeSigFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class OperationTypeSigFormService {
  createOperationTypeSigFormGroup(operationType: OperationTypeSigFormGroupInput = { operationTypeId: null }): OperationTypeSigFormGroup {
    const operationTypeRawValue = {
      ...this.getFormDefaults(),
      ...operationType,
    };
    return new FormGroup<OperationTypeSigFormGroupContent>({
      operationTypeId: new FormControl(
        { value: operationTypeRawValue.operationTypeId, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      operationTypeValue: new FormControl(operationTypeRawValue.operationTypeValue, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      operationTypeDescription: new FormControl(operationTypeRawValue.operationTypeDescription, {
        validators: [Validators.maxLength(200)],
      }),
      operationTypeParams: new FormControl(operationTypeRawValue.operationTypeParams),
      operationTypeAttributs: new FormControl(operationTypeRawValue.operationTypeAttributs),
      operationTypeStat: new FormControl(operationTypeRawValue.operationTypeStat),
    });
  }

  getOperationTypeSig(form: OperationTypeSigFormGroup): IOperationTypeSig | NewOperationTypeSig {
    return form.getRawValue() as IOperationTypeSig | NewOperationTypeSig;
  }

  resetForm(form: OperationTypeSigFormGroup, operationType: OperationTypeSigFormGroupInput): void {
    const operationTypeRawValue = { ...this.getFormDefaults(), ...operationType };
    form.reset(
      {
        ...operationTypeRawValue,
        operationTypeId: { value: operationTypeRawValue.operationTypeId, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): OperationTypeSigFormDefaults {
    return {
      operationTypeId: null,
      operationTypeStat: false,
    };
  }
}
