import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAreaSig, NewAreaSig } from '../area-sig.model';

export type PartialUpdateAreaSig = Partial<IAreaSig> & Pick<IAreaSig, 'areaId'>;

export type EntityResponseType = HttpResponse<IAreaSig>;
export type EntityArrayResponseType = HttpResponse<IAreaSig[]>;

@Injectable({ providedIn: 'root' })
export class AreaSigService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/areas');
  private areasCache: IAreaSig[] = [];

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(area: NewAreaSig): Observable<EntityResponseType> {
    this.resetAreasCache();
    return this.http.post<IAreaSig>(this.resourceUrl, area, { observe: 'response' });
  }

  update(area: IAreaSig): Observable<EntityResponseType> {
    this.resetAreasCache();
    return this.http.put<IAreaSig>(`${this.resourceUrl}/${this.getAreaSigIdentifier(area)}`, area, { observe: 'response' });
  }

  partialUpdate(area: PartialUpdateAreaSig): Observable<EntityResponseType> {
    this.resetAreasCache();
    return this.http.patch<IAreaSig>(`${this.resourceUrl}/${this.getAreaSigIdentifier(area)}`, area, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAreaSig>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAreaSig[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    this.resetAreasCache();
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAreaSigIdentifier(area: Pick<IAreaSig, 'areaId'>): number {
    return area.areaId;
  }

  compareAreaSig(o1: Pick<IAreaSig, 'areaId'> | null, o2: Pick<IAreaSig, 'areaId'> | null): boolean {
    return o1 && o2 ? this.getAreaSigIdentifier(o1) === this.getAreaSigIdentifier(o2) : o1 === o2;
  }

  addAreaSigToCollectionIfMissing<Type extends Pick<IAreaSig, 'areaId'>>(
    areaCollection: Type[],
    ...areasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const areas: Type[] = areasToCheck.filter(isPresent);
    if (areas.length > 0) {
      const areaCollectionIdentifiers = areaCollection.map(areaItem => this.getAreaSigIdentifier(areaItem)!);
      const areasToAdd = areas.filter(areaItem => {
        const areaIdentifier = this.getAreaSigIdentifier(areaItem);
        if (areaCollectionIdentifiers.includes(areaIdentifier)) {
          return false;
        }
        areaCollectionIdentifiers.push(areaIdentifier);
        return true;
      });
      return [...areasToAdd, ...areaCollection];
    }
    return areaCollection;
  }

  async getAllAreas(): Promise<IAreaSig[]> {
    // Check if areas are already in the cache
    if (this.areasCache.length > 0) {
      return this.areasCache;
    }

    try {
      const response = await this.http.get<IAreaSig[]>(this.resourceUrl).toPromise();
      this.areasCache = response!; // Store the retrieved areas in the cache
      return response!;
    } catch (error: any) {
      console.error(error!.message!);
      return [];
    }
  }

  resetAreasCache(): void {
    this.areasCache = [];
  }
}
