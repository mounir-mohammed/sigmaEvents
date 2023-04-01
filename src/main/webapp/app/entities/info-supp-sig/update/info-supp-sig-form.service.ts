import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IInfoSuppSig, NewInfoSuppSig } from '../info-supp-sig.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { infoSuppId: unknown }> = Partial<Omit<T, 'infoSuppId'>> & { infoSuppId: T['infoSuppId'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IInfoSuppSig for edit and NewInfoSuppSigFormGroupInput for create.
 */
type InfoSuppSigFormGroupInput = IInfoSuppSig | PartialWithRequiredKeyOf<NewInfoSuppSig>;

type InfoSuppSigFormDefaults = Pick<NewInfoSuppSig, 'infoSuppId' | 'infoSuppStat'>;

type InfoSuppSigFormGroupContent = {
  infoSuppId: FormControl<IInfoSuppSig['infoSuppId'] | NewInfoSuppSig['infoSuppId']>;
  infoSuppName: FormControl<IInfoSuppSig['infoSuppName']>;
  infoSuppDescription: FormControl<IInfoSuppSig['infoSuppDescription']>;
  infoSuppParams: FormControl<IInfoSuppSig['infoSuppParams']>;
  infoSuppAttributs: FormControl<IInfoSuppSig['infoSuppAttributs']>;
  infoSuppStat: FormControl<IInfoSuppSig['infoSuppStat']>;
  infoSuppType: FormControl<IInfoSuppSig['infoSuppType']>;
  accreditation: FormControl<IInfoSuppSig['accreditation']>;
  event: FormControl<IInfoSuppSig['event']>;
};

export type InfoSuppSigFormGroup = FormGroup<InfoSuppSigFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class InfoSuppSigFormService {
  createInfoSuppSigFormGroup(infoSupp: InfoSuppSigFormGroupInput = { infoSuppId: null }): InfoSuppSigFormGroup {
    const infoSuppRawValue = {
      ...this.getFormDefaults(),
      ...infoSupp,
    };
    return new FormGroup<InfoSuppSigFormGroupContent>({
      infoSuppId: new FormControl(
        { value: infoSuppRawValue.infoSuppId, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      infoSuppName: new FormControl(infoSuppRawValue.infoSuppName, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      infoSuppDescription: new FormControl(infoSuppRawValue.infoSuppDescription, {
        validators: [Validators.maxLength(200)],
      }),
      infoSuppParams: new FormControl(infoSuppRawValue.infoSuppParams),
      infoSuppAttributs: new FormControl(infoSuppRawValue.infoSuppAttributs),
      infoSuppStat: new FormControl(infoSuppRawValue.infoSuppStat),
      infoSuppType: new FormControl(infoSuppRawValue.infoSuppType),
      accreditation: new FormControl(infoSuppRawValue.accreditation),
      event: new FormControl(infoSuppRawValue.event),
    });
  }

  getInfoSuppSig(form: InfoSuppSigFormGroup): IInfoSuppSig | NewInfoSuppSig {
    return form.getRawValue() as IInfoSuppSig | NewInfoSuppSig;
  }

  resetForm(form: InfoSuppSigFormGroup, infoSupp: InfoSuppSigFormGroupInput): void {
    const infoSuppRawValue = { ...this.getFormDefaults(), ...infoSupp };
    form.reset(
      {
        ...infoSuppRawValue,
        infoSuppId: { value: infoSuppRawValue.infoSuppId, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): InfoSuppSigFormDefaults {
    return {
      infoSuppId: null,
      infoSuppStat: false,
    };
  }
}
