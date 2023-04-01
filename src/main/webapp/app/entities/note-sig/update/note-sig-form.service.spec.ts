import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../note-sig.test-samples';

import { NoteSigFormService } from './note-sig-form.service';

describe('NoteSig Form Service', () => {
  let service: NoteSigFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NoteSigFormService);
  });

  describe('Service methods', () => {
    describe('createNoteSigFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createNoteSigFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            noteId: expect.any(Object),
            noteValue: expect.any(Object),
            noteDescription: expect.any(Object),
            noteTypeParams: expect.any(Object),
            noteTypeAttributs: expect.any(Object),
            noteStat: expect.any(Object),
            accreditation: expect.any(Object),
            event: expect.any(Object),
          })
        );
      });

      it('passing INoteSig should create a new form with FormGroup', () => {
        const formGroup = service.createNoteSigFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            noteId: expect.any(Object),
            noteValue: expect.any(Object),
            noteDescription: expect.any(Object),
            noteTypeParams: expect.any(Object),
            noteTypeAttributs: expect.any(Object),
            noteStat: expect.any(Object),
            accreditation: expect.any(Object),
            event: expect.any(Object),
          })
        );
      });
    });

    describe('getNoteSig', () => {
      it('should return NewNoteSig for default NoteSig initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createNoteSigFormGroup(sampleWithNewData);

        const note = service.getNoteSig(formGroup) as any;

        expect(note).toMatchObject(sampleWithNewData);
      });

      it('should return NewNoteSig for empty NoteSig initial value', () => {
        const formGroup = service.createNoteSigFormGroup();

        const note = service.getNoteSig(formGroup) as any;

        expect(note).toMatchObject({});
      });

      it('should return INoteSig', () => {
        const formGroup = service.createNoteSigFormGroup(sampleWithRequiredData);

        const note = service.getNoteSig(formGroup) as any;

        expect(note).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing INoteSig should not enable noteId FormControl', () => {
        const formGroup = service.createNoteSigFormGroup();
        expect(formGroup.controls.noteId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.noteId.disabled).toBe(true);
      });

      it('passing NewNoteSig should disable noteId FormControl', () => {
        const formGroup = service.createNoteSigFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.noteId.disabled).toBe(true);

        service.resetForm(formGroup, { noteId: null });

        expect(formGroup.controls.noteId.disabled).toBe(true);
      });
    });
  });
});
