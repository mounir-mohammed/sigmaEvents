import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICheckAccreditationHistorySig, NewCheckAccreditationHistorySig } from '../check-accreditation-history-sig.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { checkAccreditationHistoryId: unknown }> = Partial<Omit<T, 'checkAccreditationHistoryId'>> & {
  checkAccreditationHistoryId: T['checkAccreditationHistoryId'];
};

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICheckAccreditationHistorySig for edit and NewCheckAccreditationHistorySigFormGroupInput for create.
 */
type CheckAccreditationHistorySigFormGroupInput = ICheckAccreditationHistorySig | PartialWithRequiredKeyOf<NewCheckAccreditationHistorySig>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ICheckAccreditationHistorySig | NewCheckAccreditationHistorySig> = Omit<T, 'checkAccreditationHistoryDate'> & {
  checkAccreditationHistoryDate?: string | null;
};

type CheckAccreditationHistorySigFormRawValue = FormValueOf<ICheckAccreditationHistorySig>;

type NewCheckAccreditationHistorySigFormRawValue = FormValueOf<NewCheckAccreditationHistorySig>;

type CheckAccreditationHistorySigFormDefaults = Pick<
  NewCheckAccreditationHistorySig,
  'checkAccreditationHistoryId' | 'checkAccreditationHistoryResult' | 'checkAccreditationHistoryDate' | 'checkAccreditationHistoryStat'
>;

type CheckAccreditationHistorySigFormGroupContent = {
  checkAccreditationHistoryId: FormControl<
    CheckAccreditationHistorySigFormRawValue['checkAccreditationHistoryId'] | NewCheckAccreditationHistorySig['checkAccreditationHistoryId']
  >;
  checkAccreditationHistoryReadedCode: FormControl<CheckAccreditationHistorySigFormRawValue['checkAccreditationHistoryReadedCode']>;
  checkAccreditationHistoryUserId: FormControl<CheckAccreditationHistorySigFormRawValue['checkAccreditationHistoryUserId']>;
  checkAccreditationHistoryResult: FormControl<CheckAccreditationHistorySigFormRawValue['checkAccreditationHistoryResult']>;
  checkAccreditationHistoryError: FormControl<CheckAccreditationHistorySigFormRawValue['checkAccreditationHistoryError']>;
  checkAccreditationHistoryDate: FormControl<CheckAccreditationHistorySigFormRawValue['checkAccreditationHistoryDate']>;
  checkAccreditationHistoryLocalisation: FormControl<CheckAccreditationHistorySigFormRawValue['checkAccreditationHistoryLocalisation']>;
  checkAccreditationHistoryIpAdresse: FormControl<CheckAccreditationHistorySigFormRawValue['checkAccreditationHistoryIpAdresse']>;
  checkAccreditationParams: FormControl<CheckAccreditationHistorySigFormRawValue['checkAccreditationParams']>;
  checkAccreditationAttributs: FormControl<CheckAccreditationHistorySigFormRawValue['checkAccreditationAttributs']>;
  checkAccreditationHistoryStat: FormControl<CheckAccreditationHistorySigFormRawValue['checkAccreditationHistoryStat']>;
  event: FormControl<CheckAccreditationHistorySigFormRawValue['event']>;
  accreditation: FormControl<CheckAccreditationHistorySigFormRawValue['accreditation']>;
};

export type CheckAccreditationHistorySigFormGroup = FormGroup<CheckAccreditationHistorySigFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CheckAccreditationHistorySigFormService {
  createCheckAccreditationHistorySigFormGroup(
    checkAccreditationHistory: CheckAccreditationHistorySigFormGroupInput = { checkAccreditationHistoryId: null }
  ): CheckAccreditationHistorySigFormGroup {
    const checkAccreditationHistoryRawValue = this.convertCheckAccreditationHistorySigToCheckAccreditationHistorySigRawValue({
      ...this.getFormDefaults(),
      ...checkAccreditationHistory,
    });
    return new FormGroup<CheckAccreditationHistorySigFormGroupContent>({
      checkAccreditationHistoryId: new FormControl(
        { value: checkAccreditationHistoryRawValue.checkAccreditationHistoryId, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      checkAccreditationHistoryReadedCode: new FormControl(checkAccreditationHistoryRawValue.checkAccreditationHistoryReadedCode),
      checkAccreditationHistoryUserId: new FormControl(checkAccreditationHistoryRawValue.checkAccreditationHistoryUserId),
      checkAccreditationHistoryResult: new FormControl(checkAccreditationHistoryRawValue.checkAccreditationHistoryResult),
      checkAccreditationHistoryError: new FormControl(checkAccreditationHistoryRawValue.checkAccreditationHistoryError),
      checkAccreditationHistoryDate: new FormControl(checkAccreditationHistoryRawValue.checkAccreditationHistoryDate),
      checkAccreditationHistoryLocalisation: new FormControl(checkAccreditationHistoryRawValue.checkAccreditationHistoryLocalisation),
      checkAccreditationHistoryIpAdresse: new FormControl(checkAccreditationHistoryRawValue.checkAccreditationHistoryIpAdresse),
      checkAccreditationParams: new FormControl(checkAccreditationHistoryRawValue.checkAccreditationParams),
      checkAccreditationAttributs: new FormControl(checkAccreditationHistoryRawValue.checkAccreditationAttributs),
      checkAccreditationHistoryStat: new FormControl(checkAccreditationHistoryRawValue.checkAccreditationHistoryStat),
      event: new FormControl(checkAccreditationHistoryRawValue.event),
      accreditation: new FormControl(checkAccreditationHistoryRawValue.accreditation),
    });
  }

  getCheckAccreditationHistorySig(
    form: CheckAccreditationHistorySigFormGroup
  ): ICheckAccreditationHistorySig | NewCheckAccreditationHistorySig {
    return this.convertCheckAccreditationHistorySigRawValueToCheckAccreditationHistorySig(
      form.getRawValue() as CheckAccreditationHistorySigFormRawValue | NewCheckAccreditationHistorySigFormRawValue
    );
  }

  resetForm(form: CheckAccreditationHistorySigFormGroup, checkAccreditationHistory: CheckAccreditationHistorySigFormGroupInput): void {
    const checkAccreditationHistoryRawValue = this.convertCheckAccreditationHistorySigToCheckAccreditationHistorySigRawValue({
      ...this.getFormDefaults(),
      ...checkAccreditationHistory,
    });
    form.reset(
      {
        ...checkAccreditationHistoryRawValue,
        checkAccreditationHistoryId: { value: checkAccreditationHistoryRawValue.checkAccreditationHistoryId, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CheckAccreditationHistorySigFormDefaults {
    const currentTime = dayjs();

    return {
      checkAccreditationHistoryId: null,
      checkAccreditationHistoryResult: false,
      checkAccreditationHistoryDate: currentTime,
      checkAccreditationHistoryStat: false,
    };
  }

  private convertCheckAccreditationHistorySigRawValueToCheckAccreditationHistorySig(
    rawCheckAccreditationHistorySig: CheckAccreditationHistorySigFormRawValue | NewCheckAccreditationHistorySigFormRawValue
  ): ICheckAccreditationHistorySig | NewCheckAccreditationHistorySig {
    return {
      ...rawCheckAccreditationHistorySig,
      checkAccreditationHistoryDate: dayjs(rawCheckAccreditationHistorySig.checkAccreditationHistoryDate, DATE_TIME_FORMAT),
    };
  }

  private convertCheckAccreditationHistorySigToCheckAccreditationHistorySigRawValue(
    checkAccreditationHistory:
      | ICheckAccreditationHistorySig
      | (Partial<NewCheckAccreditationHistorySig> & CheckAccreditationHistorySigFormDefaults)
  ): CheckAccreditationHistorySigFormRawValue | PartialWithRequiredKeyOf<NewCheckAccreditationHistorySigFormRawValue> {
    return {
      ...checkAccreditationHistory,
      checkAccreditationHistoryDate: checkAccreditationHistory.checkAccreditationHistoryDate
        ? checkAccreditationHistory.checkAccreditationHistoryDate.format(DATE_TIME_FORMAT)
        : undefined,
    };
  }
}
