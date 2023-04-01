import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EventControlSigFormService } from './event-control-sig-form.service';
import { EventControlSigService } from '../service/event-control-sig.service';
import { IEventControlSig } from '../event-control-sig.model';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';
import { EventSigService } from 'app/entities/event-sig/service/event-sig.service';
import { IEventFieldSig } from 'app/entities/event-field-sig/event-field-sig.model';
import { EventFieldSigService } from 'app/entities/event-field-sig/service/event-field-sig.service';

import { EventControlSigUpdateComponent } from './event-control-sig-update.component';

describe('EventControlSig Management Update Component', () => {
  let comp: EventControlSigUpdateComponent;
  let fixture: ComponentFixture<EventControlSigUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let eventControlFormService: EventControlSigFormService;
  let eventControlService: EventControlSigService;
  let eventService: EventSigService;
  let eventFieldService: EventFieldSigService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EventControlSigUpdateComponent],
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
      .overrideTemplate(EventControlSigUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EventControlSigUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    eventControlFormService = TestBed.inject(EventControlSigFormService);
    eventControlService = TestBed.inject(EventControlSigService);
    eventService = TestBed.inject(EventSigService);
    eventFieldService = TestBed.inject(EventFieldSigService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call EventSig query and add missing value', () => {
      const eventControl: IEventControlSig = { controlId: 456 };
      const event: IEventSig = { eventId: 1335 };
      eventControl.event = event;

      const eventCollection: IEventSig[] = [{ eventId: 95601 }];
      jest.spyOn(eventService, 'query').mockReturnValue(of(new HttpResponse({ body: eventCollection })));
      const additionalEventSigs = [event];
      const expectedCollection: IEventSig[] = [...additionalEventSigs, ...eventCollection];
      jest.spyOn(eventService, 'addEventSigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ eventControl });
      comp.ngOnInit();

      expect(eventService.query).toHaveBeenCalled();
      expect(eventService.addEventSigToCollectionIfMissing).toHaveBeenCalledWith(
        eventCollection,
        ...additionalEventSigs.map(expect.objectContaining)
      );
      expect(comp.eventsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call EventFieldSig query and add missing value', () => {
      const eventControl: IEventControlSig = { controlId: 456 };
      const eventField: IEventFieldSig = { fieldId: 82757 };
      eventControl.eventField = eventField;

      const eventFieldCollection: IEventFieldSig[] = [{ fieldId: 28701 }];
      jest.spyOn(eventFieldService, 'query').mockReturnValue(of(new HttpResponse({ body: eventFieldCollection })));
      const additionalEventFieldSigs = [eventField];
      const expectedCollection: IEventFieldSig[] = [...additionalEventFieldSigs, ...eventFieldCollection];
      jest.spyOn(eventFieldService, 'addEventFieldSigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ eventControl });
      comp.ngOnInit();

      expect(eventFieldService.query).toHaveBeenCalled();
      expect(eventFieldService.addEventFieldSigToCollectionIfMissing).toHaveBeenCalledWith(
        eventFieldCollection,
        ...additionalEventFieldSigs.map(expect.objectContaining)
      );
      expect(comp.eventFieldsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const eventControl: IEventControlSig = { controlId: 456 };
      const event: IEventSig = { eventId: 48505 };
      eventControl.event = event;
      const eventField: IEventFieldSig = { fieldId: 32083 };
      eventControl.eventField = eventField;

      activatedRoute.data = of({ eventControl });
      comp.ngOnInit();

      expect(comp.eventsSharedCollection).toContain(event);
      expect(comp.eventFieldsSharedCollection).toContain(eventField);
      expect(comp.eventControl).toEqual(eventControl);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEventControlSig>>();
      const eventControl = { controlId: 123 };
      jest.spyOn(eventControlFormService, 'getEventControlSig').mockReturnValue(eventControl);
      jest.spyOn(eventControlService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eventControl });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: eventControl }));
      saveSubject.complete();

      // THEN
      expect(eventControlFormService.getEventControlSig).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(eventControlService.update).toHaveBeenCalledWith(expect.objectContaining(eventControl));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEventControlSig>>();
      const eventControl = { controlId: 123 };
      jest.spyOn(eventControlFormService, 'getEventControlSig').mockReturnValue({ controlId: null });
      jest.spyOn(eventControlService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eventControl: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: eventControl }));
      saveSubject.complete();

      // THEN
      expect(eventControlFormService.getEventControlSig).toHaveBeenCalled();
      expect(eventControlService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEventControlSig>>();
      const eventControl = { controlId: 123 };
      jest.spyOn(eventControlService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eventControl });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(eventControlService.update).toHaveBeenCalled();
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

    describe('compareEventFieldSig', () => {
      it('Should forward to eventFieldService', () => {
        const entity = { fieldId: 123 };
        const entity2 = { fieldId: 456 };
        jest.spyOn(eventFieldService, 'compareEventFieldSig');
        comp.compareEventFieldSig(entity, entity2);
        expect(eventFieldService.compareEventFieldSig).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
