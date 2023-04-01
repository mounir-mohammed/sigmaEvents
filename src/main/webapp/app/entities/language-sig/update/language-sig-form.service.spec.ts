import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../language-sig.test-samples';

import { LanguageSigFormService } from './language-sig-form.service';

describe('LanguageSig Form Service', () => {
  let service: LanguageSigFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LanguageSigFormService);
  });

  describe('Service methods', () => {
    describe('createLanguageSigFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createLanguageSigFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            languageId: expect.any(Object),
            languageCode: expect.any(Object),
            languageName: expect.any(Object),
            languageDescription: expect.any(Object),
            languageParams: expect.any(Object),
            languageAttributs: expect.any(Object),
            languageStat: expect.any(Object),
          })
        );
      });

      it('passing ILanguageSig should create a new form with FormGroup', () => {
        const formGroup = service.createLanguageSigFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            languageId: expect.any(Object),
            languageCode: expect.any(Object),
            languageName: expect.any(Object),
            languageDescription: expect.any(Object),
            languageParams: expect.any(Object),
            languageAttributs: expect.any(Object),
            languageStat: expect.any(Object),
          })
        );
      });
    });

    describe('getLanguageSig', () => {
      it('should return NewLanguageSig for default LanguageSig initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createLanguageSigFormGroup(sampleWithNewData);

        const language = service.getLanguageSig(formGroup) as any;

        expect(language).toMatchObject(sampleWithNewData);
      });

      it('should return NewLanguageSig for empty LanguageSig initial value', () => {
        const formGroup = service.createLanguageSigFormGroup();

        const language = service.getLanguageSig(formGroup) as any;

        expect(language).toMatchObject({});
      });

      it('should return ILanguageSig', () => {
        const formGroup = service.createLanguageSigFormGroup(sampleWithRequiredData);

        const language = service.getLanguageSig(formGroup) as any;

        expect(language).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ILanguageSig should not enable languageId FormControl', () => {
        const formGroup = service.createLanguageSigFormGroup();
        expect(formGroup.controls.languageId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.languageId.disabled).toBe(true);
      });

      it('passing NewLanguageSig should disable languageId FormControl', () => {
        const formGroup = service.createLanguageSigFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.languageId.disabled).toBe(true);

        service.resetForm(formGroup, { languageId: null });

        expect(formGroup.controls.languageId.disabled).toBe(true);
      });
    });
  });
});
