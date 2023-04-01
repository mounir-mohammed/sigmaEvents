export interface ILanguageSig {
  languageId: number;
  languageCode?: string | null;
  languageName?: string | null;
  languageDescription?: string | null;
  languageParams?: string | null;
  languageAttributs?: string | null;
  languageStat?: boolean | null;
}

export type NewLanguageSig = Omit<ILanguageSig, 'languageId'> & { languageId: null };
