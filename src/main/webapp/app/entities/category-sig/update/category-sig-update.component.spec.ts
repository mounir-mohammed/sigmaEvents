import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CategorySigFormService } from './category-sig-form.service';
import { CategorySigService } from '../service/category-sig.service';
import { ICategorySig } from '../category-sig.model';
import { IPrintingModelSig } from 'app/entities/printing-model-sig/printing-model-sig.model';
import { PrintingModelSigService } from 'app/entities/printing-model-sig/service/printing-model-sig.service';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';
import { EventSigService } from 'app/entities/event-sig/service/event-sig.service';

import { CategorySigUpdateComponent } from './category-sig-update.component';

describe('CategorySig Management Update Component', () => {
  let comp: CategorySigUpdateComponent;
  let fixture: ComponentFixture<CategorySigUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let categoryFormService: CategorySigFormService;
  let categoryService: CategorySigService;
  let printingModelService: PrintingModelSigService;
  let eventService: EventSigService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CategorySigUpdateComponent],
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
      .overrideTemplate(CategorySigUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CategorySigUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    categoryFormService = TestBed.inject(CategorySigFormService);
    categoryService = TestBed.inject(CategorySigService);
    printingModelService = TestBed.inject(PrintingModelSigService);
    eventService = TestBed.inject(EventSigService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call PrintingModelSig query and add missing value', () => {
      const category: ICategorySig = { categoryId: 456 };
      const printingModel: IPrintingModelSig = { printingModelId: 11685 };
      category.printingModel = printingModel;

      const printingModelCollection: IPrintingModelSig[] = [{ printingModelId: 40204 }];
      jest.spyOn(printingModelService, 'query').mockReturnValue(of(new HttpResponse({ body: printingModelCollection })));
      const additionalPrintingModelSigs = [printingModel];
      const expectedCollection: IPrintingModelSig[] = [...additionalPrintingModelSigs, ...printingModelCollection];
      jest.spyOn(printingModelService, 'addPrintingModelSigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ category });
      comp.ngOnInit();

      expect(printingModelService.query).toHaveBeenCalled();
      expect(printingModelService.addPrintingModelSigToCollectionIfMissing).toHaveBeenCalledWith(
        printingModelCollection,
        ...additionalPrintingModelSigs.map(expect.objectContaining)
      );
      expect(comp.printingModelsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call EventSig query and add missing value', () => {
      const category: ICategorySig = { categoryId: 456 };
      const event: IEventSig = { eventId: 713 };
      category.event = event;

      const eventCollection: IEventSig[] = [{ eventId: 58149 }];
      jest.spyOn(eventService, 'query').mockReturnValue(of(new HttpResponse({ body: eventCollection })));
      const additionalEventSigs = [event];
      const expectedCollection: IEventSig[] = [...additionalEventSigs, ...eventCollection];
      jest.spyOn(eventService, 'addEventSigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ category });
      comp.ngOnInit();

      expect(eventService.query).toHaveBeenCalled();
      expect(eventService.addEventSigToCollectionIfMissing).toHaveBeenCalledWith(
        eventCollection,
        ...additionalEventSigs.map(expect.objectContaining)
      );
      expect(comp.eventsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const category: ICategorySig = { categoryId: 456 };
      const printingModel: IPrintingModelSig = { printingModelId: 77174 };
      category.printingModel = printingModel;
      const event: IEventSig = { eventId: 92026 };
      category.event = event;

      activatedRoute.data = of({ category });
      comp.ngOnInit();

      expect(comp.printingModelsSharedCollection).toContain(printingModel);
      expect(comp.eventsSharedCollection).toContain(event);
      expect(comp.category).toEqual(category);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICategorySig>>();
      const category = { categoryId: 123 };
      jest.spyOn(categoryFormService, 'getCategorySig').mockReturnValue(category);
      jest.spyOn(categoryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ category });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: category }));
      saveSubject.complete();

      // THEN
      expect(categoryFormService.getCategorySig).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(categoryService.update).toHaveBeenCalledWith(expect.objectContaining(category));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICategorySig>>();
      const category = { categoryId: 123 };
      jest.spyOn(categoryFormService, 'getCategorySig').mockReturnValue({ categoryId: null });
      jest.spyOn(categoryService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ category: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: category }));
      saveSubject.complete();

      // THEN
      expect(categoryFormService.getCategorySig).toHaveBeenCalled();
      expect(categoryService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICategorySig>>();
      const category = { categoryId: 123 };
      jest.spyOn(categoryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ category });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(categoryService.update).toHaveBeenCalled();
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
