import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../printing-server-sig.test-samples';

import { PrintingServerSigFormService } from './printing-server-sig-form.service';

describe('PrintingServerSig Form Service', () => {
  let service: PrintingServerSigFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PrintingServerSigFormService);
  });

  describe('Service methods', () => {
    describe('createPrintingServerSigFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPrintingServerSigFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            printingServerId: expect.any(Object),
            printingServerName: expect.any(Object),
            printingServerDescription: expect.any(Object),
            printingServerHost: expect.any(Object),
            printingServerPort: expect.any(Object),
            printingServerDns: expect.any(Object),
            printingServerProxy: expect.any(Object),
            printingServerParam1: expect.any(Object),
            printingServerParam2: expect.any(Object),
            printingServerParam3: expect.any(Object),
            printingServerStat: expect.any(Object),
            printingServerParams: expect.any(Object),
            printingServerAttributs: expect.any(Object),
            event: expect.any(Object),
          })
        );
      });

      it('passing IPrintingServerSig should create a new form with FormGroup', () => {
        const formGroup = service.createPrintingServerSigFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            printingServerId: expect.any(Object),
            printingServerName: expect.any(Object),
            printingServerDescription: expect.any(Object),
            printingServerHost: expect.any(Object),
            printingServerPort: expect.any(Object),
            printingServerDns: expect.any(Object),
            printingServerProxy: expect.any(Object),
            printingServerParam1: expect.any(Object),
            printingServerParam2: expect.any(Object),
            printingServerParam3: expect.any(Object),
            printingServerStat: expect.any(Object),
            printingServerParams: expect.any(Object),
            printingServerAttributs: expect.any(Object),
            event: expect.any(Object),
          })
        );
      });
    });

    describe('getPrintingServerSig', () => {
      it('should return NewPrintingServerSig for default PrintingServerSig initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPrintingServerSigFormGroup(sampleWithNewData);

        const printingServer = service.getPrintingServerSig(formGroup) as any;

        expect(printingServer).toMatchObject(sampleWithNewData);
      });

      it('should return NewPrintingServerSig for empty PrintingServerSig initial value', () => {
        const formGroup = service.createPrintingServerSigFormGroup();

        const printingServer = service.getPrintingServerSig(formGroup) as any;

        expect(printingServer).toMatchObject({});
      });

      it('should return IPrintingServerSig', () => {
        const formGroup = service.createPrintingServerSigFormGroup(sampleWithRequiredData);

        const printingServer = service.getPrintingServerSig(formGroup) as any;

        expect(printingServer).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPrintingServerSig should not enable printingServerId FormControl', () => {
        const formGroup = service.createPrintingServerSigFormGroup();
        expect(formGroup.controls.printingServerId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.printingServerId.disabled).toBe(true);
      });

      it('passing NewPrintingServerSig should disable printingServerId FormControl', () => {
        const formGroup = service.createPrintingServerSigFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.printingServerId.disabled).toBe(true);

        service.resetForm(formGroup, { printingServerId: null });

        expect(formGroup.controls.printingServerId.disabled).toBe(true);
      });
    });
  });
});
