export interface IOperationTypeSig {
  operationTypeId: number;
  operationTypeValue?: string | null;
  operationTypeDescription?: string | null;
  operationTypeParams?: string | null;
  operationTypeAttributs?: string | null;
  operationTypeStat?: boolean | null;
}

export type NewOperationTypeSig = Omit<IOperationTypeSig, 'operationTypeId'> & { operationTypeId: null };
