import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPrintingTypeSig, NewPrintingTypeSig } from '../printing-type-sig.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { printingTypeId: unknown }> = Partial<Omit<T, 'printingTypeId'>> & {
  printingTypeId: T['printingTypeId'];
};

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPrintingTypeSig for edit and NewPrintingTypeSigFormGroupInput for create.
 */
type PrintingTypeSigFormGroupInput = IPrintingTypeSig | PartialWithRequiredKeyOf<NewPrintingTypeSig>;

type PrintingTypeSigFormDefaults = Pick<NewPrintingTypeSig, 'printingTypeId' | 'printingTypeStat'>;

type PrintingTypeSigFormGroupContent = {
  printingTypeId: FormControl<IPrintingTypeSig['printingTypeId'] | NewPrintingTypeSig['printingTypeId']>;
  printingTypeValue: FormControl<IPrintingTypeSig['printingTypeValue']>;
  printingTypeDescription: FormControl<IPrintingTypeSig['printingTypeDescription']>;
  printingTypeParams: FormControl<IPrintingTypeSig['printingTypeParams']>;
  printingTypeAttributs: FormControl<IPrintingTypeSig['printingTypeAttributs']>;
  printingTypeStat: FormControl<IPrintingTypeSig['printingTypeStat']>;
};

export type PrintingTypeSigFormGroup = FormGroup<PrintingTypeSigFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PrintingTypeSigFormService {
  createPrintingTypeSigFormGroup(printingType: PrintingTypeSigFormGroupInput = { printingTypeId: null }): PrintingTypeSigFormGroup {
    const printingTypeRawValue = {
      ...this.getFormDefaults(),
      ...printingType,
    };
    return new FormGroup<PrintingTypeSigFormGroupContent>({
      printingTypeId: new FormControl(
        { value: printingTypeRawValue.printingTypeId, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      printingTypeValue: new FormControl(printingTypeRawValue.printingTypeValue, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      printingTypeDescription: new FormControl(printingTypeRawValue.printingTypeDescription, {
        validators: [Validators.maxLength(200)],
      }),
      printingTypeParams: new FormControl(printingTypeRawValue.printingTypeParams),
      printingTypeAttributs: new FormControl(printingTypeRawValue.printingTypeAttributs),
      printingTypeStat: new FormControl(printingTypeRawValue.printingTypeStat),
    });
  }

  getPrintingTypeSig(form: PrintingTypeSigFormGroup): IPrintingTypeSig | NewPrintingTypeSig {
    return form.getRawValue() as IPrintingTypeSig | NewPrintingTypeSig;
  }

  resetForm(form: PrintingTypeSigFormGroup, printingType: PrintingTypeSigFormGroupInput): void {
    const printingTypeRawValue = { ...this.getFormDefaults(), ...printingType };
    form.reset(
      {
        ...printingTypeRawValue,
        printingTypeId: { value: printingTypeRawValue.printingTypeId, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PrintingTypeSigFormDefaults {
    return {
      printingTypeId: null,
      printingTypeStat: false,
    };
  }
}
