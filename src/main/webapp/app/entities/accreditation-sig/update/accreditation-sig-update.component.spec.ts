import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AccreditationSigFormService } from './accreditation-sig-form.service';
import { AccreditationSigService } from '../service/accreditation-sig.service';
import { IAccreditationSig } from '../accreditation-sig.model';
import { ISiteSig } from 'app/entities/site-sig/site-sig.model';
import { SiteSigService } from 'app/entities/site-sig/service/site-sig.service';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';
import { EventSigService } from 'app/entities/event-sig/service/event-sig.service';
import { ICivilitySig } from 'app/entities/civility-sig/civility-sig.model';
import { CivilitySigService } from 'app/entities/civility-sig/service/civility-sig.service';
import { ISexeSig } from 'app/entities/sexe-sig/sexe-sig.model';
import { SexeSigService } from 'app/entities/sexe-sig/service/sexe-sig.service';
import { INationalitySig } from 'app/entities/nationality-sig/nationality-sig.model';
import { NationalitySigService } from 'app/entities/nationality-sig/service/nationality-sig.service';
import { ICountrySig } from 'app/entities/country-sig/country-sig.model';
import { CountrySigService } from 'app/entities/country-sig/service/country-sig.service';
import { ICitySig } from 'app/entities/city-sig/city-sig.model';
import { CitySigService } from 'app/entities/city-sig/service/city-sig.service';
import { ICategorySig } from 'app/entities/category-sig/category-sig.model';
import { CategorySigService } from 'app/entities/category-sig/service/category-sig.service';
import { IFonctionSig } from 'app/entities/fonction-sig/fonction-sig.model';
import { FonctionSigService } from 'app/entities/fonction-sig/service/fonction-sig.service';
import { IOrganizSig } from 'app/entities/organiz-sig/organiz-sig.model';
import { OrganizSigService } from 'app/entities/organiz-sig/service/organiz-sig.service';
import { IAccreditationTypeSig } from 'app/entities/accreditation-type-sig/accreditation-type-sig.model';
import { AccreditationTypeSigService } from 'app/entities/accreditation-type-sig/service/accreditation-type-sig.service';
import { IStatusSig } from 'app/entities/status-sig/status-sig.model';
import { StatusSigService } from 'app/entities/status-sig/service/status-sig.service';
import { IAttachementSig } from 'app/entities/attachement-sig/attachement-sig.model';
import { AttachementSigService } from 'app/entities/attachement-sig/service/attachement-sig.service';
import { ICodeSig } from 'app/entities/code-sig/code-sig.model';
import { CodeSigService } from 'app/entities/code-sig/service/code-sig.service';
import { IDayPassInfoSig } from 'app/entities/day-pass-info-sig/day-pass-info-sig.model';
import { DayPassInfoSigService } from 'app/entities/day-pass-info-sig/service/day-pass-info-sig.service';

import { AccreditationSigUpdateComponent } from './accreditation-sig-update.component';

describe('AccreditationSig Management Update Component', () => {
  let comp: AccreditationSigUpdateComponent;
  let fixture: ComponentFixture<AccreditationSigUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let accreditationFormService: AccreditationSigFormService;
  let accreditationService: AccreditationSigService;
  let siteService: SiteSigService;
  let eventService: EventSigService;
  let civilityService: CivilitySigService;
  let sexeService: SexeSigService;
  let nationalityService: NationalitySigService;
  let countryService: CountrySigService;
  let cityService: CitySigService;
  let categoryService: CategorySigService;
  let fonctionService: FonctionSigService;
  let organizService: OrganizSigService;
  let accreditationTypeService: AccreditationTypeSigService;
  let statusService: StatusSigService;
  let attachementService: AttachementSigService;
  let codeService: CodeSigService;
  let dayPassInfoService: DayPassInfoSigService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AccreditationSigUpdateComponent],
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
      .overrideTemplate(AccreditationSigUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AccreditationSigUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    accreditationFormService = TestBed.inject(AccreditationSigFormService);
    accreditationService = TestBed.inject(AccreditationSigService);
    siteService = TestBed.inject(SiteSigService);
    eventService = TestBed.inject(EventSigService);
    civilityService = TestBed.inject(CivilitySigService);
    sexeService = TestBed.inject(SexeSigService);
    nationalityService = TestBed.inject(NationalitySigService);
    countryService = TestBed.inject(CountrySigService);
    cityService = TestBed.inject(CitySigService);
    categoryService = TestBed.inject(CategorySigService);
    fonctionService = TestBed.inject(FonctionSigService);
    organizService = TestBed.inject(OrganizSigService);
    accreditationTypeService = TestBed.inject(AccreditationTypeSigService);
    statusService = TestBed.inject(StatusSigService);
    attachementService = TestBed.inject(AttachementSigService);
    codeService = TestBed.inject(CodeSigService);
    dayPassInfoService = TestBed.inject(DayPassInfoSigService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call SiteSig query and add missing value', () => {
      const accreditation: IAccreditationSig = { accreditationId: 456 };
      const sites: ISiteSig[] = [{ siteId: 75009 }];
      accreditation.sites = sites;

      const siteCollection: ISiteSig[] = [{ siteId: 49896 }];
      jest.spyOn(siteService, 'query').mockReturnValue(of(new HttpResponse({ body: siteCollection })));
      const additionalSiteSigs = [...sites];
      const expectedCollection: ISiteSig[] = [...additionalSiteSigs, ...siteCollection];
      jest.spyOn(siteService, 'addSiteSigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ accreditation });
      comp.ngOnInit();

      expect(siteService.query).toHaveBeenCalled();
      expect(siteService.addSiteSigToCollectionIfMissing).toHaveBeenCalledWith(
        siteCollection,
        ...additionalSiteSigs.map(expect.objectContaining)
      );
      expect(comp.sitesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call EventSig query and add missing value', () => {
      const accreditation: IAccreditationSig = { accreditationId: 456 };
      const event: IEventSig = { eventId: 24356 };
      accreditation.event = event;

      const eventCollection: IEventSig[] = [{ eventId: 46264 }];
      jest.spyOn(eventService, 'query').mockReturnValue(of(new HttpResponse({ body: eventCollection })));
      const additionalEventSigs = [event];
      const expectedCollection: IEventSig[] = [...additionalEventSigs, ...eventCollection];
      jest.spyOn(eventService, 'addEventSigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ accreditation });
      comp.ngOnInit();

      expect(eventService.query).toHaveBeenCalled();
      expect(eventService.addEventSigToCollectionIfMissing).toHaveBeenCalledWith(
        eventCollection,
        ...additionalEventSigs.map(expect.objectContaining)
      );
      expect(comp.eventsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call CivilitySig query and add missing value', () => {
      const accreditation: IAccreditationSig = { accreditationId: 456 };
      const civility: ICivilitySig = { civilityId: 95303 };
      accreditation.civility = civility;

      const civilityCollection: ICivilitySig[] = [{ civilityId: 99760 }];
      jest.spyOn(civilityService, 'query').mockReturnValue(of(new HttpResponse({ body: civilityCollection })));
      const additionalCivilitySigs = [civility];
      const expectedCollection: ICivilitySig[] = [...additionalCivilitySigs, ...civilityCollection];
      jest.spyOn(civilityService, 'addCivilitySigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ accreditation });
      comp.ngOnInit();

      expect(civilityService.query).toHaveBeenCalled();
      expect(civilityService.addCivilitySigToCollectionIfMissing).toHaveBeenCalledWith(
        civilityCollection,
        ...additionalCivilitySigs.map(expect.objectContaining)
      );
      expect(comp.civilitiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call SexeSig query and add missing value', () => {
      const accreditation: IAccreditationSig = { accreditationId: 456 };
      const sexe: ISexeSig = { sexeId: 68561 };
      accreditation.sexe = sexe;

      const sexeCollection: ISexeSig[] = [{ sexeId: 21230 }];
      jest.spyOn(sexeService, 'query').mockReturnValue(of(new HttpResponse({ body: sexeCollection })));
      const additionalSexeSigs = [sexe];
      const expectedCollection: ISexeSig[] = [...additionalSexeSigs, ...sexeCollection];
      jest.spyOn(sexeService, 'addSexeSigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ accreditation });
      comp.ngOnInit();

      expect(sexeService.query).toHaveBeenCalled();
      expect(sexeService.addSexeSigToCollectionIfMissing).toHaveBeenCalledWith(
        sexeCollection,
        ...additionalSexeSigs.map(expect.objectContaining)
      );
      expect(comp.sexesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call NationalitySig query and add missing value', () => {
      const accreditation: IAccreditationSig = { accreditationId: 456 };
      const nationality: INationalitySig = { nationalityId: 96777 };
      accreditation.nationality = nationality;

      const nationalityCollection: INationalitySig[] = [{ nationalityId: 95305 }];
      jest.spyOn(nationalityService, 'query').mockReturnValue(of(new HttpResponse({ body: nationalityCollection })));
      const additionalNationalitySigs = [nationality];
      const expectedCollection: INationalitySig[] = [...additionalNationalitySigs, ...nationalityCollection];
      jest.spyOn(nationalityService, 'addNationalitySigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ accreditation });
      comp.ngOnInit();

      expect(nationalityService.query).toHaveBeenCalled();
      expect(nationalityService.addNationalitySigToCollectionIfMissing).toHaveBeenCalledWith(
        nationalityCollection,
        ...additionalNationalitySigs.map(expect.objectContaining)
      );
      expect(comp.nationalitiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call CountrySig query and add missing value', () => {
      const accreditation: IAccreditationSig = { accreditationId: 456 };
      const country: ICountrySig = { countryId: 65156 };
      accreditation.country = country;

      const countryCollection: ICountrySig[] = [{ countryId: 79805 }];
      jest.spyOn(countryService, 'query').mockReturnValue(of(new HttpResponse({ body: countryCollection })));
      const additionalCountrySigs = [country];
      const expectedCollection: ICountrySig[] = [...additionalCountrySigs, ...countryCollection];
      jest.spyOn(countryService, 'addCountrySigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ accreditation });
      comp.ngOnInit();

      expect(countryService.query).toHaveBeenCalled();
      expect(countryService.addCountrySigToCollectionIfMissing).toHaveBeenCalledWith(
        countryCollection,
        ...additionalCountrySigs.map(expect.objectContaining)
      );
      expect(comp.countriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call CitySig query and add missing value', () => {
      const accreditation: IAccreditationSig = { accreditationId: 456 };
      const city: ICitySig = { cityId: 5035 };
      accreditation.city = city;

      const cityCollection: ICitySig[] = [{ cityId: 3516 }];
      jest.spyOn(cityService, 'query').mockReturnValue(of(new HttpResponse({ body: cityCollection })));
      const additionalCitySigs = [city];
      const expectedCollection: ICitySig[] = [...additionalCitySigs, ...cityCollection];
      jest.spyOn(cityService, 'addCitySigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ accreditation });
      comp.ngOnInit();

      expect(cityService.query).toHaveBeenCalled();
      expect(cityService.addCitySigToCollectionIfMissing).toHaveBeenCalledWith(
        cityCollection,
        ...additionalCitySigs.map(expect.objectContaining)
      );
      expect(comp.citiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call CategorySig query and add missing value', () => {
      const accreditation: IAccreditationSig = { accreditationId: 456 };
      const category: ICategorySig = { categoryId: 41169 };
      accreditation.category = category;

      const categoryCollection: ICategorySig[] = [{ categoryId: 33377 }];
      jest.spyOn(categoryService, 'query').mockReturnValue(of(new HttpResponse({ body: categoryCollection })));
      const additionalCategorySigs = [category];
      const expectedCollection: ICategorySig[] = [...additionalCategorySigs, ...categoryCollection];
      jest.spyOn(categoryService, 'addCategorySigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ accreditation });
      comp.ngOnInit();

      expect(categoryService.query).toHaveBeenCalled();
      expect(categoryService.addCategorySigToCollectionIfMissing).toHaveBeenCalledWith(
        categoryCollection,
        ...additionalCategorySigs.map(expect.objectContaining)
      );
      expect(comp.categoriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call FonctionSig query and add missing value', () => {
      const accreditation: IAccreditationSig = { accreditationId: 456 };
      const fonction: IFonctionSig = { fonctionId: 64619 };
      accreditation.fonction = fonction;

      const fonctionCollection: IFonctionSig[] = [{ fonctionId: 89544 }];
      jest.spyOn(fonctionService, 'query').mockReturnValue(of(new HttpResponse({ body: fonctionCollection })));
      const additionalFonctionSigs = [fonction];
      const expectedCollection: IFonctionSig[] = [...additionalFonctionSigs, ...fonctionCollection];
      jest.spyOn(fonctionService, 'addFonctionSigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ accreditation });
      comp.ngOnInit();

      expect(fonctionService.query).toHaveBeenCalled();
      expect(fonctionService.addFonctionSigToCollectionIfMissing).toHaveBeenCalledWith(
        fonctionCollection,
        ...additionalFonctionSigs.map(expect.objectContaining)
      );
      expect(comp.fonctionsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call OrganizSig query and add missing value', () => {
      const accreditation: IAccreditationSig = { accreditationId: 456 };
      const organiz: IOrganizSig = { organizId: 5438 };
      accreditation.organiz = organiz;

      const organizCollection: IOrganizSig[] = [{ organizId: 4279 }];
      jest.spyOn(organizService, 'query').mockReturnValue(of(new HttpResponse({ body: organizCollection })));
      const additionalOrganizSigs = [organiz];
      const expectedCollection: IOrganizSig[] = [...additionalOrganizSigs, ...organizCollection];
      jest.spyOn(organizService, 'addOrganizSigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ accreditation });
      comp.ngOnInit();

      expect(organizService.query).toHaveBeenCalled();
      expect(organizService.addOrganizSigToCollectionIfMissing).toHaveBeenCalledWith(
        organizCollection,
        ...additionalOrganizSigs.map(expect.objectContaining)
      );
      expect(comp.organizsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call AccreditationTypeSig query and add missing value', () => {
      const accreditation: IAccreditationSig = { accreditationId: 456 };
      const accreditationType: IAccreditationTypeSig = { accreditationTypeId: 76610 };
      accreditation.accreditationType = accreditationType;

      const accreditationTypeCollection: IAccreditationTypeSig[] = [{ accreditationTypeId: 18459 }];
      jest.spyOn(accreditationTypeService, 'query').mockReturnValue(of(new HttpResponse({ body: accreditationTypeCollection })));
      const additionalAccreditationTypeSigs = [accreditationType];
      const expectedCollection: IAccreditationTypeSig[] = [...additionalAccreditationTypeSigs, ...accreditationTypeCollection];
      jest.spyOn(accreditationTypeService, 'addAccreditationTypeSigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ accreditation });
      comp.ngOnInit();

      expect(accreditationTypeService.query).toHaveBeenCalled();
      expect(accreditationTypeService.addAccreditationTypeSigToCollectionIfMissing).toHaveBeenCalledWith(
        accreditationTypeCollection,
        ...additionalAccreditationTypeSigs.map(expect.objectContaining)
      );
      expect(comp.accreditationTypesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call StatusSig query and add missing value', () => {
      const accreditation: IAccreditationSig = { accreditationId: 456 };
      const status: IStatusSig = { statusId: 57947 };
      accreditation.status = status;

      const statusCollection: IStatusSig[] = [{ statusId: 38815 }];
      jest.spyOn(statusService, 'query').mockReturnValue(of(new HttpResponse({ body: statusCollection })));
      const additionalStatusSigs = [status];
      const expectedCollection: IStatusSig[] = [...additionalStatusSigs, ...statusCollection];
      jest.spyOn(statusService, 'addStatusSigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ accreditation });
      comp.ngOnInit();

      expect(statusService.query).toHaveBeenCalled();
      expect(statusService.addStatusSigToCollectionIfMissing).toHaveBeenCalledWith(
        statusCollection,
        ...additionalStatusSigs.map(expect.objectContaining)
      );
      expect(comp.statusesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call AttachementSig query and add missing value', () => {
      const accreditation: IAccreditationSig = { accreditationId: 456 };
      const attachement: IAttachementSig = { attachementId: 411 };
      accreditation.attachement = attachement;

      const attachementCollection: IAttachementSig[] = [{ attachementId: 37339 }];
      jest.spyOn(attachementService, 'query').mockReturnValue(of(new HttpResponse({ body: attachementCollection })));
      const additionalAttachementSigs = [attachement];
      const expectedCollection: IAttachementSig[] = [...additionalAttachementSigs, ...attachementCollection];
      jest.spyOn(attachementService, 'addAttachementSigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ accreditation });
      comp.ngOnInit();

      expect(attachementService.query).toHaveBeenCalled();
      expect(attachementService.addAttachementSigToCollectionIfMissing).toHaveBeenCalledWith(
        attachementCollection,
        ...additionalAttachementSigs.map(expect.objectContaining)
      );
      expect(comp.attachementsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call CodeSig query and add missing value', () => {
      const accreditation: IAccreditationSig = { accreditationId: 456 };
      const code: ICodeSig = { codeId: 47427 };
      accreditation.code = code;

      const codeCollection: ICodeSig[] = [{ codeId: 94502 }];
      jest.spyOn(codeService, 'query').mockReturnValue(of(new HttpResponse({ body: codeCollection })));
      const additionalCodeSigs = [code];
      const expectedCollection: ICodeSig[] = [...additionalCodeSigs, ...codeCollection];
      jest.spyOn(codeService, 'addCodeSigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ accreditation });
      comp.ngOnInit();

      expect(codeService.query).toHaveBeenCalled();
      expect(codeService.addCodeSigToCollectionIfMissing).toHaveBeenCalledWith(
        codeCollection,
        ...additionalCodeSigs.map(expect.objectContaining)
      );
      expect(comp.codesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DayPassInfoSig query and add missing value', () => {
      const accreditation: IAccreditationSig = { accreditationId: 456 };
      const dayPassInfo: IDayPassInfoSig = { dayPassInfoId: 55949 };
      accreditation.dayPassInfo = dayPassInfo;

      const dayPassInfoCollection: IDayPassInfoSig[] = [{ dayPassInfoId: 7230 }];
      jest.spyOn(dayPassInfoService, 'query').mockReturnValue(of(new HttpResponse({ body: dayPassInfoCollection })));
      const additionalDayPassInfoSigs = [dayPassInfo];
      const expectedCollection: IDayPassInfoSig[] = [...additionalDayPassInfoSigs, ...dayPassInfoCollection];
      jest.spyOn(dayPassInfoService, 'addDayPassInfoSigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ accreditation });
      comp.ngOnInit();

      expect(dayPassInfoService.query).toHaveBeenCalled();
      expect(dayPassInfoService.addDayPassInfoSigToCollectionIfMissing).toHaveBeenCalledWith(
        dayPassInfoCollection,
        ...additionalDayPassInfoSigs.map(expect.objectContaining)
      );
      expect(comp.dayPassInfosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const accreditation: IAccreditationSig = { accreditationId: 456 };
      const site: ISiteSig = { siteId: 85388 };
      accreditation.sites = [site];
      const event: IEventSig = { eventId: 28841 };
      accreditation.event = event;
      const civility: ICivilitySig = { civilityId: 90378 };
      accreditation.civility = civility;
      const sexe: ISexeSig = { sexeId: 8800 };
      accreditation.sexe = sexe;
      const nationality: INationalitySig = { nationalityId: 37253 };
      accreditation.nationality = nationality;
      const country: ICountrySig = { countryId: 48484 };
      accreditation.country = country;
      const city: ICitySig = { cityId: 84397 };
      accreditation.city = city;
      const category: ICategorySig = { categoryId: 90894 };
      accreditation.category = category;
      const fonction: IFonctionSig = { fonctionId: 72373 };
      accreditation.fonction = fonction;
      const organiz: IOrganizSig = { organizId: 57266 };
      accreditation.organiz = organiz;
      const accreditationType: IAccreditationTypeSig = { accreditationTypeId: 91170 };
      accreditation.accreditationType = accreditationType;
      const status: IStatusSig = { statusId: 28874 };
      accreditation.status = status;
      const attachement: IAttachementSig = { attachementId: 97508 };
      accreditation.attachement = attachement;
      const code: ICodeSig = { codeId: 87893 };
      accreditation.code = code;
      const dayPassInfo: IDayPassInfoSig = { dayPassInfoId: 9848 };
      accreditation.dayPassInfo = dayPassInfo;

      activatedRoute.data = of({ accreditation });
      comp.ngOnInit();

      expect(comp.sitesSharedCollection).toContain(site);
      expect(comp.eventsSharedCollection).toContain(event);
      expect(comp.civilitiesSharedCollection).toContain(civility);
      expect(comp.sexesSharedCollection).toContain(sexe);
      expect(comp.nationalitiesSharedCollection).toContain(nationality);
      expect(comp.countriesSharedCollection).toContain(country);
      expect(comp.citiesSharedCollection).toContain(city);
      expect(comp.categoriesSharedCollection).toContain(category);
      expect(comp.fonctionsSharedCollection).toContain(fonction);
      expect(comp.organizsSharedCollection).toContain(organiz);
      expect(comp.accreditationTypesSharedCollection).toContain(accreditationType);
      expect(comp.statusesSharedCollection).toContain(status);
      expect(comp.attachementsSharedCollection).toContain(attachement);
      expect(comp.codesSharedCollection).toContain(code);
      expect(comp.dayPassInfosSharedCollection).toContain(dayPassInfo);
      expect(comp.accreditation).toEqual(accreditation);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAccreditationSig>>();
      const accreditation = { accreditationId: 123 };
      jest.spyOn(accreditationFormService, 'getAccreditationSig').mockReturnValue(accreditation);
      jest.spyOn(accreditationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ accreditation });
      comp.ngOnInit();

      // WHEN
      comp.save(event);
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: accreditation }));
      saveSubject.complete();

      // THEN
      expect(accreditationFormService.getAccreditationSig).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(accreditationService.update).toHaveBeenCalledWith(expect.objectContaining(accreditation));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAccreditationSig>>();
      const accreditation = { accreditationId: 123 };
      jest.spyOn(accreditationFormService, 'getAccreditationSig').mockReturnValue({ accreditationId: null });
      jest.spyOn(accreditationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ accreditation: null });
      comp.ngOnInit();

      // WHEN
      comp.save(event);
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: accreditation }));
      saveSubject.complete();

      // THEN
      expect(accreditationFormService.getAccreditationSig).toHaveBeenCalled();
      expect(accreditationService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAccreditationSig>>();
      const accreditation = { accreditationId: 123 };
      jest.spyOn(accreditationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ accreditation });
      comp.ngOnInit();

      // WHEN
      comp.save(event);
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(accreditationService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareSiteSig', () => {
      it('Should forward to siteService', () => {
        const entity = { siteId: 123 };
        const entity2 = { siteId: 456 };
        jest.spyOn(siteService, 'compareSiteSig');
        comp.compareSiteSig(entity, entity2);
        expect(siteService.compareSiteSig).toHaveBeenCalledWith(entity, entity2);
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

    describe('compareCivilitySig', () => {
      it('Should forward to civilityService', () => {
        const entity = { civilityId: 123 };
        const entity2 = { civilityId: 456 };
        jest.spyOn(civilityService, 'compareCivilitySig');
        comp.compareCivilitySig(entity, entity2);
        expect(civilityService.compareCivilitySig).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareSexeSig', () => {
      it('Should forward to sexeService', () => {
        const entity = { sexeId: 123 };
        const entity2 = { sexeId: 456 };
        jest.spyOn(sexeService, 'compareSexeSig');
        comp.compareSexeSig(entity, entity2);
        expect(sexeService.compareSexeSig).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareNationalitySig', () => {
      it('Should forward to nationalityService', () => {
        const entity = { nationalityId: 123 };
        const entity2 = { nationalityId: 456 };
        jest.spyOn(nationalityService, 'compareNationalitySig');
        comp.compareNationalitySig(entity, entity2);
        expect(nationalityService.compareNationalitySig).toHaveBeenCalledWith(entity, entity2);
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

    describe('compareCitySig', () => {
      it('Should forward to cityService', () => {
        const entity = { cityId: 123 };
        const entity2 = { cityId: 456 };
        jest.spyOn(cityService, 'compareCitySig');
        comp.compareCitySig(entity, entity2);
        expect(cityService.compareCitySig).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareCategorySig', () => {
      it('Should forward to categoryService', () => {
        const entity = { categoryId: 123 };
        const entity2 = { categoryId: 456 };
        jest.spyOn(categoryService, 'compareCategorySig');
        comp.compareCategorySig(entity, entity2);
        expect(categoryService.compareCategorySig).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareFonctionSig', () => {
      it('Should forward to fonctionService', () => {
        const entity = { fonctionId: 123 };
        const entity2 = { fonctionId: 456 };
        jest.spyOn(fonctionService, 'compareFonctionSig');
        comp.compareFonctionSig(entity, entity2);
        expect(fonctionService.compareFonctionSig).toHaveBeenCalledWith(entity, entity2);
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

    describe('compareAccreditationTypeSig', () => {
      it('Should forward to accreditationTypeService', () => {
        const entity = { accreditationTypeId: 123 };
        const entity2 = { accreditationTypeId: 456 };
        jest.spyOn(accreditationTypeService, 'compareAccreditationTypeSig');
        comp.compareAccreditationTypeSig(entity, entity2);
        expect(accreditationTypeService.compareAccreditationTypeSig).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareStatusSig', () => {
      it('Should forward to statusService', () => {
        const entity = { statusId: 123 };
        const entity2 = { statusId: 456 };
        jest.spyOn(statusService, 'compareStatusSig');
        comp.compareStatusSig(entity, entity2);
        expect(statusService.compareStatusSig).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareAttachementSig', () => {
      it('Should forward to attachementService', () => {
        const entity = { attachementId: 123 };
        const entity2 = { attachementId: 456 };
        jest.spyOn(attachementService, 'compareAttachementSig');
        comp.compareAttachementSig(entity, entity2);
        expect(attachementService.compareAttachementSig).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareCodeSig', () => {
      it('Should forward to codeService', () => {
        const entity = { codeId: 123 };
        const entity2 = { codeId: 456 };
        jest.spyOn(codeService, 'compareCodeSig');
        comp.compareCodeSig(entity, entity2);
        expect(codeService.compareCodeSig).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareDayPassInfoSig', () => {
      it('Should forward to dayPassInfoService', () => {
        const entity = { dayPassInfoId: 123 };
        const entity2 = { dayPassInfoId: 456 };
        jest.spyOn(dayPassInfoService, 'compareDayPassInfoSig');
        comp.compareDayPassInfoSig(entity, entity2);
        expect(dayPassInfoService.compareDayPassInfoSig).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
