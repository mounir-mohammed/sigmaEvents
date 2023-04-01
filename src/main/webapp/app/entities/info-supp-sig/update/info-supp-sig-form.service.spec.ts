import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../info-supp-sig.test-samples';

import { InfoSuppSigFormService } from './info-supp-sig-form.service';

describe('InfoSuppSig Form Service', () => {
  let service: InfoSuppSigFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InfoSuppSigFormService);
  });

  describe('Service methods', () => {
    describe('createInfoSuppSigFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createInfoSuppSigFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            infoSuppId: expect.any(Object),
            infoSuppName: expect.any(Object),
            infoSuppDescription: expect.any(Object),
            infoSuppParams: expect.any(Object),
            infoSuppAttributs: expect.any(Object),
            infoSuppStat: expect.any(Object),
            infoSuppType: expect.any(Object),
            accreditation: expect.any(Object),
            event: expect.any(Object),
          })
        );
      });

      it('passing IInfoSuppSig should create a new form with FormGroup', () => {
        const formGroup = service.createInfoSuppSigFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            infoSuppId: expect.any(Object),
            infoSuppName: expect.any(Object),
            infoSuppDescription: expect.any(Object),
            infoSuppParams: expect.any(Object),
            infoSuppAttributs: expect.any(Object),
            infoSuppStat: expect.any(Object),
            infoSuppType: expect.any(Object),
            accreditation: expect.any(Object),
            event: expect.any(Object),
          })
        );
      });
    });

    describe('getInfoSuppSig', () => {
      it('should return NewInfoSuppSig for default InfoSuppSig initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createInfoSuppSigFormGroup(sampleWithNewData);

        const infoSupp = service.getInfoSuppSig(formGroup) as any;

        expect(infoSupp).toMatchObject(sampleWithNewData);
      });

      it('should return NewInfoSuppSig for empty InfoSuppSig initial value', () => {
        const formGroup = service.createInfoSuppSigFormGroup();

        const infoSupp = service.getInfoSuppSig(formGroup) as any;

        expect(infoSupp).toMatchObject({});
      });

      it('should return IInfoSuppSig', () => {
        const formGroup = service.createInfoSuppSigFormGroup(sampleWithRequiredData);

        const infoSupp = service.getInfoSuppSig(formGroup) as any;

        expect(infoSupp).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IInfoSuppSig should not enable infoSuppId FormControl', () => {
        const formGroup = service.createInfoSuppSigFormGroup();
        expect(formGroup.controls.infoSuppId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.infoSuppId.disabled).toBe(true);
      });

      it('passing NewInfoSuppSig should disable infoSuppId FormControl', () => {
        const formGroup = service.createInfoSuppSigFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.infoSuppId.disabled).toBe(true);

        service.resetForm(formGroup, { infoSuppId: null });

        expect(formGroup.controls.infoSuppId.disabled).toBe(true);
      });
    });
  });
});
