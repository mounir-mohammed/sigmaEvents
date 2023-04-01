import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAreaSig, NewAreaSig } from '../area-sig.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { areaId: unknown }> = Partial<Omit<T, 'areaId'>> & { areaId: T['areaId'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAreaSig for edit and NewAreaSigFormGroupInput for create.
 */
type AreaSigFormGroupInput = IAreaSig | PartialWithRequiredKeyOf<NewAreaSig>;

type AreaSigFormDefaults = Pick<NewAreaSig, 'areaId' | 'areaStat' | 'fonctions'>;

type AreaSigFormGroupContent = {
  areaId: FormControl<IAreaSig['areaId'] | NewAreaSig['areaId']>;
  areaName: FormControl<IAreaSig['areaName']>;
  areaAbreviation: FormControl<IAreaSig['areaAbreviation']>;
  areaColor: FormControl<IAreaSig['areaColor']>;
  areaDescription: FormControl<IAreaSig['areaDescription']>;
  areaLogo: FormControl<IAreaSig['areaLogo']>;
  areaLogoContentType: FormControl<IAreaSig['areaLogoContentType']>;
  areaParams: FormControl<IAreaSig['areaParams']>;
  areaAttributs: FormControl<IAreaSig['areaAttributs']>;
  areaStat: FormControl<IAreaSig['areaStat']>;
  event: FormControl<IAreaSig['event']>;
  fonctions: FormControl<IAreaSig['fonctions']>;
};

export type AreaSigFormGroup = FormGroup<AreaSigFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AreaSigFormService {
  createAreaSigFormGroup(area: AreaSigFormGroupInput = { areaId: null }): AreaSigFormGroup {
    const areaRawValue = {
      ...this.getFormDefaults(),
      ...area,
    };
    return new FormGroup<AreaSigFormGroupContent>({
      areaId: new FormControl(
        { value: areaRawValue.areaId, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      areaName: new FormControl(areaRawValue.areaName, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      areaAbreviation: new FormControl(areaRawValue.areaAbreviation, {
        validators: [Validators.maxLength(10)],
      }),
      areaColor: new FormControl(areaRawValue.areaColor, {
        validators: [Validators.maxLength(100)],
      }),
      areaDescription: new FormControl(areaRawValue.areaDescription, {
        validators: [Validators.maxLength(200)],
      }),
      areaLogo: new FormControl(areaRawValue.areaLogo),
      areaLogoContentType: new FormControl(areaRawValue.areaLogoContentType),
      areaParams: new FormControl(areaRawValue.areaParams),
      areaAttributs: new FormControl(areaRawValue.areaAttributs),
      areaStat: new FormControl(areaRawValue.areaStat),
      event: new FormControl(areaRawValue.event),
      fonctions: new FormControl(areaRawValue.fonctions ?? []),
    });
  }

  getAreaSig(form: AreaSigFormGroup): IAreaSig | NewAreaSig {
    return form.getRawValue() as IAreaSig | NewAreaSig;
  }

  resetForm(form: AreaSigFormGroup, area: AreaSigFormGroupInput): void {
    const areaRawValue = { ...this.getFormDefaults(), ...area };
    form.reset(
      {
        ...areaRawValue,
        areaId: { value: areaRawValue.areaId, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AreaSigFormDefaults {
    return {
      areaId: null,
      areaStat: false,
      fonctions: [],
    };
  }
}
