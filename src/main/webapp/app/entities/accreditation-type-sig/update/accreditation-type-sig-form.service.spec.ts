import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../accreditation-type-sig.test-samples';

import { AccreditationTypeSigFormService } from './accreditation-type-sig-form.service';

describe('AccreditationTypeSig Form Service', () => {
  let service: AccreditationTypeSigFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AccreditationTypeSigFormService);
  });

  describe('Service methods', () => {
    describe('createAccreditationTypeSigFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAccreditationTypeSigFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            accreditationTypeId: expect.any(Object),
            accreditationTypeValue: expect.any(Object),
            accreditationTypeAbreviation: expect.any(Object),
            accreditationTypeDescription: expect.any(Object),
            accreditationTypeParams: expect.any(Object),
            accreditationTypeAttributs: expect.any(Object),
            accreditationTypeStat: expect.any(Object),
            printingModel: expect.any(Object),
            event: expect.any(Object),
          })
        );
      });

      it('passing IAccreditationTypeSig should create a new form with FormGroup', () => {
        const formGroup = service.createAccreditationTypeSigFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            accreditationTypeId: expect.any(Object),
            accreditationTypeValue: expect.any(Object),
            accreditationTypeAbreviation: expect.any(Object),
            accreditationTypeDescription: expect.any(Object),
            accreditationTypeParams: expect.any(Object),
            accreditationTypeAttributs: expect.any(Object),
            accreditationTypeStat: expect.any(Object),
            printingModel: expect.any(Object),
            event: expect.any(Object),
          })
        );
      });
    });

    describe('getAccreditationTypeSig', () => {
      it('should return NewAccreditationTypeSig for default AccreditationTypeSig initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createAccreditationTypeSigFormGroup(sampleWithNewData);

        const accreditationType = service.getAccreditationTypeSig(formGroup) as any;

        expect(accreditationType).toMatchObject(sampleWithNewData);
      });

      it('should return NewAccreditationTypeSig for empty AccreditationTypeSig initial value', () => {
        const formGroup = service.createAccreditationTypeSigFormGroup();

        const accreditationType = service.getAccreditationTypeSig(formGroup) as any;

        expect(accreditationType).toMatchObject({});
      });

      it('should return IAccreditationTypeSig', () => {
        const formGroup = service.createAccreditationTypeSigFormGroup(sampleWithRequiredData);

        const accreditationType = service.getAccreditationTypeSig(formGroup) as any;

        expect(accreditationType).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAccreditationTypeSig should not enable accreditationTypeId FormControl', () => {
        const formGroup = service.createAccreditationTypeSigFormGroup();
        expect(formGroup.controls.accreditationTypeId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.accreditationTypeId.disabled).toBe(true);
      });

      it('passing NewAccreditationTypeSig should disable accreditationTypeId FormControl', () => {
        const formGroup = service.createAccreditationTypeSigFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.accreditationTypeId.disabled).toBe(true);

        service.resetForm(formGroup, { accreditationTypeId: null });

        expect(formGroup.controls.accreditationTypeId.disabled).toBe(true);
      });
    });
  });
});
