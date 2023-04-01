import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPrintingModelSig, NewPrintingModelSig } from '../printing-model-sig.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { printingModelId: unknown }> = Partial<Omit<T, 'printingModelId'>> & {
  printingModelId: T['printingModelId'];
};

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPrintingModelSig for edit and NewPrintingModelSigFormGroupInput for create.
 */
type PrintingModelSigFormGroupInput = IPrintingModelSig | PartialWithRequiredKeyOf<NewPrintingModelSig>;

type PrintingModelSigFormDefaults = Pick<NewPrintingModelSig, 'printingModelId' | 'printingModelStat'>;

type PrintingModelSigFormGroupContent = {
  printingModelId: FormControl<IPrintingModelSig['printingModelId'] | NewPrintingModelSig['printingModelId']>;
  printingModelName: FormControl<IPrintingModelSig['printingModelName']>;
  printingModelFile: FormControl<IPrintingModelSig['printingModelFile']>;
  printingModelPath: FormControl<IPrintingModelSig['printingModelPath']>;
  printingModelDescription: FormControl<IPrintingModelSig['printingModelDescription']>;
  printingModelData: FormControl<IPrintingModelSig['printingModelData']>;
  printingModelDataContentType: FormControl<IPrintingModelSig['printingModelDataContentType']>;
  printingModelParams: FormControl<IPrintingModelSig['printingModelParams']>;
  printingModelAttributs: FormControl<IPrintingModelSig['printingModelAttributs']>;
  printingModelStat: FormControl<IPrintingModelSig['printingModelStat']>;
  event: FormControl<IPrintingModelSig['event']>;
};

export type PrintingModelSigFormGroup = FormGroup<PrintingModelSigFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PrintingModelSigFormService {
  createPrintingModelSigFormGroup(printingModel: PrintingModelSigFormGroupInput = { printingModelId: null }): PrintingModelSigFormGroup {
    const printingModelRawValue = {
      ...this.getFormDefaults(),
      ...printingModel,
    };
    return new FormGroup<PrintingModelSigFormGroupContent>({
      printingModelId: new FormControl(
        { value: printingModelRawValue.printingModelId, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      printingModelName: new FormControl(printingModelRawValue.printingModelName, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      printingModelFile: new FormControl(printingModelRawValue.printingModelFile, {
        validators: [Validators.required],
      }),
      printingModelPath: new FormControl(printingModelRawValue.printingModelPath, {
        validators: [Validators.required],
      }),
      printingModelDescription: new FormControl(printingModelRawValue.printingModelDescription, {
        validators: [Validators.maxLength(200)],
      }),
      printingModelData: new FormControl(printingModelRawValue.printingModelData),
      printingModelDataContentType: new FormControl(printingModelRawValue.printingModelDataContentType),
      printingModelParams: new FormControl(printingModelRawValue.printingModelParams),
      printingModelAttributs: new FormControl(printingModelRawValue.printingModelAttributs),
      printingModelStat: new FormControl(printingModelRawValue.printingModelStat),
      event: new FormControl(printingModelRawValue.event),
    });
  }

  getPrintingModelSig(form: PrintingModelSigFormGroup): IPrintingModelSig | NewPrintingModelSig {
    return form.getRawValue() as IPrintingModelSig | NewPrintingModelSig;
  }

  resetForm(form: PrintingModelSigFormGroup, printingModel: PrintingModelSigFormGroupInput): void {
    const printingModelRawValue = { ...this.getFormDefaults(), ...printingModel };
    form.reset(
      {
        ...printingModelRawValue,
        printingModelId: { value: printingModelRawValue.printingModelId, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PrintingModelSigFormDefaults {
    return {
      printingModelId: null,
      printingModelStat: false,
    };
  }
}
