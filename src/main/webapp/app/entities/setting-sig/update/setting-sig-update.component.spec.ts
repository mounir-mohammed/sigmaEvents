import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SettingSigFormService } from './setting-sig-form.service';
import { SettingSigService } from '../service/setting-sig.service';
import { ISettingSig } from '../setting-sig.model';
import { ILanguageSig } from 'app/entities/language-sig/language-sig.model';
import { LanguageSigService } from 'app/entities/language-sig/service/language-sig.service';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';
import { EventSigService } from 'app/entities/event-sig/service/event-sig.service';

import { SettingSigUpdateComponent } from './setting-sig-update.component';

describe('SettingSig Management Update Component', () => {
  let comp: SettingSigUpdateComponent;
  let fixture: ComponentFixture<SettingSigUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let settingFormService: SettingSigFormService;
  let settingService: SettingSigService;
  let languageService: LanguageSigService;
  let eventService: EventSigService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SettingSigUpdateComponent],
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
      .overrideTemplate(SettingSigUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SettingSigUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    settingFormService = TestBed.inject(SettingSigFormService);
    settingService = TestBed.inject(SettingSigService);
    languageService = TestBed.inject(LanguageSigService);
    eventService = TestBed.inject(EventSigService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call LanguageSig query and add missing value', () => {
      const setting: ISettingSig = { settingId: 456 };
      const language: ILanguageSig = { languageId: 82543 };
      setting.language = language;

      const languageCollection: ILanguageSig[] = [{ languageId: 64317 }];
      jest.spyOn(languageService, 'query').mockReturnValue(of(new HttpResponse({ body: languageCollection })));
      const additionalLanguageSigs = [language];
      const expectedCollection: ILanguageSig[] = [...additionalLanguageSigs, ...languageCollection];
      jest.spyOn(languageService, 'addLanguageSigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ setting });
      comp.ngOnInit();

      expect(languageService.query).toHaveBeenCalled();
      expect(languageService.addLanguageSigToCollectionIfMissing).toHaveBeenCalledWith(
        languageCollection,
        ...additionalLanguageSigs.map(expect.objectContaining)
      );
      expect(comp.languagesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call EventSig query and add missing value', () => {
      const setting: ISettingSig = { settingId: 456 };
      const event: IEventSig = { eventId: 6737 };
      setting.event = event;

      const eventCollection: IEventSig[] = [{ eventId: 59354 }];
      jest.spyOn(eventService, 'query').mockReturnValue(of(new HttpResponse({ body: eventCollection })));
      const additionalEventSigs = [event];
      const expectedCollection: IEventSig[] = [...additionalEventSigs, ...eventCollection];
      jest.spyOn(eventService, 'addEventSigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ setting });
      comp.ngOnInit();

      expect(eventService.query).toHaveBeenCalled();
      expect(eventService.addEventSigToCollectionIfMissing).toHaveBeenCalledWith(
        eventCollection,
        ...additionalEventSigs.map(expect.objectContaining)
      );
      expect(comp.eventsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const setting: ISettingSig = { settingId: 456 };
      const language: ILanguageSig = { languageId: 57129 };
      setting.language = language;
      const event: IEventSig = { eventId: 87881 };
      setting.event = event;

      activatedRoute.data = of({ setting });
      comp.ngOnInit();

      expect(comp.languagesSharedCollection).toContain(language);
      expect(comp.eventsSharedCollection).toContain(event);
      expect(comp.setting).toEqual(setting);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISettingSig>>();
      const setting = { settingId: 123 };
      jest.spyOn(settingFormService, 'getSettingSig').mockReturnValue(setting);
      jest.spyOn(settingService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ setting });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: setting }));
      saveSubject.complete();

      // THEN
      expect(settingFormService.getSettingSig).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(settingService.update).toHaveBeenCalledWith(expect.objectContaining(setting));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISettingSig>>();
      const setting = { settingId: 123 };
      jest.spyOn(settingFormService, 'getSettingSig').mockReturnValue({ settingId: null });
      jest.spyOn(settingService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ setting: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: setting }));
      saveSubject.complete();

      // THEN
      expect(settingFormService.getSettingSig).toHaveBeenCalled();
      expect(settingService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISettingSig>>();
      const setting = { settingId: 123 };
      jest.spyOn(settingService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ setting });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(settingService.update).toHaveBeenCalled();
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
