import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AuthentificationTypeSigFormService } from './authentification-type-sig-form.service';
import { AuthentificationTypeSigService } from '../service/authentification-type-sig.service';
import { IAuthentificationTypeSig } from '../authentification-type-sig.model';

import { AuthentificationTypeSigUpdateComponent } from './authentification-type-sig-update.component';

describe('AuthentificationTypeSig Management Update Component', () => {
  let comp: AuthentificationTypeSigUpdateComponent;
  let fixture: ComponentFixture<AuthentificationTypeSigUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let authentificationTypeFormService: AuthentificationTypeSigFormService;
  let authentificationTypeService: AuthentificationTypeSigService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AuthentificationTypeSigUpdateComponent],
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
      .overrideTemplate(AuthentificationTypeSigUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AuthentificationTypeSigUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    authentificationTypeFormService = TestBed.inject(AuthentificationTypeSigFormService);
    authentificationTypeService = TestBed.inject(AuthentificationTypeSigService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const authentificationType: IAuthentificationTypeSig = { authentificationTypeId: 456 };

      activatedRoute.data = of({ authentificationType });
      comp.ngOnInit();

      expect(comp.authentificationType).toEqual(authentificationType);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAuthentificationTypeSig>>();
      const authentificationType = { authentificationTypeId: 123 };
      jest.spyOn(authentificationTypeFormService, 'getAuthentificationTypeSig').mockReturnValue(authentificationType);
      jest.spyOn(authentificationTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ authentificationType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: authentificationType }));
      saveSubject.complete();

      // THEN
      expect(authentificationTypeFormService.getAuthentificationTypeSig).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(authentificationTypeService.update).toHaveBeenCalledWith(expect.objectContaining(authentificationType));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAuthentificationTypeSig>>();
      const authentificationType = { authentificationTypeId: 123 };
      jest.spyOn(authentificationTypeFormService, 'getAuthentificationTypeSig').mockReturnValue({ authentificationTypeId: null });
      jest.spyOn(authentificationTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ authentificationType: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: authentificationType }));
      saveSubject.complete();

      // THEN
      expect(authentificationTypeFormService.getAuthentificationTypeSig).toHaveBeenCalled();
      expect(authentificationTypeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAuthentificationTypeSig>>();
      const authentificationType = { authentificationTypeId: 123 };
      jest.spyOn(authentificationTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ authentificationType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(authentificationTypeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
