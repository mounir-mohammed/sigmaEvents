import { IEventSig } from 'app/entities/event-sig/event-sig.model';
import { ICheckAccreditationHistorySig } from 'app/entities/check-accreditation-history-sig/check-accreditation-history-sig.model';

export interface ICheckAccreditationReportSig {
  checkAccreditationReportId: number;
  checkAccreditationReportDescription?: string | null;
  checkAccreditationReportPersonPhoto?: string | null;
  checkAccreditationReportPersonPhotoContentType?: string | null;
  checkAccreditationReportCINPhoto?: string | null;
  checkAccreditationReportCINPhotoContentType?: string | null;
  checkAccreditationReportAttachment?: string | null;
  checkAccreditationReportAttachmentContentType?: string | null;
  checkAccreditationReportParams?: string | null;
  checkAccreditationReportAttributs?: string | null;
  checkAccreditationReportStat?: boolean | null;
  event?: Pick<IEventSig, 'eventId'> | null;
  checkAccreditationHistory?: Pick<ICheckAccreditationHistorySig, 'checkAccreditationHistoryId'> | null;
}

export type NewCheckAccreditationReportSig = Omit<ICheckAccreditationReportSig, 'checkAccreditationReportId'> & {
  checkAccreditationReportId: null;
};
