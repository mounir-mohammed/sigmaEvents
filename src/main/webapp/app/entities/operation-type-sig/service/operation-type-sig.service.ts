import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IOperationTypeSig, NewOperationTypeSig } from '../operation-type-sig.model';

export type PartialUpdateOperationTypeSig = Partial<IOperationTypeSig> & Pick<IOperationTypeSig, 'operationTypeId'>;

export type EntityResponseType = HttpResponse<IOperationTypeSig>;
export type EntityArrayResponseType = HttpResponse<IOperationTypeSig[]>;

@Injectable({ providedIn: 'root' })
export class OperationTypeSigService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/operation-types');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(operationType: NewOperationTypeSig): Observable<EntityResponseType> {
    return this.http.post<IOperationTypeSig>(this.resourceUrl, operationType, { observe: 'response' });
  }

  update(operationType: IOperationTypeSig): Observable<EntityResponseType> {
    return this.http.put<IOperationTypeSig>(`${this.resourceUrl}/${this.getOperationTypeSigIdentifier(operationType)}`, operationType, {
      observe: 'response',
    });
  }

  partialUpdate(operationType: PartialUpdateOperationTypeSig): Observable<EntityResponseType> {
    return this.http.patch<IOperationTypeSig>(`${this.resourceUrl}/${this.getOperationTypeSigIdentifier(operationType)}`, operationType, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOperationTypeSig>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOperationTypeSig[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getOperationTypeSigIdentifier(operationType: Pick<IOperationTypeSig, 'operationTypeId'>): number {
    return operationType.operationTypeId;
  }

  compareOperationTypeSig(
    o1: Pick<IOperationTypeSig, 'operationTypeId'> | null,
    o2: Pick<IOperationTypeSig, 'operationTypeId'> | null
  ): boolean {
    return o1 && o2 ? this.getOperationTypeSigIdentifier(o1) === this.getOperationTypeSigIdentifier(o2) : o1 === o2;
  }

  addOperationTypeSigToCollectionIfMissing<Type extends Pick<IOperationTypeSig, 'operationTypeId'>>(
    operationTypeCollection: Type[],
    ...operationTypesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const operationTypes: Type[] = operationTypesToCheck.filter(isPresent);
    if (operationTypes.length > 0) {
      const operationTypeCollectionIdentifiers = operationTypeCollection.map(
        operationTypeItem => this.getOperationTypeSigIdentifier(operationTypeItem)!
      );
      const operationTypesToAdd = operationTypes.filter(operationTypeItem => {
        const operationTypeIdentifier = this.getOperationTypeSigIdentifier(operationTypeItem);
        if (operationTypeCollectionIdentifiers.includes(operationTypeIdentifier)) {
          return false;
        }
        operationTypeCollectionIdentifiers.push(operationTypeIdentifier);
        return true;
      });
      return [...operationTypesToAdd, ...operationTypeCollection];
    }
    return operationTypeCollection;
  }
}
