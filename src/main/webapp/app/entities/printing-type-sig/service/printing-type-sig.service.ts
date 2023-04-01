import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPrintingTypeSig, NewPrintingTypeSig } from '../printing-type-sig.model';

export type PartialUpdatePrintingTypeSig = Partial<IPrintingTypeSig> & Pick<IPrintingTypeSig, 'printingTypeId'>;

export type EntityResponseType = HttpResponse<IPrintingTypeSig>;
export type EntityArrayResponseType = HttpResponse<IPrintingTypeSig[]>;

@Injectable({ providedIn: 'root' })
export class PrintingTypeSigService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/printing-types');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(printingType: NewPrintingTypeSig): Observable<EntityResponseType> {
    return this.http.post<IPrintingTypeSig>(this.resourceUrl, printingType, { observe: 'response' });
  }

  update(printingType: IPrintingTypeSig): Observable<EntityResponseType> {
    return this.http.put<IPrintingTypeSig>(`${this.resourceUrl}/${this.getPrintingTypeSigIdentifier(printingType)}`, printingType, {
      observe: 'response',
    });
  }

  partialUpdate(printingType: PartialUpdatePrintingTypeSig): Observable<EntityResponseType> {
    return this.http.patch<IPrintingTypeSig>(`${this.resourceUrl}/${this.getPrintingTypeSigIdentifier(printingType)}`, printingType, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPrintingTypeSig>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPrintingTypeSig[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPrintingTypeSigIdentifier(printingType: Pick<IPrintingTypeSig, 'printingTypeId'>): number {
    return printingType.printingTypeId;
  }

  comparePrintingTypeSig(
    o1: Pick<IPrintingTypeSig, 'printingTypeId'> | null,
    o2: Pick<IPrintingTypeSig, 'printingTypeId'> | null
  ): boolean {
    return o1 && o2 ? this.getPrintingTypeSigIdentifier(o1) === this.getPrintingTypeSigIdentifier(o2) : o1 === o2;
  }

  addPrintingTypeSigToCollectionIfMissing<Type extends Pick<IPrintingTypeSig, 'printingTypeId'>>(
    printingTypeCollection: Type[],
    ...printingTypesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const printingTypes: Type[] = printingTypesToCheck.filter(isPresent);
    if (printingTypes.length > 0) {
      const printingTypeCollectionIdentifiers = printingTypeCollection.map(
        printingTypeItem => this.getPrintingTypeSigIdentifier(printingTypeItem)!
      );
      const printingTypesToAdd = printingTypes.filter(printingTypeItem => {
        const printingTypeIdentifier = this.getPrintingTypeSigIdentifier(printingTypeItem);
        if (printingTypeCollectionIdentifiers.includes(printingTypeIdentifier)) {
          return false;
        }
        printingTypeCollectionIdentifiers.push(printingTypeIdentifier);
        return true;
      });
      return [...printingTypesToAdd, ...printingTypeCollection];
    }
    return printingTypeCollection;
  }
}
