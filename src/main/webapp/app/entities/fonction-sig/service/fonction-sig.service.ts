import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFonctionSig, NewFonctionSig } from '../fonction-sig.model';

export type PartialUpdateFonctionSig = Partial<IFonctionSig> & Pick<IFonctionSig, 'fonctionId'>;

export type EntityResponseType = HttpResponse<IFonctionSig>;
export type EntityArrayResponseType = HttpResponse<IFonctionSig[]>;

@Injectable({ providedIn: 'root' })
export class FonctionSigService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/fonctions');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(fonction: NewFonctionSig): Observable<EntityResponseType> {
    return this.http.post<IFonctionSig>(this.resourceUrl, fonction, { observe: 'response' });
  }

  update(fonction: IFonctionSig): Observable<EntityResponseType> {
    return this.http.put<IFonctionSig>(`${this.resourceUrl}/${this.getFonctionSigIdentifier(fonction)}`, fonction, { observe: 'response' });
  }

  partialUpdate(fonction: PartialUpdateFonctionSig): Observable<EntityResponseType> {
    return this.http.patch<IFonctionSig>(`${this.resourceUrl}/${this.getFonctionSigIdentifier(fonction)}`, fonction, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFonctionSig>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFonctionSig[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFonctionSigIdentifier(fonction: Pick<IFonctionSig, 'fonctionId'>): number {
    return fonction.fonctionId;
  }

  compareFonctionSig(o1: Pick<IFonctionSig, 'fonctionId'> | null, o2: Pick<IFonctionSig, 'fonctionId'> | null): boolean {
    return o1 && o2 ? this.getFonctionSigIdentifier(o1) === this.getFonctionSigIdentifier(o2) : o1 === o2;
  }

  addFonctionSigToCollectionIfMissing<Type extends Pick<IFonctionSig, 'fonctionId'>>(
    fonctionCollection: Type[],
    ...fonctionsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const fonctions: Type[] = fonctionsToCheck.filter(isPresent);
    if (fonctions.length > 0) {
      const fonctionCollectionIdentifiers = fonctionCollection.map(fonctionItem => this.getFonctionSigIdentifier(fonctionItem)!);
      const fonctionsToAdd = fonctions.filter(fonctionItem => {
        const fonctionIdentifier = this.getFonctionSigIdentifier(fonctionItem);
        if (fonctionCollectionIdentifiers.includes(fonctionIdentifier)) {
          return false;
        }
        fonctionCollectionIdentifiers.push(fonctionIdentifier);
        return true;
      });
      return [...fonctionsToAdd, ...fonctionCollection];
    }
    return fonctionCollection;
  }
}
