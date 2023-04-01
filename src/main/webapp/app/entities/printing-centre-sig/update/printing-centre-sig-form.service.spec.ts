import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../printing-centre-sig.test-samples';

import { PrintingCentreSigFormService } from './printing-centre-sig-form.service';

describe('PrintingCentreSig Form Service', () => {
  let service: PrintingCentreSigFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PrintingCentreSigFormService);
  });

  describe('Service methods', () => {
    describe('createPrintingCentreSigFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPrintingCentreSigFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            printingCentreId: expect.any(Object),
            printingCentreDescription: expect.any(Object),
            printingCentreName: expect.any(Object),
            printingCentreLogo: expect.any(Object),
            printingCentreAdresse: expect.any(Object),
            printingCentreEmail: expect.any(Object),
            printingCentreTel: expect.any(Object),
            printingCentreFax: expect.any(Object),
            printingCentreResponsableName: expect.any(Object),
            printingParams: expect.any(Object),
            printingAttributs: expect.any(Object),
            printingCentreStat: expect.any(Object),
            city: expect.any(Object),
            country: expect.any(Object),
            organiz: expect.any(Object),
            printingType: expect.any(Object),
            printingServer: expect.any(Object),
            printingModel: expect.any(Object),
            language: expect.any(Object),
            event: expect.any(Object),
          })
        );
      });

      it('passing IPrintingCentreSig should create a new form with FormGroup', () => {
        const formGroup = service.createPrintingCentreSigFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            printingCentreId: expect.any(Object),
            printingCentreDescription: expect.any(Object),
            printingCentreName: expect.any(Object),
            printingCentreLogo: expect.any(Object),
            printingCentreAdresse: expect.any(Object),
            printingCentreEmail: expect.any(Object),
            printingCentreTel: expect.any(Object),
            printingCentreFax: expect.any(Object),
            printingCentreResponsableName: expect.any(Object),
            printingParams: expect.any(Object),
            printingAttributs: expect.any(Object),
            printingCentreStat: expect.any(Object),
            city: expect.any(Object),
            country: expect.any(Object),
            organiz: expect.any(Object),
            printingType: expect.any(Object),
            printingServer: expect.any(Object),
            printingModel: expect.any(Object),
            language: expect.any(Object),
            event: expect.any(Object),
          })
        );
      });
    });

    describe('getPrintingCentreSig', () => {
      it('should return NewPrintingCentreSig for default PrintingCentreSig initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPrintingCentreSigFormGroup(sampleWithNewData);

        const printingCentre = service.getPrintingCentreSig(formGroup) as any;

        expect(printingCentre).toMatchObject(sampleWithNewData);
      });

      it('should return NewPrintingCentreSig for empty PrintingCentreSig initial value', () => {
        const formGroup = service.createPrintingCentreSigFormGroup();

        const printingCentre = service.getPrintingCentreSig(formGroup) as any;

        expect(printingCentre).toMatchObject({});
      });

      it('should return IPrintingCentreSig', () => {
        const formGroup = service.createPrintingCentreSigFormGroup(sampleWithRequiredData);

        const printingCentre = service.getPrintingCentreSig(formGroup) as any;

        expect(printingCentre).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPrintingCentreSig should not enable printingCentreId FormControl', () => {
        const formGroup = service.createPrintingCentreSigFormGroup();
        expect(formGroup.controls.printingCentreId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.printingCentreId.disabled).toBe(true);
      });

      it('passing NewPrintingCentreSig should disable printingCentreId FormControl', () => {
        const formGroup = service.createPrintingCentreSigFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.printingCentreId.disabled).toBe(true);

        service.resetForm(formGroup, { printingCentreId: null });

        expect(formGroup.controls.printingCentreId.disabled).toBe(true);
      });
    });
  });
});
