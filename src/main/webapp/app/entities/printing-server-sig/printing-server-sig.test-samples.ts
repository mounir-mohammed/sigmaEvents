import { IPrintingServerSig, NewPrintingServerSig } from './printing-server-sig.model';

export const sampleWithRequiredData: IPrintingServerSig = {
  printingServerId: 80730,
};

export const sampleWithPartialData: IPrintingServerSig = {
  printingServerId: 2772,
  printingServerName: 'deposit USB model',
  printingServerDescription: 'Corporate cyan',
  printingServerPort: 'Granite monitoring',
  printingServerDns: 'Investment Roads Australian',
  printingServerParam2: 'seize e-tailers',
  printingServerStat: true,
  printingServerAttributs: 'Awesome overriding partnerships',
};

export const sampleWithFullData: IPrintingServerSig = {
  printingServerId: 3706,
  printingServerName: 'neural Agent Orchestrator',
  printingServerDescription: 'Executive Bedfordshire Account',
  printingServerHost: 'Unbranded Stravenue',
  printingServerPort: 'protocol haptic Borders',
  printingServerDns: 'Officer primary',
  printingServerProxy: 'SDD overriding Forward',
  printingServerParam1: 'Buckinghamshire',
  printingServerParam2: 'Home PCI Bahamas',
  printingServerParam3: 'exploit mobile',
  printingServerStat: false,
  printingServerParams: 'Legacy',
  printingServerAttributs: 'Georgia Trail',
};

export const sampleWithNewData: NewPrintingServerSig = {
  printingServerId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
