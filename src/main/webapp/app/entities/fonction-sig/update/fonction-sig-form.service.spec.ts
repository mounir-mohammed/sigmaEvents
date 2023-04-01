import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../fonction-sig.test-samples';

import { FonctionSigFormService } from './fonction-sig-form.service';

describe('FonctionSig Form Service', () => {
  let service: FonctionSigFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FonctionSigFormService);
  });

  describe('Service methods', () => {
    describe('createFonctionSigFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFonctionSigFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            fonctionId: expect.any(Object),
            fonctionName: expect.any(Object),
            fonctionAbreviation: expect.any(Object),
            fonctionColor: expect.any(Object),
            fonctionDescription: expect.any(Object),
            fonctionLogo: expect.any(Object),
            fonctionParams: expect.any(Object),
            fonctionAttributs: expect.any(Object),
            fonctionStat: expect.any(Object),
            areas: expect.any(Object),
            category: expect.any(Object),
            event: expect.any(Object),
          })
        );
      });

      it('passing IFonctionSig should create a new form with FormGroup', () => {
        const formGroup = service.createFonctionSigFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            fonctionId: expect.any(Object),
            fonctionName: expect.any(Object),
            fonctionAbreviation: expect.any(Object),
            fonctionColor: expect.any(Object),
            fonctionDescription: expect.any(Object),
            fonctionLogo: expect.any(Object),
            fonctionParams: expect.any(Object),
            fonctionAttributs: expect.any(Object),
            fonctionStat: expect.any(Object),
            areas: expect.any(Object),
            category: expect.any(Object),
            event: expect.any(Object),
          })
        );
      });
    });

    describe('getFonctionSig', () => {
      it('should return NewFonctionSig for default FonctionSig initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createFonctionSigFormGroup(sampleWithNewData);

        const fonction = service.getFonctionSig(formGroup) as any;

        expect(fonction).toMatchObject(sampleWithNewData);
      });

      it('should return NewFonctionSig for empty FonctionSig initial value', () => {
        const formGroup = service.createFonctionSigFormGroup();

        const fonction = service.getFonctionSig(formGroup) as any;

        expect(fonction).toMatchObject({});
      });

      it('should return IFonctionSig', () => {
        const formGroup = service.createFonctionSigFormGroup(sampleWithRequiredData);

        const fonction = service.getFonctionSig(formGroup) as any;

        expect(fonction).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFonctionSig should not enable fonctionId FormControl', () => {
        const formGroup = service.createFonctionSigFormGroup();
        expect(formGroup.controls.fonctionId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.fonctionId.disabled).toBe(true);
      });

      it('passing NewFonctionSig should disable fonctionId FormControl', () => {
        const formGroup = service.createFonctionSigFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.fonctionId.disabled).toBe(true);

        service.resetForm(formGroup, { fonctionId: null });

        expect(formGroup.controls.fonctionId.disabled).toBe(true);
      });
    });
  });
});
