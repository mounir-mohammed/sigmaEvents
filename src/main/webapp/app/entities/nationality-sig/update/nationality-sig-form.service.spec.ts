import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../nationality-sig.test-samples';

import { NationalitySigFormService } from './nationality-sig-form.service';

describe('NationalitySig Form Service', () => {
  let service: NationalitySigFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NationalitySigFormService);
  });

  describe('Service methods', () => {
    describe('createNationalitySigFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createNationalitySigFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            nationalityId: expect.any(Object),
            nationalityValue: expect.any(Object),
            nationalityAbreviation: expect.any(Object),
            nationalityDescription: expect.any(Object),
            nationalityFlag: expect.any(Object),
            nationalityParams: expect.any(Object),
            nationalityAttributs: expect.any(Object),
            nationalityStat: expect.any(Object),
          })
        );
      });

      it('passing INationalitySig should create a new form with FormGroup', () => {
        const formGroup = service.createNationalitySigFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            nationalityId: expect.any(Object),
            nationalityValue: expect.any(Object),
            nationalityAbreviation: expect.any(Object),
            nationalityDescription: expect.any(Object),
            nationalityFlag: expect.any(Object),
            nationalityParams: expect.any(Object),
            nationalityAttributs: expect.any(Object),
            nationalityStat: expect.any(Object),
          })
        );
      });
    });

    describe('getNationalitySig', () => {
      it('should return NewNationalitySig for default NationalitySig initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createNationalitySigFormGroup(sampleWithNewData);

        const nationality = service.getNationalitySig(formGroup) as any;

        expect(nationality).toMatchObject(sampleWithNewData);
      });

      it('should return NewNationalitySig for empty NationalitySig initial value', () => {
        const formGroup = service.createNationalitySigFormGroup();

        const nationality = service.getNationalitySig(formGroup) as any;

        expect(nationality).toMatchObject({});
      });

      it('should return INationalitySig', () => {
        const formGroup = service.createNationalitySigFormGroup(sampleWithRequiredData);

        const nationality = service.getNationalitySig(formGroup) as any;

        expect(nationality).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing INationalitySig should not enable nationalityId FormControl', () => {
        const formGroup = service.createNationalitySigFormGroup();
        expect(formGroup.controls.nationalityId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.nationalityId.disabled).toBe(true);
      });

      it('passing NewNationalitySig should disable nationalityId FormControl', () => {
        const formGroup = service.createNationalitySigFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.nationalityId.disabled).toBe(true);

        service.resetForm(formGroup, { nationalityId: null });

        expect(formGroup.controls.nationalityId.disabled).toBe(true);
      });
    });
  });
});
