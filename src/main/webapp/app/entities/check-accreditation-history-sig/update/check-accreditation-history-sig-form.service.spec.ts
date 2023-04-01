import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../check-accreditation-history-sig.test-samples';

import { CheckAccreditationHistorySigFormService } from './check-accreditation-history-sig-form.service';

describe('CheckAccreditationHistorySig Form Service', () => {
  let service: CheckAccreditationHistorySigFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CheckAccreditationHistorySigFormService);
  });

  describe('Service methods', () => {
    describe('createCheckAccreditationHistorySigFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCheckAccreditationHistorySigFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            checkAccreditationHistoryId: expect.any(Object),
            checkAccreditationHistoryReadedCode: expect.any(Object),
            checkAccreditationHistoryUserId: expect.any(Object),
            checkAccreditationHistoryResult: expect.any(Object),
            checkAccreditationHistoryError: expect.any(Object),
            checkAccreditationHistoryDate: expect.any(Object),
            checkAccreditationHistoryLocalisation: expect.any(Object),
            checkAccreditationHistoryIpAdresse: expect.any(Object),
            checkAccreditationParams: expect.any(Object),
            checkAccreditationAttributs: expect.any(Object),
            checkAccreditationHistoryStat: expect.any(Object),
            event: expect.any(Object),
            accreditation: expect.any(Object),
          })
        );
      });

      it('passing ICheckAccreditationHistorySig should create a new form with FormGroup', () => {
        const formGroup = service.createCheckAccreditationHistorySigFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            checkAccreditationHistoryId: expect.any(Object),
            checkAccreditationHistoryReadedCode: expect.any(Object),
            checkAccreditationHistoryUserId: expect.any(Object),
            checkAccreditationHistoryResult: expect.any(Object),
            checkAccreditationHistoryError: expect.any(Object),
            checkAccreditationHistoryDate: expect.any(Object),
            checkAccreditationHistoryLocalisation: expect.any(Object),
            checkAccreditationHistoryIpAdresse: expect.any(Object),
            checkAccreditationParams: expect.any(Object),
            checkAccreditationAttributs: expect.any(Object),
            checkAccreditationHistoryStat: expect.any(Object),
            event: expect.any(Object),
            accreditation: expect.any(Object),
          })
        );
      });
    });

    describe('getCheckAccreditationHistorySig', () => {
      it('should return NewCheckAccreditationHistorySig for default CheckAccreditationHistorySig initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCheckAccreditationHistorySigFormGroup(sampleWithNewData);

        const checkAccreditationHistory = service.getCheckAccreditationHistorySig(formGroup) as any;

        expect(checkAccreditationHistory).toMatchObject(sampleWithNewData);
      });

      it('should return NewCheckAccreditationHistorySig for empty CheckAccreditationHistorySig initial value', () => {
        const formGroup = service.createCheckAccreditationHistorySigFormGroup();

        const checkAccreditationHistory = service.getCheckAccreditationHistorySig(formGroup) as any;

        expect(checkAccreditationHistory).toMatchObject({});
      });

      it('should return ICheckAccreditationHistorySig', () => {
        const formGroup = service.createCheckAccreditationHistorySigFormGroup(sampleWithRequiredData);

        const checkAccreditationHistory = service.getCheckAccreditationHistorySig(formGroup) as any;

        expect(checkAccreditationHistory).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICheckAccreditationHistorySig should not enable checkAccreditationHistoryId FormControl', () => {
        const formGroup = service.createCheckAccreditationHistorySigFormGroup();
        expect(formGroup.controls.checkAccreditationHistoryId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.checkAccreditationHistoryId.disabled).toBe(true);
      });

      it('passing NewCheckAccreditationHistorySig should disable checkAccreditationHistoryId FormControl', () => {
        const formGroup = service.createCheckAccreditationHistorySigFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.checkAccreditationHistoryId.disabled).toBe(true);

        service.resetForm(formGroup, { checkAccreditationHistoryId: null });

        expect(formGroup.controls.checkAccreditationHistoryId.disabled).toBe(true);
      });
    });
  });
});
