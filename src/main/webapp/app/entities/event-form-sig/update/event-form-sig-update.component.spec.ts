import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EventFormSigFormService } from './event-form-sig-form.service';
import { EventFormSigService } from '../service/event-form-sig.service';
import { IEventFormSig } from '../event-form-sig.model';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';
import { EventSigService } from 'app/entities/event-sig/service/event-sig.service';

import { EventFormSigUpdateComponent } from './event-form-sig-update.component';

describe('EventFormSig Management Update Component', () => {
  let comp: EventFormSigUpdateComponent;
  let fixture: ComponentFixture<EventFormSigUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let eventFormFormService: EventFormSigFormService;
  let eventFormService: EventFormSigService;
  let eventService: EventSigService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EventFormSigUpdateComponent],
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
      .overrideTemplate(EventFormSigUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EventFormSigUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    eventFormFormService = TestBed.inject(EventFormSigFormService);
    eventFormService = TestBed.inject(EventFormSigService);
    eventService = TestBed.inject(EventSigService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call EventSig query and add missing value', () => {
      const eventForm: IEventFormSig = { formId: 456 };
      const event: IEventSig = { eventId: 91498 };
      eventForm.event = event;

      const eventCollection: IEventSig[] = [{ eventId: 39945 }];
      jest.spyOn(eventService, 'query').mockReturnValue(of(new HttpResponse({ body: eventCollection })));
      const additionalEventSigs = [event];
      const expectedCollection: IEventSig[] = [...additionalEventSigs, ...eventCollection];
      jest.spyOn(eventService, 'addEventSigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ eventForm });
      comp.ngOnInit();

      expect(eventService.query).toHaveBeenCalled();
      expect(eventService.addEventSigToCollectionIfMissing).toHaveBeenCalledWith(
        eventCollection,
        ...additionalEventSigs.map(expect.objectContaining)
      );
      expect(comp.eventsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const eventForm: IEventFormSig = { formId: 456 };
      const event: IEventSig = { eventId: 88767 };
      eventForm.event = event;

      activatedRoute.data = of({ eventForm });
      comp.ngOnInit();

      expect(comp.eventsSharedCollection).toContain(event);
      expect(comp.eventForm).toEqual(eventForm);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEventFormSig>>();
      const eventForm = { formId: 123 };
      jest.spyOn(eventFormFormService, 'getEventFormSig').mockReturnValue(eventForm);
      jest.spyOn(eventFormService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eventForm });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: eventForm }));
      saveSubject.complete();

      // THEN
      expect(eventFormFormService.getEventFormSig).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(eventFormService.update).toHaveBeenCalledWith(expect.objectContaining(eventForm));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEventFormSig>>();
      const eventForm = { formId: 123 };
      jest.spyOn(eventFormFormService, 'getEventFormSig').mockReturnValue({ formId: null });
      jest.spyOn(eventFormService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eventForm: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: eventForm }));
      saveSubject.complete();

      // THEN
      expect(eventFormFormService.getEventFormSig).toHaveBeenCalled();
      expect(eventFormService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEventFormSig>>();
      const eventForm = { formId: 123 };
      jest.spyOn(eventFormService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eventForm });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(eventFormService.update).toHaveBeenCalled();
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
  });
});
