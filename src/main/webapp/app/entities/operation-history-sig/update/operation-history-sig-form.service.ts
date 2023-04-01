import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IOperationHistorySig, NewOperationHistorySig } from '../operation-history-sig.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { operationHistoryId: unknown }> = Partial<Omit<T, 'operationHistoryId'>> & {
  operationHistoryId: T['operationHistoryId'];
};

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IOperationHistorySig for edit and NewOperationHistorySigFormGroupInput for create.
 */
type OperationHistorySigFormGroupInput = IOperationHistorySig | PartialWithRequiredKeyOf<NewOperationHistorySig>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IOperationHistorySig | NewOperationHistorySig> = Omit<T, 'operationHistoryDate'> & {
  operationHistoryDate?: string | null;
};

type OperationHistorySigFormRawValue = FormValueOf<IOperationHistorySig>;

type NewOperationHistorySigFormRawValue = FormValueOf<NewOperationHistorySig>;

type OperationHistorySigFormDefaults = Pick<NewOperationHistorySig, 'operationHistoryId' | 'operationHistoryDate' | 'operationHistoryStat'>;

type OperationHistorySigFormGroupContent = {
  operationHistoryId: FormControl<OperationHistorySigFormRawValue['operationHistoryId'] | NewOperationHistorySig['operationHistoryId']>;
  operationHistoryDescription: FormControl<OperationHistorySigFormRawValue['operationHistoryDescription']>;
  operationHistoryDate: FormControl<OperationHistorySigFormRawValue['operationHistoryDate']>;
  operationHistoryUserID: FormControl<OperationHistorySigFormRawValue['operationHistoryUserID']>;
  operationHistoryOldValue: FormControl<OperationHistorySigFormRawValue['operationHistoryOldValue']>;
  operationHistoryNewValue: FormControl<OperationHistorySigFormRawValue['operationHistoryNewValue']>;
  operationHistoryOldId: FormControl<OperationHistorySigFormRawValue['operationHistoryOldId']>;
  operationHistoryNewId: FormControl<OperationHistorySigFormRawValue['operationHistoryNewId']>;
  operationHistoryImportedFile: FormControl<OperationHistorySigFormRawValue['operationHistoryImportedFile']>;
  operationHistoryImportedFilePath: FormControl<OperationHistorySigFormRawValue['operationHistoryImportedFilePath']>;
  operationHistoryParams: FormControl<OperationHistorySigFormRawValue['operationHistoryParams']>;
  operationHistoryAttributs: FormControl<OperationHistorySigFormRawValue['operationHistoryAttributs']>;
  operationHistoryStat: FormControl<OperationHistorySigFormRawValue['operationHistoryStat']>;
  typeoperation: FormControl<OperationHistorySigFormRawValue['typeoperation']>;
  event: FormControl<OperationHistorySigFormRawValue['event']>;
};

export type OperationHistorySigFormGroup = FormGroup<OperationHistorySigFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class OperationHistorySigFormService {
  createOperationHistorySigFormGroup(
    operationHistory: OperationHistorySigFormGroupInput = { operationHistoryId: null }
  ): OperationHistorySigFormGroup {
    const operationHistoryRawValue = this.convertOperationHistorySigToOperationHistorySigRawValue({
      ...this.getFormDefaults(),
      ...operationHistory,
    });
    return new FormGroup<OperationHistorySigFormGroupContent>({
      operationHistoryId: new FormControl(
        { value: operationHistoryRawValue.operationHistoryId, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      operationHistoryDescription: new FormControl(operationHistoryRawValue.operationHistoryDescription, {
        validators: [Validators.maxLength(200)],
      }),
      operationHistoryDate: new FormControl(operationHistoryRawValue.operationHistoryDate),
      operationHistoryUserID: new FormControl(operationHistoryRawValue.operationHistoryUserID),
      operationHistoryOldValue: new FormControl(operationHistoryRawValue.operationHistoryOldValue),
      operationHistoryNewValue: new FormControl(operationHistoryRawValue.operationHistoryNewValue),
      operationHistoryOldId: new FormControl(operationHistoryRawValue.operationHistoryOldId),
      operationHistoryNewId: new FormControl(operationHistoryRawValue.operationHistoryNewId),
      operationHistoryImportedFile: new FormControl(operationHistoryRawValue.operationHistoryImportedFile),
      operationHistoryImportedFilePath: new FormControl(operationHistoryRawValue.operationHistoryImportedFilePath),
      operationHistoryParams: new FormControl(operationHistoryRawValue.operationHistoryParams),
      operationHistoryAttributs: new FormControl(operationHistoryRawValue.operationHistoryAttributs),
      operationHistoryStat: new FormControl(operationHistoryRawValue.operationHistoryStat),
      typeoperation: new FormControl(operationHistoryRawValue.typeoperation),
      event: new FormControl(operationHistoryRawValue.event),
    });
  }

  getOperationHistorySig(form: OperationHistorySigFormGroup): IOperationHistorySig | NewOperationHistorySig {
    return this.convertOperationHistorySigRawValueToOperationHistorySig(
      form.getRawValue() as OperationHistorySigFormRawValue | NewOperationHistorySigFormRawValue
    );
  }

  resetForm(form: OperationHistorySigFormGroup, operationHistory: OperationHistorySigFormGroupInput): void {
    const operationHistoryRawValue = this.convertOperationHistorySigToOperationHistorySigRawValue({
      ...this.getFormDefaults(),
      ...operationHistory,
    });
    form.reset(
      {
        ...operationHistoryRawValue,
        operationHistoryId: { value: operationHistoryRawValue.operationHistoryId, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): OperationHistorySigFormDefaults {
    const currentTime = dayjs();

    return {
      operationHistoryId: null,
      operationHistoryDate: currentTime,
      operationHistoryStat: false,
    };
  }

  private convertOperationHistorySigRawValueToOperationHistorySig(
    rawOperationHistorySig: OperationHistorySigFormRawValue | NewOperationHistorySigFormRawValue
  ): IOperationHistorySig | NewOperationHistorySig {
    return {
      ...rawOperationHistorySig,
      operationHistoryDate: dayjs(rawOperationHistorySig.operationHistoryDate, DATE_TIME_FORMAT),
    };
  }

  private convertOperationHistorySigToOperationHistorySigRawValue(
    operationHistory: IOperationHistorySig | (Partial<NewOperationHistorySig> & OperationHistorySigFormDefaults)
  ): OperationHistorySigFormRawValue | PartialWithRequiredKeyOf<NewOperationHistorySigFormRawValue> {
    return {
      ...operationHistory,
      operationHistoryDate: operationHistory.operationHistoryDate
        ? operationHistory.operationHistoryDate.format(DATE_TIME_FORMAT)
        : undefined,
    };
  }
}
