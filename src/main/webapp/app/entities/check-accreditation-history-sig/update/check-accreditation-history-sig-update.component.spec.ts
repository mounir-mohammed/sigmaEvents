import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CheckAccreditationHistorySigFormService } from './check-accreditation-history-sig-form.service';
import { CheckAccreditationHistorySigService } from '../service/check-accreditation-history-sig.service';
import { ICheckAccreditationHistorySig } from '../check-accreditation-history-sig.model';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';
import { EventSigService } from 'app/entities/event-sig/service/event-sig.service';
import { IAccreditationSig } from 'app/entities/accreditation-sig/accreditation-sig.model';
import { AccreditationSigService } from 'app/entities/accreditation-sig/service/accreditation-sig.service';

import { CheckAccreditationHistorySigUpdateComponent } from './check-accreditation-history-sig-update.component';

describe('CheckAccreditationHistorySig Management Update Component', () => {
  let comp: CheckAccreditationHistorySigUpdateComponent;
  let fixture: ComponentFixture<CheckAccreditationHistorySigUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let checkAccreditationHistoryFormService: CheckAccreditationHistorySigFormService;
  let checkAccreditationHistoryService: CheckAccreditationHistorySigService;
  let eventService: EventSigService;
  let accreditationService: AccreditationSigService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CheckAccreditationHistorySigUpdateComponent],
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
      .overrideTemplate(CheckAccreditationHistorySigUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CheckAccreditationHistorySigUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    checkAccreditationHistoryFormService = TestBed.inject(CheckAccreditationHistorySigFormService);
    checkAccreditationHistoryService = TestBed.inject(CheckAccreditationHistorySigService);
    eventService = TestBed.inject(EventSigService);
    accreditationService = TestBed.inject(AccreditationSigService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call EventSig query and add missing value', () => {
      const checkAccreditationHistory: ICheckAccreditationHistorySig = { checkAccreditationHistoryId: 456 };
      const event: IEventSig = { eventId: 47128 };
      checkAccreditationHistory.event = event;

      const eventCollection: IEventSig[] = [{ eventId: 92098 }];
      jest.spyOn(eventService, 'query').mockReturnValue(of(new HttpResponse({ body: eventCollection })));
      const additionalEventSigs = [event];
      const expectedCollection: IEventSig[] = [...additionalEventSigs, ...eventCollection];
      jest.spyOn(eventService, 'addEventSigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ checkAccreditationHistory });
      comp.ngOnInit();

      expect(eventService.query).toHaveBeenCalled();
      expect(eventService.addEventSigToCollectionIfMissing).toHaveBeenCalledWith(
        eventCollection,
        ...additionalEventSigs.map(expect.objectContaining)
      );
      expect(comp.eventsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call AccreditationSig query and add missing value', () => {
      const checkAccreditationHistory: ICheckAccreditationHistorySig = { checkAccreditationHistoryId: 456 };
      const accreditation: IAccreditationSig = { accreditationId: 67629 };
      checkAccreditationHistory.accreditation = accreditation;

      const accreditationCollection: IAccreditationSig[] = [{ accreditationId: 91264 }];
      jest.spyOn(accreditationService, 'query').mockReturnValue(of(new HttpResponse({ body: accreditationCollection })));
      const additionalAccreditationSigs = [accreditation];
      const expectedCollection: IAccreditationSig[] = [...additionalAccreditationSigs, ...accreditationCollection];
      jest.spyOn(accreditationService, 'addAccreditationSigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ checkAccreditationHistory });
      comp.ngOnInit();

      expect(accreditationService.query).toHaveBeenCalled();
      expect(accreditationService.addAccreditationSigToCollectionIfMissing).toHaveBeenCalledWith(
        accreditationCollection,
        ...additionalAccreditationSigs.map(expect.objectContaining)
      );
      expect(comp.accreditationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const checkAccreditationHistory: ICheckAccreditationHistorySig = { checkAccreditationHistoryId: 456 };
      const event: IEventSig = { eventId: 41364 };
      checkAccreditationHistory.event = event;
      const accreditation: IAccreditationSig = { accreditationId: 70365 };
      checkAccreditationHistory.accreditation = accreditation;

      activatedRoute.data = of({ checkAccreditationHistory });
      comp.ngOnInit();

      expect(comp.eventsSharedCollection).toContain(event);
      expect(comp.accreditationsSharedCollection).toContain(accreditation);
      expect(comp.checkAccreditationHistory).toEqual(checkAccreditationHistory);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICheckAccreditationHistorySig>>();
      const checkAccreditationHistory = { checkAccreditationHistoryId: 123 };
      jest.spyOn(checkAccreditationHistoryFormService, 'getCheckAccreditationHistorySig').mockReturnValue(checkAccreditationHistory);
      jest.spyOn(checkAccreditationHistoryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ checkAccreditationHistory });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: checkAccreditationHistory }));
      saveSubject.complete();

      // THEN
      expect(checkAccreditationHistoryFormService.getCheckAccreditationHistorySig).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(checkAccreditationHistoryService.update).toHaveBeenCalledWith(expect.objectContaining(checkAccreditationHistory));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICheckAccreditationHistorySig>>();
      const checkAccreditationHistory = { checkAccreditationHistoryId: 123 };
      jest
        .spyOn(checkAccreditationHistoryFormService, 'getCheckAccreditationHistorySig')
        .mockReturnValue({ checkAccreditationHistoryId: null });
      jest.spyOn(checkAccreditationHistoryService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ checkAccreditationHistory: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: checkAccreditationHistory }));
      saveSubject.complete();

      // THEN
      expect(checkAccreditationHistoryFormService.getCheckAccreditationHistorySig).toHaveBeenCalled();
      expect(checkAccreditationHistoryService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICheckAccreditationHistorySig>>();
      const checkAccreditationHistory = { checkAccreditationHistoryId: 123 };
      jest.spyOn(checkAccreditationHistoryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ checkAccreditationHistory });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(checkAccreditationHistoryService.update).toHaveBeenCalled();
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

    describe('compareAccreditationSig', () => {
      it('Should forward to accreditationService', () => {
        const entity = { accreditationId: 123 };
        const entity2 = { accreditationId: 456 };
        jest.spyOn(accreditationService, 'compareAccreditationSig');
        comp.compareAccreditationSig(entity, entity2);
        expect(accreditationService.compareAccreditationSig).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
