import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../check-accreditation-report-sig.test-samples';

import { CheckAccreditationReportSigFormService } from './check-accreditation-report-sig-form.service';

describe('CheckAccreditationReportSig Form Service', () => {
  let service: CheckAccreditationReportSigFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CheckAccreditationReportSigFormService);
  });

  describe('Service methods', () => {
    describe('createCheckAccreditationReportSigFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCheckAccreditationReportSigFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            checkAccreditationReportId: expect.any(Object),
            checkAccreditationReportDescription: expect.any(Object),
            checkAccreditationReportPersonPhoto: expect.any(Object),
            checkAccreditationReportCINPhoto: expect.any(Object),
            checkAccreditationReportAttachment: expect.any(Object),
            checkAccreditationReportParams: expect.any(Object),
            checkAccreditationReportAttributs: expect.any(Object),
            checkAccreditationReportStat: expect.any(Object),
            event: expect.any(Object),
            checkAccreditationHistory: expect.any(Object),
          })
        );
      });

      it('passing ICheckAccreditationReportSig should create a new form with FormGroup', () => {
        const formGroup = service.createCheckAccreditationReportSigFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            checkAccreditationReportId: expect.any(Object),
            checkAccreditationReportDescription: expect.any(Object),
            checkAccreditationReportPersonPhoto: expect.any(Object),
            checkAccreditationReportCINPhoto: expect.any(Object),
            checkAccreditationReportAttachment: expect.any(Object),
            checkAccreditationReportParams: expect.any(Object),
            checkAccreditationReportAttributs: expect.any(Object),
            checkAccreditationReportStat: expect.any(Object),
            event: expect.any(Object),
            checkAccreditationHistory: expect.any(Object),
          })
        );
      });
    });

    describe('getCheckAccreditationReportSig', () => {
      it('should return NewCheckAccreditationReportSig for default CheckAccreditationReportSig initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCheckAccreditationReportSigFormGroup(sampleWithNewData);

        const checkAccreditationReport = service.getCheckAccreditationReportSig(formGroup) as any;

        expect(checkAccreditationReport).toMatchObject(sampleWithNewData);
      });

      it('should return NewCheckAccreditationReportSig for empty CheckAccreditationReportSig initial value', () => {
        const formGroup = service.createCheckAccreditationReportSigFormGroup();

        const checkAccreditationReport = service.getCheckAccreditationReportSig(formGroup) as any;

        expect(checkAccreditationReport).toMatchObject({});
      });

      it('should return ICheckAccreditationReportSig', () => {
        const formGroup = service.createCheckAccreditationReportSigFormGroup(sampleWithRequiredData);

        const checkAccreditationReport = service.getCheckAccreditationReportSig(formGroup) as any;

        expect(checkAccreditationReport).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICheckAccreditationReportSig should not enable checkAccreditationReportId FormControl', () => {
        const formGroup = service.createCheckAccreditationReportSigFormGroup();
        expect(formGroup.controls.checkAccreditationReportId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.checkAccreditationReportId.disabled).toBe(true);
      });

      it('passing NewCheckAccreditationReportSig should disable checkAccreditationReportId FormControl', () => {
        const formGroup = service.createCheckAccreditationReportSigFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.checkAccreditationReportId.disabled).toBe(true);

        service.resetForm(formGroup, { checkAccreditationReportId: null });

        expect(formGroup.controls.checkAccreditationReportId.disabled).toBe(true);
      });
    });
  });
});
