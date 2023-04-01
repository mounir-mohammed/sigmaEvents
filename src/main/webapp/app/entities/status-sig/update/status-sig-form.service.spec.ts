import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../status-sig.test-samples';

import { StatusSigFormService } from './status-sig-form.service';

describe('StatusSig Form Service', () => {
  let service: StatusSigFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StatusSigFormService);
  });

  describe('Service methods', () => {
    describe('createStatusSigFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createStatusSigFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            statusId: expect.any(Object),
            statusName: expect.any(Object),
            statusAbreviation: expect.any(Object),
            statusColor: expect.any(Object),
            statusDescription: expect.any(Object),
            statusUserCanPrint: expect.any(Object),
            statusUserCanUpdate: expect.any(Object),
            statusUserCanValidate: expect.any(Object),
            statusParams: expect.any(Object),
            statusAttributs: expect.any(Object),
            statusStat: expect.any(Object),
          })
        );
      });

      it('passing IStatusSig should create a new form with FormGroup', () => {
        const formGroup = service.createStatusSigFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            statusId: expect.any(Object),
            statusName: expect.any(Object),
            statusAbreviation: expect.any(Object),
            statusColor: expect.any(Object),
            statusDescription: expect.any(Object),
            statusUserCanPrint: expect.any(Object),
            statusUserCanUpdate: expect.any(Object),
            statusUserCanValidate: expect.any(Object),
            statusParams: expect.any(Object),
            statusAttributs: expect.any(Object),
            statusStat: expect.any(Object),
          })
        );
      });
    });

    describe('getStatusSig', () => {
      it('should return NewStatusSig for default StatusSig initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createStatusSigFormGroup(sampleWithNewData);

        const status = service.getStatusSig(formGroup) as any;

        expect(status).toMatchObject(sampleWithNewData);
      });

      it('should return NewStatusSig for empty StatusSig initial value', () => {
        const formGroup = service.createStatusSigFormGroup();

        const status = service.getStatusSig(formGroup) as any;

        expect(status).toMatchObject({});
      });

      it('should return IStatusSig', () => {
        const formGroup = service.createStatusSigFormGroup(sampleWithRequiredData);

        const status = service.getStatusSig(formGroup) as any;

        expect(status).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IStatusSig should not enable statusId FormControl', () => {
        const formGroup = service.createStatusSigFormGroup();
        expect(formGroup.controls.statusId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.statusId.disabled).toBe(true);
      });

      it('passing NewStatusSig should disable statusId FormControl', () => {
        const formGroup = service.createStatusSigFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.statusId.disabled).toBe(true);

        service.resetForm(formGroup, { statusId: null });

        expect(formGroup.controls.statusId.disabled).toBe(true);
      });
    });
  });
});
