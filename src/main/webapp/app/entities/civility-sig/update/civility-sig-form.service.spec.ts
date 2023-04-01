import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../civility-sig.test-samples';

import { CivilitySigFormService } from './civility-sig-form.service';

describe('CivilitySig Form Service', () => {
  let service: CivilitySigFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CivilitySigFormService);
  });

  describe('Service methods', () => {
    describe('createCivilitySigFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCivilitySigFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            civilityId: expect.any(Object),
            civilityValue: expect.any(Object),
            civilityDescription: expect.any(Object),
            civilityCode: expect.any(Object),
            civilityParams: expect.any(Object),
            civilityAttributs: expect.any(Object),
            civilityStat: expect.any(Object),
          })
        );
      });

      it('passing ICivilitySig should create a new form with FormGroup', () => {
        const formGroup = service.createCivilitySigFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            civilityId: expect.any(Object),
            civilityValue: expect.any(Object),
            civilityDescription: expect.any(Object),
            civilityCode: expect.any(Object),
            civilityParams: expect.any(Object),
            civilityAttributs: expect.any(Object),
            civilityStat: expect.any(Object),
          })
        );
      });
    });

    describe('getCivilitySig', () => {
      it('should return NewCivilitySig for default CivilitySig initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCivilitySigFormGroup(sampleWithNewData);

        const civility = service.getCivilitySig(formGroup) as any;

        expect(civility).toMatchObject(sampleWithNewData);
      });

      it('should return NewCivilitySig for empty CivilitySig initial value', () => {
        const formGroup = service.createCivilitySigFormGroup();

        const civility = service.getCivilitySig(formGroup) as any;

        expect(civility).toMatchObject({});
      });

      it('should return ICivilitySig', () => {
        const formGroup = service.createCivilitySigFormGroup(sampleWithRequiredData);

        const civility = service.getCivilitySig(formGroup) as any;

        expect(civility).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICivilitySig should not enable civilityId FormControl', () => {
        const formGroup = service.createCivilitySigFormGroup();
        expect(formGroup.controls.civilityId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.civilityId.disabled).toBe(true);
      });

      it('passing NewCivilitySig should disable civilityId FormControl', () => {
        const formGroup = service.createCivilitySigFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.civilityId.disabled).toBe(true);

        service.resetForm(formGroup, { civilityId: null });

        expect(formGroup.controls.civilityId.disabled).toBe(true);
      });
    });
  });
});
