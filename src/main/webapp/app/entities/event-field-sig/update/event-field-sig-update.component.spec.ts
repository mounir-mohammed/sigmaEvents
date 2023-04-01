import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EventFieldSigFormService } from './event-field-sig-form.service';
import { EventFieldSigService } from '../service/event-field-sig.service';
import { IEventFieldSig } from '../event-field-sig.model';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';
import { EventSigService } from 'app/entities/event-sig/service/event-sig.service';
import { IEventFormSig } from 'app/entities/event-form-sig/event-form-sig.model';
import { EventFormSigService } from 'app/entities/event-form-sig/service/event-form-sig.service';

import { EventFieldSigUpdateComponent } from './event-field-sig-update.component';

describe('EventFieldSig Management Update Component', () => {
  let comp: EventFieldSigUpdateComponent;
  let fixture: ComponentFixture<EventFieldSigUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let eventFieldFormService: EventFieldSigFormService;
  let eventFieldService: EventFieldSigService;
  let eventService: EventSigService;
  let eventFormService: EventFormSigService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EventFieldSigUpdateComponent],
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
      .overrideTemplate(EventFieldSigUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EventFieldSigUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    eventFieldFormService = TestBed.inject(EventFieldSigFormService);
    eventFieldService = TestBed.inject(EventFieldSigService);
    eventService = TestBed.inject(EventSigService);
    eventFormService = TestBed.inject(EventFormSigService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call EventSig query and add missing value', () => {
      const eventField: IEventFieldSig = { fieldId: 456 };
      const event: IEventSig = { eventId: 55399 };
      eventField.event = event;

      const eventCollection: IEventSig[] = [{ eventId: 6638 }];
      jest.spyOn(eventService, 'query').mockReturnValue(of(new HttpResponse({ body: eventCollection })));
      const additionalEventSigs = [event];
      const expectedCollection: IEventSig[] = [...additionalEventSigs, ...eventCollection];
      jest.spyOn(eventService, 'addEventSigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ eventField });
      comp.ngOnInit();

      expect(eventService.query).toHaveBeenCalled();
      expect(eventService.addEventSigToCollectionIfMissing).toHaveBeenCalledWith(
        eventCollection,
        ...additionalEventSigs.map(expect.objectContaining)
      );
      expect(comp.eventsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call EventFormSig query and add missing value', () => {
      const eventField: IEventFieldSig = { fieldId: 456 };
      const eventForm: IEventFormSig = { formId: 17113 };
      eventField.eventForm = eventForm;

      const eventFormCollection: IEventFormSig[] = [{ formId: 14341 }];
      jest.spyOn(eventFormService, 'query').mockReturnValue(of(new HttpResponse({ body: eventFormCollection })));
      const additionalEventFormSigs = [eventForm];
      const expectedCollection: IEventFormSig[] = [...additionalEventFormSigs, ...eventFormCollection];
      jest.spyOn(eventFormService, 'addEventFormSigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ eventField });
      comp.ngOnInit();

      expect(eventFormService.query).toHaveBeenCalled();
      expect(eventFormService.addEventFormSigToCollectionIfMissing).toHaveBeenCalledWith(
        eventFormCollection,
        ...additionalEventFormSigs.map(expect.objectContaining)
      );
      expect(comp.eventFormsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const eventField: IEventFieldSig = { fieldId: 456 };
      const event: IEventSig = { eventId: 70418 };
      eventField.event = event;
      const eventForm: IEventFormSig = { formId: 22744 };
      eventField.eventForm = eventForm;

      activatedRoute.data = of({ eventField });
      comp.ngOnInit();

      expect(comp.eventsSharedCollection).toContain(event);
      expect(comp.eventFormsSharedCollection).toContain(eventForm);
      expect(comp.eventField).toEqual(eventField);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEventFieldSig>>();
      const eventField = { fieldId: 123 };
      jest.spyOn(eventFieldFormService, 'getEventFieldSig').mockReturnValue(eventField);
      jest.spyOn(eventFieldService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eventField });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: eventField }));
      saveSubject.complete();

      // THEN
      expect(eventFieldFormService.getEventFieldSig).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(eventFieldService.update).toHaveBeenCalledWith(expect.objectContaining(eventField));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEventFieldSig>>();
      const eventField = { fieldId: 123 };
      jest.spyOn(eventFieldFormService, 'getEventFieldSig').mockReturnValue({ fieldId: null });
      jest.spyOn(eventFieldService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eventField: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: eventField }));
      saveSubject.complete();

      // THEN
      expect(eventFieldFormService.getEventFieldSig).toHaveBeenCalled();
      expect(eventFieldService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEventFieldSig>>();
      const eventField = { fieldId: 123 };
      jest.spyOn(eventFieldService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eventField });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(eventFieldService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareEventSig', () => {
      it('Should forward to eventService', () => {
        const entity = { eventId: 123 };
        const entity2 = { eventId: 456 };
        jest.spyOn(eventService, 'compareEventSig');
        comp.compareEventSig(entity, entity2);
        expect(eventService.compareEventSig).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareEventFormSig', () => {
      it('Should forward to eventFormService', () => {
        const entity = { formId: 123 };
        const entity2 = { formId: 456 };
        jest.spyOn(eventFormService, 'compareEventFormSig');
        comp.compareEventFormSig(entity, entity2);
        expect(eventFormService.compareEventFormSig).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
