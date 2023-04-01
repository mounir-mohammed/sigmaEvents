import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../sexe-sig.test-samples';

import { SexeSigFormService } from './sexe-sig-form.service';

describe('SexeSig Form Service', () => {
  let service: SexeSigFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SexeSigFormService);
  });

  describe('Service methods', () => {
    describe('createSexeSigFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSexeSigFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            sexeId: expect.any(Object),
            sexeValue: expect.any(Object),
            sexeDescription: expect.any(Object),
            sexeTypeParams: expect.any(Object),
            sexeTypeAttributs: expect.any(Object),
            sexeStat: expect.any(Object),
          })
        );
      });

      it('passing ISexeSig should create a new form with FormGroup', () => {
        const formGroup = service.createSexeSigFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            sexeId: expect.any(Object),
            sexeValue: expect.any(Object),
            sexeDescription: expect.any(Object),
            sexeTypeParams: expect.any(Object),
            sexeTypeAttributs: expect.any(Object),
            sexeStat: expect.any(Object),
          })
        );
      });
    });

    describe('getSexeSig', () => {
      it('should return NewSexeSig for default SexeSig initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createSexeSigFormGroup(sampleWithNewData);

        const sexe = service.getSexeSig(formGroup) as any;

        expect(sexe).toMatchObject(sampleWithNewData);
      });

      it('should return NewSexeSig for empty SexeSig initial value', () => {
        const formGroup = service.createSexeSigFormGroup();

        const sexe = service.getSexeSig(formGroup) as any;

        expect(sexe).toMatchObject({});
      });

      it('should return ISexeSig', () => {
        const formGroup = service.createSexeSigFormGroup(sampleWithRequiredData);

        const sexe = service.getSexeSig(formGroup) as any;

        expect(sexe).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISexeSig should not enable sexeId FormControl', () => {
        const formGroup = service.createSexeSigFormGroup();
        expect(formGroup.controls.sexeId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.sexeId.disabled).toBe(true);
      });

      it('passing NewSexeSig should disable sexeId FormControl', () => {
        const formGroup = service.createSexeSigFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.sexeId.disabled).toBe(true);

        service.resetForm(formGroup, { sexeId: null });

        expect(formGroup.controls.sexeId.disabled).toBe(true);
      });
    });
  });
});
