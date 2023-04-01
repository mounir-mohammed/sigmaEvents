import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IStatusSig, NewStatusSig } from '../status-sig.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { statusId: unknown }> = Partial<Omit<T, 'statusId'>> & { statusId: T['statusId'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IStatusSig for edit and NewStatusSigFormGroupInput for create.
 */
type StatusSigFormGroupInput = IStatusSig | PartialWithRequiredKeyOf<NewStatusSig>;

type StatusSigFormDefaults = Pick<
  NewStatusSig,
  'statusId' | 'statusUserCanPrint' | 'statusUserCanUpdate' | 'statusUserCanValidate' | 'statusStat'
>;

type StatusSigFormGroupContent = {
  statusId: FormControl<IStatusSig['statusId'] | NewStatusSig['statusId']>;
  statusName: FormControl<IStatusSig['statusName']>;
  statusAbreviation: FormControl<IStatusSig['statusAbreviation']>;
  statusColor: FormControl<IStatusSig['statusColor']>;
  statusDescription: FormControl<IStatusSig['statusDescription']>;
  statusUserCanPrint: FormControl<IStatusSig['statusUserCanPrint']>;
  statusUserCanUpdate: FormControl<IStatusSig['statusUserCanUpdate']>;
  statusUserCanValidate: FormControl<IStatusSig['statusUserCanValidate']>;
  statusParams: FormControl<IStatusSig['statusParams']>;
  statusAttributs: FormControl<IStatusSig['statusAttributs']>;
  statusStat: FormControl<IStatusSig['statusStat']>;
};

export type StatusSigFormGroup = FormGroup<StatusSigFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class StatusSigFormService {
  createStatusSigFormGroup(status: StatusSigFormGroupInput = { statusId: null }): StatusSigFormGroup {
    const statusRawValue = {
      ...this.getFormDefaults(),
      ...status,
    };
    return new FormGroup<StatusSigFormGroupContent>({
      statusId: new FormControl(
        { value: statusRawValue.statusId, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      statusName: new FormControl(statusRawValue.statusName, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      statusAbreviation: new FormControl(statusRawValue.statusAbreviation, {
        validators: [Validators.maxLength(10)],
      }),
      statusColor: new FormControl(statusRawValue.statusColor, {
        validators: [Validators.maxLength(100)],
      }),
      statusDescription: new FormControl(statusRawValue.statusDescription, {
        validators: [Validators.maxLength(200)],
      }),
      statusUserCanPrint: new FormControl(statusRawValue.statusUserCanPrint),
      statusUserCanUpdate: new FormControl(statusRawValue.statusUserCanUpdate),
      statusUserCanValidate: new FormControl(statusRawValue.statusUserCanValidate),
      statusParams: new FormControl(statusRawValue.statusParams),
      statusAttributs: new FormControl(statusRawValue.statusAttributs),
      statusStat: new FormControl(statusRawValue.statusStat),
    });
  }

  getStatusSig(form: StatusSigFormGroup): IStatusSig | NewStatusSig {
    return form.getRawValue() as IStatusSig | NewStatusSig;
  }

  resetForm(form: StatusSigFormGroup, status: StatusSigFormGroupInput): void {
    const statusRawValue = { ...this.getFormDefaults(), ...status };
    form.reset(
      {
        ...statusRawValue,
        statusId: { value: statusRawValue.statusId, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): StatusSigFormDefaults {
    return {
      statusId: null,
      statusUserCanPrint: false,
      statusUserCanUpdate: false,
      statusUserCanValidate: false,
      statusStat: false,
    };
  }
}
