import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../operation-history-sig.test-samples';

import { OperationHistorySigFormService } from './operation-history-sig-form.service';

describe('OperationHistorySig Form Service', () => {
  let service: OperationHistorySigFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OperationHistorySigFormService);
  });

  describe('Service methods', () => {
    describe('createOperationHistorySigFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createOperationHistorySigFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            operationHistoryId: expect.any(Object),
            operationHistoryDescription: expect.any(Object),
            operationHistoryDate: expect.any(Object),
            operationHistoryUserID: expect.any(Object),
            operationHistoryOldValue: expect.any(Object),
            operationHistoryNewValue: expect.any(Object),
            operationHistoryOldId: expect.any(Object),
            operationHistoryNewId: expect.any(Object),
            operationHistoryImportedFile: expect.any(Object),
            operationHistoryImportedFilePath: expect.any(Object),
            operationHistoryParams: expect.any(Object),
            operationHistoryAttributs: expect.any(Object),
            operationHistoryStat: expect.any(Object),
            typeoperation: expect.any(Object),
            event: expect.any(Object),
          })
        );
      });

      it('passing IOperationHistorySig should create a new form with FormGroup', () => {
        const formGroup = service.createOperationHistorySigFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            operationHistoryId: expect.any(Object),
            operationHistoryDescription: expect.any(Object),
            operationHistoryDate: expect.any(Object),
            operationHistoryUserID: expect.any(Object),
            operationHistoryOldValue: expect.any(Object),
            operationHistoryNewValue: expect.any(Object),
            operationHistoryOldId: expect.any(Object),
            operationHistoryNewId: expect.any(Object),
            operationHistoryImportedFile: expect.any(Object),
            operationHistoryImportedFilePath: expect.any(Object),
            operationHistoryParams: expect.any(Object),
            operationHistoryAttributs: expect.any(Object),
            operationHistoryStat: expect.any(Object),
            typeoperation: expect.any(Object),
            event: expect.any(Object),
          })
        );
      });
    });

    describe('getOperationHistorySig', () => {
      it('should return NewOperationHistorySig for default OperationHistorySig initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createOperationHistorySigFormGroup(sampleWithNewData);

        const operationHistory = service.getOperationHistorySig(formGroup) as any;

        expect(operationHistory).toMatchObject(sampleWithNewData);
      });

      it('should return NewOperationHistorySig for empty OperationHistorySig initial value', () => {
        const formGroup = service.createOperationHistorySigFormGroup();

        const operationHistory = service.getOperationHistorySig(formGroup) as any;

        expect(operationHistory).toMatchObject({});
      });

      it('should return IOperationHistorySig', () => {
        const formGroup = service.createOperationHistorySigFormGroup(sampleWithRequiredData);

        const operationHistory = service.getOperationHistorySig(formGroup) as any;

        expect(operationHistory).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IOperationHistorySig should not enable operationHistoryId FormControl', () => {
        const formGroup = service.createOperationHistorySigFormGroup();
        expect(formGroup.controls.operationHistoryId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.operationHistoryId.disabled).toBe(true);
      });

      it('passing NewOperationHistorySig should disable operationHistoryId FormControl', () => {
        const formGroup = service.createOperationHistorySigFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.operationHistoryId.disabled).toBe(true);

        service.resetForm(formGroup, { operationHistoryId: null });

        expect(formGroup.controls.operationHistoryId.disabled).toBe(true);
      });
    });
  });
});
