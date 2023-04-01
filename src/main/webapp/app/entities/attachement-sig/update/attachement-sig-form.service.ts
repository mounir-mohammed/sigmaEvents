import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAttachementSig, NewAttachementSig } from '../attachement-sig.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { attachementId: unknown }> = Partial<Omit<T, 'attachementId'>> & {
  attachementId: T['attachementId'];
};

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAttachementSig for edit and NewAttachementSigFormGroupInput for create.
 */
type AttachementSigFormGroupInput = IAttachementSig | PartialWithRequiredKeyOf<NewAttachementSig>;

type AttachementSigFormDefaults = Pick<NewAttachementSig, 'attachementId' | 'attachementStat'>;

type AttachementSigFormGroupContent = {
  attachementId: FormControl<IAttachementSig['attachementId'] | NewAttachementSig['attachementId']>;
  attachementName: FormControl<IAttachementSig['attachementName']>;
  attachementPath: FormControl<IAttachementSig['attachementPath']>;
  attachementBlob: FormControl<IAttachementSig['attachementBlob']>;
  attachementBlobContentType: FormControl<IAttachementSig['attachementBlobContentType']>;
  attachementDescription: FormControl<IAttachementSig['attachementDescription']>;
  attachementPhoto: FormControl<IAttachementSig['attachementPhoto']>;
  attachementPhotoContentType: FormControl<IAttachementSig['attachementPhotoContentType']>;
  attachementParams: FormControl<IAttachementSig['attachementParams']>;
  attachementAttributs: FormControl<IAttachementSig['attachementAttributs']>;
  attachementStat: FormControl<IAttachementSig['attachementStat']>;
  attachementType: FormControl<IAttachementSig['attachementType']>;
  event: FormControl<IAttachementSig['event']>;
};

export type AttachementSigFormGroup = FormGroup<AttachementSigFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AttachementSigFormService {
  createAttachementSigFormGroup(attachement: AttachementSigFormGroupInput = { attachementId: null }): AttachementSigFormGroup {
    const attachementRawValue = {
      ...this.getFormDefaults(),
      ...attachement,
    };
    return new FormGroup<AttachementSigFormGroupContent>({
      attachementId: new FormControl(
        { value: attachementRawValue.attachementId, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      attachementName: new FormControl(attachementRawValue.attachementName, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      attachementPath: new FormControl(attachementRawValue.attachementPath),
      attachementBlob: new FormControl(attachementRawValue.attachementBlob),
      attachementBlobContentType: new FormControl(attachementRawValue.attachementBlobContentType),
      attachementDescription: new FormControl(attachementRawValue.attachementDescription, {
        validators: [Validators.maxLength(200)],
      }),
      attachementPhoto: new FormControl(attachementRawValue.attachementPhoto),
      attachementPhotoContentType: new FormControl(attachementRawValue.attachementPhotoContentType),
      attachementParams: new FormControl(attachementRawValue.attachementParams),
      attachementAttributs: new FormControl(attachementRawValue.attachementAttributs),
      attachementStat: new FormControl(attachementRawValue.attachementStat),
      attachementType: new FormControl(attachementRawValue.attachementType),
      event: new FormControl(attachementRawValue.event),
    });
  }

  getAttachementSig(form: AttachementSigFormGroup): IAttachementSig | NewAttachementSig {
    return form.getRawValue() as IAttachementSig | NewAttachementSig;
  }

  resetForm(form: AttachementSigFormGroup, attachement: AttachementSigFormGroupInput): void {
    const attachementRawValue = { ...this.getFormDefaults(), ...attachement };
    form.reset(
      {
        ...attachementRawValue,
        attachementId: { value: attachementRawValue.attachementId, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AttachementSigFormDefaults {
    return {
      attachementId: null,
      attachementStat: false,
    };
  }
}
