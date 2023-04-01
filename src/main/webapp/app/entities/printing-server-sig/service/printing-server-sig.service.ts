import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPrintingServerSig, NewPrintingServerSig } from '../printing-server-sig.model';

export type PartialUpdatePrintingServerSig = Partial<IPrintingServerSig> & Pick<IPrintingServerSig, 'printingServerId'>;

export type EntityResponseType = HttpResponse<IPrintingServerSig>;
export type EntityArrayResponseType = HttpResponse<IPrintingServerSig[]>;

@Injectable({ providedIn: 'root' })
export class PrintingServerSigService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/printing-servers');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(printingServer: NewPrintingServerSig): Observable<EntityResponseType> {
    return this.http.post<IPrintingServerSig>(this.resourceUrl, printingServer, { observe: 'response' });
  }

  update(printingServer: IPrintingServerSig): Observable<EntityResponseType> {
    return this.http.put<IPrintingServerSig>(`${this.resourceUrl}/${this.getPrintingServerSigIdentifier(printingServer)}`, printingServer, {
      observe: 'response',
    });
  }

  partialUpdate(printingServer: PartialUpdatePrintingServerSig): Observable<EntityResponseType> {
    return this.http.patch<IPrintingServerSig>(
      `${this.resourceUrl}/${this.getPrintingServerSigIdentifier(printingServer)}`,
      printingServer,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPrintingServerSig>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPrintingServerSig[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPrintingServerSigIdentifier(printingServer: Pick<IPrintingServerSig, 'printingServerId'>): number {
    return printingServer.printingServerId;
  }

  comparePrintingServerSig(
    o1: Pick<IPrintingServerSig, 'printingServerId'> | null,
    o2: Pick<IPrintingServerSig, 'printingServerId'> | null
  ): boolean {
    return o1 && o2 ? this.getPrintingServerSigIdentifier(o1) === this.getPrintingServerSigIdentifier(o2) : o1 === o2;
  }

  addPrintingServerSigToCollectionIfMissing<Type extends Pick<IPrintingServerSig, 'printingServerId'>>(
    printingServerCollection: Type[],
    ...printingServersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const printingServers: Type[] = printingServersToCheck.filter(isPresent);
    if (printingServers.length > 0) {
      const printingServerCollectionIdentifiers = printingServerCollection.map(
        printingServerItem => this.getPrintingServerSigIdentifier(printingServerItem)!
      );
      const printingServersToAdd = printingServers.filter(printingServerItem => {
        const printingServerIdentifier = this.getPrintingServerSigIdentifier(printingServerItem);
        if (printingServerCollectionIdentifiers.includes(printingServerIdentifier)) {
          return false;
        }
        printingServerCollectionIdentifiers.push(printingServerIdentifier);
        return true;
      });
      return [...printingServersToAdd, ...printingServerCollection];
    }
    return printingServerCollection;
  }
}
