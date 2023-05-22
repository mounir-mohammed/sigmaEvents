import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISettingSig, NewSettingSig } from '../setting-sig.model';

export type PartialUpdateSettingSig = Partial<ISettingSig> & Pick<ISettingSig, 'settingId'>;

type RestOf<T extends ISettingSig | NewSettingSig> = Omit<T, 'settingValueDate'> & {
  settingValueDate?: string | null;
};

export type RestSettingSig = RestOf<ISettingSig>;

export type NewRestSettingSig = RestOf<NewSettingSig>;

export type PartialUpdateRestSettingSig = RestOf<PartialUpdateSettingSig>;

export type EntityResponseType = HttpResponse<ISettingSig>;
export type EntityArrayResponseType = HttpResponse<ISettingSig[]>;

@Injectable({ providedIn: 'root' })
export class SettingSigService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/settings');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(setting: NewSettingSig): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(setting);
    return this.http
      .post<RestSettingSig>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(setting: ISettingSig): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(setting);
    return this.http
      .put<RestSettingSig>(`${this.resourceUrl}/${this.getSettingSigIdentifier(setting)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(setting: PartialUpdateSettingSig): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(setting);
    return this.http
      .patch<RestSettingSig>(`${this.resourceUrl}/${this.getSettingSigIdentifier(setting)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestSettingSig>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestSettingSig[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSettingSigIdentifier(setting: Pick<ISettingSig, 'settingId'>): number {
    return setting.settingId;
  }

  compareSettingSig(o1: Pick<ISettingSig, 'settingId'> | null, o2: Pick<ISettingSig, 'settingId'> | null): boolean {
    return o1 && o2 ? this.getSettingSigIdentifier(o1) === this.getSettingSigIdentifier(o2) : o1 === o2;
  }

  addSettingSigToCollectionIfMissing<Type extends Pick<ISettingSig, 'settingId'>>(
    settingCollection: Type[],
    ...settingsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const settings: Type[] = settingsToCheck.filter(isPresent);
    if (settings.length > 0) {
      const settingCollectionIdentifiers = settingCollection.map(settingItem => this.getSettingSigIdentifier(settingItem)!);
      const settingsToAdd = settings.filter(settingItem => {
        const settingIdentifier = this.getSettingSigIdentifier(settingItem);
        if (settingCollectionIdentifiers.includes(settingIdentifier)) {
          return false;
        }
        settingCollectionIdentifiers.push(settingIdentifier);
        return true;
      });
      return [...settingsToAdd, ...settingCollection];
    }
    return settingCollection;
  }

  protected convertDateFromClient<T extends ISettingSig | NewSettingSig | PartialUpdateSettingSig>(setting: T): RestOf<T> {
    return {
      ...setting,
      settingValueDate: setting.settingValueDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restSettingSig: RestSettingSig): ISettingSig {
    return {
      ...restSettingSig,
      settingValueDate: restSettingSig.settingValueDate ? dayjs(restSettingSig.settingValueDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestSettingSig>): HttpResponse<ISettingSig> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestSettingSig[]>): HttpResponse<ISettingSig[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }

  public getSetting(id: number): Promise<ISettingSig> {
    return new Promise(resolve => {
      this.http.get<ISettingSig>(`${this.resourceUrl}/${id}`).subscribe(response => {
        resolve(response);
      });
    });
  }
}
