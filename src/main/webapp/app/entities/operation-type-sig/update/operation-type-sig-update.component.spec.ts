import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { OperationTypeSigFormService } from './operation-type-sig-form.service';
import { OperationTypeSigService } from '../service/operation-type-sig.service';
import { IOperationTypeSig } from '../operation-type-sig.model';

import { OperationTypeSigUpdateComponent } from './operation-type-sig-update.component';

describe('OperationTypeSig Management Update Component', () => {
  let comp: OperationTypeSigUpdateComponent;
  let fixture: ComponentFixture<OperationTypeSigUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let operationTypeFormService: OperationTypeSigFormService;
  let operationTypeService: OperationTypeSigService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [OperationTypeSigUpdateComponent],
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
      .overrideTemplate(OperationTypeSigUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OperationTypeSigUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    operationTypeFormService = TestBed.inject(OperationTypeSigFormService);
    operationTypeService = TestBed.inject(OperationTypeSigService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const operationType: IOperationTypeSig = { operationTypeId: 456 };

      activatedRoute.data = of({ operationType });
      comp.ngOnInit();

      expect(comp.operationType).toEqual(operationType);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOperationTypeSig>>();
      const operationType = { operationTypeId: 123 };
      jest.spyOn(operationTypeFormService, 'getOperationTypeSig').mockReturnValue(operationType);
      jest.spyOn(operationTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ operationType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: operationType }));
      saveSubject.complete();

      // THEN
      expect(operationTypeFormService.getOperationTypeSig).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(operationTypeService.update).toHaveBeenCalledWith(expect.objectContaining(operationType));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOperationTypeSig>>();
      const operationType = { operationTypeId: 123 };
      jest.spyOn(operationTypeFormService, 'getOperationTypeSig').mockReturnValue({ operationTypeId: null });
      jest.spyOn(operationTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ operationType: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: operationType }));
      saveSubject.complete();

      // THEN
      expect(operationTypeFormService.getOperationTypeSig).toHaveBeenCalled();
      expect(operationTypeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOperationTypeSig>>();
      const operationType = { operationTypeId: 123 };
      jest.spyOn(operationTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ operationType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(operationTypeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
