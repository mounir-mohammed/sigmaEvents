export interface ICodeTypeSig {
  codeTypeId: number;
  codeTypeValue?: string | null;
  codeTypeDescription?: string | null;
  codeTypeParams?: string | null;
  codeTypeAttributs?: string | null;
  codeTypeStat?: boolean | null;
}

export type NewCodeTypeSig = Omit<ICodeTypeSig, 'codeTypeId'> & { codeTypeId: null };
