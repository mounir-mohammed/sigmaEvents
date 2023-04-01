import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../area-sig.test-samples';

import { AreaSigFormService } from './area-sig-form.service';

describe('AreaSig Form Service', () => {
  let service: AreaSigFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AreaSigFormService);
  });

  describe('Service methods', () => {
    describe('createAreaSigFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAreaSigFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            areaId: expect.any(Object),
            areaName: expect.any(Object),
            areaAbreviation: expect.any(Object),
            areaColor: expect.any(Object),
            areaDescription: expect.any(Object),
            areaLogo: expect.any(Object),
            areaParams: expect.any(Object),
            areaAttributs: expect.any(Object),
            areaStat: expect.any(Object),
            event: expect.any(Object),
            fonctions: expect.any(Object),
          })
        );
      });

      it('passing IAreaSig should create a new form with FormGroup', () => {
        const formGroup = service.createAreaSigFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            areaId: expect.any(Object),
            areaName: expect.any(Object),
            areaAbreviation: expect.any(Object),
            areaColor: expect.any(Object),
            areaDescription: expect.any(Object),
            areaLogo: expect.any(Object),
            areaParams: expect.any(Object),
            areaAttributs: expect.any(Object),
            areaStat: expect.any(Object),
            event: expect.any(Object),
            fonctions: expect.any(Object),
          })
        );
      });
    });

    describe('getAreaSig', () => {
      it('should return NewAreaSig for default AreaSig initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createAreaSigFormGroup(sampleWithNewData);

        const area = service.getAreaSig(formGroup) as any;

        expect(area).toMatchObject(sampleWithNewData);
      });

      it('should return NewAreaSig for empty AreaSig initial value', () => {
        const formGroup = service.createAreaSigFormGroup();

        const area = service.getAreaSig(formGroup) as any;

        expect(area).toMatchObject({});
      });

      it('should return IAreaSig', () => {
        const formGroup = service.createAreaSigFormGroup(sampleWithRequiredData);

        const area = service.getAreaSig(formGroup) as any;

        expect(area).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAreaSig should not enable areaId FormControl', () => {
        const formGroup = service.createAreaSigFormGroup();
        expect(formGroup.controls.areaId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.areaId.disabled).toBe(true);
      });

      it('passing NewAreaSig should disable areaId FormControl', () => {
        const formGroup = service.createAreaSigFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.areaId.disabled).toBe(true);

        service.resetForm(formGroup, { areaId: null });

        expect(formGroup.controls.areaId.disabled).toBe(true);
      });
    });
  });
});
