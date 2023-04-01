import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICategorySig, NewCategorySig } from '../category-sig.model';

export type PartialUpdateCategorySig = Partial<ICategorySig> & Pick<ICategorySig, 'categoryId'>;

export type EntityResponseType = HttpResponse<ICategorySig>;
export type EntityArrayResponseType = HttpResponse<ICategorySig[]>;

@Injectable({ providedIn: 'root' })
export class CategorySigService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/categories');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(category: NewCategorySig): Observable<EntityResponseType> {
    return this.http.post<ICategorySig>(this.resourceUrl, category, { observe: 'response' });
  }

  update(category: ICategorySig): Observable<EntityResponseType> {
    return this.http.put<ICategorySig>(`${this.resourceUrl}/${this.getCategorySigIdentifier(category)}`, category, { observe: 'response' });
  }

  partialUpdate(category: PartialUpdateCategorySig): Observable<EntityResponseType> {
    return this.http.patch<ICategorySig>(`${this.resourceUrl}/${this.getCategorySigIdentifier(category)}`, category, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICategorySig>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICategorySig[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCategorySigIdentifier(category: Pick<ICategorySig, 'categoryId'>): number {
    return category.categoryId;
  }

  compareCategorySig(o1: Pick<ICategorySig, 'categoryId'> | null, o2: Pick<ICategorySig, 'categoryId'> | null): boolean {
    return o1 && o2 ? this.getCategorySigIdentifier(o1) === this.getCategorySigIdentifier(o2) : o1 === o2;
  }

  addCategorySigToCollectionIfMissing<Type extends Pick<ICategorySig, 'categoryId'>>(
    categoryCollection: Type[],
    ...categoriesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const categories: Type[] = categoriesToCheck.filter(isPresent);
    if (categories.length > 0) {
      const categoryCollectionIdentifiers = categoryCollection.map(categoryItem => this.getCategorySigIdentifier(categoryItem)!);
      const categoriesToAdd = categories.filter(categoryItem => {
        const categoryIdentifier = this.getCategorySigIdentifier(categoryItem);
        if (categoryCollectionIdentifiers.includes(categoryIdentifier)) {
          return false;
        }
        categoryCollectionIdentifiers.push(categoryIdentifier);
        return true;
      });
      return [...categoriesToAdd, ...categoryCollection];
    }
    return categoryCollection;
  }
}
