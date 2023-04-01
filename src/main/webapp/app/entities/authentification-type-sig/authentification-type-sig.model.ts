export interface IAuthentificationTypeSig {
  authentificationTypeId: number;
  authentificationTypeValue?: string | null;
  authentificationTypeDescription?: string | null;
  authentificationTypeParams?: string | null;
  authentificationTypeAttributs?: string | null;
  authentificationTypeStat?: boolean | null;
}

export type NewAuthentificationTypeSig = Omit<IAuthentificationTypeSig, 'authentificationTypeId'> & { authentificationTypeId: null };
