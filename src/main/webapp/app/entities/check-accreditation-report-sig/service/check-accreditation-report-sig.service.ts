import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICheckAccreditationReportSig, NewCheckAccreditationReportSig } from '../check-accreditation-report-sig.model';

export type PartialUpdateCheckAccreditationReportSig = Partial<ICheckAccreditationReportSig> &
  Pick<ICheckAccreditationReportSig, 'checkAccreditationReportId'>;

export type EntityResponseType = HttpResponse<ICheckAccreditationReportSig>;
export type EntityArrayResponseType = HttpResponse<ICheckAccreditationReportSig[]>;

@Injectable({ providedIn: 'root' })
export class CheckAccreditationReportSigService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/check-accreditation-reports');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(checkAccreditationReport: NewCheckAccreditationReportSig): Observable<EntityResponseType> {
    return this.http.post<ICheckAccreditationReportSig>(this.resourceUrl, checkAccreditationReport, { observe: 'response' });
  }

  update(checkAccreditationReport: ICheckAccreditationReportSig): Observable<EntityResponseType> {
    return this.http.put<ICheckAccreditationReportSig>(
      `${this.resourceUrl}/${this.getCheckAccreditationReportSigIdentifier(checkAccreditationReport)}`,
      checkAccreditationReport,
      { observe: 'response' }
    );
  }

  partialUpdate(checkAccreditationReport: PartialUpdateCheckAccreditationReportSig): Observable<EntityResponseType> {
    return this.http.patch<ICheckAccreditationReportSig>(
      `${this.resourceUrl}/${this.getCheckAccreditationReportSigIdentifier(checkAccreditationReport)}`,
      checkAccreditationReport,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICheckAccreditationReportSig>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICheckAccreditationReportSig[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCheckAccreditationReportSigIdentifier(
    checkAccreditationReport: Pick<ICheckAccreditationReportSig, 'checkAccreditationReportId'>
  ): number {
    return checkAccreditationReport.checkAccreditationReportId;
  }

  compareCheckAccreditationReportSig(
    o1: Pick<ICheckAccreditationReportSig, 'checkAccreditationReportId'> | null,
    o2: Pick<ICheckAccreditationReportSig, 'checkAccreditationReportId'> | null
  ): boolean {
    return o1 && o2 ? this.getCheckAccreditationReportSigIdentifier(o1) === this.getCheckAccreditationReportSigIdentifier(o2) : o1 === o2;
  }

  addCheckAccreditationReportSigToCollectionIfMissing<Type extends Pick<ICheckAccreditationReportSig, 'checkAccreditationReportId'>>(
    checkAccreditationReportCollection: Type[],
    ...checkAccreditationReportsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const checkAccreditationReports: Type[] = checkAccreditationReportsToCheck.filter(isPresent);
    if (checkAccreditationReports.length > 0) {
      const checkAccreditationReportCollectionIdentifiers = checkAccreditationReportCollection.map(
        checkAccreditationReportItem => this.getCheckAccreditationReportSigIdentifier(checkAccreditationReportItem)!
      );
      const checkAccreditationReportsToAdd = checkAccreditationReports.filter(checkAccreditationReportItem => {
        const checkAccreditationReportIdentifier = this.getCheckAccreditationReportSigIdentifier(checkAccreditationReportItem);
        if (checkAccreditationReportCollectionIdentifiers.includes(checkAccreditationReportIdentifier)) {
          return false;
        }
        checkAccreditationReportCollectionIdentifiers.push(checkAccreditationReportIdentifier);
        return true;
      });
      return [...checkAccreditationReportsToAdd, ...checkAccreditationReportCollection];
    }
    return checkAccreditationReportCollection;
  }
}
