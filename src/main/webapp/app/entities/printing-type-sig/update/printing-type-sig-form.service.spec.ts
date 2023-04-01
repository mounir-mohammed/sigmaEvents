import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../printing-type-sig.test-samples';

import { PrintingTypeSigFormService } from './printing-type-sig-form.service';

describe('PrintingTypeSig Form Service', () => {
  let service: PrintingTypeSigFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PrintingTypeSigFormService);
  });

  describe('Service methods', () => {
    describe('createPrintingTypeSigFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPrintingTypeSigFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            printingTypeId: expect.any(Object),
            printingTypeValue: expect.any(Object),
            printingTypeDescription: expect.any(Object),
            printingTypeParams: expect.any(Object),
            printingTypeAttributs: expect.any(Object),
            printingTypeStat: expect.any(Object),
          })
        );
      });

      it('passing IPrintingTypeSig should create a new form with FormGroup', () => {
        const formGroup = service.createPrintingTypeSigFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            printingTypeId: expect.any(Object),
            printingTypeValue: expect.any(Object),
            printingTypeDescription: expect.any(Object),
            printingTypeParams: expect.any(Object),
            printingTypeAttributs: expect.any(Object),
            printingTypeStat: expect.any(Object),
          })
        );
      });
    });

    describe('getPrintingTypeSig', () => {
      it('should return NewPrintingTypeSig for default PrintingTypeSig initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPrintingTypeSigFormGroup(sampleWithNewData);

        const printingType = service.getPrintingTypeSig(formGroup) as any;

        expect(printingType).toMatchObject(sampleWithNewData);
      });

      it('should return NewPrintingTypeSig for empty PrintingTypeSig initial value', () => {
        const formGroup = service.createPrintingTypeSigFormGroup();

        const printingType = service.getPrintingTypeSig(formGroup) as any;

        expect(printingType).toMatchObject({});
      });

      it('should return IPrintingTypeSig', () => {
        const formGroup = service.createPrintingTypeSigFormGroup(sampleWithRequiredData);

        const printingType = service.getPrintingTypeSig(formGroup) as any;

        expect(printingType).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPrintingTypeSig should not enable printingTypeId FormControl', () => {
        const formGroup = service.createPrintingTypeSigFormGroup();
        expect(formGroup.controls.printingTypeId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.printingTypeId.disabled).toBe(true);
      });

      it('passing NewPrintingTypeSig should disable printingTypeId FormControl', () => {
        const formGroup = service.createPrintingTypeSigFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.printingTypeId.disabled).toBe(true);

        service.resetForm(formGroup, { printingTypeId: null });

        expect(formGroup.controls.printingTypeId.disabled).toBe(true);
      });
    });
  });
});
