import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../printing-model-sig.test-samples';

import { PrintingModelSigFormService } from './printing-model-sig-form.service';

describe('PrintingModelSig Form Service', () => {
  let service: PrintingModelSigFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PrintingModelSigFormService);
  });

  describe('Service methods', () => {
    describe('createPrintingModelSigFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPrintingModelSigFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            printingModelId: expect.any(Object),
            printingModelName: expect.any(Object),
            printingModelFile: expect.any(Object),
            printingModelPath: expect.any(Object),
            printingModelDescription: expect.any(Object),
            printingModelData: expect.any(Object),
            printingModelParams: expect.any(Object),
            printingModelAttributs: expect.any(Object),
            printingModelStat: expect.any(Object),
            event: expect.any(Object),
          })
        );
      });

      it('passing IPrintingModelSig should create a new form with FormGroup', () => {
        const formGroup = service.createPrintingModelSigFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            printingModelId: expect.any(Object),
            printingModelName: expect.any(Object),
            printingModelFile: expect.any(Object),
            printingModelPath: expect.any(Object),
            printingModelDescription: expect.any(Object),
            printingModelData: expect.any(Object),
            printingModelParams: expect.any(Object),
            printingModelAttributs: expect.any(Object),
            printingModelStat: expect.any(Object),
            event: expect.any(Object),
          })
        );
      });
    });

    describe('getPrintingModelSig', () => {
      it('should return NewPrintingModelSig for default PrintingModelSig initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPrintingModelSigFormGroup(sampleWithNewData);

        const printingModel = service.getPrintingModelSig(formGroup) as any;

        expect(printingModel).toMatchObject(sampleWithNewData);
      });

      it('should return NewPrintingModelSig for empty PrintingModelSig initial value', () => {
        const formGroup = service.createPrintingModelSigFormGroup();

        const printingModel = service.getPrintingModelSig(formGroup) as any;

        expect(printingModel).toMatchObject({});
      });

      it('should return IPrintingModelSig', () => {
        const formGroup = service.createPrintingModelSigFormGroup(sampleWithRequiredData);

        const printingModel = service.getPrintingModelSig(formGroup) as any;

        expect(printingModel).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPrintingModelSig should not enable printingModelId FormControl', () => {
        const formGroup = service.createPrintingModelSigFormGroup();
        expect(formGroup.controls.printingModelId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.printingModelId.disabled).toBe(true);
      });

      it('passing NewPrintingModelSig should disable printingModelId FormControl', () => {
        const formGroup = service.createPrintingModelSigFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.printingModelId.disabled).toBe(true);

        service.resetForm(formGroup, { printingModelId: null });

        expect(formGroup.controls.printingModelId.disabled).toBe(true);
      });
    });
  });
});
