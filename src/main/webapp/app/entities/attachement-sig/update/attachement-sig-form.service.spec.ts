import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../attachement-sig.test-samples';

import { AttachementSigFormService } from './attachement-sig-form.service';

describe('AttachementSig Form Service', () => {
  let service: AttachementSigFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AttachementSigFormService);
  });

  describe('Service methods', () => {
    describe('createAttachementSigFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAttachementSigFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            attachementId: expect.any(Object),
            attachementName: expect.any(Object),
            attachementPath: expect.any(Object),
            attachementBlob: expect.any(Object),
            attachementDescription: expect.any(Object),
            attachementPhoto: expect.any(Object),
            attachementParams: expect.any(Object),
            attachementAttributs: expect.any(Object),
            attachementStat: expect.any(Object),
            attachementType: expect.any(Object),
            event: expect.any(Object),
          })
        );
      });

      it('passing IAttachementSig should create a new form with FormGroup', () => {
        const formGroup = service.createAttachementSigFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            attachementId: expect.any(Object),
            attachementName: expect.any(Object),
            attachementPath: expect.any(Object),
            attachementBlob: expect.any(Object),
            attachementDescription: expect.any(Object),
            attachementPhoto: expect.any(Object),
            attachementParams: expect.any(Object),
            attachementAttributs: expect.any(Object),
            attachementStat: expect.any(Object),
            attachementType: expect.any(Object),
            event: expect.any(Object),
          })
        );
      });
    });

    describe('getAttachementSig', () => {
      it('should return NewAttachementSig for default AttachementSig initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createAttachementSigFormGroup(sampleWithNewData);

        const attachement = service.getAttachementSig(formGroup) as any;

        expect(attachement).toMatchObject(sampleWithNewData);
      });

      it('should return NewAttachementSig for empty AttachementSig initial value', () => {
        const formGroup = service.createAttachementSigFormGroup();

        const attachement = service.getAttachementSig(formGroup) as any;

        expect(attachement).toMatchObject({});
      });

      it('should return IAttachementSig', () => {
        const formGroup = service.createAttachementSigFormGroup(sampleWithRequiredData);

        const attachement = service.getAttachementSig(formGroup) as any;

        expect(attachement).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAttachementSig should not enable attachementId FormControl', () => {
        const formGroup = service.createAttachementSigFormGroup();
        expect(formGroup.controls.attachementId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.attachementId.disabled).toBe(true);
      });

      it('passing NewAttachementSig should disable attachementId FormControl', () => {
        const formGroup = service.createAttachementSigFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.attachementId.disabled).toBe(true);

        service.resetForm(formGroup, { attachementId: null });

        expect(formGroup.controls.attachementId.disabled).toBe(true);
      });
    });
  });
});
