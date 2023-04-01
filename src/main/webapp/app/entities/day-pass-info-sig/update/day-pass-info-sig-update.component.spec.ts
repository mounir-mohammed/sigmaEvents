import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DayPassInfoSigFormService } from './day-pass-info-sig-form.service';
import { DayPassInfoSigService } from '../service/day-pass-info-sig.service';
import { IDayPassInfoSig } from '../day-pass-info-sig.model';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';
import { EventSigService } from 'app/entities/event-sig/service/event-sig.service';

import { DayPassInfoSigUpdateComponent } from './day-pass-info-sig-update.component';

describe('DayPassInfoSig Management Update Component', () => {
  let comp: DayPassInfoSigUpdateComponent;
  let fixture: ComponentFixture<DayPassInfoSigUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let dayPassInfoFormService: DayPassInfoSigFormService;
  let dayPassInfoService: DayPassInfoSigService;
  let eventService: EventSigService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DayPassInfoSigUpdateComponent],
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
      .overrideTemplate(DayPassInfoSigUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DayPassInfoSigUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    dayPassInfoFormService = TestBed.inject(DayPassInfoSigFormService);
    dayPassInfoService = TestBed.inject(DayPassInfoSigService);
    eventService = TestBed.inject(EventSigService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call EventSig query and add missing value', () => {
      const dayPassInfo: IDayPassInfoSig = { dayPassInfoId: 456 };
      const event: IEventSig = { eventId: 70211 };
      dayPassInfo.event = event;

      const eventCollection: IEventSig[] = [{ eventId: 83405 }];
      jest.spyOn(eventService, 'query').mockReturnValue(of(new HttpResponse({ body: eventCollection })));
      const additionalEventSigs = [event];
      const expectedCollection: IEventSig[] = [...additionalEventSigs, ...eventCollection];
      jest.spyOn(eventService, 'addEventSigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dayPassInfo });
      comp.ngOnInit();

      expect(eventService.query).toHaveBeenCalled();
      expect(eventService.addEventSigToCollectionIfMissing).toHaveBeenCalledWith(
        eventCollection,
        ...additionalEventSigs.map(expect.objectContaining)
      );
      expect(comp.eventsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const dayPassInfo: IDayPassInfoSig = { dayPassInfoId: 456 };
      const event: IEventSig = { eventId: 27471 };
      dayPassInfo.event = event;

      activatedRoute.data = of({ dayPassInfo });
      comp.ngOnInit();

      expect(comp.eventsSharedCollection).toContain(event);
      expect(comp.dayPassInfo).toEqual(dayPassInfo);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDayPassInfoSig>>();
      const dayPassInfo = { dayPassInfoId: 123 };
      jest.spyOn(dayPassInfoFormService, 'getDayPassInfoSig').mockReturnValue(dayPassInfo);
      jest.spyOn(dayPassInfoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dayPassInfo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dayPassInfo }));
      saveSubject.complete();

      // THEN
      expect(dayPassInfoFormService.getDayPassInfoSig).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(dayPassInfoService.update).toHaveBeenCalledWith(expect.objectContaining(dayPassInfo));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDayPassInfoSig>>();
      const dayPassInfo = { dayPassInfoId: 123 };
      jest.spyOn(dayPassInfoFormService, 'getDayPassInfoSig').mockReturnValue({ dayPassInfoId: null });
      jest.spyOn(dayPassInfoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dayPassInfo: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dayPassInfo }));
      saveSubject.complete();

      // THEN
      expect(dayPassInfoFormService.getDayPassInfoSig).toHaveBeenCalled();
      expect(dayPassInfoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDayPassInfoSig>>();
      const dayPassInfo = { dayPassInfoId: 123 };
      jest.spyOn(dayPassInfoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dayPassInfo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(dayPassInfoService.update).toHaveBeenCalled();
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
