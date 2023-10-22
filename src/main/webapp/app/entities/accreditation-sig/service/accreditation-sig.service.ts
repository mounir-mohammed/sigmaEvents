import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAccreditationSig, NewAccreditationSig } from '../accreditation-sig.model';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { OperationHistorySigService } from 'app/entities/operation-history-sig/service/operation-history-sig.service';
import { Entity, OperationType } from 'app/config/operationType.contants';
import { IAccreditationMassUpdateSig, NewAccreditationMassUpdateSig } from '../accreditation-mass-update-sig.model';

export type PartialUpdateAccreditationSig = Partial<IAccreditationSig> & Pick<IAccreditationSig, 'accreditationId'>;

type RestOf<T extends IAccreditationSig | NewAccreditationSig> = Omit<
  T,
  | 'accreditationBirthDay'
  | 'accreditationCreationDate'
  | 'accreditationUpdateDate'
  | 'accreditationDateStart'
  | 'accreditationDateEnd'
  | 'accreditationPrintDate'
> & {
  accreditationBirthDay?: string | null;
  accreditationCreationDate?: string | null;
  accreditationUpdateDate?: string | null;
  accreditationPrintDate?: string | null;
  accreditationDateStart?: string | null;
  accreditationDateEnd?: string | null;
};

type DefOf<T extends IAccreditationMassUpdateSig | NewAccreditationMassUpdateSig> = Omit<
  T,
  'accreditationDateEnd' | 'accreditationDateStart'
> & {
  accreditationDateEnd?: string | null;
  accreditationDateStart?: string | null;
};

export type RestAccreditationSig = RestOf<IAccreditationSig>;

export type NewRestAccreditationSig = RestOf<NewAccreditationSig>;

export type PartialUpdateRestAccreditationSig = RestOf<PartialUpdateAccreditationSig>;

export type EntityResponseType = HttpResponse<IAccreditationSig>;
export type EntityArrayResponseType = HttpResponse<IAccreditationSig[]>;

@Injectable({ providedIn: 'root' })
export class AccreditationSigService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/accreditations');
  currentAccount: Account | null = null;

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
    private accountService: AccountService,
    private operationHistorySigService: OperationHistorySigService
  ) {}

  create(accreditation: NewAccreditationSig, isImport: boolean): Observable<EntityResponseType> {
    this.accountService.identity().subscribe(account => (this.currentAccount = account));
    accreditation.accreditationActivated = true;
    accreditation.accreditationStat = true;
    accreditation.accreditationCreatedByuser = this.currentAccount?.login;
    accreditation.accreditationCreationDate = dayjs();
    accreditation.accreditationUpdateDate = null;
    accreditation.accreditationPrintDate = null;
    accreditation.accreditationPrintNumber = 0;
    accreditation.accreditationPrintStat = false;

    const copy = this.convertDateFromClient(accreditation);
    return this.http
      .post<RestAccreditationSig>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res, isImport ? OperationType.Import : OperationType.Create, [])));
  }

  update(accreditation: IAccreditationSig, isMassUpdate: boolean, isUserDelete: boolean): Observable<EntityResponseType> {
    let operationType = OperationType.Update;

    if (isMassUpdate) {
      operationType = OperationType.MassUpdate;
    }
    if (isUserDelete) {
      operationType = OperationType.Delete;
    }

    this.accountService.identity().subscribe(account => (this.currentAccount = account));
    accreditation.accreditationUpdateDate = dayjs();
    accreditation.accreditationUpdatedByuser = this.currentAccount?.login;
    const copy = this.convertDateFromClient(accreditation);
    return this.http
      .put<RestAccreditationSig>(`${this.resourceUrl}/${this.getAccreditationSigIdentifier(accreditation)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res, operationType, [])));
  }

  partialUpdate(accreditation: PartialUpdateAccreditationSig, isMassUpdate: boolean): Observable<EntityResponseType> {
    this.accountService.identity().subscribe(account => (this.currentAccount = account));
    const copy = this.convertDateFromClient(accreditation);
    return this.http
      .patch<RestAccreditationSig>(`${this.resourceUrl}/${this.getAccreditationSigIdentifier(accreditation)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res, isMassUpdate ? OperationType.MassUpdate : OperationType.Update, [])));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestAccreditationSig>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res, OperationType.Search, [])));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestAccreditationSig[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    this.accountService.identity().subscribe(account => (this.currentAccount = account));
    this.operationHistorySigService
      .createNewOperation(
        Entity.Accreditation,
        id,
        dayjs(),
        this.currentAccount?.printingCentre?.event!,
        OperationType.Delete,
        this.currentAccount?.login!,
        [],
        this.currentAccount?.printingCentre?.printingCentreId!,
        ''
      )
      .subscribe();
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAccreditationSigIdentifier(accreditation: Pick<IAccreditationSig, 'accreditationId'>): number {
    return accreditation.accreditationId;
  }

  compareAccreditationSig(
    o1: Pick<IAccreditationSig, 'accreditationId'> | null,
    o2: Pick<IAccreditationSig, 'accreditationId'> | null
  ): boolean {
    return o1 && o2 ? this.getAccreditationSigIdentifier(o1) === this.getAccreditationSigIdentifier(o2) : o1 === o2;
  }

  addAccreditationSigToCollectionIfMissing<Type extends Pick<IAccreditationSig, 'accreditationId'>>(
    accreditationCollection: Type[],
    ...accreditationsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const accreditations: Type[] = accreditationsToCheck.filter(isPresent);
    if (accreditations.length > 0) {
      const accreditationCollectionIdentifiers = accreditationCollection.map(
        accreditationItem => this.getAccreditationSigIdentifier(accreditationItem)!
      );
      const accreditationsToAdd = accreditations.filter(accreditationItem => {
        const accreditationIdentifier = this.getAccreditationSigIdentifier(accreditationItem);
        if (accreditationCollectionIdentifiers.includes(accreditationIdentifier)) {
          return false;
        }
        accreditationCollectionIdentifiers.push(accreditationIdentifier);
        return true;
      });
      return [...accreditationsToAdd, ...accreditationCollection];
    }
    return accreditationCollection;
  }

  protected convertDateFromClient<T extends IAccreditationSig | NewAccreditationSig | PartialUpdateAccreditationSig>(
    accreditation: T
  ): RestOf<T> {
    return {
      ...accreditation,
      accreditationBirthDay: accreditation.accreditationBirthDay?.format(DATE_FORMAT) ?? null,
      accreditationCreationDate: accreditation.accreditationCreationDate?.toJSON() ?? null,
      accreditationUpdateDate: accreditation.accreditationUpdateDate?.toJSON() ?? null,
      accreditationPrintDate: accreditation.accreditationPrintDate?.toJSON() ?? null,
      accreditationDateStart: accreditation.accreditationDateStart?.toJSON() ?? null,
      accreditationDateEnd: accreditation.accreditationDateEnd?.toJSON() ?? null,
    };
  }

  protected convertDateFromClientForMassUpdate<T extends IAccreditationMassUpdateSig | NewAccreditationMassUpdateSig>(
    accreditationMassUpdateSig: T
  ): DefOf<T> {
    if (accreditationMassUpdateSig.accreditationDateEnd && accreditationMassUpdateSig.accreditationDateStart) {
      return {
        ...accreditationMassUpdateSig,
        accreditationDateEnd:
          accreditationMassUpdateSig.accreditationDateEnd != null ? accreditationMassUpdateSig.accreditationDateEnd.toJSON() ?? null : null,
        accreditationDateStart:
          accreditationMassUpdateSig.accreditationDateStart != null
            ? accreditationMassUpdateSig.accreditationDateStart.toJSON() ?? null
            : null,
      };
    } else if (accreditationMassUpdateSig.accreditationDateEnd && !accreditationMassUpdateSig.accreditationDateStart) {
      return {
        ...accreditationMassUpdateSig,
        accreditationDateEnd:
          accreditationMassUpdateSig.accreditationDateEnd != null ? accreditationMassUpdateSig.accreditationDateEnd.toJSON() ?? null : null,
        accreditationDateStart: null,
      };
    } else if (!accreditationMassUpdateSig.accreditationDateEnd && accreditationMassUpdateSig.accreditationDateStart) {
      return {
        ...accreditationMassUpdateSig,
        accreditationDateEnd: null,
        accreditationDateStart:
          accreditationMassUpdateSig.accreditationDateStart != null
            ? accreditationMassUpdateSig.accreditationDateStart.toJSON() ?? null
            : null,
      };
    } else {
      return {
        ...accreditationMassUpdateSig,
        accreditationDateEnd: null,
        accreditationDateStart: null,
      };
    }
  }

  protected convertDateFromServer(restAccreditationSig: RestAccreditationSig): IAccreditationSig {
    return {
      ...restAccreditationSig,
      accreditationBirthDay: restAccreditationSig.accreditationBirthDay ? dayjs(restAccreditationSig.accreditationBirthDay) : undefined,
      accreditationCreationDate: restAccreditationSig.accreditationCreationDate
        ? dayjs(restAccreditationSig.accreditationCreationDate)
        : undefined,
      accreditationUpdateDate: restAccreditationSig.accreditationUpdateDate
        ? dayjs(restAccreditationSig.accreditationUpdateDate)
        : undefined,
      accreditationPrintDate: restAccreditationSig.accreditationPrintDate ? dayjs(restAccreditationSig.accreditationPrintDate) : undefined,
      accreditationDateStart: restAccreditationSig.accreditationDateStart ? dayjs(restAccreditationSig.accreditationDateStart) : undefined,
      accreditationDateEnd: restAccreditationSig.accreditationDateEnd ? dayjs(restAccreditationSig.accreditationDateEnd) : undefined,
    };
  }

  protected convertResponseFromServer(
    res: HttpResponse<RestAccreditationSig>,
    operationType: string,
    ids: number[]
  ): HttpResponse<IAccreditationSig> {
    if (operationType != OperationType.Search) {
      console.log('convertResponseFromServer');
      const entity = res.body ? this.convertDateFromServer(res.body) : null;
      this.operationHistorySigService
        .createNewOperation(
          Entity.Accreditation,
          entity?.accreditationId!,
          dayjs(),
          this.currentAccount?.printingCentre?.event!,
          operationType,
          this.currentAccount?.login!,
          ids,
          this.currentAccount?.printingCentre?.printingCentreId!,
          ''
        )
        .subscribe();
    }
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestAccreditationSig[]>): HttpResponse<IAccreditationSig[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }

  public getAccreditation(id: number): Promise<IAccreditationSig | null> {
    return new Promise(resolve => {
      if (id) {
        this.http.get<IAccreditationSig>(`${this.resourceUrl}/${id}`).subscribe(response => {
          resolve(response);
        });
      } else {
        console.error('getAccreditation() id is null');
        resolve(null);
      }
    });
  }

  validate(accreditationId: number, statusId: number): Observable<EntityResponseType> {
    this.accountService.identity().subscribe(account => (this.currentAccount = account));
    return this.http
      .put<RestAccreditationSig>(`${this.resourceUrl}/${accreditationId}/status/${statusId}/validate`, null, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res, OperationType.Validate, [])));
  }

  print(accreditationId: number, statusId: number): Observable<EntityResponseType> {
    this.accountService.identity().subscribe(account => (this.currentAccount = account));
    return this.http
      .put<RestAccreditationSig>(`${this.resourceUrl}/${accreditationId}/status/${statusId}/print`, null, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res, OperationType.Print, [])));
  }

  massPrint(accreditationId: number[], statusId: number): Observable<EntityResponseType> {
    this.accountService.identity().subscribe(account => (this.currentAccount = account));
    return this.http
      .put<RestAccreditationSig>(`${this.resourceUrl}/${accreditationId}/status/${statusId}/massprint`, null, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res, OperationType.MassPrint, accreditationId)));
  }

  massUpdate(accreditationMassUpdate: IAccreditationMassUpdateSig | NewAccreditationMassUpdateSig): Observable<EntityResponseType> {
    const copy = this.convertDateFromClientForMassUpdate(accreditationMassUpdate);
    return this.http
      .post<RestAccreditationSig>(`${this.resourceUrl}/massUpdate`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res, OperationType.MassUpdate, [])));
  }
}
