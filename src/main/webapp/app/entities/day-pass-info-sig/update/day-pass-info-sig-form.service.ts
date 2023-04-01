import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IDayPassInfoSig, NewDayPassInfoSig } from '../day-pass-info-sig.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { dayPassInfoId: unknown }> = Partial<Omit<T, 'dayPassInfoId'>> & {
  dayPassInfoId: T['dayPassInfoId'];
};

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDayPassInfoSig for edit and NewDayPassInfoSigFormGroupInput for create.
 */
type DayPassInfoSigFormGroupInput = IDayPassInfoSig | PartialWithRequiredKeyOf<NewDayPassInfoSig>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IDayPassInfoSig | NewDayPassInfoSig> = Omit<
  T,
  'dayPassInfoCreationDate' | 'dayPassInfoUpdateDate' | 'dayPassInfoDateStart' | 'dayPassInfoDateEnd'
> & {
  dayPassInfoCreationDate?: string | null;
  dayPassInfoUpdateDate?: string | null;
  dayPassInfoDateStart?: string | null;
  dayPassInfoDateEnd?: string | null;
};

type DayPassInfoSigFormRawValue = FormValueOf<IDayPassInfoSig>;

type NewDayPassInfoSigFormRawValue = FormValueOf<NewDayPassInfoSig>;

type DayPassInfoSigFormDefaults = Pick<
  NewDayPassInfoSig,
  'dayPassInfoId' | 'dayPassInfoCreationDate' | 'dayPassInfoUpdateDate' | 'dayPassInfoDateStart' | 'dayPassInfoDateEnd' | 'dayPassInfoStat'
>;

type DayPassInfoSigFormGroupContent = {
  dayPassInfoId: FormControl<DayPassInfoSigFormRawValue['dayPassInfoId'] | NewDayPassInfoSig['dayPassInfoId']>;
  dayPassInfoName: FormControl<DayPassInfoSigFormRawValue['dayPassInfoName']>;
  dayPassDescription: FormControl<DayPassInfoSigFormRawValue['dayPassDescription']>;
  dayPassLogo: FormControl<DayPassInfoSigFormRawValue['dayPassLogo']>;
  dayPassLogoContentType: FormControl<DayPassInfoSigFormRawValue['dayPassLogoContentType']>;
  dayPassInfoCreationDate: FormControl<DayPassInfoSigFormRawValue['dayPassInfoCreationDate']>;
  dayPassInfoUpdateDate: FormControl<DayPassInfoSigFormRawValue['dayPassInfoUpdateDate']>;
  dayPassInfoCreatedByuser: FormControl<DayPassInfoSigFormRawValue['dayPassInfoCreatedByuser']>;
  dayPassInfoDateStart: FormControl<DayPassInfoSigFormRawValue['dayPassInfoDateStart']>;
  dayPassInfoDateEnd: FormControl<DayPassInfoSigFormRawValue['dayPassInfoDateEnd']>;
  dayPassInfoNumber: FormControl<DayPassInfoSigFormRawValue['dayPassInfoNumber']>;
  dayPassParams: FormControl<DayPassInfoSigFormRawValue['dayPassParams']>;
  dayPassAttributs: FormControl<DayPassInfoSigFormRawValue['dayPassAttributs']>;
  dayPassInfoStat: FormControl<DayPassInfoSigFormRawValue['dayPassInfoStat']>;
  event: FormControl<DayPassInfoSigFormRawValue['event']>;
};

export type DayPassInfoSigFormGroup = FormGroup<DayPassInfoSigFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DayPassInfoSigFormService {
  createDayPassInfoSigFormGroup(dayPassInfo: DayPassInfoSigFormGroupInput = { dayPassInfoId: null }): DayPassInfoSigFormGroup {
    const dayPassInfoRawValue = this.convertDayPassInfoSigToDayPassInfoSigRawValue({
      ...this.getFormDefaults(),
      ...dayPassInfo,
    });
    return new FormGroup<DayPassInfoSigFormGroupContent>({
      dayPassInfoId: new FormControl(
        { value: dayPassInfoRawValue.dayPassInfoId, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      dayPassInfoName: new FormControl(dayPassInfoRawValue.dayPassInfoName),
      dayPassDescription: new FormControl(dayPassInfoRawValue.dayPassDescription, {
        validators: [Validators.maxLength(200)],
      }),
      dayPassLogo: new FormControl(dayPassInfoRawValue.dayPassLogo),
      dayPassLogoContentType: new FormControl(dayPassInfoRawValue.dayPassLogoContentType),
      dayPassInfoCreationDate: new FormControl(dayPassInfoRawValue.dayPassInfoCreationDate),
      dayPassInfoUpdateDate: new FormControl(dayPassInfoRawValue.dayPassInfoUpdateDate),
      dayPassInfoCreatedByuser: new FormControl(dayPassInfoRawValue.dayPassInfoCreatedByuser),
      dayPassInfoDateStart: new FormControl(dayPassInfoRawValue.dayPassInfoDateStart),
      dayPassInfoDateEnd: new FormControl(dayPassInfoRawValue.dayPassInfoDateEnd),
      dayPassInfoNumber: new FormControl(dayPassInfoRawValue.dayPassInfoNumber),
      dayPassParams: new FormControl(dayPassInfoRawValue.dayPassParams),
      dayPassAttributs: new FormControl(dayPassInfoRawValue.dayPassAttributs),
      dayPassInfoStat: new FormControl(dayPassInfoRawValue.dayPassInfoStat),
      event: new FormControl(dayPassInfoRawValue.event),
    });
  }

  getDayPassInfoSig(form: DayPassInfoSigFormGroup): IDayPassInfoSig | NewDayPassInfoSig {
    return this.convertDayPassInfoSigRawValueToDayPassInfoSig(
      form.getRawValue() as DayPassInfoSigFormRawValue | NewDayPassInfoSigFormRawValue
    );
  }

  resetForm(form: DayPassInfoSigFormGroup, dayPassInfo: DayPassInfoSigFormGroupInput): void {
    const dayPassInfoRawValue = this.convertDayPassInfoSigToDayPassInfoSigRawValue({ ...this.getFormDefaults(), ...dayPassInfo });
    form.reset(
      {
        ...dayPassInfoRawValue,
        dayPassInfoId: { value: dayPassInfoRawValue.dayPassInfoId, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DayPassInfoSigFormDefaults {
    const currentTime = dayjs();

    return {
      dayPassInfoId: null,
      dayPassInfoCreationDate: currentTime,
      dayPassInfoUpdateDate: currentTime,
      dayPassInfoDateStart: currentTime,
      dayPassInfoDateEnd: currentTime,
      dayPassInfoStat: false,
    };
  }

  private convertDayPassInfoSigRawValueToDayPassInfoSig(
    rawDayPassInfoSig: DayPassInfoSigFormRawValue | NewDayPassInfoSigFormRawValue
  ): IDayPassInfoSig | NewDayPassInfoSig {
    return {
      ...rawDayPassInfoSig,
      dayPassInfoCreationDate: dayjs(rawDayPassInfoSig.dayPassInfoCreationDate, DATE_TIME_FORMAT),
      dayPassInfoUpdateDate: dayjs(rawDayPassInfoSig.dayPassInfoUpdateDate, DATE_TIME_FORMAT),
      dayPassInfoDateStart: dayjs(rawDayPassInfoSig.dayPassInfoDateStart, DATE_TIME_FORMAT),
      dayPassInfoDateEnd: dayjs(rawDayPassInfoSig.dayPassInfoDateEnd, DATE_TIME_FORMAT),
    };
  }

  private convertDayPassInfoSigToDayPassInfoSigRawValue(
    dayPassInfo: IDayPassInfoSig | (Partial<NewDayPassInfoSig> & DayPassInfoSigFormDefaults)
  ): DayPassInfoSigFormRawValue | PartialWithRequiredKeyOf<NewDayPassInfoSigFormRawValue> {
    return {
      ...dayPassInfo,
      dayPassInfoCreationDate: dayPassInfo.dayPassInfoCreationDate
        ? dayPassInfo.dayPassInfoCreationDate.format(DATE_TIME_FORMAT)
        : undefined,
      dayPassInfoUpdateDate: dayPassInfo.dayPassInfoUpdateDate ? dayPassInfo.dayPassInfoUpdateDate.format(DATE_TIME_FORMAT) : undefined,
      dayPassInfoDateStart: dayPassInfo.dayPassInfoDateStart ? dayPassInfo.dayPassInfoDateStart.format(DATE_TIME_FORMAT) : undefined,
      dayPassInfoDateEnd: dayPassInfo.dayPassInfoDateEnd ? dayPassInfo.dayPassInfoDateEnd.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
