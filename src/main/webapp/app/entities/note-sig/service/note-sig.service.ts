import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { INoteSig, NewNoteSig } from '../note-sig.model';

export type PartialUpdateNoteSig = Partial<INoteSig> & Pick<INoteSig, 'noteId'>;

export type EntityResponseType = HttpResponse<INoteSig>;
export type EntityArrayResponseType = HttpResponse<INoteSig[]>;

@Injectable({ providedIn: 'root' })
export class NoteSigService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/notes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(note: NewNoteSig): Observable<EntityResponseType> {
    return this.http.post<INoteSig>(this.resourceUrl, note, { observe: 'response' });
  }

  update(note: INoteSig): Observable<EntityResponseType> {
    return this.http.put<INoteSig>(`${this.resourceUrl}/${this.getNoteSigIdentifier(note)}`, note, { observe: 'response' });
  }

  partialUpdate(note: PartialUpdateNoteSig): Observable<EntityResponseType> {
    return this.http.patch<INoteSig>(`${this.resourceUrl}/${this.getNoteSigIdentifier(note)}`, note, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INoteSig>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INoteSig[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getNoteSigIdentifier(note: Pick<INoteSig, 'noteId'>): number {
    return note.noteId;
  }

  compareNoteSig(o1: Pick<INoteSig, 'noteId'> | null, o2: Pick<INoteSig, 'noteId'> | null): boolean {
    return o1 && o2 ? this.getNoteSigIdentifier(o1) === this.getNoteSigIdentifier(o2) : o1 === o2;
  }

  addNoteSigToCollectionIfMissing<Type extends Pick<INoteSig, 'noteId'>>(
    noteCollection: Type[],
    ...notesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const notes: Type[] = notesToCheck.filter(isPresent);
    if (notes.length > 0) {
      const noteCollectionIdentifiers = noteCollection.map(noteItem => this.getNoteSigIdentifier(noteItem)!);
      const notesToAdd = notes.filter(noteItem => {
        const noteIdentifier = this.getNoteSigIdentifier(noteItem);
        if (noteCollectionIdentifiers.includes(noteIdentifier)) {
          return false;
        }
        noteCollectionIdentifiers.push(noteIdentifier);
        return true;
      });
      return [...notesToAdd, ...noteCollection];
    }
    return noteCollection;
  }
}
