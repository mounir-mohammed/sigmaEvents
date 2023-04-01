import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAccreditationTypeSig, NewAccreditationTypeSig } from '../accreditation-type-sig.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { accreditationTypeId: unknown }> = Partial<Omit<T, 'accreditationTypeId'>> & {
  accreditationTypeId: T['accreditationTypeId'];
};

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAccreditationTypeSig for edit and NewAccreditationTypeSigFormGroupInput for create.
 */
type AccreditationTypeSigFormGroupInput = IAccreditationTypeSig | PartialWithRequiredKeyOf<NewAccreditationTypeSig>;

type AccreditationTypeSigFormDefaults = Pick<NewAccreditationTypeSig, 'accreditationTypeId' | 'accreditationTypeStat'>;

type AccreditationTypeSigFormGroupContent = {
  accreditationTypeId: FormControl<IAccreditationTypeSig['accreditationTypeId'] | NewAccreditationTypeSig['accreditationTypeId']>;
  accreditationTypeValue: FormControl<IAccreditationTypeSig['accreditationTypeValue']>;
  accreditationTypeAbreviation: FormControl<IAccreditationTypeSig['accreditationTypeAbreviation']>;
  accreditationTypeDescription: FormControl<IAccreditationTypeSig['accreditationTypeDescription']>;
  accreditationTypeParams: FormControl<IAccreditationTypeSig['accreditationTypeParams']>;
  accreditationTypeAttributs: FormControl<IAccreditationTypeSig['accreditationTypeAttributs']>;
  accreditationTypeStat: FormControl<IAccreditationTypeSig['accreditationTypeStat']>;
  printingModel: FormControl<IAccreditationTypeSig['printingModel']>;
  event: FormControl<IAccreditationTypeSig['event']>;
};

export type AccreditationTypeSigFormGroup = FormGroup<AccreditationTypeSigFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AccreditationTypeSigFormService {
  createAccreditationTypeSigFormGroup(
    accreditationType: AccreditationTypeSigFormGroupInput = { accreditationTypeId: null }
  ): AccreditationTypeSigFormGroup {
    const accreditationTypeRawValue = {
      ...this.getFormDefaults(),
      ...accreditationType,
    };
    return new FormGroup<AccreditationTypeSigFormGroupContent>({
      accreditationTypeId: new FormControl(
        { value: accreditationTypeRawValue.accreditationTypeId, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      accreditationTypeValue: new FormControl(accreditationTypeRawValue.accreditationTypeValue, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      accreditationTypeAbreviation: new FormControl(accreditationTypeRawValue.accreditationTypeAbreviation, {
        validators: [Validators.maxLength(10)],
      }),
      accreditationTypeDescription: new FormControl(accreditationTypeRawValue.accreditationTypeDescription, {
        validators: [Validators.maxLength(200)],
      }),
      accreditationTypeParams: new FormControl(accreditationTypeRawValue.accreditationTypeParams),
      accreditationTypeAttributs: new FormControl(accreditationTypeRawValue.accreditationTypeAttributs),
      accreditationTypeStat: new FormControl(accreditationTypeRawValue.accreditationTypeStat),
      printingModel: new FormControl(accreditationTypeRawValue.printingModel),
      event: new FormControl(accreditationTypeRawValue.event),
    });
  }

  getAccreditationTypeSig(form: AccreditationTypeSigFormGroup): IAccreditationTypeSig | NewAccreditationTypeSig {
    return form.getRawValue() as IAccreditationTypeSig | NewAccreditationTypeSig;
  }

  resetForm(form: AccreditationTypeSigFormGroup, accreditationType: AccreditationTypeSigFormGroupInput): void {
    const accreditationTypeRawValue = { ...this.getFormDefaults(), ...accreditationType };
    form.reset(
      {
        ...accreditationTypeRawValue,
        accreditationTypeId: { value: accreditationTypeRawValue.accreditationTypeId, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AccreditationTypeSigFormDefaults {
    return {
      accreditationTypeId: null,
      accreditationTypeStat: false,
    };
  }
}
