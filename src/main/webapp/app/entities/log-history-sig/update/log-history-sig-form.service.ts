import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ILogHistorySig, NewLogHistorySig } from '../log-history-sig.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { logHistory: unknown }> = Partial<Omit<T, 'logHistory'>> & { logHistory: T['logHistory'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ILogHistorySig for edit and NewLogHistorySigFormGroupInput for create.
 */
type LogHistorySigFormGroupInput = ILogHistorySig | PartialWithRequiredKeyOf<NewLogHistorySig>;

type LogHistorySigFormDefaults = Pick<NewLogHistorySig, 'logHistory' | 'logHistoryStat'>;

type LogHistorySigFormGroupContent = {
  logHistory: FormControl<ILogHistorySig['logHistory'] | NewLogHistorySig['logHistory']>;
  logHistoryDescription: FormControl<ILogHistorySig['logHistoryDescription']>;
  logHistoryParams: FormControl<ILogHistorySig['logHistoryParams']>;
  logHistoryAttributs: FormControl<ILogHistorySig['logHistoryAttributs']>;
  logHistoryStat: FormControl<ILogHistorySig['logHistoryStat']>;
};

export type LogHistorySigFormGroup = FormGroup<LogHistorySigFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class LogHistorySigFormService {
  createLogHistorySigFormGroup(logHistory: LogHistorySigFormGroupInput = { logHistory: null }): LogHistorySigFormGroup {
    const logHistoryRawValue = {
      ...this.getFormDefaults(),
      ...logHistory,
    };
    return new FormGroup<LogHistorySigFormGroupContent>({
      logHistory: new FormControl(
        { value: logHistoryRawValue.logHistory, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      logHistoryDescription: new FormControl(logHistoryRawValue.logHistoryDescription),
      logHistoryParams: new FormControl(logHistoryRawValue.logHistoryParams),
      logHistoryAttributs: new FormControl(logHistoryRawValue.logHistoryAttributs),
      logHistoryStat: new FormControl(logHistoryRawValue.logHistoryStat),
    });
  }

  getLogHistorySig(form: LogHistorySigFormGroup): ILogHistorySig | NewLogHistorySig {
    return form.getRawValue() as ILogHistorySig | NewLogHistorySig;
  }

  resetForm(form: LogHistorySigFormGroup, logHistory: LogHistorySigFormGroupInput): void {
    const logHistoryRawValue = { ...this.getFormDefaults(), ...logHistory };
    form.reset(
      {
        ...logHistoryRawValue,
        logHistory: { value: logHistoryRawValue.logHistory, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): LogHistorySigFormDefaults {
    return {
      logHistory: null,
      logHistoryStat: false,
    };
  }
}
