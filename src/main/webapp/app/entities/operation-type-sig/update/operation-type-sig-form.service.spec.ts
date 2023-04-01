import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../operation-type-sig.test-samples';

import { OperationTypeSigFormService } from './operation-type-sig-form.service';

describe('OperationTypeSig Form Service', () => {
  let service: OperationTypeSigFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OperationTypeSigFormService);
  });

  describe('Service methods', () => {
    describe('createOperationTypeSigFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createOperationTypeSigFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            operationTypeId: expect.any(Object),
            operationTypeValue: expect.any(Object),
            operationTypeDescription: expect.any(Object),
            operationTypeParams: expect.any(Object),
            operationTypeAttributs: expect.any(Object),
            operationTypeStat: expect.any(Object),
          })
        );
      });

      it('passing IOperationTypeSig should create a new form with FormGroup', () => {
        const formGroup = service.createOperationTypeSigFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            operationTypeId: expect.any(Object),
            operationTypeValue: expect.any(Object),
            operationTypeDescription: expect.any(Object),
            operationTypeParams: expect.any(Object),
            operationTypeAttributs: expect.any(Object),
            operationTypeStat: expect.any(Object),
          })
        );
      });
    });

    describe('getOperationTypeSig', () => {
      it('should return NewOperationTypeSig for default OperationTypeSig initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createOperationTypeSigFormGroup(sampleWithNewData);

        const operationType = service.getOperationTypeSig(formGroup) as any;

        expect(operationType).toMatchObject(sampleWithNewData);
      });

      it('should return NewOperationTypeSig for empty OperationTypeSig initial value', () => {
        const formGroup = service.createOperationTypeSigFormGroup();

        const operationType = service.getOperationTypeSig(formGroup) as any;

        expect(operationType).toMatchObject({});
      });

      it('should return IOperationTypeSig', () => {
        const formGroup = service.createOperationTypeSigFormGroup(sampleWithRequiredData);

        const operationType = service.getOperationTypeSig(formGroup) as any;

        expect(operationType).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IOperationTypeSig should not enable operationTypeId FormControl', () => {
        const formGroup = service.createOperationTypeSigFormGroup();
        expect(formGroup.controls.operationTypeId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.operationTypeId.disabled).toBe(true);
      });

      it('passing NewOperationTypeSig should disable operationTypeId FormControl', () => {
        const formGroup = service.createOperationTypeSigFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.operationTypeId.disabled).toBe(true);

        service.resetForm(formGroup, { operationTypeId: null });

        expect(formGroup.controls.operationTypeId.disabled).toBe(true);
      });
    });
  });
});
