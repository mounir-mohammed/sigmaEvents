import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IOrganizSig, NewOrganizSig } from '../organiz-sig.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { organizId: unknown }> = Partial<Omit<T, 'organizId'>> & { organizId: T['organizId'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IOrganizSig for edit and NewOrganizSigFormGroupInput for create.
 */
type OrganizSigFormGroupInput = IOrganizSig | PartialWithRequiredKeyOf<NewOrganizSig>;

type OrganizSigFormDefaults = Pick<NewOrganizSig, 'organizId' | 'organizStat'>;

type OrganizSigFormGroupContent = {
  organizId: FormControl<IOrganizSig['organizId'] | NewOrganizSig['organizId']>;
  organizName: FormControl<IOrganizSig['organizName']>;
  organizDescription: FormControl<IOrganizSig['organizDescription']>;
  organizLogo: FormControl<IOrganizSig['organizLogo']>;
  organizLogoContentType: FormControl<IOrganizSig['organizLogoContentType']>;
  organizTel: FormControl<IOrganizSig['organizTel']>;
  organizFax: FormControl<IOrganizSig['organizFax']>;
  organizEmail: FormControl<IOrganizSig['organizEmail']>;
  organizAdresse: FormControl<IOrganizSig['organizAdresse']>;
  organizParams: FormControl<IOrganizSig['organizParams']>;
  organizAttributs: FormControl<IOrganizSig['organizAttributs']>;
  organizStat: FormControl<IOrganizSig['organizStat']>;
  country: FormControl<IOrganizSig['country']>;
  city: FormControl<IOrganizSig['city']>;
  event: FormControl<IOrganizSig['event']>;
};

export type OrganizSigFormGroup = FormGroup<OrganizSigFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class OrganizSigFormService {
  createOrganizSigFormGroup(organiz: OrganizSigFormGroupInput = { organizId: null }): OrganizSigFormGroup {
    const organizRawValue = {
      ...this.getFormDefaults(),
      ...organiz,
    };
    return new FormGroup<OrganizSigFormGroupContent>({
      organizId: new FormControl(
        { value: organizRawValue.organizId, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      organizName: new FormControl(organizRawValue.organizName, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      organizDescription: new FormControl(organizRawValue.organizDescription, {
        validators: [Validators.maxLength(200)],
      }),
      organizLogo: new FormControl(organizRawValue.organizLogo),
      organizLogoContentType: new FormControl(organizRawValue.organizLogoContentType),
      organizTel: new FormControl(organizRawValue.organizTel),
      organizFax: new FormControl(organizRawValue.organizFax),
      organizEmail: new FormControl(organizRawValue.organizEmail),
      organizAdresse: new FormControl(organizRawValue.organizAdresse),
      organizParams: new FormControl(organizRawValue.organizParams),
      organizAttributs: new FormControl(organizRawValue.organizAttributs),
      organizStat: new FormControl(organizRawValue.organizStat),
      country: new FormControl(organizRawValue.country),
      city: new FormControl(organizRawValue.city),
      event: new FormControl(organizRawValue.event),
    });
  }

  getOrganizSig(form: OrganizSigFormGroup): IOrganizSig | NewOrganizSig {
    return form.getRawValue() as IOrganizSig | NewOrganizSig;
  }

  resetForm(form: OrganizSigFormGroup, organiz: OrganizSigFormGroupInput): void {
    const organizRawValue = { ...this.getFormDefaults(), ...organiz };
    form.reset(
      {
        ...organizRawValue,
        organizId: { value: organizRawValue.organizId, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): OrganizSigFormDefaults {
    return {
      organizId: null,
      organizStat: false,
    };
  }
}
