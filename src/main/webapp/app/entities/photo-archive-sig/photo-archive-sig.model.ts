import { IAccreditationSig } from 'app/entities/accreditation-sig/accreditation-sig.model';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';

export interface IPhotoArchiveSig {
  photoArchiveId: number;
  photoArchiveName?: string | null;
  photoArchivePath?: string | null;
  photoArchivePhoto?: string | null;
  photoArchivePhotoContentType?: string | null;
  photoArchiveDescription?: string | null;
  photoArchiveParams?: string | null;
  photoArchiveAttributs?: string | null;
  photoArchiveStat?: boolean | null;
  accreditation?: Pick<IAccreditationSig, 'accreditationId'> | null;
  event?: Pick<IEventSig, 'eventId'> | null;
}

export type NewPhotoArchiveSig = Omit<IPhotoArchiveSig, 'photoArchiveId'> & { photoArchiveId: null };
