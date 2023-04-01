import { IPrintingModelSig } from 'app/entities/printing-model-sig/printing-model-sig.model';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';

export interface ICategorySig {
  categoryId: number;
  categoryName?: string | null;
  categoryAbreviation?: string | null;
  categoryColor?: string | null;
  categoryDescription?: string | null;
  categoryLogo?: string | null;
  categoryLogoContentType?: string | null;
  categoryParams?: string | null;
  categoryAttributs?: string | null;
  categoryStat?: boolean | null;
  printingModel?: Pick<IPrintingModelSig, 'printingModelId'> | null;
  event?: Pick<IEventSig, 'eventId'> | null;
}

export type NewCategorySig = Omit<ICategorySig, 'categoryId'> & { categoryId: null };
