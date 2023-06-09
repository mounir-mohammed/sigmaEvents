import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { PrintingCentreSigFormService, PrintingCentreSigFormGroup } from './printing-centre-sig-form.service';
import { IPrintingCentreSig } from '../printing-centre-sig.model';
import { PrintingCentreSigService } from '../service/printing-centre-sig.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { ICitySig } from 'app/entities/city-sig/city-sig.model';
import { CitySigService } from 'app/entities/city-sig/service/city-sig.service';
import { ICountrySig } from 'app/entities/country-sig/country-sig.model';
import { CountrySigService } from 'app/entities/country-sig/service/country-sig.service';
import { IOrganizSig } from 'app/entities/organiz-sig/organiz-sig.model';
import { OrganizSigService } from 'app/entities/organiz-sig/service/organiz-sig.service';
import { IPrintingTypeSig } from 'app/entities/printing-type-sig/printing-type-sig.model';
import { PrintingTypeSigService } from 'app/entities/printing-type-sig/service/printing-type-sig.service';
import { IPrintingServerSig } from 'app/entities/printing-server-sig/printing-server-sig.model';
import { PrintingServerSigService } from 'app/entities/printing-server-sig/service/printing-server-sig.service';
import { IPrintingModelSig } from 'app/entities/printing-model-sig/printing-model-sig.model';
import { PrintingModelSigService } from 'app/entities/printing-model-sig/service/printing-model-sig.service';
import { ILanguageSig } from 'app/entities/language-sig/language-sig.model';
import { LanguageSigService } from 'app/entities/language-sig/service/language-sig.service';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';
import { EventSigService } from 'app/entities/event-sig/service/event-sig.service';
import { RECORD_ITEMS } from 'app/config/pagination.constants';

@Component({
  selector: 'sigma-printing-centre-sig-update',
  templateUrl: './printing-centre-sig-update.component.html',
})
export class PrintingCentreSigUpdateComponent implements OnInit {
  isSaving = false;
  printingCentre: IPrintingCentreSig | null = null;

  citiesSharedCollection: ICitySig[] = [];
  countriesSharedCollection: ICountrySig[] = [];
  organizsSharedCollection: IOrganizSig[] = [];
  printingTypesSharedCollection: IPrintingTypeSig[] = [];
  printingServersSharedCollection: IPrintingServerSig[] = [];
  printingModelsSharedCollection: IPrintingModelSig[] = [];
  languagesSharedCollection: ILanguageSig[] = [];
  eventsSharedCollection: IEventSig[] = [];

  editForm: PrintingCentreSigFormGroup = this.printingCentreFormService.createPrintingCentreSigFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected printingCentreService: PrintingCentreSigService,
    protected printingCentreFormService: PrintingCentreSigFormService,
    protected cityService: CitySigService,
    protected countryService: CountrySigService,
    protected organizService: OrganizSigService,
    protected printingTypeService: PrintingTypeSigService,
    protected printingServerService: PrintingServerSigService,
    protected printingModelService: PrintingModelSigService,
    protected languageService: LanguageSigService,
    protected eventService: EventSigService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareCitySig = (o1: ICitySig | null, o2: ICitySig | null): boolean => this.cityService.compareCitySig(o1, o2);

  compareCountrySig = (o1: ICountrySig | null, o2: ICountrySig | null): boolean => this.countryService.compareCountrySig(o1, o2);

  compareOrganizSig = (o1: IOrganizSig | null, o2: IOrganizSig | null): boolean => this.organizService.compareOrganizSig(o1, o2);

  comparePrintingTypeSig = (o1: IPrintingTypeSig | null, o2: IPrintingTypeSig | null): boolean =>
    this.printingTypeService.comparePrintingTypeSig(o1, o2);

  comparePrintingServerSig = (o1: IPrintingServerSig | null, o2: IPrintingServerSig | null): boolean =>
    this.printingServerService.comparePrintingServerSig(o1, o2);

  comparePrintingModelSig = (o1: IPrintingModelSig | null, o2: IPrintingModelSig | null): boolean =>
    this.printingModelService.comparePrintingModelSig(o1, o2);

  compareLanguageSig = (o1: ILanguageSig | null, o2: ILanguageSig | null): boolean => this.languageService.compareLanguageSig(o1, o2);

  compareEventSig = (o1: IEventSig | null, o2: IEventSig | null): boolean => this.eventService.compareEventSig(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ printingCentre }) => {
      this.printingCentre = printingCentre;
      if (printingCentre) {
        this.updateForm(printingCentre);
      }

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('sigmaEventsApp.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const printingCentre = this.printingCentreFormService.getPrintingCentreSig(this.editForm);
    if (printingCentre.printingCentreId !== null) {
      this.subscribeToSaveResponse(this.printingCentreService.update(printingCentre));
    } else {
      this.subscribeToSaveResponse(this.printingCentreService.create(printingCentre));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPrintingCentreSig>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(printingCentre: IPrintingCentreSig): void {
    this.printingCentre = printingCentre;
    this.printingCentreFormService.resetForm(this.editForm, printingCentre);

    this.citiesSharedCollection = this.cityService.addCitySigToCollectionIfMissing<ICitySig>(
      this.citiesSharedCollection,
      printingCentre.city
    );
    this.countriesSharedCollection = this.countryService.addCountrySigToCollectionIfMissing<ICountrySig>(
      this.countriesSharedCollection,
      printingCentre.country
    );
    this.organizsSharedCollection = this.organizService.addOrganizSigToCollectionIfMissing<IOrganizSig>(
      this.organizsSharedCollection,
      printingCentre.organiz
    );
    this.printingTypesSharedCollection = this.printingTypeService.addPrintingTypeSigToCollectionIfMissing<IPrintingTypeSig>(
      this.printingTypesSharedCollection,
      printingCentre.printingType
    );
    this.printingServersSharedCollection = this.printingServerService.addPrintingServerSigToCollectionIfMissing<IPrintingServerSig>(
      this.printingServersSharedCollection,
      printingCentre.printingServer
    );
    this.printingModelsSharedCollection = this.printingModelService.addPrintingModelSigToCollectionIfMissing<IPrintingModelSig>(
      this.printingModelsSharedCollection,
      printingCentre.printingModel
    );
    this.languagesSharedCollection = this.languageService.addLanguageSigToCollectionIfMissing<ILanguageSig>(
      this.languagesSharedCollection,
      printingCentre.language
    );
    this.eventsSharedCollection = this.eventService.addEventSigToCollectionIfMissing<IEventSig>(
      this.eventsSharedCollection,
      printingCentre.event
    );
  }

  protected loadRelationshipsOptions(): void {
    this.cityService
      .query({ size: RECORD_ITEMS })
      .pipe(map((res: HttpResponse<ICitySig[]>) => res.body ?? []))
      .pipe(map((cities: ICitySig[]) => this.cityService.addCitySigToCollectionIfMissing<ICitySig>(cities, this.printingCentre?.city)))
      .subscribe((cities: ICitySig[]) => (this.citiesSharedCollection = cities));

    this.countryService
      .query({ size: RECORD_ITEMS })
      .pipe(map((res: HttpResponse<ICountrySig[]>) => res.body ?? []))
      .pipe(
        map((countries: ICountrySig[]) =>
          this.countryService.addCountrySigToCollectionIfMissing<ICountrySig>(countries, this.printingCentre?.country)
        )
      )
      .subscribe((countries: ICountrySig[]) => (this.countriesSharedCollection = countries));

    this.organizService
      .query({ size: RECORD_ITEMS })
      .pipe(map((res: HttpResponse<IOrganizSig[]>) => res.body ?? []))
      .pipe(
        map((organizs: IOrganizSig[]) =>
          this.organizService.addOrganizSigToCollectionIfMissing<IOrganizSig>(organizs, this.printingCentre?.organiz)
        )
      )
      .subscribe((organizs: IOrganizSig[]) => (this.organizsSharedCollection = organizs));

    this.printingTypeService
      .query({ size: RECORD_ITEMS })
      .pipe(map((res: HttpResponse<IPrintingTypeSig[]>) => res.body ?? []))
      .pipe(
        map((printingTypes: IPrintingTypeSig[]) =>
          this.printingTypeService.addPrintingTypeSigToCollectionIfMissing<IPrintingTypeSig>(
            printingTypes,
            this.printingCentre?.printingType
          )
        )
      )
      .subscribe((printingTypes: IPrintingTypeSig[]) => (this.printingTypesSharedCollection = printingTypes));

    this.printingServerService
      .query({ size: RECORD_ITEMS })
      .pipe(map((res: HttpResponse<IPrintingServerSig[]>) => res.body ?? []))
      .pipe(
        map((printingServers: IPrintingServerSig[]) =>
          this.printingServerService.addPrintingServerSigToCollectionIfMissing<IPrintingServerSig>(
            printingServers,
            this.printingCentre?.printingServer
          )
        )
      )
      .subscribe((printingServers: IPrintingServerSig[]) => (this.printingServersSharedCollection = printingServers));

    this.printingModelService
      .query({ size: RECORD_ITEMS })
      .pipe(map((res: HttpResponse<IPrintingModelSig[]>) => res.body ?? []))
      .pipe(
        map((printingModels: IPrintingModelSig[]) =>
          this.printingModelService.addPrintingModelSigToCollectionIfMissing<IPrintingModelSig>(
            printingModels,
            this.printingCentre?.printingModel
          )
        )
      )
      .subscribe((printingModels: IPrintingModelSig[]) => (this.printingModelsSharedCollection = printingModels));

    this.languageService
      .query({ size: RECORD_ITEMS })
      .pipe(map((res: HttpResponse<ILanguageSig[]>) => res.body ?? []))
      .pipe(
        map((languages: ILanguageSig[]) =>
          this.languageService.addLanguageSigToCollectionIfMissing<ILanguageSig>(languages, this.printingCentre?.language)
        )
      )
      .subscribe((languages: ILanguageSig[]) => (this.languagesSharedCollection = languages));

    this.eventService
      .query({ size: RECORD_ITEMS })
      .pipe(map((res: HttpResponse<IEventSig[]>) => res.body ?? []))
      .pipe(map((events: IEventSig[]) => this.eventService.addEventSigToCollectionIfMissing<IEventSig>(events, this.printingCentre?.event)))
      .subscribe((events: IEventSig[]) => (this.eventsSharedCollection = events));
  }
}
