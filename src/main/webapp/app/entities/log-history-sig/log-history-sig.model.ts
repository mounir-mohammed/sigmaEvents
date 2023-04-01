export interface ILogHistorySig {
  logHistory: number;
  logHistoryDescription?: string | null;
  logHistoryParams?: string | null;
  logHistoryAttributs?: string | null;
  logHistoryStat?: boolean | null;
}

export type NewLogHistorySig = Omit<ILogHistorySig, 'logHistory'> & { logHistory: null };
