export interface IAttachementTypeSig {
  attachementTypeId: number;
  attachementTypeName?: string | null;
  attachementTypeDescription?: string | null;
  attachementTypeParams?: string | null;
  attachementTypeAttributs?: string | null;
  attachementTypeStat?: boolean | null;
}

export type NewAttachementTypeSig = Omit<IAttachementTypeSig, 'attachementTypeId'> & { attachementTypeId: null };
