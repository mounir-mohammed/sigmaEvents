import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FonctionSigFormService } from './fonction-sig-form.service';
import { FonctionSigService } from '../service/fonction-sig.service';
import { IFonctionSig } from '../fonction-sig.model';
import { IAreaSig } from 'app/entities/area-sig/area-sig.model';
import { AreaSigService } from 'app/entities/area-sig/service/area-sig.service';
import { ICategorySig } from 'app/entities/category-sig/category-sig.model';
import { CategorySigService } from 'app/entities/category-sig/service/category-sig.service';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';
import { EventSigService } from 'app/entities/event-sig/service/event-sig.service';

import { FonctionSigUpdateComponent } from './fonction-sig-update.component';

describe('FonctionSig Management Update Component', () => {
  let comp: FonctionSigUpdateComponent;
  let fixture: ComponentFixture<FonctionSigUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let fonctionFormService: FonctionSigFormService;
  let fonctionService: FonctionSigService;
  let areaService: AreaSigService;
  let categoryService: CategorySigService;
  let eventService: EventSigService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FonctionSigUpdateComponent],
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
      .overrideTemplate(FonctionSigUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FonctionSigUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    fonctionFormService = TestBed.inject(FonctionSigFormService);
    fonctionService = TestBed.inject(FonctionSigService);
    areaService = TestBed.inject(AreaSigService);
    categoryService = TestBed.inject(CategorySigService);
    eventService = TestBed.inject(EventSigService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call AreaSig query and add missing value', () => {
      const fonction: IFonctionSig = { fonctionId: 456 };
      const areas: IAreaSig[] = [{ areaId: 83926 }];
      fonction.areas = areas;

      const areaCollection: IAreaSig[] = [{ areaId: 28497 }];
      jest.spyOn(areaService, 'query').mockReturnValue(of(new HttpResponse({ body: areaCollection })));
      const additionalAreaSigs = [...areas];
      const expectedCollection: IAreaSig[] = [...additionalAreaSigs, ...areaCollection];
      jest.spyOn(areaService, 'addAreaSigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ fonction });
      comp.ngOnInit();

      expect(areaService.query).toHaveBeenCalled();
      expect(areaService.addAreaSigToCollectionIfMissing).toHaveBeenCalledWith(
        areaCollection,
        ...additionalAreaSigs.map(expect.objectContaining)
      );
      expect(comp.areasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call CategorySig query and add missing value', () => {
      const fonction: IFonctionSig = { fonctionId: 456 };
      const category: ICategorySig = { categoryId: 76101 };
      fonction.category = category;

      const categoryCollection: ICategorySig[] = [{ categoryId: 11599 }];
      jest.spyOn(categoryService, 'query').mockReturnValue(of(new HttpResponse({ body: categoryCollection })));
      const additionalCategorySigs = [category];
      const expectedCollection: ICategorySig[] = [...additionalCategorySigs, ...categoryCollection];
      jest.spyOn(categoryService, 'addCategorySigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ fonction });
      comp.ngOnInit();

      expect(categoryService.query).toHaveBeenCalled();
      expect(categoryService.addCategorySigToCollectionIfMissing).toHaveBeenCalledWith(
        categoryCollection,
        ...additionalCategorySigs.map(expect.objectContaining)
      );
      expect(comp.categoriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call EventSig query and add missing value', () => {
      const fonction: IFonctionSig = { fonctionId: 456 };
      const event: IEventSig = { eventId: 11046 };
      fonction.event = event;

      const eventCollection: IEventSig[] = [{ eventId: 9482 }];
      jest.spyOn(eventService, 'query').mockReturnValue(of(new HttpResponse({ body: eventCollection })));
      const additionalEventSigs = [event];
      const expectedCollection: IEventSig[] = [...additionalEventSigs, ...eventCollection];
      jest.spyOn(eventService, 'addEventSigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ fonction });
      comp.ngOnInit();

      expect(eventService.query).toHaveBeenCalled();
      expect(eventService.addEventSigToCollectionIfMissing).toHaveBeenCalledWith(
        eventCollection,
        ...additionalEventSigs.map(expect.objectContaining)
      );
      expect(comp.eventsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const fonction: IFonctionSig = { fonctionId: 456 };
      const area: IAreaSig = { areaId: 81668 };
      fonction.areas = [area];
      const category: ICategorySig = { categoryId: 24308 };
      fonction.category = category;
      const event: IEventSig = { eventId: 42641 };
      fonction.event = event;

      activatedRoute.data = of({ fonction });
      comp.ngOnInit();

      expect(comp.areasSharedCollection).toContain(area);
      expect(comp.categoriesSharedCollection).toContain(category);
      expect(comp.eventsSharedCollection).toContain(event);
      expect(comp.fonction).toEqual(fonction);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFonctionSig>>();
      const fonction = { fonctionId: 123 };
      jest.spyOn(fonctionFormService, 'getFonctionSig').mockReturnValue(fonction);
      jest.spyOn(fonctionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fonction });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fonction }));
      saveSubject.complete();

      // THEN
      expect(fonctionFormService.getFonctionSig).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(fonctionService.update).toHaveBeenCalledWith(expect.objectContaining(fonction));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFonctionSig>>();
      const fonction = { fonctionId: 123 };
      jest.spyOn(fonctionFormService, 'getFonctionSig').mockReturnValue({ fonctionId: null });
      jest.spyOn(fonctionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fonction: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fonction }));
      saveSubject.complete();

      // THEN
      expect(fonctionFormService.getFonctionSig).toHaveBeenCalled();
      expect(fonctionService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFonctionSig>>();
      const fonction = { fonctionId: 123 };
      jest.spyOn(fonctionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fonction });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(fonctionService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareAreaSig', () => {
      it('Should forward to areaService', () => {
        const entity = { areaId: 123 };
        const entity2 = { areaId: 456 };
        jest.spyOn(areaService, 'compareAreaSig');
        comp.compareAreaSig(entity, entity2);
        expect(areaService.compareAreaSig).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareCategorySig', () => {
      it('Should forward to categoryService', () => {
        const entity = { categoryId: 123 };
        const entity2 = { categoryId: 456 };
        jest.spyOn(categoryService, 'compareCategorySig');
        comp.compareCategorySig(entity, entity2);
        expect(categoryService.compareCategorySig).toHaveBeenCalledWith(entity, entity2);
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
