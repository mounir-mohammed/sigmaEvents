import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICodeTypeSig, NewCodeTypeSig } from '../code-type-sig.model';

export type PartialUpdateCodeTypeSig = Partial<ICodeTypeSig> & Pick<ICodeTypeSig, 'codeTypeId'>;

export type EntityResponseType = HttpResponse<ICodeTypeSig>;
export type EntityArrayResponseType = HttpResponse<ICodeTypeSig[]>;

@Injectable({ providedIn: 'root' })
export class CodeTypeSigService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/code-types');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(codeType: NewCodeTypeSig): Observable<EntityResponseType> {
    return this.http.post<ICodeTypeSig>(this.resourceUrl, codeType, { observe: 'response' });
  }

  update(codeType: ICodeTypeSig): Observable<EntityResponseType> {
    return this.http.put<ICodeTypeSig>(`${this.resourceUrl}/${this.getCodeTypeSigIdentifier(codeType)}`, codeType, { observe: 'response' });
  }

  partialUpdate(codeType: PartialUpdateCodeTypeSig): Observable<EntityResponseType> {
    return this.http.patch<ICodeTypeSig>(`${this.resourceUrl}/${this.getCodeTypeSigIdentifier(codeType)}`, codeType, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICodeTypeSig>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICodeTypeSig[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCodeTypeSigIdentifier(codeType: Pick<ICodeTypeSig, 'codeTypeId'>): number {
    return codeType.codeTypeId;
  }

  compareCodeTypeSig(o1: Pick<ICodeTypeSig, 'codeTypeId'> | null, o2: Pick<ICodeTypeSig, 'codeTypeId'> | null): boolean {
    return o1 && o2 ? this.getCodeTypeSigIdentifier(o1) === this.getCodeTypeSigIdentifier(o2) : o1 === o2;
  }

  addCodeTypeSigToCollectionIfMissing<Type extends Pick<ICodeTypeSig, 'codeTypeId'>>(
    codeTypeCollection: Type[],
    ...codeTypesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const codeTypes: Type[] = codeTypesToCheck.filter(isPresent);
    if (codeTypes.length > 0) {
      const codeTypeCollectionIdentifiers = codeTypeCollection.map(codeTypeItem => this.getCodeTypeSigIdentifier(codeTypeItem)!);
      const codeTypesToAdd = codeTypes.filter(codeTypeItem => {
        const codeTypeIdentifier = this.getCodeTypeSigIdentifier(codeTypeItem);
        if (codeTypeCollectionIdentifiers.includes(codeTypeIdentifier)) {
          return false;
        }
        codeTypeCollectionIdentifiers.push(codeTypeIdentifier);
        return true;
      });
      return [...codeTypesToAdd, ...codeTypeCollection];
    }
    return codeTypeCollection;
  }
}
