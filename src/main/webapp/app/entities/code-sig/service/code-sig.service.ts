import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICodeSig, NewCodeSig } from '../code-sig.model';

export type PartialUpdateCodeSig = Partial<ICodeSig> & Pick<ICodeSig, 'codeId'>;

export type EntityResponseType = HttpResponse<ICodeSig>;
export type EntityArrayResponseType = HttpResponse<ICodeSig[]>;

@Injectable({ providedIn: 'root' })
export class CodeSigService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/codes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(code: NewCodeSig): Observable<EntityResponseType> {
    return this.http.post<ICodeSig>(this.resourceUrl, code, { observe: 'response' });
  }

  update(code: ICodeSig): Observable<EntityResponseType> {
    return this.http.put<ICodeSig>(`${this.resourceUrl}/${this.getCodeSigIdentifier(code)}`, code, { observe: 'response' });
  }

  partialUpdate(code: PartialUpdateCodeSig): Observable<EntityResponseType> {
    return this.http.patch<ICodeSig>(`${this.resourceUrl}/${this.getCodeSigIdentifier(code)}`, code, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICodeSig>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICodeSig[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCodeSigIdentifier(code: Pick<ICodeSig, 'codeId'>): number {
    return code.codeId;
  }

  compareCodeSig(o1: Pick<ICodeSig, 'codeId'> | null, o2: Pick<ICodeSig, 'codeId'> | null): boolean {
    return o1 && o2 ? this.getCodeSigIdentifier(o1) === this.getCodeSigIdentifier(o2) : o1 === o2;
  }

  addCodeSigToCollectionIfMissing<Type extends Pick<ICodeSig, 'codeId'>>(
    codeCollection: Type[],
    ...codesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const codes: Type[] = codesToCheck.filter(isPresent);
    if (codes.length > 0) {
      const codeCollectionIdentifiers = codeCollection.map(codeItem => this.getCodeSigIdentifier(codeItem)!);
      const codesToAdd = codes.filter(codeItem => {
        const codeIdentifier = this.getCodeSigIdentifier(codeItem);
        if (codeCollectionIdentifiers.includes(codeIdentifier)) {
          return false;
        }
        codeCollectionIdentifiers.push(codeIdentifier);
        return true;
      });
      return [...codesToAdd, ...codeCollection];
    }
    return codeCollection;
  }
}
