import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CodeTypeSigFormService } from './code-type-sig-form.service';
import { CodeTypeSigService } from '../service/code-type-sig.service';
import { ICodeTypeSig } from '../code-type-sig.model';

import { CodeTypeSigUpdateComponent } from './code-type-sig-update.component';

describe('CodeTypeSig Management Update Component', () => {
  let comp: CodeTypeSigUpdateComponent;
  let fixture: ComponentFixture<CodeTypeSigUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let codeTypeFormService: CodeTypeSigFormService;
  let codeTypeService: CodeTypeSigService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CodeTypeSigUpdateComponent],
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
      .overrideTemplate(CodeTypeSigUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CodeTypeSigUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    codeTypeFormService = TestBed.inject(CodeTypeSigFormService);
    codeTypeService = TestBed.inject(CodeTypeSigService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const codeType: ICodeTypeSig = { codeTypeId: 456 };

      activatedRoute.data = of({ codeType });
      comp.ngOnInit();

      expect(comp.codeType).toEqual(codeType);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICodeTypeSig>>();
      const codeType = { codeTypeId: 123 };
      jest.spyOn(codeTypeFormService, 'getCodeTypeSig').mockReturnValue(codeType);
      jest.spyOn(codeTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ codeType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: codeType }));
      saveSubject.complete();

      // THEN
      expect(codeTypeFormService.getCodeTypeSig).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(codeTypeService.update).toHaveBeenCalledWith(expect.objectContaining(codeType));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICodeTypeSig>>();
      const codeType = { codeTypeId: 123 };
      jest.spyOn(codeTypeFormService, 'getCodeTypeSig').mockReturnValue({ codeTypeId: null });
      jest.spyOn(codeTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ codeType: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: codeType }));
      saveSubject.complete();

      // THEN
      expect(codeTypeFormService.getCodeTypeSig).toHaveBeenCalled();
      expect(codeTypeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICodeTypeSig>>();
      const codeType = { codeTypeId: 123 };
      jest.spyOn(codeTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ codeType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(codeTypeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
