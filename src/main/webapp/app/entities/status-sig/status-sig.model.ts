export interface IStatusSig {
  statusId: number;
  statusName?: string | null;
  statusAbreviation?: string | null;
  statusColor?: string | null;
  statusDescription?: string | null;
  statusUserCanPrint?: boolean | null;
  statusUserCanUpdate?: boolean | null;
  statusUserCanValidate?: boolean | null;
  statusParams?: string | null;
  statusAttributs?: string | null;
  statusStat?: boolean | null;
}

export type NewStatusSig = Omit<IStatusSig, 'statusId'> & { statusId: null };
