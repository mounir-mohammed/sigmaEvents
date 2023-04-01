import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPrintingServerSig, NewPrintingServerSig } from '../printing-server-sig.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { printingServerId: unknown }> = Partial<Omit<T, 'printingServerId'>> & {
  printingServerId: T['printingServerId'];
};

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPrintingServerSig for edit and NewPrintingServerSigFormGroupInput for create.
 */
type PrintingServerSigFormGroupInput = IPrintingServerSig | PartialWithRequiredKeyOf<NewPrintingServerSig>;

type PrintingServerSigFormDefaults = Pick<NewPrintingServerSig, 'printingServerId' | 'printingServerStat'>;

type PrintingServerSigFormGroupContent = {
  printingServerId: FormControl<IPrintingServerSig['printingServerId'] | NewPrintingServerSig['printingServerId']>;
  printingServerName: FormControl<IPrintingServerSig['printingServerName']>;
  printingServerDescription: FormControl<IPrintingServerSig['printingServerDescription']>;
  printingServerHost: FormControl<IPrintingServerSig['printingServerHost']>;
  printingServerPort: FormControl<IPrintingServerSig['printingServerPort']>;
  printingServerDns: FormControl<IPrintingServerSig['printingServerDns']>;
  printingServerProxy: FormControl<IPrintingServerSig['printingServerProxy']>;
  printingServerParam1: FormControl<IPrintingServerSig['printingServerParam1']>;
  printingServerParam2: FormControl<IPrintingServerSig['printingServerParam2']>;
  printingServerParam3: FormControl<IPrintingServerSig['printingServerParam3']>;
  printingServerStat: FormControl<IPrintingServerSig['printingServerStat']>;
  printingServerParams: FormControl<IPrintingServerSig['printingServerParams']>;
  printingServerAttributs: FormControl<IPrintingServerSig['printingServerAttributs']>;
  event: FormControl<IPrintingServerSig['event']>;
};

export type PrintingServerSigFormGroup = FormGroup<PrintingServerSigFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PrintingServerSigFormService {
  createPrintingServerSigFormGroup(
    printingServer: PrintingServerSigFormGroupInput = { printingServerId: null }
  ): PrintingServerSigFormGroup {
    const printingServerRawValue = {
      ...this.getFormDefaults(),
      ...printingServer,
    };
    return new FormGroup<PrintingServerSigFormGroupContent>({
      printingServerId: new FormControl(
        { value: printingServerRawValue.printingServerId, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      printingServerName: new FormControl(printingServerRawValue.printingServerName),
      printingServerDescription: new FormControl(printingServerRawValue.printingServerDescription, {
        validators: [Validators.maxLength(200)],
      }),
      printingServerHost: new FormControl(printingServerRawValue.printingServerHost),
      printingServerPort: new FormControl(printingServerRawValue.printingServerPort),
      printingServerDns: new FormControl(printingServerRawValue.printingServerDns),
      printingServerProxy: new FormControl(printingServerRawValue.printingServerProxy),
      printingServerParam1: new FormControl(printingServerRawValue.printingServerParam1),
      printingServerParam2: new FormControl(printingServerRawValue.printingServerParam2),
      printingServerParam3: new FormControl(printingServerRawValue.printingServerParam3),
      printingServerStat: new FormControl(printingServerRawValue.printingServerStat),
      printingServerParams: new FormControl(printingServerRawValue.printingServerParams),
      printingServerAttributs: new FormControl(printingServerRawValue.printingServerAttributs),
      event: new FormControl(printingServerRawValue.event),
    });
  }

  getPrintingServerSig(form: PrintingServerSigFormGroup): IPrintingServerSig | NewPrintingServerSig {
    return form.getRawValue() as IPrintingServerSig | NewPrintingServerSig;
  }

  resetForm(form: PrintingServerSigFormGroup, printingServer: PrintingServerSigFormGroupInput): void {
    const printingServerRawValue = { ...this.getFormDefaults(), ...printingServer };
    form.reset(
      {
        ...printingServerRawValue,
        printingServerId: { value: printingServerRawValue.printingServerId, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PrintingServerSigFormDefaults {
    return {
      printingServerId: null,
      printingServerStat: false,
    };
  }
}
