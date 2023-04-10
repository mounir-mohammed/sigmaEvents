import { IEventSig } from 'app/entities/event-sig/event-sig.model';

export interface IPrintingServerSig {
  printingServerId: number;
  printingServerName?: string | null;
  printingServerDescription?: string | null;
  printingServerHost?: string | null;
  printingServerPort?: string | null;
  printingServerDns?: string | null;
  printingServerProxy?: string | null;
  printingServerParam1?: string | null;
  printingServerParam2?: string | null;
  printingServerParam3?: string | null;
  printingServerStat?: boolean | null;
  printingServerParams?: string | null;
  printingServerAttributs?: string | null;
  event?: Pick<IEventSig, 'eventId' | 'eventName' | 'eventStat'> | null;
}

export type NewPrintingServerSig = Omit<IPrintingServerSig, 'printingServerId'> & { printingServerId: null };
