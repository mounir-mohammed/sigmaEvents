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
    private accountService: AccountService
  ) {}

  create(accreditation: NewAccreditationSig): Observable<EntityResponseType> {
    this.accountService.identity().subscribe(account => (this.currentAccount = account));
    accreditation.accreditationActivated = true;
    accreditation.accreditationStat = true;
    accreditation.accreditationCreatedByuser = this.currentAccount?.login;
    accreditation.accreditationCreationDate = dayjs();
    accreditation.accreditationUpdateDate = null;
    accreditation.accreditationPrintDate = null;
    const copy = this.convertDateFromClient(accreditation);
    return this.http
      .post<RestAccreditationSig>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(accreditation: IAccreditationSig): Observable<EntityResponseType> {
    this.accountService.identity().subscribe(account => (this.currentAccount = account));
    accreditation.accreditationUpdateDate = dayjs();
    accreditation.accreditationUpdatedByuser = this.currentAccount?.login;
    const copy = this.convertDateFromClient(accreditation);
    return this.http
      .put<RestAccreditationSig>(`${this.resourceUrl}/${this.getAccreditationSigIdentifier(accreditation)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(accreditation: PartialUpdateAccreditationSig): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(accreditation);
    return this.http
      .patch<RestAccreditationSig>(`${this.resourceUrl}/${this.getAccreditationSigIdentifier(accreditation)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestAccreditationSig>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestAccreditationSig[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
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

  protected convertResponseFromServer(res: HttpResponse<RestAccreditationSig>): HttpResponse<IAccreditationSig> {
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
    return this.http
      .put<RestAccreditationSig>(`${this.resourceUrl}/${accreditationId}/status/${statusId}/validate`, null, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  print(accreditationId: number, statusId: number): Observable<EntityResponseType> {
    return this.http
      .put<RestAccreditationSig>(`${this.resourceUrl}/${accreditationId}/status/${statusId}/print`, null, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  massPrint(accreditationId: number[], statusId: number): Observable<EntityResponseType> {
    return this.http
      .put<RestAccreditationSig>(`${this.resourceUrl}/${accreditationId}/status/${statusId}/massprint`, null, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }
}
