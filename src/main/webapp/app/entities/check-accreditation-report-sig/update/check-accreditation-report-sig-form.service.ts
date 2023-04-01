import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICheckAccreditationReportSig, NewCheckAccreditationReportSig } from '../check-accreditation-report-sig.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { checkAccreditationReportId: unknown }> = Partial<Omit<T, 'checkAccreditationReportId'>> & {
  checkAccreditationReportId: T['checkAccreditationReportId'];
};

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICheckAccreditationReportSig for edit and NewCheckAccreditationReportSigFormGroupInput for create.
 */
type CheckAccreditationReportSigFormGroupInput = ICheckAccreditationReportSig | PartialWithRequiredKeyOf<NewCheckAccreditationReportSig>;

type CheckAccreditationReportSigFormDefaults = Pick<
  NewCheckAccreditationReportSig,
  'checkAccreditationReportId' | 'checkAccreditationReportStat'
>;

type CheckAccreditationReportSigFormGroupContent = {
  checkAccreditationReportId: FormControl<
    ICheckAccreditationReportSig['checkAccreditationReportId'] | NewCheckAccreditationReportSig['checkAccreditationReportId']
  >;
  checkAccreditationReportDescription: FormControl<ICheckAccreditationReportSig['checkAccreditationReportDescription']>;
  checkAccreditationReportPersonPhoto: FormControl<ICheckAccreditationReportSig['checkAccreditationReportPersonPhoto']>;
  checkAccreditationReportPersonPhotoContentType: FormControl<
    ICheckAccreditationReportSig['checkAccreditationReportPersonPhotoContentType']
  >;
  checkAccreditationReportCINPhoto: FormControl<ICheckAccreditationReportSig['checkAccreditationReportCINPhoto']>;
  checkAccreditationReportCINPhotoContentType: FormControl<ICheckAccreditationReportSig['checkAccreditationReportCINPhotoContentType']>;
  checkAccreditationReportAttachment: FormControl<ICheckAccreditationReportSig['checkAccreditationReportAttachment']>;
  checkAccreditationReportAttachmentContentType: FormControl<ICheckAccreditationReportSig['checkAccreditationReportAttachmentContentType']>;
  checkAccreditationReportParams: FormControl<ICheckAccreditationReportSig['checkAccreditationReportParams']>;
  checkAccreditationReportAttributs: FormControl<ICheckAccreditationReportSig['checkAccreditationReportAttributs']>;
  checkAccreditationReportStat: FormControl<ICheckAccreditationReportSig['checkAccreditationReportStat']>;
  event: FormControl<ICheckAccreditationReportSig['event']>;
  checkAccreditationHistory: FormControl<ICheckAccreditationReportSig['checkAccreditationHistory']>;
};

export type CheckAccreditationReportSigFormGroup = FormGroup<CheckAccreditationReportSigFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CheckAccreditationReportSigFormService {
  createCheckAccreditationReportSigFormGroup(
    checkAccreditationReport: CheckAccreditationReportSigFormGroupInput = { checkAccreditationReportId: null }
  ): CheckAccreditationReportSigFormGroup {
    const checkAccreditationReportRawValue = {
      ...this.getFormDefaults(),
      ...checkAccreditationReport,
    };
    return new FormGroup<CheckAccreditationReportSigFormGroupContent>({
      checkAccreditationReportId: new FormControl(
        { value: checkAccreditationReportRawValue.checkAccreditationReportId, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      checkAccreditationReportDescription: new FormControl(checkAccreditationReportRawValue.checkAccreditationReportDescription, {
        validators: [Validators.maxLength(200)],
      }),
      checkAccreditationReportPersonPhoto: new FormControl(checkAccreditationReportRawValue.checkAccreditationReportPersonPhoto),
      checkAccreditationReportPersonPhotoContentType: new FormControl(
        checkAccreditationReportRawValue.checkAccreditationReportPersonPhotoContentType
      ),
      checkAccreditationReportCINPhoto: new FormControl(checkAccreditationReportRawValue.checkAccreditationReportCINPhoto),
      checkAccreditationReportCINPhotoContentType: new FormControl(
        checkAccreditationReportRawValue.checkAccreditationReportCINPhotoContentType
      ),
      checkAccreditationReportAttachment: new FormControl(checkAccreditationReportRawValue.checkAccreditationReportAttachment),
      checkAccreditationReportAttachmentContentType: new FormControl(
        checkAccreditationReportRawValue.checkAccreditationReportAttachmentContentType
      ),
      checkAccreditationReportParams: new FormControl(checkAccreditationReportRawValue.checkAccreditationReportParams),
      checkAccreditationReportAttributs: new FormControl(checkAccreditationReportRawValue.checkAccreditationReportAttributs),
      checkAccreditationReportStat: new FormControl(checkAccreditationReportRawValue.checkAccreditationReportStat),
      event: new FormControl(checkAccreditationReportRawValue.event),
      checkAccreditationHistory: new FormControl(checkAccreditationReportRawValue.checkAccreditationHistory),
    });
  }

  getCheckAccreditationReportSig(
    form: CheckAccreditationReportSigFormGroup
  ): ICheckAccreditationReportSig | NewCheckAccreditationReportSig {
    return form.getRawValue() as ICheckAccreditationReportSig | NewCheckAccreditationReportSig;
  }

  resetForm(form: CheckAccreditationReportSigFormGroup, checkAccreditationReport: CheckAccreditationReportSigFormGroupInput): void {
    const checkAccreditationReportRawValue = { ...this.getFormDefaults(), ...checkAccreditationReport };
    form.reset(
      {
        ...checkAccreditationReportRawValue,
        checkAccreditationReportId: { value: checkAccreditationReportRawValue.checkAccreditationReportId, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CheckAccreditationReportSigFormDefaults {
    return {
      checkAccreditationReportId: null,
      checkAccreditationReportStat: false,
    };
  }
}
