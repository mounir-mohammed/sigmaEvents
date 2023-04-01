import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILanguageSig, NewLanguageSig } from '../language-sig.model';

export type PartialUpdateLanguageSig = Partial<ILanguageSig> & Pick<ILanguageSig, 'languageId'>;

export type EntityResponseType = HttpResponse<ILanguageSig>;
export type EntityArrayResponseType = HttpResponse<ILanguageSig[]>;

@Injectable({ providedIn: 'root' })
export class LanguageSigService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/languages');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(language: NewLanguageSig): Observable<EntityResponseType> {
    return this.http.post<ILanguageSig>(this.resourceUrl, language, { observe: 'response' });
  }

  update(language: ILanguageSig): Observable<EntityResponseType> {
    return this.http.put<ILanguageSig>(`${this.resourceUrl}/${this.getLanguageSigIdentifier(language)}`, language, { observe: 'response' });
  }

  partialUpdate(language: PartialUpdateLanguageSig): Observable<EntityResponseType> {
    return this.http.patch<ILanguageSig>(`${this.resourceUrl}/${this.getLanguageSigIdentifier(language)}`, language, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILanguageSig>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILanguageSig[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getLanguageSigIdentifier(language: Pick<ILanguageSig, 'languageId'>): number {
    return language.languageId;
  }

  compareLanguageSig(o1: Pick<ILanguageSig, 'languageId'> | null, o2: Pick<ILanguageSig, 'languageId'> | null): boolean {
    return o1 && o2 ? this.getLanguageSigIdentifier(o1) === this.getLanguageSigIdentifier(o2) : o1 === o2;
  }

  addLanguageSigToCollectionIfMissing<Type extends Pick<ILanguageSig, 'languageId'>>(
    languageCollection: Type[],
    ...languagesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const languages: Type[] = languagesToCheck.filter(isPresent);
    if (languages.length > 0) {
      const languageCollectionIdentifiers = languageCollection.map(languageItem => this.getLanguageSigIdentifier(languageItem)!);
      const languagesToAdd = languages.filter(languageItem => {
        const languageIdentifier = this.getLanguageSigIdentifier(languageItem);
        if (languageCollectionIdentifiers.includes(languageIdentifier)) {
          return false;
        }
        languageCollectionIdentifiers.push(languageIdentifier);
        return true;
      });
      return [...languagesToAdd, ...languageCollection];
    }
    return languageCollection;
  }
}
