import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IFonctionSig, NewFonctionSig } from '../fonction-sig.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { fonctionId: unknown }> = Partial<Omit<T, 'fonctionId'>> & { fonctionId: T['fonctionId'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFonctionSig for edit and NewFonctionSigFormGroupInput for create.
 */
type FonctionSigFormGroupInput = IFonctionSig | PartialWithRequiredKeyOf<NewFonctionSig>;

type FonctionSigFormDefaults = Pick<NewFonctionSig, 'fonctionId' | 'fonctionStat' | 'areas'>;

type FonctionSigFormGroupContent = {
  fonctionId: FormControl<IFonctionSig['fonctionId'] | NewFonctionSig['fonctionId']>;
  fonctionName: FormControl<IFonctionSig['fonctionName']>;
  fonctionAbreviation: FormControl<IFonctionSig['fonctionAbreviation']>;
  fonctionColor: FormControl<IFonctionSig['fonctionColor']>;
  fonctionDescription: FormControl<IFonctionSig['fonctionDescription']>;
  fonctionLogo: FormControl<IFonctionSig['fonctionLogo']>;
  fonctionLogoContentType: FormControl<IFonctionSig['fonctionLogoContentType']>;
  fonctionParams: FormControl<IFonctionSig['fonctionParams']>;
  fonctionAttributs: FormControl<IFonctionSig['fonctionAttributs']>;
  fonctionStat: FormControl<IFonctionSig['fonctionStat']>;
  areas: FormControl<IFonctionSig['areas']>;
  category: FormControl<IFonctionSig['category']>;
  event: FormControl<IFonctionSig['event']>;
};

export type FonctionSigFormGroup = FormGroup<FonctionSigFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FonctionSigFormService {
  createFonctionSigFormGroup(fonction: FonctionSigFormGroupInput = { fonctionId: null }): FonctionSigFormGroup {
    const fonctionRawValue = {
      ...this.getFormDefaults(),
      ...fonction,
    };
    return new FormGroup<FonctionSigFormGroupContent>({
      fonctionId: new FormControl(
        { value: fonctionRawValue.fonctionId, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      fonctionName: new FormControl(fonctionRawValue.fonctionName, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      fonctionAbreviation: new FormControl(fonctionRawValue.fonctionAbreviation, {
        validators: [Validators.maxLength(10)],
      }),
      fonctionColor: new FormControl(fonctionRawValue.fonctionColor, {
        validators: [Validators.maxLength(100)],
      }),
      fonctionDescription: new FormControl(fonctionRawValue.fonctionDescription, {
        validators: [Validators.maxLength(200)],
      }),
      fonctionLogo: new FormControl(fonctionRawValue.fonctionLogo),
      fonctionLogoContentType: new FormControl(fonctionRawValue.fonctionLogoContentType),
      fonctionParams: new FormControl(fonctionRawValue.fonctionParams),
      fonctionAttributs: new FormControl(fonctionRawValue.fonctionAttributs),
      fonctionStat: new FormControl(fonctionRawValue.fonctionStat),
      areas: new FormControl(fonctionRawValue.areas ?? []),
      category: new FormControl(fonctionRawValue.category),
      event: new FormControl(fonctionRawValue.event),
    });
  }

  getFonctionSig(form: FonctionSigFormGroup): IFonctionSig | NewFonctionSig {
    return form.getRawValue() as IFonctionSig | NewFonctionSig;
  }

  resetForm(form: FonctionSigFormGroup, fonction: FonctionSigFormGroupInput): void {
    const fonctionRawValue = { ...this.getFormDefaults(), ...fonction };
    form.reset(
      {
        ...fonctionRawValue,
        fonctionId: { value: fonctionRawValue.fonctionId, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): FonctionSigFormDefaults {
    return {
      fonctionId: null,
      fonctionStat: false,
      areas: [],
    };
  }
}
