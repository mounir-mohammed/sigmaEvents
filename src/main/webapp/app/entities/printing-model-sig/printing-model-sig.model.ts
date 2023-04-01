import { IEventSig } from 'app/entities/event-sig/event-sig.model';

export interface IPrintingModelSig {
  printingModelId: number;
  printingModelName?: string | null;
  printingModelFile?: string | null;
  printingModelPath?: string | null;
  printingModelDescription?: string | null;
  printingModelData?: string | null;
  printingModelDataContentType?: string | null;
  printingModelParams?: string | null;
  printingModelAttributs?: string | null;
  printingModelStat?: boolean | null;
  event?: Pick<IEventSig, 'eventId'> | null;
}

export type NewPrintingModelSig = Omit<IPrintingModelSig, 'printingModelId'> & { printingModelId: null };
