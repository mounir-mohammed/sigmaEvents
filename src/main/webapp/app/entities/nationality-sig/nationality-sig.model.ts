export interface INationalitySig {
  nationalityId: number;
  nationalityValue?: string | null;
  nationalityAbreviation?: string | null;
  nationalityDescription?: string | null;
  nationalityFlag?: string | null;
  nationalityFlagContentType?: string | null;
  nationalityParams?: string | null;
  nationalityAttributs?: string | null;
  nationalityStat?: boolean | null;
}

export type NewNationalitySig = Omit<INationalitySig, 'nationalityId'> & { nationalityId: null };
