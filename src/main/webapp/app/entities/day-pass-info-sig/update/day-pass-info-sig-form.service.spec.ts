import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../day-pass-info-sig.test-samples';

import { DayPassInfoSigFormService } from './day-pass-info-sig-form.service';

describe('DayPassInfoSig Form Service', () => {
  let service: DayPassInfoSigFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DayPassInfoSigFormService);
  });

  describe('Service methods', () => {
    describe('createDayPassInfoSigFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDayPassInfoSigFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            dayPassInfoId: expect.any(Object),
            dayPassInfoName: expect.any(Object),
            dayPassDescription: expect.any(Object),
            dayPassLogo: expect.any(Object),
            dayPassInfoCreationDate: expect.any(Object),
            dayPassInfoUpdateDate: expect.any(Object),
            dayPassInfoCreatedByuser: expect.any(Object),
            dayPassInfoDateStart: expect.any(Object),
            dayPassInfoDateEnd: expect.any(Object),
            dayPassInfoNumber: expect.any(Object),
            dayPassParams: expect.any(Object),
            dayPassAttributs: expect.any(Object),
            dayPassInfoStat: expect.any(Object),
            event: expect.any(Object),
          })
        );
      });

      it('passing IDayPassInfoSig should create a new form with FormGroup', () => {
        const formGroup = service.createDayPassInfoSigFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            dayPassInfoId: expect.any(Object),
            dayPassInfoName: expect.any(Object),
            dayPassDescription: expect.any(Object),
            dayPassLogo: expect.any(Object),
            dayPassInfoCreationDate: expect.any(Object),
            dayPassInfoUpdateDate: expect.any(Object),
            dayPassInfoCreatedByuser: expect.any(Object),
            dayPassInfoDateStart: expect.any(Object),
            dayPassInfoDateEnd: expect.any(Object),
            dayPassInfoNumber: expect.any(Object),
            dayPassParams: expect.any(Object),
            dayPassAttributs: expect.any(Object),
            dayPassInfoStat: expect.any(Object),
            event: expect.any(Object),
          })
        );
      });
    });

    describe('getDayPassInfoSig', () => {
      it('should return NewDayPassInfoSig for default DayPassInfoSig initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createDayPassInfoSigFormGroup(sampleWithNewData);

        const dayPassInfo = service.getDayPassInfoSig(formGroup) as any;

        expect(dayPassInfo).toMatchObject(sampleWithNewData);
      });

      it('should return NewDayPassInfoSig for empty DayPassInfoSig initial value', () => {
        const formGroup = service.createDayPassInfoSigFormGroup();

        const dayPassInfo = service.getDayPassInfoSig(formGroup) as any;

        expect(dayPassInfo).toMatchObject({});
      });

      it('should return IDayPassInfoSig', () => {
        const formGroup = service.createDayPassInfoSigFormGroup(sampleWithRequiredData);

        const dayPassInfo = service.getDayPassInfoSig(formGroup) as any;

        expect(dayPassInfo).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDayPassInfoSig should not enable dayPassInfoId FormControl', () => {
        const formGroup = service.createDayPassInfoSigFormGroup();
        expect(formGroup.controls.dayPassInfoId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.dayPassInfoId.disabled).toBe(true);
      });

      it('passing NewDayPassInfoSig should disable dayPassInfoId FormControl', () => {
        const formGroup = service.createDayPassInfoSigFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.dayPassInfoId.disabled).toBe(true);

        service.resetForm(formGroup, { dayPassInfoId: null });

        expect(formGroup.controls.dayPassInfoId.disabled).toBe(true);
      });
    });
  });
});
