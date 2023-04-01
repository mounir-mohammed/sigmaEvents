import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICategorySig, NewCategorySig } from '../category-sig.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { categoryId: unknown }> = Partial<Omit<T, 'categoryId'>> & { categoryId: T['categoryId'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICategorySig for edit and NewCategorySigFormGroupInput for create.
 */
type CategorySigFormGroupInput = ICategorySig | PartialWithRequiredKeyOf<NewCategorySig>;

type CategorySigFormDefaults = Pick<NewCategorySig, 'categoryId' | 'categoryStat'>;

type CategorySigFormGroupContent = {
  categoryId: FormControl<ICategorySig['categoryId'] | NewCategorySig['categoryId']>;
  categoryName: FormControl<ICategorySig['categoryName']>;
  categoryAbreviation: FormControl<ICategorySig['categoryAbreviation']>;
  categoryColor: FormControl<ICategorySig['categoryColor']>;
  categoryDescription: FormControl<ICategorySig['categoryDescription']>;
  categoryLogo: FormControl<ICategorySig['categoryLogo']>;
  categoryLogoContentType: FormControl<ICategorySig['categoryLogoContentType']>;
  categoryParams: FormControl<ICategorySig['categoryParams']>;
  categoryAttributs: FormControl<ICategorySig['categoryAttributs']>;
  categoryStat: FormControl<ICategorySig['categoryStat']>;
  printingModel: FormControl<ICategorySig['printingModel']>;
  event: FormControl<ICategorySig['event']>;
};

export type CategorySigFormGroup = FormGroup<CategorySigFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CategorySigFormService {
  createCategorySigFormGroup(category: CategorySigFormGroupInput = { categoryId: null }): CategorySigFormGroup {
    const categoryRawValue = {
      ...this.getFormDefaults(),
      ...category,
    };
    return new FormGroup<CategorySigFormGroupContent>({
      categoryId: new FormControl(
        { value: categoryRawValue.categoryId, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      categoryName: new FormControl(categoryRawValue.categoryName, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      categoryAbreviation: new FormControl(categoryRawValue.categoryAbreviation, {
        validators: [Validators.maxLength(10)],
      }),
      categoryColor: new FormControl(categoryRawValue.categoryColor, {
        validators: [Validators.maxLength(100)],
      }),
      categoryDescription: new FormControl(categoryRawValue.categoryDescription, {
        validators: [Validators.maxLength(200)],
      }),
      categoryLogo: new FormControl(categoryRawValue.categoryLogo),
      categoryLogoContentType: new FormControl(categoryRawValue.categoryLogoContentType),
      categoryParams: new FormControl(categoryRawValue.categoryParams),
      categoryAttributs: new FormControl(categoryRawValue.categoryAttributs),
      categoryStat: new FormControl(categoryRawValue.categoryStat),
      printingModel: new FormControl(categoryRawValue.printingModel),
      event: new FormControl(categoryRawValue.event),
    });
  }

  getCategorySig(form: CategorySigFormGroup): ICategorySig | NewCategorySig {
    return form.getRawValue() as ICategorySig | NewCategorySig;
  }

  resetForm(form: CategorySigFormGroup, category: CategorySigFormGroupInput): void {
    const categoryRawValue = { ...this.getFormDefaults(), ...category };
    form.reset(
      {
        ...categoryRawValue,
        categoryId: { value: categoryRawValue.categoryId, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CategorySigFormDefaults {
    return {
      categoryId: null,
      categoryStat: false,
    };
  }
}
