export interface IPrintingTypeSig {
  printingTypeId: number;
  printingTypeValue?: string | null;
  printingTypeDescription?: string | null;
  printingTypeParams?: string | null;
  printingTypeAttributs?: string | null;
  printingTypeStat?: boolean | null;
}

export type NewPrintingTypeSig = Omit<IPrintingTypeSig, 'printingTypeId'> & { printingTypeId: null };
