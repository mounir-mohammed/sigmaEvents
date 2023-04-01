import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPrintingCentreSig, NewPrintingCentreSig } from '../printing-centre-sig.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { printingCentreId: unknown }> = Partial<Omit<T, 'printingCentreId'>> & {
  printingCentreId: T['printingCentreId'];
};

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPrintingCentreSig for edit and NewPrintingCentreSigFormGroupInput for create.
 */
type PrintingCentreSigFormGroupInput = IPrintingCentreSig | PartialWithRequiredKeyOf<NewPrintingCentreSig>;

type PrintingCentreSigFormDefaults = Pick<NewPrintingCentreSig, 'printingCentreId' | 'printingCentreStat'>;

type PrintingCentreSigFormGroupContent = {
  printingCentreId: FormControl<IPrintingCentreSig['printingCentreId'] | NewPrintingCentreSig['printingCentreId']>;
  printingCentreDescription: FormControl<IPrintingCentreSig['printingCentreDescription']>;
  printingCentreName: FormControl<IPrintingCentreSig['printingCentreName']>;
  printingCentreLogo: FormControl<IPrintingCentreSig['printingCentreLogo']>;
  printingCentreLogoContentType: FormControl<IPrintingCentreSig['printingCentreLogoContentType']>;
  printingCentreAdresse: FormControl<IPrintingCentreSig['printingCentreAdresse']>;
  printingCentreEmail: FormControl<IPrintingCentreSig['printingCentreEmail']>;
  printingCentreTel: FormControl<IPrintingCentreSig['printingCentreTel']>;
  printingCentreFax: FormControl<IPrintingCentreSig['printingCentreFax']>;
  printingCentreResponsableName: FormControl<IPrintingCentreSig['printingCentreResponsableName']>;
  printingParams: FormControl<IPrintingCentreSig['printingParams']>;
  printingAttributs: FormControl<IPrintingCentreSig['printingAttributs']>;
  printingCentreStat: FormControl<IPrintingCentreSig['printingCentreStat']>;
  city: FormControl<IPrintingCentreSig['city']>;
  country: FormControl<IPrintingCentreSig['country']>;
  organiz: FormControl<IPrintingCentreSig['organiz']>;
  printingType: FormControl<IPrintingCentreSig['printingType']>;
  printingServer: FormControl<IPrintingCentreSig['printingServer']>;
  printingModel: FormControl<IPrintingCentreSig['printingModel']>;
  language: FormControl<IPrintingCentreSig['language']>;
  event: FormControl<IPrintingCentreSig['event']>;
};

export type PrintingCentreSigFormGroup = FormGroup<PrintingCentreSigFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PrintingCentreSigFormService {
  createPrintingCentreSigFormGroup(
    printingCentre: PrintingCentreSigFormGroupInput = { printingCentreId: null }
  ): PrintingCentreSigFormGroup {
    const printingCentreRawValue = {
      ...this.getFormDefaults(),
      ...printingCentre,
    };
    return new FormGroup<PrintingCentreSigFormGroupContent>({
      printingCentreId: new FormControl(
        { value: printingCentreRawValue.printingCentreId, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      printingCentreDescription: new FormControl(printingCentreRawValue.printingCentreDescription, {
        validators: [Validators.maxLength(200)],
      }),
      printingCentreName: new FormControl(printingCentreRawValue.printingCentreName, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      printingCentreLogo: new FormControl(printingCentreRawValue.printingCentreLogo),
      printingCentreLogoContentType: new FormControl(printingCentreRawValue.printingCentreLogoContentType),
      printingCentreAdresse: new FormControl(printingCentreRawValue.printingCentreAdresse),
      printingCentreEmail: new FormControl(printingCentreRawValue.printingCentreEmail),
      printingCentreTel: new FormControl(printingCentreRawValue.printingCentreTel),
      printingCentreFax: new FormControl(printingCentreRawValue.printingCentreFax),
      printingCentreResponsableName: new FormControl(printingCentreRawValue.printingCentreResponsableName),
      printingParams: new FormControl(printingCentreRawValue.printingParams),
      printingAttributs: new FormControl(printingCentreRawValue.printingAttributs),
      printingCentreStat: new FormControl(printingCentreRawValue.printingCentreStat),
      city: new FormControl(printingCentreRawValue.city),
      country: new FormControl(printingCentreRawValue.country),
      organiz: new FormControl(printingCentreRawValue.organiz),
      printingType: new FormControl(printingCentreRawValue.printingType),
      printingServer: new FormControl(printingCentreRawValue.printingServer),
      printingModel: new FormControl(printingCentreRawValue.printingModel),
      language: new FormControl(printingCentreRawValue.language),
      event: new FormControl(printingCentreRawValue.event),
    });
  }

  getPrintingCentreSig(form: PrintingCentreSigFormGroup): IPrintingCentreSig | NewPrintingCentreSig {
    return form.getRawValue() as IPrintingCentreSig | NewPrintingCentreSig;
  }

  resetForm(form: PrintingCentreSigFormGroup, printingCentre: PrintingCentreSigFormGroupInput): void {
    const printingCentreRawValue = { ...this.getFormDefaults(), ...printingCentre };
    form.reset(
      {
        ...printingCentreRawValue,
        printingCentreId: { value: printingCentreRawValue.printingCentreId, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PrintingCentreSigFormDefaults {
    return {
      printingCentreId: null,
      printingCentreStat: false,
    };
  }
}
