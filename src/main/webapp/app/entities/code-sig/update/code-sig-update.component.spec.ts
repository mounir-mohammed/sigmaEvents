import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CodeSigFormService } from './code-sig-form.service';
import { CodeSigService } from '../service/code-sig.service';
import { ICodeSig } from '../code-sig.model';
import { ICodeTypeSig } from 'app/entities/code-type-sig/code-type-sig.model';
import { CodeTypeSigService } from 'app/entities/code-type-sig/service/code-type-sig.service';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';
import { EventSigService } from 'app/entities/event-sig/service/event-sig.service';

import { CodeSigUpdateComponent } from './code-sig-update.component';

describe('CodeSig Management Update Component', () => {
  let comp: CodeSigUpdateComponent;
  let fixture: ComponentFixture<CodeSigUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let codeFormService: CodeSigFormService;
  let codeService: CodeSigService;
  let codeTypeService: CodeTypeSigService;
  let eventService: EventSigService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CodeSigUpdateComponent],
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
      .overrideTemplate(CodeSigUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CodeSigUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    codeFormService = TestBed.inject(CodeSigFormService);
    codeService = TestBed.inject(CodeSigService);
    codeTypeService = TestBed.inject(CodeTypeSigService);
    eventService = TestBed.inject(EventSigService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call CodeTypeSig query and add missing value', () => {
      const code: ICodeSig = { codeId: 456 };
      const codeType: ICodeTypeSig = { codeTypeId: 792 };
      code.codeType = codeType;

      const codeTypeCollection: ICodeTypeSig[] = [{ codeTypeId: 170 }];
      jest.spyOn(codeTypeService, 'query').mockReturnValue(of(new HttpResponse({ body: codeTypeCollection })));
      const additionalCodeTypeSigs = [codeType];
      const expectedCollection: ICodeTypeSig[] = [...additionalCodeTypeSigs, ...codeTypeCollection];
      jest.spyOn(codeTypeService, 'addCodeTypeSigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ code });
      comp.ngOnInit();

      expect(codeTypeService.query).toHaveBeenCalled();
      expect(codeTypeService.addCodeTypeSigToCollectionIfMissing).toHaveBeenCalledWith(
        codeTypeCollection,
        ...additionalCodeTypeSigs.map(expect.objectContaining)
      );
      expect(comp.codeTypesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call EventSig query and add missing value', () => {
      const code: ICodeSig = { codeId: 456 };
      const event: IEventSig = { eventId: 17839 };
      code.event = event;

      const eventCollection: IEventSig[] = [{ eventId: 84093 }];
      jest.spyOn(eventService, 'query').mockReturnValue(of(new HttpResponse({ body: eventCollection })));
      const additionalEventSigs = [event];
      const expectedCollection: IEventSig[] = [...additionalEventSigs, ...eventCollection];
      jest.spyOn(eventService, 'addEventSigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ code });
      comp.ngOnInit();

      expect(eventService.query).toHaveBeenCalled();
      expect(eventService.addEventSigToCollectionIfMissing).toHaveBeenCalledWith(
        eventCollection,
        ...additionalEventSigs.map(expect.objectContaining)
      );
      expect(comp.eventsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const code: ICodeSig = { codeId: 456 };
      const codeType: ICodeTypeSig = { codeTypeId: 32121 };
      code.codeType = codeType;
      const event: IEventSig = { eventId: 42067 };
      code.event = event;

      activatedRoute.data = of({ code });
      comp.ngOnInit();

      expect(comp.codeTypesSharedCollection).toContain(codeType);
      expect(comp.eventsSharedCollection).toContain(event);
      expect(comp.code).toEqual(code);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICodeSig>>();
      const code = { codeId: 123 };
      jest.spyOn(codeFormService, 'getCodeSig').mockReturnValue(code);
      jest.spyOn(codeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ code });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: code }));
      saveSubject.complete();

      // THEN
      expect(codeFormService.getCodeSig).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(codeService.update).toHaveBeenCalledWith(expect.objectContaining(code));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICodeSig>>();
      const code = { codeId: 123 };
      jest.spyOn(codeFormService, 'getCodeSig').mockReturnValue({ codeId: null });
      jest.spyOn(codeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ code: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: code }));
      saveSubject.complete();

      // THEN
      expect(codeFormService.getCodeSig).toHaveBeenCalled();
      expect(codeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICodeSig>>();
      const code = { codeId: 123 };
      jest.spyOn(codeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ code });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(codeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCodeTypeSig', () => {
      it('Should forward to codeTypeService', () => {
        const entity = { codeTypeId: 123 };
        const entity2 = { codeTypeId: 456 };
        jest.spyOn(codeTypeService, 'compareCodeTypeSig');
        comp.compareCodeTypeSig(entity, entity2);
        expect(codeTypeService.compareCodeTypeSig).toHaveBeenCalledWith(entity, entity2);
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
