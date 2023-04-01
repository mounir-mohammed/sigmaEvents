import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SexeSigFormService } from './sexe-sig-form.service';
import { SexeSigService } from '../service/sexe-sig.service';
import { ISexeSig } from '../sexe-sig.model';

import { SexeSigUpdateComponent } from './sexe-sig-update.component';

describe('SexeSig Management Update Component', () => {
  let comp: SexeSigUpdateComponent;
  let fixture: ComponentFixture<SexeSigUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let sexeFormService: SexeSigFormService;
  let sexeService: SexeSigService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SexeSigUpdateComponent],
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
      .overrideTemplate(SexeSigUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SexeSigUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    sexeFormService = TestBed.inject(SexeSigFormService);
    sexeService = TestBed.inject(SexeSigService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const sexe: ISexeSig = { sexeId: 456 };

      activatedRoute.data = of({ sexe });
      comp.ngOnInit();

      expect(comp.sexe).toEqual(sexe);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISexeSig>>();
      const sexe = { sexeId: 123 };
      jest.spyOn(sexeFormService, 'getSexeSig').mockReturnValue(sexe);
      jest.spyOn(sexeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sexe });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sexe }));
      saveSubject.complete();

      // THEN
      expect(sexeFormService.getSexeSig).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(sexeService.update).toHaveBeenCalledWith(expect.objectContaining(sexe));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISexeSig>>();
      const sexe = { sexeId: 123 };
      jest.spyOn(sexeFormService, 'getSexeSig').mockReturnValue({ sexeId: null });
      jest.spyOn(sexeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sexe: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sexe }));
      saveSubject.complete();

      // THEN
      expect(sexeFormService.getSexeSig).toHaveBeenCalled();
      expect(sexeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISexeSig>>();
      const sexe = { sexeId: 123 };
      jest.spyOn(sexeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sexe });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(sexeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
