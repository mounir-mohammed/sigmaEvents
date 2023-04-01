import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EventSigFormService } from './event-sig-form.service';
import { EventSigService } from '../service/event-sig.service';
import { IEventSig } from '../event-sig.model';
import { ILanguageSig } from 'app/entities/language-sig/language-sig.model';
import { LanguageSigService } from 'app/entities/language-sig/service/language-sig.service';

import { EventSigUpdateComponent } from './event-sig-update.component';

describe('EventSig Management Update Component', () => {
  let comp: EventSigUpdateComponent;
  let fixture: ComponentFixture<EventSigUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let eventFormService: EventSigFormService;
  let eventService: EventSigService;
  let languageService: LanguageSigService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EventSigUpdateComponent],
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
      .overrideTemplate(EventSigUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EventSigUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    eventFormService = TestBed.inject(EventSigFormService);
    eventService = TestBed.inject(EventSigService);
    languageService = TestBed.inject(LanguageSigService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call LanguageSig query and add missing value', () => {
      const event: IEventSig = { eventId: 456 };
      const language: ILanguageSig = { languageId: 43281 };
      event.language = language;

      const languageCollection: ILanguageSig[] = [{ languageId: 75760 }];
      jest.spyOn(languageService, 'query').mockReturnValue(of(new HttpResponse({ body: languageCollection })));
      const additionalLanguageSigs = [language];
      const expectedCollection: ILanguageSig[] = [...additionalLanguageSigs, ...languageCollection];
      jest.spyOn(languageService, 'addLanguageSigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ event });
      comp.ngOnInit();

      expect(languageService.query).toHaveBeenCalled();
      expect(languageService.addLanguageSigToCollectionIfMissing).toHaveBeenCalledWith(
        languageCollection,
        ...additionalLanguageSigs.map(expect.objectContaining)
      );
      expect(comp.languagesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const event: IEventSig = { eventId: 456 };
      const language: ILanguageSig = { languageId: 93910 };
      event.language = language;

      activatedRoute.data = of({ event });
      comp.ngOnInit();

      expect(comp.languagesSharedCollection).toContain(language);
      expect(comp.event).toEqual(event);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEventSig>>();
      const event = { eventId: 123 };
      jest.spyOn(eventFormService, 'getEventSig').mockReturnValue(event);
      jest.spyOn(eventService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ event });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: event }));
      saveSubject.complete();

      // THEN
      expect(eventFormService.getEventSig).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(eventService.update).toHaveBeenCalledWith(expect.objectContaining(event));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEventSig>>();
      const event = { eventId: 123 };
      jest.spyOn(eventFormService, 'getEventSig').mockReturnValue({ eventId: null });
      jest.spyOn(eventService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ event: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: event }));
      saveSubject.complete();

      // THEN
      expect(eventFormService.getEventSig).toHaveBeenCalled();
      expect(eventService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEventSig>>();
      const event = { eventId: 123 };
      jest.spyOn(eventService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ event });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(eventService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareLanguageSig', () => {
      it('Should forward to languageService', () => {
        const entity = { languageId: 123 };
        const entity2 = { languageId: 456 };
        jest.spyOn(languageService, 'compareLanguageSig');
        comp.compareLanguageSig(entity, entity2);
        expect(languageService.compareLanguageSig).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
