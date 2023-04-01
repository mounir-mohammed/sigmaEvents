import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../info-supp-type-sig.test-samples';

import { InfoSuppTypeSigFormService } from './info-supp-type-sig-form.service';

describe('InfoSuppTypeSig Form Service', () => {
  let service: InfoSuppTypeSigFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InfoSuppTypeSigFormService);
  });

  describe('Service methods', () => {
    describe('createInfoSuppTypeSigFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createInfoSuppTypeSigFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            infoSuppTypeId: expect.any(Object),
            infoSuppTypeName: expect.any(Object),
            infoSuppTypeDescription: expect.any(Object),
            infoSuppTypeParams: expect.any(Object),
            infoSuppTypeAttributs: expect.any(Object),
            infoSuppTypeStat: expect.any(Object),
          })
        );
      });

      it('passing IInfoSuppTypeSig should create a new form with FormGroup', () => {
        const formGroup = service.createInfoSuppTypeSigFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            infoSuppTypeId: expect.any(Object),
            infoSuppTypeName: expect.any(Object),
            infoSuppTypeDescription: expect.any(Object),
            infoSuppTypeParams: expect.any(Object),
            infoSuppTypeAttributs: expect.any(Object),
            infoSuppTypeStat: expect.any(Object),
          })
        );
      });
    });

    describe('getInfoSuppTypeSig', () => {
      it('should return NewInfoSuppTypeSig for default InfoSuppTypeSig initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createInfoSuppTypeSigFormGroup(sampleWithNewData);

        const infoSuppType = service.getInfoSuppTypeSig(formGroup) as any;

        expect(infoSuppType).toMatchObject(sampleWithNewData);
      });

      it('should return NewInfoSuppTypeSig for empty InfoSuppTypeSig initial value', () => {
        const formGroup = service.createInfoSuppTypeSigFormGroup();

        const infoSuppType = service.getInfoSuppTypeSig(formGroup) as any;

        expect(infoSuppType).toMatchObject({});
      });

      it('should return IInfoSuppTypeSig', () => {
        const formGroup = service.createInfoSuppTypeSigFormGroup(sampleWithRequiredData);

        const infoSuppType = service.getInfoSuppTypeSig(formGroup) as any;

        expect(infoSuppType).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IInfoSuppTypeSig should not enable infoSuppTypeId FormControl', () => {
        const formGroup = service.createInfoSuppTypeSigFormGroup();
        expect(formGroup.controls.infoSuppTypeId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.infoSuppTypeId.disabled).toBe(true);
      });

      it('passing NewInfoSuppTypeSig should disable infoSuppTypeId FormControl', () => {
        const formGroup = service.createInfoSuppTypeSigFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.infoSuppTypeId.disabled).toBe(true);

        service.resetForm(formGroup, { infoSuppTypeId: null });

        expect(formGroup.controls.infoSuppTypeId.disabled).toBe(true);
      });
    });
  });
});
