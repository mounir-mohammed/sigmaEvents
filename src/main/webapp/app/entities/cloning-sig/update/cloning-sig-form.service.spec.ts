import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../cloning-sig.test-samples';

import { CloningSigFormService } from './cloning-sig-form.service';

describe('CloningSig Form Service', () => {
  let service: CloningSigFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CloningSigFormService);
  });

  describe('Service methods', () => {
    describe('createCloningSigFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCloningSigFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            cloningId: expect.any(Object),
            cloningDescription: expect.any(Object),
            cloningOldEventId: expect.any(Object),
            cloningNewEventId: expect.any(Object),
            cloningUserId: expect.any(Object),
            cloningDate: expect.any(Object),
            clonedEntitys: expect.any(Object),
            clonedParams: expect.any(Object),
            clonedAttributs: expect.any(Object),
            clonedStat: expect.any(Object),
          })
        );
      });

      it('passing ICloningSig should create a new form with FormGroup', () => {
        const formGroup = service.createCloningSigFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            cloningId: expect.any(Object),
            cloningDescription: expect.any(Object),
            cloningOldEventId: expect.any(Object),
            cloningNewEventId: expect.any(Object),
            cloningUserId: expect.any(Object),
            cloningDate: expect.any(Object),
            clonedEntitys: expect.any(Object),
            clonedParams: expect.any(Object),
            clonedAttributs: expect.any(Object),
            clonedStat: expect.any(Object),
          })
        );
      });
    });

    describe('getCloningSig', () => {
      it('should return NewCloningSig for default CloningSig initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCloningSigFormGroup(sampleWithNewData);

        const cloning = service.getCloningSig(formGroup) as any;

        expect(cloning).toMatchObject(sampleWithNewData);
      });

      it('should return NewCloningSig for empty CloningSig initial value', () => {
        const formGroup = service.createCloningSigFormGroup();

        const cloning = service.getCloningSig(formGroup) as any;

        expect(cloning).toMatchObject({});
      });

      it('should return ICloningSig', () => {
        const formGroup = service.createCloningSigFormGroup(sampleWithRequiredData);

        const cloning = service.getCloningSig(formGroup) as any;

        expect(cloning).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICloningSig should not enable cloningId FormControl', () => {
        const formGroup = service.createCloningSigFormGroup();
        expect(formGroup.controls.cloningId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.cloningId.disabled).toBe(true);
      });

      it('passing NewCloningSig should disable cloningId FormControl', () => {
        const formGroup = service.createCloningSigFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.cloningId.disabled).toBe(true);

        service.resetForm(formGroup, { cloningId: null });

        expect(formGroup.controls.cloningId.disabled).toBe(true);
      });
    });
  });
});
