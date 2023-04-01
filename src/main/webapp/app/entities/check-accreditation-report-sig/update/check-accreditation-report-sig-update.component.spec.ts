import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CheckAccreditationReportSigFormService } from './check-accreditation-report-sig-form.service';
import { CheckAccreditationReportSigService } from '../service/check-accreditation-report-sig.service';
import { ICheckAccreditationReportSig } from '../check-accreditation-report-sig.model';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';
import { EventSigService } from 'app/entities/event-sig/service/event-sig.service';
import { ICheckAccreditationHistorySig } from 'app/entities/check-accreditation-history-sig/check-accreditation-history-sig.model';
import { CheckAccreditationHistorySigService } from 'app/entities/check-accreditation-history-sig/service/check-accreditation-history-sig.service';

import { CheckAccreditationReportSigUpdateComponent } from './check-accreditation-report-sig-update.component';

describe('CheckAccreditationReportSig Management Update Component', () => {
  let comp: CheckAccreditationReportSigUpdateComponent;
  let fixture: ComponentFixture<CheckAccreditationReportSigUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let checkAccreditationReportFormService: CheckAccreditationReportSigFormService;
  let checkAccreditationReportService: CheckAccreditationReportSigService;
  let eventService: EventSigService;
  let checkAccreditationHistoryService: CheckAccreditationHistorySigService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CheckAccreditationReportSigUpdateComponent],
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
      .overrideTemplate(CheckAccreditationReportSigUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CheckAccreditationReportSigUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    checkAccreditationReportFormService = TestBed.inject(CheckAccreditationReportSigFormService);
    checkAccreditationReportService = TestBed.inject(CheckAccreditationReportSigService);
    eventService = TestBed.inject(EventSigService);
    checkAccreditationHistoryService = TestBed.inject(CheckAccreditationHistorySigService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call EventSig query and add missing value', () => {
      const checkAccreditationReport: ICheckAccreditationReportSig = { checkAccreditationReportId: 456 };
      const event: IEventSig = { eventId: 99754 };
      checkAccreditationReport.event = event;

      const eventCollection: IEventSig[] = [{ eventId: 91543 }];
      jest.spyOn(eventService, 'query').mockReturnValue(of(new HttpResponse({ body: eventCollection })));
      const additionalEventSigs = [event];
      const expectedCollection: IEventSig[] = [...additionalEventSigs, ...eventCollection];
      jest.spyOn(eventService, 'addEventSigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ checkAccreditationReport });
      comp.ngOnInit();

      expect(eventService.query).toHaveBeenCalled();
      expect(eventService.addEventSigToCollectionIfMissing).toHaveBeenCalledWith(
        eventCollection,
        ...additionalEventSigs.map(expect.objectContaining)
      );
      expect(comp.eventsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call CheckAccreditationHistorySig query and add missing value', () => {
      const checkAccreditationReport: ICheckAccreditationReportSig = { checkAccreditationReportId: 456 };
      const checkAccreditationHistory: ICheckAccreditationHistorySig = { checkAccreditationHistoryId: 10408 };
      checkAccreditationReport.checkAccreditationHistory = checkAccreditationHistory;

      const checkAccreditationHistoryCollection: ICheckAccreditationHistorySig[] = [{ checkAccreditationHistoryId: 84210 }];
      jest
        .spyOn(checkAccreditationHistoryService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: checkAccreditationHistoryCollection })));
      const additionalCheckAccreditationHistorySigs = [checkAccreditationHistory];
      const expectedCollection: ICheckAccreditationHistorySig[] = [
        ...additionalCheckAccreditationHistorySigs,
        ...checkAccreditationHistoryCollection,
      ];
      jest
        .spyOn(checkAccreditationHistoryService, 'addCheckAccreditationHistorySigToCollectionIfMissing')
        .mockReturnValue(expectedCollection);

      activatedRoute.data = of({ checkAccreditationReport });
      comp.ngOnInit();

      expect(checkAccreditationHistoryService.query).toHaveBeenCalled();
      expect(checkAccreditationHistoryService.addCheckAccreditationHistorySigToCollectionIfMissing).toHaveBeenCalledWith(
        checkAccreditationHistoryCollection,
        ...additionalCheckAccreditationHistorySigs.map(expect.objectContaining)
      );
      expect(comp.checkAccreditationHistoriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const checkAccreditationReport: ICheckAccreditationReportSig = { checkAccreditationReportId: 456 };
      const event: IEventSig = { eventId: 16639 };
      checkAccreditationReport.event = event;
      const checkAccreditationHistory: ICheckAccreditationHistorySig = { checkAccreditationHistoryId: 20203 };
      checkAccreditationReport.checkAccreditationHistory = checkAccreditationHistory;

      activatedRoute.data = of({ checkAccreditationReport });
      comp.ngOnInit();

      expect(comp.eventsSharedCollection).toContain(event);
      expect(comp.checkAccreditationHistoriesSharedCollection).toContain(checkAccreditationHistory);
      expect(comp.checkAccreditationReport).toEqual(checkAccreditationReport);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICheckAccreditationReportSig>>();
      const checkAccreditationReport = { checkAccreditationReportId: 123 };
      jest.spyOn(checkAccreditationReportFormService, 'getCheckAccreditationReportSig').mockReturnValue(checkAccreditationReport);
      jest.spyOn(checkAccreditationReportService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ checkAccreditationReport });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: checkAccreditationReport }));
      saveSubject.complete();

      // THEN
      expect(checkAccreditationReportFormService.getCheckAccreditationReportSig).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(checkAccreditationReportService.update).toHaveBeenCalledWith(expect.objectContaining(checkAccreditationReport));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICheckAccreditationReportSig>>();
      const checkAccreditationReport = { checkAccreditationReportId: 123 };
      jest
        .spyOn(checkAccreditationReportFormService, 'getCheckAccreditationReportSig')
        .mockReturnValue({ checkAccreditationReportId: null });
      jest.spyOn(checkAccreditationReportService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ checkAccreditationReport: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: checkAccreditationReport }));
      saveSubject.complete();

      // THEN
      expect(checkAccreditationReportFormService.getCheckAccreditationReportSig).toHaveBeenCalled();
      expect(checkAccreditationReportService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICheckAccreditationReportSig>>();
      const checkAccreditationReport = { checkAccreditationReportId: 123 };
      jest.spyOn(checkAccreditationReportService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ checkAccreditationReport });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(checkAccreditationReportService.update).toHaveBeenCalled();
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

    describe('compareCheckAccreditationHistorySig', () => {
      it('Should forward to checkAccreditationHistoryService', () => {
        const entity = { checkAccreditationHistoryId: 123 };
        const entity2 = { checkAccreditationHistoryId: 456 };
        jest.spyOn(checkAccreditationHistoryService, 'compareCheckAccreditationHistorySig');
        comp.compareCheckAccreditationHistorySig(entity, entity2);
        expect(checkAccreditationHistoryService.compareCheckAccreditationHistorySig).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
