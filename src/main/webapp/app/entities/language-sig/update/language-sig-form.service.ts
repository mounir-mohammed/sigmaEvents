import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ILanguageSig, NewLanguageSig } from '../language-sig.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { languageId: unknown }> = Partial<Omit<T, 'languageId'>> & { languageId: T['languageId'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ILanguageSig for edit and NewLanguageSigFormGroupInput for create.
 */
type LanguageSigFormGroupInput = ILanguageSig | PartialWithRequiredKeyOf<NewLanguageSig>;

type LanguageSigFormDefaults = Pick<NewLanguageSig, 'languageId' | 'languageStat'>;

type LanguageSigFormGroupContent = {
  languageId: FormControl<ILanguageSig['languageId'] | NewLanguageSig['languageId']>;
  languageCode: FormControl<ILanguageSig['languageCode']>;
  languageName: FormControl<ILanguageSig['languageName']>;
  languageDescription: FormControl<ILanguageSig['languageDescription']>;
  languageParams: FormControl<ILanguageSig['languageParams']>;
  languageAttributs: FormControl<ILanguageSig['languageAttributs']>;
  languageStat: FormControl<ILanguageSig['languageStat']>;
};

export type LanguageSigFormGroup = FormGroup<LanguageSigFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class LanguageSigFormService {
  createLanguageSigFormGroup(language: LanguageSigFormGroupInput = { languageId: null }): LanguageSigFormGroup {
    const languageRawValue = {
      ...this.getFormDefaults(),
      ...language,
    };
    return new FormGroup<LanguageSigFormGroupContent>({
      languageId: new FormControl(
        { value: languageRawValue.languageId, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      languageCode: new FormControl(languageRawValue.languageCode, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      languageName: new FormControl(languageRawValue.languageName, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      languageDescription: new FormControl(languageRawValue.languageDescription, {
        validators: [Validators.maxLength(200)],
      }),
      languageParams: new FormControl(languageRawValue.languageParams),
      languageAttributs: new FormControl(languageRawValue.languageAttributs),
      languageStat: new FormControl(languageRawValue.languageStat),
    });
  }

  getLanguageSig(form: LanguageSigFormGroup): ILanguageSig | NewLanguageSig {
    return form.getRawValue() as ILanguageSig | NewLanguageSig;
  }

  resetForm(form: LanguageSigFormGroup, language: LanguageSigFormGroupInput): void {
    const languageRawValue = { ...this.getFormDefaults(), ...language };
    form.reset(
      {
        ...languageRawValue,
        languageId: { value: languageRawValue.languageId, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): LanguageSigFormDefaults {
    return {
      languageId: null,
      languageStat: false,
    };
  }
}
