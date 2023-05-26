import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAccreditationSig, NewAccreditationSig } from '../accreditation-sig.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { accreditationId: unknown }> = Partial<Omit<T, 'accreditationId'>> & {
  accreditationId: T['accreditationId'];
};

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAccreditationSig for edit and NewAccreditationSigFormGroupInput for create.
 */
type AccreditationSigFormGroupInput = IAccreditationSig | PartialWithRequiredKeyOf<NewAccreditationSig>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IAccreditationSig | NewAccreditationSig> = Omit<
  T,
  'accreditationCreationDate' | 'accreditationUpdateDate' | 'accreditationDateStart' | 'accreditationDateEnd' | 'accreditationPrintDate'
> & {
  accreditationCreationDate?: string | null;
  accreditationUpdateDate?: string | null;
  accreditationPrintDate?: string | null;
  accreditationDateStart?: string | null;
  accreditationDateEnd?: string | null;
};

type AccreditationSigFormRawValue = FormValueOf<IAccreditationSig>;

type NewAccreditationSigFormRawValue = FormValueOf<NewAccreditationSig>;

type AccreditationSigFormDefaults = Pick<
  NewAccreditationSig,
  | 'accreditationId'
  | 'accreditationCreationDate'
  | 'accreditationUpdateDate'
  | 'accreditationPrintDate'
  | 'accreditationDateStart'
  | 'accreditationDateEnd'
  | 'accreditationPrintStat'
  | 'accreditationStat'
  | 'accreditationActivated'
  | 'sites'
>;

type AccreditationSigFormGroupContent = {
  accreditationId: FormControl<AccreditationSigFormRawValue['accreditationId'] | NewAccreditationSig['accreditationId']>;
  accreditationFirstName: FormControl<AccreditationSigFormRawValue['accreditationFirstName']>;
  accreditationSecondName: FormControl<AccreditationSigFormRawValue['accreditationSecondName']>;
  accreditationLastName: FormControl<AccreditationSigFormRawValue['accreditationLastName']>;
  accreditationBirthDay: FormControl<AccreditationSigFormRawValue['accreditationBirthDay']>;
  accreditationSexe: FormControl<AccreditationSigFormRawValue['accreditationSexe']>;
  accreditationOccupation: FormControl<AccreditationSigFormRawValue['accreditationOccupation']>;
  accreditationDescription: FormControl<AccreditationSigFormRawValue['accreditationDescription']>;
  accreditationEmail: FormControl<AccreditationSigFormRawValue['accreditationEmail']>;
  accreditationTel: FormControl<AccreditationSigFormRawValue['accreditationTel']>;
  accreditationPhoto: FormControl<AccreditationSigFormRawValue['accreditationPhoto']>;
  accreditationPhotoContentType: FormControl<AccreditationSigFormRawValue['accreditationPhotoContentType']>;
  accreditationCinId: FormControl<AccreditationSigFormRawValue['accreditationCinId']>;
  accreditationPasseportId: FormControl<AccreditationSigFormRawValue['accreditationPasseportId']>;
  accreditationCartePresseId: FormControl<AccreditationSigFormRawValue['accreditationCartePresseId']>;
  accreditationCarteProfessionnelleId: FormControl<AccreditationSigFormRawValue['accreditationCarteProfessionnelleId']>;
  accreditationCreationDate: FormControl<AccreditationSigFormRawValue['accreditationCreationDate']>;
  accreditationUpdateDate: FormControl<AccreditationSigFormRawValue['accreditationUpdateDate']>;
  accreditationPrintDate: FormControl<AccreditationSigFormRawValue['accreditationPrintDate']>;
  accreditationCreatedByuser: FormControl<AccreditationSigFormRawValue['accreditationCreatedByuser']>;
  accreditationUpdatedByuser: FormControl<AccreditationSigFormRawValue['accreditationUpdatedByuser']>;
  accreditationPrintedByuser: FormControl<AccreditationSigFormRawValue['accreditationPrintedByuser']>;
  accreditationDateStart: FormControl<AccreditationSigFormRawValue['accreditationDateStart']>;
  accreditationDateEnd: FormControl<AccreditationSigFormRawValue['accreditationDateEnd']>;
  accreditationPrintStat: FormControl<AccreditationSigFormRawValue['accreditationPrintStat']>;
  accreditationPrintNumber: FormControl<AccreditationSigFormRawValue['accreditationPrintNumber']>;
  accreditationParams: FormControl<AccreditationSigFormRawValue['accreditationParams']>;
  accreditationAttributs: FormControl<AccreditationSigFormRawValue['accreditationAttributs']>;
  accreditationStat: FormControl<AccreditationSigFormRawValue['accreditationStat']>;
  accreditationActivated: FormControl<AccreditationSigFormRawValue['accreditationActivated']>;
  sites: FormControl<AccreditationSigFormRawValue['sites']>;
  event: FormControl<AccreditationSigFormRawValue['event']>;
  civility: FormControl<AccreditationSigFormRawValue['civility']>;
  sexe: FormControl<AccreditationSigFormRawValue['sexe']>;
  nationality: FormControl<AccreditationSigFormRawValue['nationality']>;
  country: FormControl<AccreditationSigFormRawValue['country']>;
  city: FormControl<AccreditationSigFormRawValue['city']>;
  category: FormControl<AccreditationSigFormRawValue['category']>;
  fonction: FormControl<AccreditationSigFormRawValue['fonction']>;
  organiz: FormControl<AccreditationSigFormRawValue['organiz']>;
  accreditationType: FormControl<AccreditationSigFormRawValue['accreditationType']>;
  status: FormControl<AccreditationSigFormRawValue['status']>;
  attachement: FormControl<AccreditationSigFormRawValue['attachement']>;
  code: FormControl<AccreditationSigFormRawValue['code']>;
  dayPassInfo: FormControl<AccreditationSigFormRawValue['dayPassInfo']>;
};

export type AccreditationSigFormGroup = FormGroup<AccreditationSigFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AccreditationSigFormService {
  createAccreditationSigFormGroup(accreditation: AccreditationSigFormGroupInput = { accreditationId: null }): AccreditationSigFormGroup {
    const accreditationRawValue = this.convertAccreditationSigToAccreditationSigRawValue({
      ...this.getFormDefaults(),
      ...accreditation,
    });
    return new FormGroup<AccreditationSigFormGroupContent>({
      accreditationId: new FormControl(
        { value: accreditationRawValue.accreditationId, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      accreditationFirstName: new FormControl(accreditationRawValue.accreditationFirstName, {
        validators: [Validators.required, Validators.minLength(2), Validators.maxLength(50)],
      }),
      accreditationSecondName: new FormControl(accreditationRawValue.accreditationSecondName, {
        validators: [Validators.maxLength(50)],
      }),
      accreditationLastName: new FormControl(accreditationRawValue.accreditationLastName, {
        validators: [Validators.required, Validators.minLength(2), Validators.maxLength(50)],
      }),
      accreditationBirthDay: new FormControl(accreditationRawValue.accreditationBirthDay, {
        validators: [Validators.required],
      }),
      accreditationSexe: new FormControl(accreditationRawValue.accreditationSexe),
      accreditationOccupation: new FormControl(accreditationRawValue.accreditationOccupation),
      accreditationDescription: new FormControl(accreditationRawValue.accreditationDescription),
      accreditationEmail: new FormControl(accreditationRawValue.accreditationEmail),
      accreditationTel: new FormControl(accreditationRawValue.accreditationTel),
      accreditationPhoto: new FormControl(accreditationRawValue.accreditationPhoto),
      accreditationPhotoContentType: new FormControl(accreditationRawValue.accreditationPhotoContentType),
      accreditationCinId: new FormControl(accreditationRawValue.accreditationCinId),
      accreditationPasseportId: new FormControl(accreditationRawValue.accreditationPasseportId),
      accreditationCartePresseId: new FormControl(accreditationRawValue.accreditationCartePresseId),
      accreditationCarteProfessionnelleId: new FormControl(accreditationRawValue.accreditationCarteProfessionnelleId),
      accreditationCreationDate: new FormControl(accreditationRawValue.accreditationCreationDate),
      accreditationUpdateDate: new FormControl(accreditationRawValue.accreditationUpdateDate),
      accreditationPrintDate: new FormControl(accreditationRawValue.accreditationPrintDate),
      accreditationCreatedByuser: new FormControl(accreditationRawValue.accreditationCreatedByuser),
      accreditationUpdatedByuser: new FormControl(accreditationRawValue.accreditationUpdatedByuser),
      accreditationPrintedByuser: new FormControl(accreditationRawValue.accreditationPrintedByuser),
      accreditationDateStart: new FormControl(accreditationRawValue.accreditationDateStart),
      accreditationDateEnd: new FormControl(accreditationRawValue.accreditationDateEnd),
      accreditationPrintStat: new FormControl(accreditationRawValue.accreditationPrintStat),
      accreditationPrintNumber: new FormControl(accreditationRawValue.accreditationPrintNumber),
      accreditationParams: new FormControl(accreditationRawValue.accreditationParams),
      accreditationAttributs: new FormControl(accreditationRawValue.accreditationAttributs),
      accreditationStat: new FormControl(accreditationRawValue.accreditationStat),
      accreditationActivated: new FormControl(accreditationRawValue.accreditationActivated),
      sites: new FormControl(accreditationRawValue.sites ?? [], {
        validators: [Validators.required],
      }),
      event: new FormControl(accreditationRawValue.event),
      civility: new FormControl(accreditationRawValue.civility),
      sexe: new FormControl(accreditationRawValue.sexe),
      nationality: new FormControl(accreditationRawValue.nationality, {
        validators: [Validators.required],
      }),
      country: new FormControl(accreditationRawValue.country),
      city: new FormControl(accreditationRawValue.city),
      category: new FormControl(accreditationRawValue.category, {
        validators: [Validators.required],
      }),
      fonction: new FormControl(accreditationRawValue.fonction, {
        validators: [Validators.required],
      }),
      organiz: new FormControl(accreditationRawValue.organiz, {
        validators: [Validators.required],
      }),
      accreditationType: new FormControl(accreditationRawValue.accreditationType, {
        validators: [Validators.required],
      }),
      status: new FormControl(accreditationRawValue.status),
      attachement: new FormControl(accreditationRawValue.attachement),
      code: new FormControl(accreditationRawValue.code),
      dayPassInfo: new FormControl(accreditationRawValue.dayPassInfo),
    });
  }

  getAccreditationSig(form: AccreditationSigFormGroup): IAccreditationSig | NewAccreditationSig {
    return this.convertAccreditationSigRawValueToAccreditationSig(
      form.getRawValue() as AccreditationSigFormRawValue | NewAccreditationSigFormRawValue
    );
  }

  resetForm(form: AccreditationSigFormGroup, accreditation: AccreditationSigFormGroupInput): void {
    const accreditationRawValue = this.convertAccreditationSigToAccreditationSigRawValue({ ...this.getFormDefaults(), ...accreditation });
    form.reset(
      {
        ...accreditationRawValue,
        accreditationId: { value: accreditationRawValue.accreditationId, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AccreditationSigFormDefaults {
    const currentTime = dayjs();

    return {
      accreditationId: null,
      accreditationCreationDate: currentTime,
      accreditationUpdateDate: currentTime,
      accreditationDateStart: currentTime,
      accreditationDateEnd: currentTime,
      accreditationPrintDate: currentTime,
      accreditationPrintStat: false,
      accreditationStat: false,
      accreditationActivated: false,
      sites: [],
    };
  }

  private convertAccreditationSigRawValueToAccreditationSig(
    rawAccreditationSig: AccreditationSigFormRawValue | NewAccreditationSigFormRawValue
  ): IAccreditationSig | NewAccreditationSig {
    return {
      ...rawAccreditationSig,
      accreditationCreationDate: dayjs(rawAccreditationSig.accreditationCreationDate, DATE_TIME_FORMAT),
      accreditationUpdateDate: dayjs(rawAccreditationSig.accreditationUpdateDate, DATE_TIME_FORMAT),
      accreditationPrintDate: dayjs(rawAccreditationSig.accreditationPrintDate, DATE_TIME_FORMAT),
      accreditationDateStart: dayjs(rawAccreditationSig.accreditationDateStart, DATE_TIME_FORMAT),
      accreditationDateEnd: dayjs(rawAccreditationSig.accreditationDateEnd, DATE_TIME_FORMAT),
    };
  }

  private convertAccreditationSigToAccreditationSigRawValue(
    accreditation: IAccreditationSig | (Partial<NewAccreditationSig> & AccreditationSigFormDefaults)
  ): AccreditationSigFormRawValue | PartialWithRequiredKeyOf<NewAccreditationSigFormRawValue> {
    return {
      ...accreditation,
      accreditationCreationDate: accreditation.accreditationCreationDate
        ? accreditation.accreditationCreationDate.format(DATE_TIME_FORMAT)
        : undefined,
      accreditationUpdateDate: accreditation.accreditationUpdateDate
        ? accreditation.accreditationUpdateDate.format(DATE_TIME_FORMAT)
        : undefined,
      accreditationDateStart: accreditation.accreditationDateStart
        ? accreditation.accreditationDateStart.format(DATE_TIME_FORMAT)
        : undefined,
      accreditationPrintDate: accreditation.accreditationPrintDate
        ? accreditation.accreditationPrintDate.format(DATE_TIME_FORMAT)
        : undefined,
      accreditationDateEnd: accreditation.accreditationDateEnd ? accreditation.accreditationDateEnd.format(DATE_TIME_FORMAT) : undefined,
      sites: accreditation.sites ?? [],
    };
  }
}
