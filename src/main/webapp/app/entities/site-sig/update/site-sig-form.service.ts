import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ISiteSig, NewSiteSig } from '../site-sig.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { siteId: unknown }> = Partial<Omit<T, 'siteId'>> & { siteId: T['siteId'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISiteSig for edit and NewSiteSigFormGroupInput for create.
 */
type SiteSigFormGroupInput = ISiteSig | PartialWithRequiredKeyOf<NewSiteSig>;

type SiteSigFormDefaults = Pick<NewSiteSig, 'siteId' | 'siteStat' | 'accreditations'>;

type SiteSigFormGroupContent = {
  siteId: FormControl<ISiteSig['siteId'] | NewSiteSig['siteId']>;
  siteName: FormControl<ISiteSig['siteName']>;
  siteColor: FormControl<ISiteSig['siteColor']>;
  siteAbreviation: FormControl<ISiteSig['siteAbreviation']>;
  siteDescription: FormControl<ISiteSig['siteDescription']>;
  siteLogo: FormControl<ISiteSig['siteLogo']>;
  siteLogoContentType: FormControl<ISiteSig['siteLogoContentType']>;
  siteAdresse: FormControl<ISiteSig['siteAdresse']>;
  siteEmail: FormControl<ISiteSig['siteEmail']>;
  siteTel: FormControl<ISiteSig['siteTel']>;
  siteFax: FormControl<ISiteSig['siteFax']>;
  siteResponsableName: FormControl<ISiteSig['siteResponsableName']>;
  siteParams: FormControl<ISiteSig['siteParams']>;
  siteAttributs: FormControl<ISiteSig['siteAttributs']>;
  siteStat: FormControl<ISiteSig['siteStat']>;
  city: FormControl<ISiteSig['city']>;
  event: FormControl<ISiteSig['event']>;
  accreditations: FormControl<ISiteSig['accreditations']>;
};

export type SiteSigFormGroup = FormGroup<SiteSigFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SiteSigFormService {
  createSiteSigFormGroup(site: SiteSigFormGroupInput = { siteId: null }): SiteSigFormGroup {
    const siteRawValue = {
      ...this.getFormDefaults(),
      ...site,
    };
    return new FormGroup<SiteSigFormGroupContent>({
      siteId: new FormControl(
        { value: siteRawValue.siteId, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      siteName: new FormControl(siteRawValue.siteName, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      siteColor: new FormControl(siteRawValue.siteColor, {
        validators: [Validators.maxLength(100)],
      }),
      siteAbreviation: new FormControl(siteRawValue.siteAbreviation, {
        validators: [Validators.maxLength(10)],
      }),
      siteDescription: new FormControl(siteRawValue.siteDescription, {
        validators: [Validators.maxLength(200)],
      }),
      siteLogo: new FormControl(siteRawValue.siteLogo),
      siteLogoContentType: new FormControl(siteRawValue.siteLogoContentType),
      siteAdresse: new FormControl(siteRawValue.siteAdresse),
      siteEmail: new FormControl(siteRawValue.siteEmail),
      siteTel: new FormControl(siteRawValue.siteTel),
      siteFax: new FormControl(siteRawValue.siteFax),
      siteResponsableName: new FormControl(siteRawValue.siteResponsableName),
      siteParams: new FormControl(siteRawValue.siteParams),
      siteAttributs: new FormControl(siteRawValue.siteAttributs),
      siteStat: new FormControl(siteRawValue.siteStat),
      city: new FormControl(siteRawValue.city),
      event: new FormControl(siteRawValue.event),
      accreditations: new FormControl(siteRawValue.accreditations ?? []),
    });
  }

  getSiteSig(form: SiteSigFormGroup): ISiteSig | NewSiteSig {
    return form.getRawValue() as ISiteSig | NewSiteSig;
  }

  resetForm(form: SiteSigFormGroup, site: SiteSigFormGroupInput): void {
    const siteRawValue = { ...this.getFormDefaults(), ...site };
    form.reset(
      {
        ...siteRawValue,
        siteId: { value: siteRawValue.siteId, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): SiteSigFormDefaults {
    return {
      siteId: null,
      siteStat: false,
      accreditations: [],
    };
  }
}
