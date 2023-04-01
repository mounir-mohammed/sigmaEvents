import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PrintingModelSigFormService } from './printing-model-sig-form.service';
import { PrintingModelSigService } from '../service/printing-model-sig.service';
import { IPrintingModelSig } from '../printing-model-sig.model';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';
import { EventSigService } from 'app/entities/event-sig/service/event-sig.service';

import { PrintingModelSigUpdateComponent } from './printing-model-sig-update.component';

describe('PrintingModelSig Management Update Component', () => {
  let comp: PrintingModelSigUpdateComponent;
  let fixture: ComponentFixture<PrintingModelSigUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let printingModelFormService: PrintingModelSigFormService;
  let printingModelService: PrintingModelSigService;
  let eventService: EventSigService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PrintingModelSigUpdateComponent],
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
      .overrideTemplate(PrintingModelSigUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PrintingModelSigUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    printingModelFormService = TestBed.inject(PrintingModelSigFormService);
    printingModelService = TestBed.inject(PrintingModelSigService);
    eventService = TestBed.inject(EventSigService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call EventSig query and add missing value', () => {
      const printingModel: IPrintingModelSig = { printingModelId: 456 };
      const event: IEventSig = { eventId: 91383 };
      printingModel.event = event;

      const eventCollection: IEventSig[] = [{ eventId: 87766 }];
      jest.spyOn(eventService, 'query').mockReturnValue(of(new HttpResponse({ body: eventCollection })));
      const additionalEventSigs = [event];
      const expectedCollection: IEventSig[] = [...additionalEventSigs, ...eventCollection];
      jest.spyOn(eventService, 'addEventSigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ printingModel });
      comp.ngOnInit();

      expect(eventService.query).toHaveBeenCalled();
      expect(eventService.addEventSigToCollectionIfMissing).toHaveBeenCalledWith(
        eventCollection,
        ...additionalEventSigs.map(expect.objectContaining)
      );
      expect(comp.eventsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const printingModel: IPrintingModelSig = { printingModelId: 456 };
      const event: IEventSig = { eventId: 20678 };
      printingModel.event = event;

      activatedRoute.data = of({ printingModel });
      comp.ngOnInit();

      expect(comp.eventsSharedCollection).toContain(event);
      expect(comp.printingModel).toEqual(printingModel);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPrintingModelSig>>();
      const printingModel = { printingModelId: 123 };
      jest.spyOn(printingModelFormService, 'getPrintingModelSig').mockReturnValue(printingModel);
      jest.spyOn(printingModelService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ printingModel });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: printingModel }));
      saveSubject.complete();

      // THEN
      expect(printingModelFormService.getPrintingModelSig).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(printingModelService.update).toHaveBeenCalledWith(expect.objectContaining(printingModel));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPrintingModelSig>>();
      const printingModel = { printingModelId: 123 };
      jest.spyOn(printingModelFormService, 'getPrintingModelSig').mockReturnValue({ printingModelId: null });
      jest.spyOn(printingModelService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ printingModel: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: printingModel }));
      saveSubject.complete();

      // THEN
      expect(printingModelFormService.getPrintingModelSig).toHaveBeenCalled();
      expect(printingModelService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPrintingModelSig>>();
      const printingModel = { printingModelId: 123 };
      jest.spyOn(printingModelService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ printingModel });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(printingModelService.update).toHaveBeenCalled();
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
