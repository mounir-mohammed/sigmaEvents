import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../code-sig.test-samples';

import { CodeSigFormService } from './code-sig-form.service';

describe('CodeSig Form Service', () => {
  let service: CodeSigFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CodeSigFormService);
  });

  describe('Service methods', () => {
    describe('createCodeSigFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCodeSigFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            codeId: expect.any(Object),
            codeForEntity: expect.any(Object),
            codeEntityValue: expect.any(Object),
            codeValue: expect.any(Object),
            codeUsed: expect.any(Object),
            codeParams: expect.any(Object),
            codeAttributs: expect.any(Object),
            codeStat: expect.any(Object),
            codeType: expect.any(Object),
            event: expect.any(Object),
          })
        );
      });

      it('passing ICodeSig should create a new form with FormGroup', () => {
        const formGroup = service.createCodeSigFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            codeId: expect.any(Object),
            codeForEntity: expect.any(Object),
            codeEntityValue: expect.any(Object),
            codeValue: expect.any(Object),
            codeUsed: expect.any(Object),
            codeParams: expect.any(Object),
            codeAttributs: expect.any(Object),
            codeStat: expect.any(Object),
            codeType: expect.any(Object),
            event: expect.any(Object),
          })
        );
      });
    });

    describe('getCodeSig', () => {
      it('should return NewCodeSig for default CodeSig initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCodeSigFormGroup(sampleWithNewData);

        const code = service.getCodeSig(formGroup) as any;

        expect(code).toMatchObject(sampleWithNewData);
      });

      it('should return NewCodeSig for empty CodeSig initial value', () => {
        const formGroup = service.createCodeSigFormGroup();

        const code = service.getCodeSig(formGroup) as any;

        expect(code).toMatchObject({});
      });

      it('should return ICodeSig', () => {
        const formGroup = service.createCodeSigFormGroup(sampleWithRequiredData);

        const code = service.getCodeSig(formGroup) as any;

        expect(code).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICodeSig should not enable codeId FormControl', () => {
        const formGroup = service.createCodeSigFormGroup();
        expect(formGroup.controls.codeId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.codeId.disabled).toBe(true);
      });

      it('passing NewCodeSig should disable codeId FormControl', () => {
        const formGroup = service.createCodeSigFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.codeId.disabled).toBe(true);

        service.resetForm(formGroup, { codeId: null });

        expect(formGroup.controls.codeId.disabled).toBe(true);
      });
    });
  });
});
