import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../category-sig.test-samples';

import { CategorySigFormService } from './category-sig-form.service';

describe('CategorySig Form Service', () => {
  let service: CategorySigFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CategorySigFormService);
  });

  describe('Service methods', () => {
    describe('createCategorySigFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCategorySigFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            categoryId: expect.any(Object),
            categoryName: expect.any(Object),
            categoryAbreviation: expect.any(Object),
            categoryColor: expect.any(Object),
            categoryDescription: expect.any(Object),
            categoryLogo: expect.any(Object),
            categoryParams: expect.any(Object),
            categoryAttributs: expect.any(Object),
            categoryStat: expect.any(Object),
            printingModel: expect.any(Object),
            event: expect.any(Object),
          })
        );
      });

      it('passing ICategorySig should create a new form with FormGroup', () => {
        const formGroup = service.createCategorySigFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            categoryId: expect.any(Object),
            categoryName: expect.any(Object),
            categoryAbreviation: expect.any(Object),
            categoryColor: expect.any(Object),
            categoryDescription: expect.any(Object),
            categoryLogo: expect.any(Object),
            categoryParams: expect.any(Object),
            categoryAttributs: expect.any(Object),
            categoryStat: expect.any(Object),
            printingModel: expect.any(Object),
            event: expect.any(Object),
          })
        );
      });
    });

    describe('getCategorySig', () => {
      it('should return NewCategorySig for default CategorySig initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCategorySigFormGroup(sampleWithNewData);

        const category = service.getCategorySig(formGroup) as any;

        expect(category).toMatchObject(sampleWithNewData);
      });

      it('should return NewCategorySig for empty CategorySig initial value', () => {
        const formGroup = service.createCategorySigFormGroup();

        const category = service.getCategorySig(formGroup) as any;

        expect(category).toMatchObject({});
      });

      it('should return ICategorySig', () => {
        const formGroup = service.createCategorySigFormGroup(sampleWithRequiredData);

        const category = service.getCategorySig(formGroup) as any;

        expect(category).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICategorySig should not enable categoryId FormControl', () => {
        const formGroup = service.createCategorySigFormGroup();
        expect(formGroup.controls.categoryId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.categoryId.disabled).toBe(true);
      });

      it('passing NewCategorySig should disable categoryId FormControl', () => {
        const formGroup = service.createCategorySigFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.categoryId.disabled).toBe(true);

        service.resetForm(formGroup, { categoryId: null });

        expect(formGroup.controls.categoryId.disabled).toBe(true);
      });
    });
  });
});
