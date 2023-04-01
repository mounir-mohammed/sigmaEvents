import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { INoteSig, NewNoteSig } from '../note-sig.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { noteId: unknown }> = Partial<Omit<T, 'noteId'>> & { noteId: T['noteId'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts INoteSig for edit and NewNoteSigFormGroupInput for create.
 */
type NoteSigFormGroupInput = INoteSig | PartialWithRequiredKeyOf<NewNoteSig>;

type NoteSigFormDefaults = Pick<NewNoteSig, 'noteId' | 'noteStat'>;

type NoteSigFormGroupContent = {
  noteId: FormControl<INoteSig['noteId'] | NewNoteSig['noteId']>;
  noteValue: FormControl<INoteSig['noteValue']>;
  noteDescription: FormControl<INoteSig['noteDescription']>;
  noteTypeParams: FormControl<INoteSig['noteTypeParams']>;
  noteTypeAttributs: FormControl<INoteSig['noteTypeAttributs']>;
  noteStat: FormControl<INoteSig['noteStat']>;
  accreditation: FormControl<INoteSig['accreditation']>;
  event: FormControl<INoteSig['event']>;
};

export type NoteSigFormGroup = FormGroup<NoteSigFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class NoteSigFormService {
  createNoteSigFormGroup(note: NoteSigFormGroupInput = { noteId: null }): NoteSigFormGroup {
    const noteRawValue = {
      ...this.getFormDefaults(),
      ...note,
    };
    return new FormGroup<NoteSigFormGroupContent>({
      noteId: new FormControl(
        { value: noteRawValue.noteId, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      noteValue: new FormControl(noteRawValue.noteValue, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      noteDescription: new FormControl(noteRawValue.noteDescription, {
        validators: [Validators.maxLength(200)],
      }),
      noteTypeParams: new FormControl(noteRawValue.noteTypeParams),
      noteTypeAttributs: new FormControl(noteRawValue.noteTypeAttributs),
      noteStat: new FormControl(noteRawValue.noteStat),
      accreditation: new FormControl(noteRawValue.accreditation),
      event: new FormControl(noteRawValue.event),
    });
  }

  getNoteSig(form: NoteSigFormGroup): INoteSig | NewNoteSig {
    return form.getRawValue() as INoteSig | NewNoteSig;
  }

  resetForm(form: NoteSigFormGroup, note: NoteSigFormGroupInput): void {
    const noteRawValue = { ...this.getFormDefaults(), ...note };
    form.reset(
      {
        ...noteRawValue,
        noteId: { value: noteRawValue.noteId, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): NoteSigFormDefaults {
    return {
      noteId: null,
      noteStat: false,
    };
  }
}
