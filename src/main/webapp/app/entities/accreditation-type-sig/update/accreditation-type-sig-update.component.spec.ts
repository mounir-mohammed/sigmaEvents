import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AccreditationTypeSigFormService } from './accreditation-type-sig-form.service';
import { AccreditationTypeSigService } from '../service/accreditation-type-sig.service';
import { IAccreditationTypeSig } from '../accreditation-type-sig.model';
import { IPrintingModelSig } from 'app/entities/printing-model-sig/printing-model-sig.model';
import { PrintingModelSigService } from 'app/entities/printing-model-sig/service/printing-model-sig.service';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';
import { EventSigService } from 'app/entities/event-sig/service/event-sig.service';

import { AccreditationTypeSigUpdateComponent } from './accreditation-type-sig-update.component';

describe('AccreditationTypeSig Management Update Component', () => {
  let comp: AccreditationTypeSigUpdateComponent;
  let fixture: ComponentFixture<AccreditationTypeSigUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let accreditationTypeFormService: AccreditationTypeSigFormService;
  let accreditationTypeService: AccreditationTypeSigService;
  let printingModelService: PrintingModelSigService;
  let eventService: EventSigService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AccreditationTypeSigUpdateComponent],
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
      .overrideTemplate(AccreditationTypeSigUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AccreditationTypeSigUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    accreditationTypeFormService = TestBed.inject(AccreditationTypeSigFormService);
    accreditationTypeService = TestBed.inject(AccreditationTypeSigService);
    printingModelService = TestBed.inject(PrintingModelSigService);
    eventService = TestBed.inject(EventSigService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call PrintingModelSig query and add missing value', () => {
      const accreditationType: IAccreditationTypeSig = { accreditationTypeId: 456 };
      const printingModel: IPrintingModelSig = { printingModelId: 55314 };
      accreditationType.printingModel = printingModel;

      const printingModelCollection: IPrintingModelSig[] = [{ printingModelId: 44935 }];
      jest.spyOn(printingModelService, 'query').mockReturnValue(of(new HttpResponse({ body: printingModelCollection })));
      const additionalPrintingModelSigs = [printingModel];
      const expectedCollection: IPrintingModelSig[] = [...additionalPrintingModelSigs, ...printingModelCollection];
      jest.spyOn(printingModelService, 'addPrintingModelSigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ accreditationType });
      comp.ngOnInit();

      expect(printingModelService.query).toHaveBeenCalled();
      expect(printingModelService.addPrintingModelSigToCollectionIfMissing).toHaveBeenCalledWith(
        printingModelCollection,
        ...additionalPrintingModelSigs.map(expect.objectContaining)
      );
      expect(comp.printingModelsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call EventSig query and add missing value', () => {
      const accreditationType: IAccreditationTypeSig = { accreditationTypeId: 456 };
      const event: IEventSig = { eventId: 20647 };
      accreditationType.event = event;

      const eventCollection: IEventSig[] = [{ eventId: 13780 }];
      jest.spyOn(eventService, 'query').mockReturnValue(of(new HttpResponse({ body: eventCollection })));
      const additionalEventSigs = [event];
      const expectedCollection: IEventSig[] = [...additionalEventSigs, ...eventCollection];
      jest.spyOn(eventService, 'addEventSigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ accreditationType });
      comp.ngOnInit();

      expect(eventService.query).toHaveBeenCalled();
      expect(eventService.addEventSigToCollectionIfMissing).toHaveBeenCalledWith(
        eventCollection,
        ...additionalEventSigs.map(expect.objectContaining)
      );
      expect(comp.eventsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const accreditationType: IAccreditationTypeSig = { accreditationTypeId: 456 };
      const printingModel: IPrintingModelSig = { printingModelId: 24591 };
      accreditationType.printingModel = printingModel;
      const event: IEventSig = { eventId: 56163 };
      accreditationType.event = event;

      activatedRoute.data = of({ accreditationType });
      comp.ngOnInit();

      expect(comp.printingModelsSharedCollection).toContain(printingModel);
      expect(comp.eventsSharedCollection).toContain(event);
      expect(comp.accreditationType).toEqual(accreditationType);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAccreditationTypeSig>>();
      const accreditationType = { accreditationTypeId: 123 };
      jest.spyOn(accreditationTypeFormService, 'getAccreditationTypeSig').mockReturnValue(accreditationType);
      jest.spyOn(accreditationTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ accreditationType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: accreditationType }));
      saveSubject.complete();

      // THEN
      expect(accreditationTypeFormService.getAccreditationTypeSig).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(accreditationTypeService.update).toHaveBeenCalledWith(expect.objectContaining(accreditationType));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAccreditationTypeSig>>();
      const accreditationType = { accreditationTypeId: 123 };
      jest.spyOn(accreditationTypeFormService, 'getAccreditationTypeSig').mockReturnValue({ accreditationTypeId: null });
      jest.spyOn(accreditationTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ accreditationType: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: accreditationType }));
      saveSubject.complete();

      // THEN
      expect(accreditationTypeFormService.getAccreditationTypeSig).toHaveBeenCalled();
      expect(accreditationTypeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAccreditationTypeSig>>();
      const accreditationType = { accreditationTypeId: 123 };
      jest.spyOn(accreditationTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ accreditationType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(accreditationTypeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('comparePrintingModelSig', () => {
      it('Should forward to printingModelService', () => {
        const entity = { printingModelId: 123 };
        const entity2 = { printingModelId: 456 };
        jest.spyOn(printingModelService, 'comparePrintingModelSig');
        comp.comparePrintingModelSig(entity, entity2);
        expect(printingModelService.comparePrintingModelSig).toHaveBeenCalledWith(entity, entity2);
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
