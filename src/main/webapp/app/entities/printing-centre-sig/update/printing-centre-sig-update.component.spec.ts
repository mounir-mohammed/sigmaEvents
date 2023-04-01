import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PrintingCentreSigFormService } from './printing-centre-sig-form.service';
import { PrintingCentreSigService } from '../service/printing-centre-sig.service';
import { IPrintingCentreSig } from '../printing-centre-sig.model';
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

import { PrintingCentreSigUpdateComponent } from './printing-centre-sig-update.component';

describe('PrintingCentreSig Management Update Component', () => {
  let comp: PrintingCentreSigUpdateComponent;
  let fixture: ComponentFixture<PrintingCentreSigUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let printingCentreFormService: PrintingCentreSigFormService;
  let printingCentreService: PrintingCentreSigService;
  let cityService: CitySigService;
  let countryService: CountrySigService;
  let organizService: OrganizSigService;
  let printingTypeService: PrintingTypeSigService;
  let printingServerService: PrintingServerSigService;
  let printingModelService: PrintingModelSigService;
  let languageService: LanguageSigService;
  let eventService: EventSigService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PrintingCentreSigUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(PrintingCentreSigUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PrintingCentreSigUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    printingCentreFormService = TestBed.inject(PrintingCentreSigFormService);
    printingCentreService = TestBed.inject(PrintingCentreSigService);
    cityService = TestBed.inject(CitySigService);
    countryService = TestBed.inject(CountrySigService);
    organizService = TestBed.inject(OrganizSigService);
    printingTypeService = TestBed.inject(PrintingTypeSigService);
    printingServerService = TestBed.inject(PrintingServerSigService);
    printingModelService = TestBed.inject(PrintingModelSigService);
    languageService = TestBed.inject(LanguageSigService);
    eventService = TestBed.inject(EventSigService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call CitySig query and add missing value', () => {
      const printingCentre: IPrintingCentreSig = { printingCentreId: 456 };
      const city: ICitySig = { cityId: 70764 };
      printingCentre.city = city;

      const cityCollection: ICitySig[] = [{ cityId: 56139 }];
      jest.spyOn(cityService, 'query').mockReturnValue(of(new HttpResponse({ body: cityCollection })));
      const additionalCitySigs = [city];
      const expectedCollection: ICitySig[] = [...additionalCitySigs, ...cityCollection];
      jest.spyOn(cityService, 'addCitySigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ printingCentre });
      comp.ngOnInit();

      expect(cityService.query).toHaveBeenCalled();
      expect(cityService.addCitySigToCollectionIfMissing).toHaveBeenCalledWith(
        cityCollection,
        ...additionalCitySigs.map(expect.objectContaining)
      );
      expect(comp.citiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call CountrySig query and add missing value', () => {
      const printingCentre: IPrintingCentreSig = { printingCentreId: 456 };
      const country: ICountrySig = { countryId: 72290 };
      printingCentre.country = country;

      const countryCollection: ICountrySig[] = [{ countryId: 4430 }];
      jest.spyOn(countryService, 'query').mockReturnValue(of(new HttpResponse({ body: countryCollection })));
      const additionalCountrySigs = [country];
      const expectedCollection: ICountrySig[] = [...additionalCountrySigs, ...countryCollection];
      jest.spyOn(countryService, 'addCountrySigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ printingCentre });
      comp.ngOnInit();

      expect(countryService.query).toHaveBeenCalled();
      expect(countryService.addCountrySigToCollectionIfMissing).toHaveBeenCalledWith(
        countryCollection,
        ...additionalCountrySigs.map(expect.objectContaining)
      );
      expect(comp.countriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call OrganizSig query and add missing value', () => {
      const printingCentre: IPrintingCentreSig = { printingCentreId: 456 };
      const organiz: IOrganizSig = { organizId: 40763 };
      printingCentre.organiz = organiz;

      const organizCollection: IOrganizSig[] = [{ organizId: 54001 }];
      jest.spyOn(organizService, 'query').mockReturnValue(of(new HttpResponse({ body: organizCollection })));
      const additionalOrganizSigs = [organiz];
      const expectedCollection: IOrganizSig[] = [...additionalOrganizSigs, ...organizCollection];
      jest.spyOn(organizService, 'addOrganizSigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ printingCentre });
      comp.ngOnInit();

      expect(organizService.query).toHaveBeenCalled();
      expect(organizService.addOrganizSigToCollectionIfMissing).toHaveBeenCalledWith(
        organizCollection,
        ...additionalOrganizSigs.map(expect.objectContaining)
      );
      expect(comp.organizsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call PrintingTypeSig query and add missing value', () => {
      const printingCentre: IPrintingCentreSig = { printingCentreId: 456 };
      const printingType: IPrintingTypeSig = { printingTypeId: 8567 };
      printingCentre.printingType = printingType;

      const printingTypeCollection: IPrintingTypeSig[] = [{ printingTypeId: 89961 }];
      jest.spyOn(printingTypeService, 'query').mockReturnValue(of(new HttpResponse({ body: printingTypeCollection })));
      const additionalPrintingTypeSigs = [printingType];
      const expectedCollection: IPrintingTypeSig[] = [...additionalPrintingTypeSigs, ...printingTypeCollection];
      jest.spyOn(printingTypeService, 'addPrintingTypeSigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ printingCentre });
      comp.ngOnInit();

      expect(printingTypeService.query).toHaveBeenCalled();
      expect(printingTypeService.addPrintingTypeSigToCollectionIfMissing).toHaveBeenCalledWith(
        printingTypeCollection,
        ...additionalPrintingTypeSigs.map(expect.objectContaining)
      );
      expect(comp.printingTypesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call PrintingServerSig query and add missing value', () => {
      const printingCentre: IPrintingCentreSig = { printingCentreId: 456 };
      const printingServer: IPrintingServerSig = { printingServerId: 89366 };
      printingCentre.printingServer = printingServer;

      const printingServerCollection: IPrintingServerSig[] = [{ printingServerId: 34399 }];
      jest.spyOn(printingServerService, 'query').mockReturnValue(of(new HttpResponse({ body: printingServerCollection })));
      const additionalPrintingServerSigs = [printingServer];
      const expectedCollection: IPrintingServerSig[] = [...additionalPrintingServerSigs, ...printingServerCollection];
      jest.spyOn(printingServerService, 'addPrintingServerSigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ printingCentre });
      comp.ngOnInit();

      expect(printingServerService.query).toHaveBeenCalled();
      expect(printingServerService.addPrintingServerSigToCollectionIfMissing).toHaveBeenCalledWith(
        printingServerCollection,
        ...additionalPrintingServerSigs.map(expect.objectContaining)
      );
      expect(comp.printingServersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call PrintingModelSig query and add missing value', () => {
      const printingCentre: IPrintingCentreSig = { printingCentreId: 456 };
      const printingModel: IPrintingModelSig = { printingModelId: 39434 };
      printingCentre.printingModel = printingModel;

      const printingModelCollection: IPrintingModelSig[] = [{ printingModelId: 46227 }];
      jest.spyOn(printingModelService, 'query').mockReturnValue(of(new HttpResponse({ body: printingModelCollection })));
      const additionalPrintingModelSigs = [printingModel];
      const expectedCollection: IPrintingModelSig[] = [...additionalPrintingModelSigs, ...printingModelCollection];
      jest.spyOn(printingModelService, 'addPrintingModelSigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ printingCentre });
      comp.ngOnInit();

      expect(printingModelService.query).toHaveBeenCalled();
      expect(printingModelService.addPrintingModelSigToCollectionIfMissing).toHaveBeenCalledWith(
        printingModelCollection,
        ...additionalPrintingModelSigs.map(expect.objectContaining)
      );
      expect(comp.printingModelsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call LanguageSig query and add missing value', () => {
      const printingCentre: IPrintingCentreSig = { printingCentreId: 456 };
      const language: ILanguageSig = { languageId: 1549 };
      printingCentre.language = language;

      const languageCollection: ILanguageSig[] = [{ languageId: 69072 }];
      jest.spyOn(languageService, 'query').mockReturnValue(of(new HttpResponse({ body: languageCollection })));
      const additionalLanguageSigs = [language];
      const expectedCollection: ILanguageSig[] = [...additionalLanguageSigs, ...languageCollection];
      jest.spyOn(languageService, 'addLanguageSigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ printingCentre });
      comp.ngOnInit();

      expect(languageService.query).toHaveBeenCalled();
      expect(languageService.addLanguageSigToCollectionIfMissing).toHaveBeenCalledWith(
        languageCollection,
        ...additionalLanguageSigs.map(expect.objectContaining)
      );
      expect(comp.languagesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call EventSig query and add missing value', () => {
      const printingCentre: IPrintingCentreSig = { printingCentreId: 456 };
      const event: IEventSig = { eventId: 86615 };
      printingCentre.event = event;

      const eventCollection: IEventSig[] = [{ eventId: 3311 }];
      jest.spyOn(eventService, 'query').mockReturnValue(of(new HttpResponse({ body: eventCollection })));
      const additionalEventSigs = [event];
      const expectedCollection: IEventSig[] = [...additionalEventSigs, ...eventCollection];
      jest.spyOn(eventService, 'addEventSigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ printingCentre });
      comp.ngOnInit();

      expect(eventService.query).toHaveBeenCalled();
      expect(eventService.addEventSigToCollectionIfMissing).toHaveBeenCalledWith(
        eventCollection,
        ...additionalEventSigs.map(expect.objectContaining)
      );
      expect(comp.eventsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const printingCentre: IPrintingCentreSig = { printingCentreId: 456 };
      const city: ICitySig = { cityId: 77412 };
      printingCentre.city = city;
      const country: ICountrySig = { countryId: 97315 };
      printingCentre.country = country;
      const organiz: IOrganizSig = { organizId: 33060 };
      printingCentre.organiz = organiz;
      const printingType: IPrintingTypeSig = { printingTypeId: 44312 };
      printingCentre.printingType = printingType;
      const printingServer: IPrintingServerSig = { printingServerId: 18182 };
      printingCentre.printingServer = printingServer;
      const printingModel: IPrintingModelSig = { printingModelId: 57678 };
      printingCentre.printingModel = printingModel;
      const language: ILanguageSig = { languageId: 67672 };
      printingCentre.language = language;
      const event: IEventSig = { eventId: 8523 };
      printingCentre.event = event;

      activatedRoute.data = of({ printingCentre });
      comp.ngOnInit();

      expect(comp.citiesSharedCollection).toContain(city);
      expect(comp.countriesSharedCollection).toContain(country);
      expect(comp.organizsSharedCollection).toContain(organiz);
      expect(comp.printingTypesSharedCollection).toContain(printingType);
      expect(comp.printingServersSharedCollection).toContain(printingServer);
      expect(comp.printingModelsSharedCollection).toContain(printingModel);
      expect(comp.languagesSharedCollection).toContain(language);
      expect(comp.eventsSharedCollection).toContain(event);
      expect(comp.printingCentre).toEqual(printingCentre);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPrintingCentreSig>>();
      const printingCentre = { printingCentreId: 123 };
      jest.spyOn(printingCentreFormService, 'getPrintingCentreSig').mockReturnValue(printingCentre);
      jest.spyOn(printingCentreService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ printingCentre });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: printingCentre }));
      saveSubject.complete();

      // THEN
      expect(printingCentreFormService.getPrintingCentreSig).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(printingCentreService.update).toHaveBeenCalledWith(expect.objectContaining(printingCentre));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPrintingCentreSig>>();
      const printingCentre = { printingCentreId: 123 };
      jest.spyOn(printingCentreFormService, 'getPrintingCentreSig').mockReturnValue({ printingCentreId: null });
      jest.spyOn(printingCentreService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ printingCentre: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: printingCentre }));
      saveSubject.complete();

      // THEN
      expect(printingCentreFormService.getPrintingCentreSig).toHaveBeenCalled();
      expect(printingCentreService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPrintingCentreSig>>();
      const printingCentre = { printingCentreId: 123 };
      jest.spyOn(printingCentreService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ printingCentre });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(printingCentreService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCitySig', () => {
      it('Should forward to cityService', () => {
        const entity = { cityId: 123 };
        const entity2 = { cityId: 456 };
        jest.spyOn(cityService, 'compareCitySig');
        comp.compareCitySig(entity, entity2);
        expect(cityService.compareCitySig).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareCountrySig', () => {
      it('Should forward to countryService', () => {
        const entity = { countryId: 123 };
        const entity2 = { countryId: 456 };
        jest.spyOn(countryService, 'compareCountrySig');
        comp.compareCountrySig(entity, entity2);
        expect(countryService.compareCountrySig).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareOrganizSig', () => {
      it('Should forward to organizService', () => {
        const entity = { organizId: 123 };
        const entity2 = { organizId: 456 };
        jest.spyOn(organizService, 'compareOrganizSig');
        comp.compareOrganizSig(entity, entity2);
        expect(organizService.compareOrganizSig).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('comparePrintingTypeSig', () => {
      it('Should forward to printingTypeService', () => {
        const entity = { printingTypeId: 123 };
        const entity2 = { printingTypeId: 456 };
        jest.spyOn(printingTypeService, 'comparePrintingTypeSig');
        comp.comparePrintingTypeSig(entity, entity2);
        expect(printingTypeService.comparePrintingTypeSig).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('comparePrintingServerSig', () => {
      it('Should forward to printingServerService', () => {
        const entity = { printingServerId: 123 };
        const entity2 = { printingServerId: 456 };
        jest.spyOn(printingServerService, 'comparePrintingServerSig');
        comp.comparePrintingServerSig(entity, entity2);
        expect(printingServerService.comparePrintingServerSig).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('comparePrintingModelSig', () => {
      it('Should forward to printingModelService', () => {
        const entity = { printingModelId: 123 };
        const entity2 = { printingModelId: 456 };
        jest.spyOn(printingModelService, 'comparePrintingModelSig');
        comp.comparePrintingModelSig(entity, entity2);
        expect(printingModelService.comparePrintingModelSig).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareLanguageSig', () => {
      it('Should forward to languageService', () => {
        const entity = { languageId: 123 };
        const entity2 = { languageId: 456 };
        jest.spyOn(languageService, 'compareLanguageSig');
        comp.compareLanguageSig(entity, entity2);
        expect(languageService.compareLanguageSig).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareEventSig', () => {
      it('Should forward to eventService', () => {
        const entity = { eventId: 123 };
        const entity2 = { eventId: 456 };
        jest.spyOn(eventService, 'compareEventSig');
        comp.compareEventSig(entity, entity2);
        expect(eventService.compareEventSig).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
