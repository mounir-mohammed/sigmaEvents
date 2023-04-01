import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SiteSigFormService } from './site-sig-form.service';
import { SiteSigService } from '../service/site-sig.service';
import { ISiteSig } from '../site-sig.model';
import { ICitySig } from 'app/entities/city-sig/city-sig.model';
import { CitySigService } from 'app/entities/city-sig/service/city-sig.service';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';
import { EventSigService } from 'app/entities/event-sig/service/event-sig.service';

import { SiteSigUpdateComponent } from './site-sig-update.component';

describe('SiteSig Management Update Component', () => {
  let comp: SiteSigUpdateComponent;
  let fixture: ComponentFixture<SiteSigUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let siteFormService: SiteSigFormService;
  let siteService: SiteSigService;
  let cityService: CitySigService;
  let eventService: EventSigService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SiteSigUpdateComponent],
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
      .overrideTemplate(SiteSigUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SiteSigUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    siteFormService = TestBed.inject(SiteSigFormService);
    siteService = TestBed.inject(SiteSigService);
    cityService = TestBed.inject(CitySigService);
    eventService = TestBed.inject(EventSigService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call CitySig query and add missing value', () => {
      const site: ISiteSig = { siteId: 456 };
      const city: ICitySig = { cityId: 52909 };
      site.city = city;

      const cityCollection: ICitySig[] = [{ cityId: 51394 }];
      jest.spyOn(cityService, 'query').mockReturnValue(of(new HttpResponse({ body: cityCollection })));
      const additionalCitySigs = [city];
      const expectedCollection: ICitySig[] = [...additionalCitySigs, ...cityCollection];
      jest.spyOn(cityService, 'addCitySigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ site });
      comp.ngOnInit();

      expect(cityService.query).toHaveBeenCalled();
      expect(cityService.addCitySigToCollectionIfMissing).toHaveBeenCalledWith(
        cityCollection,
        ...additionalCitySigs.map(expect.objectContaining)
      );
      expect(comp.citiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call EventSig query and add missing value', () => {
      const site: ISiteSig = { siteId: 456 };
      const event: IEventSig = { eventId: 32046 };
      site.event = event;

      const eventCollection: IEventSig[] = [{ eventId: 55375 }];
      jest.spyOn(eventService, 'query').mockReturnValue(of(new HttpResponse({ body: eventCollection })));
      const additionalEventSigs = [event];
      const expectedCollection: IEventSig[] = [...additionalEventSigs, ...eventCollection];
      jest.spyOn(eventService, 'addEventSigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ site });
      comp.ngOnInit();

      expect(eventService.query).toHaveBeenCalled();
      expect(eventService.addEventSigToCollectionIfMissing).toHaveBeenCalledWith(
        eventCollection,
        ...additionalEventSigs.map(expect.objectContaining)
      );
      expect(comp.eventsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const site: ISiteSig = { siteId: 456 };
      const city: ICitySig = { cityId: 49461 };
      site.city = city;
      const event: IEventSig = { eventId: 50549 };
      site.event = event;

      activatedRoute.data = of({ site });
      comp.ngOnInit();

      expect(comp.citiesSharedCollection).toContain(city);
      expect(comp.eventsSharedCollection).toContain(event);
      expect(comp.site).toEqual(site);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISiteSig>>();
      const site = { siteId: 123 };
      jest.spyOn(siteFormService, 'getSiteSig').mockReturnValue(site);
      jest.spyOn(siteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ site });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: site }));
      saveSubject.complete();

      // THEN
      expect(siteFormService.getSiteSig).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(siteService.update).toHaveBeenCalledWith(expect.objectContaining(site));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISiteSig>>();
      const site = { siteId: 123 };
      jest.spyOn(siteFormService, 'getSiteSig').mockReturnValue({ siteId: null });
      jest.spyOn(siteService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ site: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: site }));
      saveSubject.complete();

      // THEN
      expect(siteFormService.getSiteSig).toHaveBeenCalled();
      expect(siteService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISiteSig>>();
      const site = { siteId: 123 };
      jest.spyOn(siteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ site });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(siteService.update).toHaveBeenCalled();
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
