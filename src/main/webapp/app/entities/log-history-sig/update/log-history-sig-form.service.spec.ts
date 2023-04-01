import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../log-history-sig.test-samples';

import { LogHistorySigFormService } from './log-history-sig-form.service';

describe('LogHistorySig Form Service', () => {
  let service: LogHistorySigFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LogHistorySigFormService);
  });

  describe('Service methods', () => {
    describe('createLogHistorySigFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createLogHistorySigFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            logHistory: expect.any(Object),
            logHistoryDescription: expect.any(Object),
            logHistoryParams: expect.any(Object),
            logHistoryAttributs: expect.any(Object),
            logHistoryStat: expect.any(Object),
          })
        );
      });

      it('passing ILogHistorySig should create a new form with FormGroup', () => {
        const formGroup = service.createLogHistorySigFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            logHistory: expect.any(Object),
            logHistoryDescription: expect.any(Object),
            logHistoryParams: expect.any(Object),
            logHistoryAttributs: expect.any(Object),
            logHistoryStat: expect.any(Object),
          })
        );
      });
    });

    describe('getLogHistorySig', () => {
      it('should return NewLogHistorySig for default LogHistorySig initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createLogHistorySigFormGroup(sampleWithNewData);

        const logHistory = service.getLogHistorySig(formGroup) as any;

        expect(logHistory).toMatchObject(sampleWithNewData);
      });

      it('should return NewLogHistorySig for empty LogHistorySig initial value', () => {
        const formGroup = service.createLogHistorySigFormGroup();

        const logHistory = service.getLogHistorySig(formGroup) as any;

        expect(logHistory).toMatchObject({});
      });

      it('should return ILogHistorySig', () => {
        const formGroup = service.createLogHistorySigFormGroup(sampleWithRequiredData);

        const logHistory = service.getLogHistorySig(formGroup) as any;

        expect(logHistory).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ILogHistorySig should not enable logHistory FormControl', () => {
        const formGroup = service.createLogHistorySigFormGroup();
        expect(formGroup.controls.logHistory.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.logHistory.disabled).toBe(true);
      });

      it('passing NewLogHistorySig should disable logHistory FormControl', () => {
        const formGroup = service.createLogHistorySigFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.logHistory.disabled).toBe(true);

        service.resetForm(formGroup, { logHistory: null });

        expect(formGroup.controls.logHistory.disabled).toBe(true);
      });
    });
  });
});
