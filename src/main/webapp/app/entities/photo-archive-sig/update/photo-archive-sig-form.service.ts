import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPhotoArchiveSig, NewPhotoArchiveSig } from '../photo-archive-sig.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { photoArchiveId: unknown }> = Partial<Omit<T, 'photoArchiveId'>> & {
  photoArchiveId: T['photoArchiveId'];
};

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPhotoArchiveSig for edit and NewPhotoArchiveSigFormGroupInput for create.
 */
type PhotoArchiveSigFormGroupInput = IPhotoArchiveSig | PartialWithRequiredKeyOf<NewPhotoArchiveSig>;

type PhotoArchiveSigFormDefaults = Pick<NewPhotoArchiveSig, 'photoArchiveId' | 'photoArchiveStat'>;

type PhotoArchiveSigFormGroupContent = {
  photoArchiveId: FormControl<IPhotoArchiveSig['photoArchiveId'] | NewPhotoArchiveSig['photoArchiveId']>;
  photoArchiveName: FormControl<IPhotoArchiveSig['photoArchiveName']>;
  photoArchivePath: FormControl<IPhotoArchiveSig['photoArchivePath']>;
  photoArchivePhoto: FormControl<IPhotoArchiveSig['photoArchivePhoto']>;
  photoArchivePhotoContentType: FormControl<IPhotoArchiveSig['photoArchivePhotoContentType']>;
  photoArchiveDescription: FormControl<IPhotoArchiveSig['photoArchiveDescription']>;
  photoArchiveParams: FormControl<IPhotoArchiveSig['photoArchiveParams']>;
  photoArchiveAttributs: FormControl<IPhotoArchiveSig['photoArchiveAttributs']>;
  photoArchiveStat: FormControl<IPhotoArchiveSig['photoArchiveStat']>;
  accreditation: FormControl<IPhotoArchiveSig['accreditation']>;
  event: FormControl<IPhotoArchiveSig['event']>;
};

export type PhotoArchiveSigFormGroup = FormGroup<PhotoArchiveSigFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PhotoArchiveSigFormService {
  createPhotoArchiveSigFormGroup(photoArchive: PhotoArchiveSigFormGroupInput = { photoArchiveId: null }): PhotoArchiveSigFormGroup {
    const photoArchiveRawValue = {
      ...this.getFormDefaults(),
      ...photoArchive,
    };
    return new FormGroup<PhotoArchiveSigFormGroupContent>({
      photoArchiveId: new FormControl(
        { value: photoArchiveRawValue.photoArchiveId, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      photoArchiveName: new FormControl(photoArchiveRawValue.photoArchiveName, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      photoArchivePath: new FormControl(photoArchiveRawValue.photoArchivePath),
      photoArchivePhoto: new FormControl(photoArchiveRawValue.photoArchivePhoto),
      photoArchivePhotoContentType: new FormControl(photoArchiveRawValue.photoArchivePhotoContentType),
      photoArchiveDescription: new FormControl(photoArchiveRawValue.photoArchiveDescription, {
        validators: [Validators.maxLength(200)],
      }),
      photoArchiveParams: new FormControl(photoArchiveRawValue.photoArchiveParams),
      photoArchiveAttributs: new FormControl(photoArchiveRawValue.photoArchiveAttributs),
      photoArchiveStat: new FormControl(photoArchiveRawValue.photoArchiveStat),
      accreditation: new FormControl(photoArchiveRawValue.accreditation),
      event: new FormControl(photoArchiveRawValue.event),
    });
  }

  getPhotoArchiveSig(form: PhotoArchiveSigFormGroup): IPhotoArchiveSig | NewPhotoArchiveSig {
    return form.getRawValue() as IPhotoArchiveSig | NewPhotoArchiveSig;
  }

  resetForm(form: PhotoArchiveSigFormGroup, photoArchive: PhotoArchiveSigFormGroupInput): void {
    const photoArchiveRawValue = { ...this.getFormDefaults(), ...photoArchive };
    form.reset(
      {
        ...photoArchiveRawValue,
        photoArchiveId: { value: photoArchiveRawValue.photoArchiveId, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PhotoArchiveSigFormDefaults {
    return {
      photoArchiveId: null,
      photoArchiveStat: false,
    };
  }
}
