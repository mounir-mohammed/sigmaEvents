import { IAttachementTypeSig } from 'app/entities/attachement-type-sig/attachement-type-sig.model';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';

export interface IAttachementSig {
  attachementId: number;
  attachementName?: string | null;
  attachementPath?: string | null;
  attachementBlob?: string | null;
  attachementBlobContentType?: string | null;
  attachementDescription?: string | null;
  attachementPhoto?: string | null;
  attachementPhotoContentType?: string | null;
  attachementParams?: string | null;
  attachementAttributs?: string | null;
  attachementStat?: boolean | null;
  attachementType?: Pick<IAttachementTypeSig, 'attachementTypeId'> | null;
  event?: Pick<IEventSig, 'eventId'> | null;
}

export type NewAttachementSig = Omit<IAttachementSig, 'attachementId'> & { attachementId: null };
