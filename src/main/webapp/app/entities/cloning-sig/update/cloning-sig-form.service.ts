import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICloningSig, NewCloningSig } from '../cloning-sig.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { cloningId: unknown }> = Partial<Omit<T, 'cloningId'>> & { cloningId: T['cloningId'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICloningSig for edit and NewCloningSigFormGroupInput for create.
 */
type CloningSigFormGroupInput = ICloningSig | PartialWithRequiredKeyOf<NewCloningSig>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ICloningSig | NewCloningSig> = Omit<T, 'cloningDate'> & {
  cloningDate?: string | null;
};

type CloningSigFormRawValue = FormValueOf<ICloningSig>;

type NewCloningSigFormRawValue = FormValueOf<NewCloningSig>;

type CloningSigFormDefaults = Pick<NewCloningSig, 'cloningId' | 'cloningDate' | 'clonedStat'>;

type CloningSigFormGroupContent = {
  cloningId: FormControl<CloningSigFormRawValue['cloningId'] | NewCloningSig['cloningId']>;
  cloningDescription: FormControl<CloningSigFormRawValue['cloningDescription']>;
  cloningOldEventId: FormControl<CloningSigFormRawValue['cloningOldEventId']>;
  cloningNewEventId: FormControl<CloningSigFormRawValue['cloningNewEventId']>;
  cloningUserId: FormControl<CloningSigFormRawValue['cloningUserId']>;
  cloningDate: FormControl<CloningSigFormRawValue['cloningDate']>;
  clonedEntitys: FormControl<CloningSigFormRawValue['clonedEntitys']>;
  clonedParams: FormControl<CloningSigFormRawValue['clonedParams']>;
  clonedAttributs: FormControl<CloningSigFormRawValue['clonedAttributs']>;
  clonedStat: FormControl<CloningSigFormRawValue['clonedStat']>;
};

export type CloningSigFormGroup = FormGroup<CloningSigFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CloningSigFormService {
  createCloningSigFormGroup(cloning: CloningSigFormGroupInput = { cloningId: null }): CloningSigFormGroup {
    const cloningRawValue = this.convertCloningSigToCloningSigRawValue({
      ...this.getFormDefaults(),
      ...cloning,
    });
    return new FormGroup<CloningSigFormGroupContent>({
      cloningId: new FormControl(
        { value: cloningRawValue.cloningId, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      cloningDescription: new FormControl(cloningRawValue.cloningDescription, {
        validators: [Validators.maxLength(200)],
      }),
      cloningOldEventId: new FormControl(cloningRawValue.cloningOldEventId),
      cloningNewEventId: new FormControl(cloningRawValue.cloningNewEventId),
      cloningUserId: new FormControl(cloningRawValue.cloningUserId),
      cloningDate: new FormControl(cloningRawValue.cloningDate),
      clonedEntitys: new FormControl(cloningRawValue.clonedEntitys),
      clonedParams: new FormControl(cloningRawValue.clonedParams),
      clonedAttributs: new FormControl(cloningRawValue.clonedAttributs),
      clonedStat: new FormControl(cloningRawValue.clonedStat),
    });
  }

  getCloningSig(form: CloningSigFormGroup): ICloningSig | NewCloningSig {
    return this.convertCloningSigRawValueToCloningSig(form.getRawValue() as CloningSigFormRawValue | NewCloningSigFormRawValue);
  }

  resetForm(form: CloningSigFormGroup, cloning: CloningSigFormGroupInput): void {
    const cloningRawValue = this.convertCloningSigToCloningSigRawValue({ ...this.getFormDefaults(), ...cloning });
    form.reset(
      {
        ...cloningRawValue,
        cloningId: { value: cloningRawValue.cloningId, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CloningSigFormDefaults {
    const currentTime = dayjs();

    return {
      cloningId: null,
      cloningDate: currentTime,
      clonedStat: false,
    };
  }

  private convertCloningSigRawValueToCloningSig(
    rawCloningSig: CloningSigFormRawValue | NewCloningSigFormRawValue
  ): ICloningSig | NewCloningSig {
    return {
      ...rawCloningSig,
      cloningDate: dayjs(rawCloningSig.cloningDate, DATE_TIME_FORMAT),
    };
  }

  private convertCloningSigToCloningSigRawValue(
    cloning: ICloningSig | (Partial<NewCloningSig> & CloningSigFormDefaults)
  ): CloningSigFormRawValue | PartialWithRequiredKeyOf<NewCloningSigFormRawValue> {
    return {
      ...cloning,
      cloningDate: cloning.cloningDate ? cloning.cloningDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
