import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { OperationHistorySigFormService } from './operation-history-sig-form.service';
import { OperationHistorySigService } from '../service/operation-history-sig.service';
import { IOperationHistorySig } from '../operation-history-sig.model';
import { IOperationTypeSig } from 'app/entities/operation-type-sig/operation-type-sig.model';
import { OperationTypeSigService } from 'app/entities/operation-type-sig/service/operation-type-sig.service';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';
import { EventSigService } from 'app/entities/event-sig/service/event-sig.service';

import { OperationHistorySigUpdateComponent } from './operation-history-sig-update.component';

describe('OperationHistorySig Management Update Component', () => {
  let comp: OperationHistorySigUpdateComponent;
  let fixture: ComponentFixture<OperationHistorySigUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let operationHistoryFormService: OperationHistorySigFormService;
  let operationHistoryService: OperationHistorySigService;
  let operationTypeService: OperationTypeSigService;
  let eventService: EventSigService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [OperationHistorySigUpdateComponent],
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
      .overrideTemplate(OperationHistorySigUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OperationHistorySigUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    operationHistoryFormService = TestBed.inject(OperationHistorySigFormService);
    operationHistoryService = TestBed.inject(OperationHistorySigService);
    operationTypeService = TestBed.inject(OperationTypeSigService);
    eventService = TestBed.inject(EventSigService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call OperationTypeSig query and add missing value', () => {
      const operationHistory: IOperationHistorySig = { operationHistoryId: 456 };
      const typeoperation: IOperationTypeSig = { operationTypeId: 43438 };
      operationHistory.typeoperation = typeoperation;

      const operationTypeCollection: IOperationTypeSig[] = [{ operationTypeId: 93890 }];
      jest.spyOn(operationTypeService, 'query').mockReturnValue(of(new HttpResponse({ body: operationTypeCollection })));
      const additionalOperationTypeSigs = [typeoperation];
      const expectedCollection: IOperationTypeSig[] = [...additionalOperationTypeSigs, ...operationTypeCollection];
      jest.spyOn(operationTypeService, 'addOperationTypeSigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ operationHistory });
      comp.ngOnInit();

      expect(operationTypeService.query).toHaveBeenCalled();
      expect(operationTypeService.addOperationTypeSigToCollectionIfMissing).toHaveBeenCalledWith(
        operationTypeCollection,
        ...additionalOperationTypeSigs.map(expect.objectContaining)
      );
      expect(comp.operationTypesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call EventSig query and add missing value', () => {
      const operationHistory: IOperationHistorySig = { operationHistoryId: 456 };
      const event: IEventSig = { eventId: 88403 };
      operationHistory.event = event;

      const eventCollection: IEventSig[] = [{ eventId: 55476 }];
      jest.spyOn(eventService, 'query').mockReturnValue(of(new HttpResponse({ body: eventCollection })));
      const additionalEventSigs = [event];
      const expectedCollection: IEventSig[] = [...additionalEventSigs, ...eventCollection];
      jest.spyOn(eventService, 'addEventSigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ operationHistory });
      comp.ngOnInit();

      expect(eventService.query).toHaveBeenCalled();
      expect(eventService.addEventSigToCollectionIfMissing).toHaveBeenCalledWith(
        eventCollection,
        ...additionalEventSigs.map(expect.objectContaining)
      );
      expect(comp.eventsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const operationHistory: IOperationHistorySig = { operationHistoryId: 456 };
      const typeoperation: IOperationTypeSig = { operationTypeId: 87460 };
      operationHistory.typeoperation = typeoperation;
      const event: IEventSig = { eventId: 47018 };
      operationHistory.event = event;

      activatedRoute.data = of({ operationHistory });
      comp.ngOnInit();

      expect(comp.operationTypesSharedCollection).toContain(typeoperation);
      expect(comp.eventsSharedCollection).toContain(event);
      expect(comp.operationHistory).toEqual(operationHistory);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOperationHistorySig>>();
      const operationHistory = { operationHistoryId: 123 };
      jest.spyOn(operationHistoryFormService, 'getOperationHistorySig').mockReturnValue(operationHistory);
      jest.spyOn(operationHistoryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ operationHistory });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: operationHistory }));
      saveSubject.complete();

      // THEN
      expect(operationHistoryFormService.getOperationHistorySig).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(operationHistoryService.update).toHaveBeenCalledWith(expect.objectContaining(operationHistory));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOperationHistorySig>>();
      const operationHistory = { operationHistoryId: 123 };
      jest.spyOn(operationHistoryFormService, 'getOperationHistorySig').mockReturnValue({ operationHistoryId: null });
      jest.spyOn(operationHistoryService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ operationHistory: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: operationHistory }));
      saveSubject.complete();

      // THEN
      expect(operationHistoryFormService.getOperationHistorySig).toHaveBeenCalled();
      expect(operationHistoryService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOperationHistorySig>>();
      const operationHistory = { operationHistoryId: 123 };
      jest.spyOn(operationHistoryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ operationHistory });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(operationHistoryService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareOperationTypeSig', () => {
      it('Should forward to operationTypeService', () => {
        const entity = { operationTypeId: 123 };
        const entity2 = { operationTypeId: 456 };
        jest.spyOn(operationTypeService, 'compareOperationTypeSig');
        comp.compareOperationTypeSig(entity, entity2);
        expect(operationTypeService.compareOperationTypeSig).toHaveBeenCalledWith(entity, entity2);
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
