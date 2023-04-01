import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPrintingCentreSig, NewPrintingCentreSig } from '../printing-centre-sig.model';

export type PartialUpdatePrintingCentreSig = Partial<IPrintingCentreSig> & Pick<IPrintingCentreSig, 'printingCentreId'>;

export type EntityResponseType = HttpResponse<IPrintingCentreSig>;
export type EntityArrayResponseType = HttpResponse<IPrintingCentreSig[]>;

@Injectable({ providedIn: 'root' })
export class PrintingCentreSigService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/printing-centres');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(printingCentre: NewPrintingCentreSig): Observable<EntityResponseType> {
    return this.http.post<IPrintingCentreSig>(this.resourceUrl, printingCentre, { observe: 'response' });
  }

  update(printingCentre: IPrintingCentreSig): Observable<EntityResponseType> {
    return this.http.put<IPrintingCentreSig>(`${this.resourceUrl}/${this.getPrintingCentreSigIdentifier(printingCentre)}`, printingCentre, {
      observe: 'response',
    });
  }

  partialUpdate(printingCentre: PartialUpdatePrintingCentreSig): Observable<EntityResponseType> {
    return this.http.patch<IPrintingCentreSig>(
      `${this.resourceUrl}/${this.getPrintingCentreSigIdentifier(printingCentre)}`,
      printingCentre,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPrintingCentreSig>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPrintingCentreSig[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPrintingCentreSigIdentifier(printingCentre: Pick<IPrintingCentreSig, 'printingCentreId'>): number {
    return printingCentre.printingCentreId;
  }

  comparePrintingCentreSig(
    o1: Pick<IPrintingCentreSig, 'printingCentreId'> | null,
    o2: Pick<IPrintingCentreSig, 'printingCentreId'> | null
  ): boolean {
    return o1 && o2 ? this.getPrintingCentreSigIdentifier(o1) === this.getPrintingCentreSigIdentifier(o2) : o1 === o2;
  }

  addPrintingCentreSigToCollectionIfMissing<Type extends Pick<IPrintingCentreSig, 'printingCentreId'>>(
    printingCentreCollection: Type[],
    ...printingCentresToCheck: (Type | null | undefined)[]
  ): Type[] {
    const printingCentres: Type[] = printingCentresToCheck.filter(isPresent);
    if (printingCentres.length > 0) {
      const printingCentreCollectionIdentifiers = printingCentreCollection.map(
        printingCentreItem => this.getPrintingCentreSigIdentifier(printingCentreItem)!
      );
      const printingCentresToAdd = printingCentres.filter(printingCentreItem => {
        const printingCentreIdentifier = this.getPrintingCentreSigIdentifier(printingCentreItem);
        if (printingCentreCollectionIdentifiers.includes(printingCentreIdentifier)) {
          return false;
        }
        printingCentreCollectionIdentifiers.push(printingCentreIdentifier);
        return true;
      });
      return [...printingCentresToAdd, ...printingCentreCollection];
    }
    return printingCentreCollection;
  }
}
