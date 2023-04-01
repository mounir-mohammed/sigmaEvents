import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { OrganizSigFormService } from './organiz-sig-form.service';
import { OrganizSigService } from '../service/organiz-sig.service';
import { IOrganizSig } from '../organiz-sig.model';
import { ICountrySig } from 'app/entities/country-sig/country-sig.model';
import { CountrySigService } from 'app/entities/country-sig/service/country-sig.service';
import { ICitySig } from 'app/entities/city-sig/city-sig.model';
import { CitySigService } from 'app/entities/city-sig/service/city-sig.service';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';
import { EventSigService } from 'app/entities/event-sig/service/event-sig.service';

import { OrganizSigUpdateComponent } from './organiz-sig-update.component';

describe('OrganizSig Management Update Component', () => {
  let comp: OrganizSigUpdateComponent;
  let fixture: ComponentFixture<OrganizSigUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let organizFormService: OrganizSigFormService;
  let organizService: OrganizSigService;
  let countryService: CountrySigService;
  let cityService: CitySigService;
  let eventService: EventSigService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [OrganizSigUpdateComponent],
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
      .overrideTemplate(OrganizSigUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OrganizSigUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    organizFormService = TestBed.inject(OrganizSigFormService);
    organizService = TestBed.inject(OrganizSigService);
    countryService = TestBed.inject(CountrySigService);
    cityService = TestBed.inject(CitySigService);
    eventService = TestBed.inject(EventSigService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call CountrySig query and add missing value', () => {
      const organiz: IOrganizSig = { organizId: 456 };
      const country: ICountrySig = { countryId: 83555 };
      organiz.country = country;

      const countryCollection: ICountrySig[] = [{ countryId: 33877 }];
      jest.spyOn(countryService, 'query').mockReturnValue(of(new HttpResponse({ body: countryCollection })));
      const additionalCountrySigs = [country];
      const expectedCollection: ICountrySig[] = [...additionalCountrySigs, ...countryCollection];
      jest.spyOn(countryService, 'addCountrySigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ organiz });
      comp.ngOnInit();

      expect(countryService.query).toHaveBeenCalled();
      expect(countryService.addCountrySigToCollectionIfMissing).toHaveBeenCalledWith(
        countryCollection,
        ...additionalCountrySigs.map(expect.objectContaining)
      );
      expect(comp.countriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call CitySig query and add missing value', () => {
      const organiz: IOrganizSig = { organizId: 456 };
      const city: ICitySig = { cityId: 51809 };
      organiz.city = city;

      const cityCollection: ICitySig[] = [{ cityId: 72952 }];
      jest.spyOn(cityService, 'query').mockReturnValue(of(new HttpResponse({ body: cityCollection })));
      const additionalCitySigs = [city];
      const expectedCollection: ICitySig[] = [...additionalCitySigs, ...cityCollection];
      jest.spyOn(cityService, 'addCitySigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ organiz });
      comp.ngOnInit();

      expect(cityService.query).toHaveBeenCalled();
      expect(cityService.addCitySigToCollectionIfMissing).toHaveBeenCalledWith(
        cityCollection,
        ...additionalCitySigs.map(expect.objectContaining)
      );
      expect(comp.citiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call EventSig query and add missing value', () => {
      const organiz: IOrganizSig = { organizId: 456 };
      const event: IEventSig = { eventId: 14622 };
      organiz.event = event;

      const eventCollection: IEventSig[] = [{ eventId: 59003 }];
      jest.spyOn(eventService, 'query').mockReturnValue(of(new HttpResponse({ body: eventCollection })));
      const additionalEventSigs = [event];
      const expectedCollection: IEventSig[] = [...additionalEventSigs, ...eventCollection];
      jest.spyOn(eventService, 'addEventSigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ organiz });
      comp.ngOnInit();

      expect(eventService.query).toHaveBeenCalled();
      expect(eventService.addEventSigToCollectionIfMissing).toHaveBeenCalledWith(
        eventCollection,
        ...additionalEventSigs.map(expect.objectContaining)
      );
      expect(comp.eventsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const organiz: IOrganizSig = { organizId: 456 };
      const country: ICountrySig = { countryId: 67156 };
      organiz.country = country;
      const city: ICitySig = { cityId: 29834 };
      organiz.city = city;
      const event: IEventSig = { eventId: 76245 };
      organiz.event = event;

      activatedRoute.data = of({ organiz });
      comp.ngOnInit();

      expect(comp.countriesSharedCollection).toContain(country);
      expect(comp.citiesSharedCollection).toContain(city);
      expect(comp.eventsSharedCollection).toContain(event);
      expect(comp.organiz).toEqual(organiz);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrganizSig>>();
      const organiz = { organizId: 123 };
      jest.spyOn(organizFormService, 'getOrganizSig').mockReturnValue(organiz);
      jest.spyOn(organizService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ organiz });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: organiz }));
      saveSubject.complete();

      // THEN
      expect(organizFormService.getOrganizSig).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(organizService.update).toHaveBeenCalledWith(expect.objectContaining(organiz));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrganizSig>>();
      const organiz = { organizId: 123 };
      jest.spyOn(organizFormService, 'getOrganizSig').mockReturnValue({ organizId: null });
      jest.spyOn(organizService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ organiz: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: organiz }));
      saveSubject.complete();

      // THEN
      expect(organizFormService.getOrganizSig).toHaveBeenCalled();
      expect(organizService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrganizSig>>();
      const organiz = { organizId: 123 };
      jest.spyOn(organizService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ organiz });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(organizService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
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
