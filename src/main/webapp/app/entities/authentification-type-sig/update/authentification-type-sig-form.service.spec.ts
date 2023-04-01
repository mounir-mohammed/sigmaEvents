import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../authentification-type-sig.test-samples';

import { AuthentificationTypeSigFormService } from './authentification-type-sig-form.service';

describe('AuthentificationTypeSig Form Service', () => {
  let service: AuthentificationTypeSigFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AuthentificationTypeSigFormService);
  });

  describe('Service methods', () => {
    describe('createAuthentificationTypeSigFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAuthentificationTypeSigFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            authentificationTypeId: expect.any(Object),
            authentificationTypeValue: expect.any(Object),
            authentificationTypeDescription: expect.any(Object),
            authentificationTypeParams: expect.any(Object),
            authentificationTypeAttributs: expect.any(Object),
            authentificationTypeStat: expect.any(Object),
          })
        );
      });

      it('passing IAuthentificationTypeSig should create a new form with FormGroup', () => {
        const formGroup = service.createAuthentificationTypeSigFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            authentificationTypeId: expect.any(Object),
            authentificationTypeValue: expect.any(Object),
            authentificationTypeDescription: expect.any(Object),
            authentificationTypeParams: expect.any(Object),
            authentificationTypeAttributs: expect.any(Object),
            authentificationTypeStat: expect.any(Object),
          })
        );
      });
    });

    describe('getAuthentificationTypeSig', () => {
      it('should return NewAuthentificationTypeSig for default AuthentificationTypeSig initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createAuthentificationTypeSigFormGroup(sampleWithNewData);

        const authentificationType = service.getAuthentificationTypeSig(formGroup) as any;

        expect(authentificationType).toMatchObject(sampleWithNewData);
      });

      it('should return NewAuthentificationTypeSig for empty AuthentificationTypeSig initial value', () => {
        const formGroup = service.createAuthentificationTypeSigFormGroup();

        const authentificationType = service.getAuthentificationTypeSig(formGroup) as any;

        expect(authentificationType).toMatchObject({});
      });

      it('should return IAuthentificationTypeSig', () => {
        const formGroup = service.createAuthentificationTypeSigFormGroup(sampleWithRequiredData);

        const authentificationType = service.getAuthentificationTypeSig(formGroup) as any;

        expect(authentificationType).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAuthentificationTypeSig should not enable authentificationTypeId FormControl', () => {
        const formGroup = service.createAuthentificationTypeSigFormGroup();
        expect(formGroup.controls.authentificationTypeId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.authentificationTypeId.disabled).toBe(true);
      });

      it('passing NewAuthentificationTypeSig should disable authentificationTypeId FormControl', () => {
        const formGroup = service.createAuthentificationTypeSigFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.authentificationTypeId.disabled).toBe(true);

        service.resetForm(formGroup, { authentificationTypeId: null });

        expect(formGroup.controls.authentificationTypeId.disabled).toBe(true);
      });
    });
  });
});
