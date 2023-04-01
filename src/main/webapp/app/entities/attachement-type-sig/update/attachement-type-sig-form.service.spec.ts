import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../attachement-type-sig.test-samples';

import { AttachementTypeSigFormService } from './attachement-type-sig-form.service';

describe('AttachementTypeSig Form Service', () => {
  let service: AttachementTypeSigFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AttachementTypeSigFormService);
  });

  describe('Service methods', () => {
    describe('createAttachementTypeSigFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAttachementTypeSigFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            attachementTypeId: expect.any(Object),
            attachementTypeName: expect.any(Object),
            attachementTypeDescription: expect.any(Object),
            attachementTypeParams: expect.any(Object),
            attachementTypeAttributs: expect.any(Object),
            attachementTypeStat: expect.any(Object),
          })
        );
      });

      it('passing IAttachementTypeSig should create a new form with FormGroup', () => {
        const formGroup = service.createAttachementTypeSigFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            attachementTypeId: expect.any(Object),
            attachementTypeName: expect.any(Object),
            attachementTypeDescription: expect.any(Object),
            attachementTypeParams: expect.any(Object),
            attachementTypeAttributs: expect.any(Object),
            attachementTypeStat: expect.any(Object),
          })
        );
      });
    });

    describe('getAttachementTypeSig', () => {
      it('should return NewAttachementTypeSig for default AttachementTypeSig initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createAttachementTypeSigFormGroup(sampleWithNewData);

        const attachementType = service.getAttachementTypeSig(formGroup) as any;

        expect(attachementType).toMatchObject(sampleWithNewData);
      });

      it('should return NewAttachementTypeSig for empty AttachementTypeSig initial value', () => {
        const formGroup = service.createAttachementTypeSigFormGroup();

        const attachementType = service.getAttachementTypeSig(formGroup) as any;

        expect(attachementType).toMatchObject({});
      });

      it('should return IAttachementTypeSig', () => {
        const formGroup = service.createAttachementTypeSigFormGroup(sampleWithRequiredData);

        const attachementType = service.getAttachementTypeSig(formGroup) as any;

        expect(attachementType).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAttachementTypeSig should not enable attachementTypeId FormControl', () => {
        const formGroup = service.createAttachementTypeSigFormGroup();
        expect(formGroup.controls.attachementTypeId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.attachementTypeId.disabled).toBe(true);
      });

      it('passing NewAttachementTypeSig should disable attachementTypeId FormControl', () => {
        const formGroup = service.createAttachementTypeSigFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.attachementTypeId.disabled).toBe(true);

        service.resetForm(formGroup, { attachementTypeId: null });

        expect(formGroup.controls.attachementTypeId.disabled).toBe(true);
      });
    });
  });
});
