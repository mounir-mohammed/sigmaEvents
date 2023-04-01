import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../code-type-sig.test-samples';

import { CodeTypeSigFormService } from './code-type-sig-form.service';

describe('CodeTypeSig Form Service', () => {
  let service: CodeTypeSigFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CodeTypeSigFormService);
  });

  describe('Service methods', () => {
    describe('createCodeTypeSigFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCodeTypeSigFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            codeTypeId: expect.any(Object),
            codeTypeValue: expect.any(Object),
            codeTypeDescription: expect.any(Object),
            codeTypeParams: expect.any(Object),
            codeTypeAttributs: expect.any(Object),
            codeTypeStat: expect.any(Object),
          })
        );
      });

      it('passing ICodeTypeSig should create a new form with FormGroup', () => {
        const formGroup = service.createCodeTypeSigFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            codeTypeId: expect.any(Object),
            codeTypeValue: expect.any(Object),
            codeTypeDescription: expect.any(Object),
            codeTypeParams: expect.any(Object),
            codeTypeAttributs: expect.any(Object),
            codeTypeStat: expect.any(Object),
          })
        );
      });
    });

    describe('getCodeTypeSig', () => {
      it('should return NewCodeTypeSig for default CodeTypeSig initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCodeTypeSigFormGroup(sampleWithNewData);

        const codeType = service.getCodeTypeSig(formGroup) as any;

        expect(codeType).toMatchObject(sampleWithNewData);
      });

      it('should return NewCodeTypeSig for empty CodeTypeSig initial value', () => {
        const formGroup = service.createCodeTypeSigFormGroup();

        const codeType = service.getCodeTypeSig(formGroup) as any;

        expect(codeType).toMatchObject({});
      });

      it('should return ICodeTypeSig', () => {
        const formGroup = service.createCodeTypeSigFormGroup(sampleWithRequiredData);

        const codeType = service.getCodeTypeSig(formGroup) as any;

        expect(codeType).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICodeTypeSig should not enable codeTypeId FormControl', () => {
        const formGroup = service.createCodeTypeSigFormGroup();
        expect(formGroup.controls.codeTypeId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.codeTypeId.disabled).toBe(true);
      });

      it('passing NewCodeTypeSig should disable codeTypeId FormControl', () => {
        const formGroup = service.createCodeTypeSigFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.codeTypeId.disabled).toBe(true);

        service.resetForm(formGroup, { codeTypeId: null });

        expect(formGroup.controls.codeTypeId.disabled).toBe(true);
      });
    });
  });
});
