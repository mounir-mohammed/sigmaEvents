import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPrintingModelSig, NewPrintingModelSig } from '../printing-model-sig.model';
import { DataUtils } from 'app/core/util/data-util.service';

export type PartialUpdatePrintingModelSig = Partial<IPrintingModelSig> & Pick<IPrintingModelSig, 'printingModelId'>;

export type EntityResponseType = HttpResponse<IPrintingModelSig>;
export type EntityArrayResponseType = HttpResponse<IPrintingModelSig[]>;

@Injectable({ providedIn: 'root' })
export class PrintingModelSigService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/printing-models');

  private printingModelCache: Map<string, any> = new Map<string, any>();

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService, protected dataUtils: DataUtils) {}

  create(printingModel: NewPrintingModelSig): Observable<EntityResponseType> {
    return this.http.post<IPrintingModelSig>(this.resourceUrl, printingModel, { observe: 'response' });
  }

  update(printingModel: IPrintingModelSig): Observable<EntityResponseType> {
    this.updatePrintingModelCache(printingModel.printingModelId);
    return this.http.put<IPrintingModelSig>(`${this.resourceUrl}/${this.getPrintingModelSigIdentifier(printingModel)}`, printingModel, {
      observe: 'response',
    });
  }

  partialUpdate(printingModel: PartialUpdatePrintingModelSig): Observable<EntityResponseType> {
    this.updatePrintingModelCache(printingModel.printingModelId);
    return this.http.patch<IPrintingModelSig>(`${this.resourceUrl}/${this.getPrintingModelSigIdentifier(printingModel)}`, printingModel, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPrintingModelSig>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPrintingModelSig[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    this.updatePrintingModelCache(id);
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPrintingModelSigIdentifier(printingModel: Pick<IPrintingModelSig, 'printingModelId'>): number {
    return printingModel.printingModelId;
  }

  comparePrintingModelSig(
    o1: Pick<IPrintingModelSig, 'printingModelId'> | null,
    o2: Pick<IPrintingModelSig, 'printingModelId'> | null
  ): boolean {
    return o1 && o2 ? this.getPrintingModelSigIdentifier(o1) === this.getPrintingModelSigIdentifier(o2) : o1 === o2;
  }

  addPrintingModelSigToCollectionIfMissing<Type extends Pick<IPrintingModelSig, 'printingModelId'>>(
    printingModelCollection: Type[],
    ...printingModelsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const printingModels: Type[] = printingModelsToCheck.filter(isPresent);
    if (printingModels.length > 0) {
      const printingModelCollectionIdentifiers = printingModelCollection.map(
        printingModelItem => this.getPrintingModelSigIdentifier(printingModelItem)!
      );
      const printingModelsToAdd = printingModels.filter(printingModelItem => {
        const printingModelIdentifier = this.getPrintingModelSigIdentifier(printingModelItem);
        if (printingModelCollectionIdentifiers.includes(printingModelIdentifier)) {
          return false;
        }
        printingModelCollectionIdentifiers.push(printingModelIdentifier);
        return true;
      });
      return [...printingModelsToAdd, ...printingModelCollection];
    }
    return printingModelCollection;
  }

  async getPrintingModelConfig(modelId: number): Promise<any> {
    console.log('START getPrintingModelConfig()');

    // Check if the printing model data is already in the cache
    if (this.printingModelCache.has(this.getIdPrintingModelSigIdentifier(modelId))) {
      return this.printingModelCache.get(this.getIdPrintingModelSigIdentifier(modelId));
    }
    try {
      if (modelId) {
        const resp = await this.find(modelId).toPromise();
        if (resp!.status === 200) {
          const printingModel = resp!.body;
          if (printingModel) {
            if (printingModel.printingModelStat) {
              if (printingModel.printingModelData) {
                const modelData = this.dataUtils.base64ToJson(printingModel.printingModelData);
                if (modelData) {
                  // Store the printing model data in the cache
                  this.printingModelCache.set(this.getIdPrintingModelSigIdentifier(modelId), modelData);
                  return modelData;
                } else {
                  console.error('getPrintingModelConfig() => PRINTING MODEL PARSING ERROR');
                  return false;
                }
              } else {
                console.error('getPrintingModelConfig() => PRINTING MODEL DATA IS EMPTY');
                return false;
              }
            } else {
              console.error('getPrintingModelConfig() => PRINTING MODEL STATE NOT ACTIVATED');
              return false;
            }
          } else {
            console.error('getPrintingModelConfig(): RESPONSE EMPTY');
            return false;
          }
        } else {
          console.error('getPrintingModelConfig(): NETWORK ERROR' + resp!.status);
          return false;
        }
      } else {
        console.error('getPrintingModelConfig(): modelId IS NULL');
        return false;
      }
    } catch (error: any) {
      console.error(error!.message!);
      return false;
    }
  }

  updatePrintingModelCache(id: number): void {
    this.printingModelCache.delete(this.getIdPrintingModelSigIdentifier(id));
  }

  getIdPrintingModelSigIdentifier(id: number): string {
    return id.toString();
  }

  resetPrintingModelCache(): void {
    this.printingModelCache.clear();
  }
}
