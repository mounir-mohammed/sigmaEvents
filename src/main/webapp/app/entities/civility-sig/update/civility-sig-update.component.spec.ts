import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CivilitySigFormService } from './civility-sig-form.service';
import { CivilitySigService } from '../service/civility-sig.service';
import { ICivilitySig } from '../civility-sig.model';

import { CivilitySigUpdateComponent } from './civility-sig-update.component';

describe('CivilitySig Management Update Component', () => {
  let comp: CivilitySigUpdateComponent;
  let fixture: ComponentFixture<CivilitySigUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let civilityFormService: CivilitySigFormService;
  let civilityService: CivilitySigService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CivilitySigUpdateComponent],
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
      .overrideTemplate(CivilitySigUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CivilitySigUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    civilityFormService = TestBed.inject(CivilitySigFormService);
    civilityService = TestBed.inject(CivilitySigService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const civility: ICivilitySig = { civilityId: 456 };

      activatedRoute.data = of({ civility });
      comp.ngOnInit();

      expect(comp.civility).toEqual(civility);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICivilitySig>>();
      const civility = { civilityId: 123 };
      jest.spyOn(civilityFormService, 'getCivilitySig').mockReturnValue(civility);
      jest.spyOn(civilityService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ civility });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: civility }));
      saveSubject.complete();

      // THEN
      expect(civilityFormService.getCivilitySig).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(civilityService.update).toHaveBeenCalledWith(expect.objectContaining(civility));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICivilitySig>>();
      const civility = { civilityId: 123 };
      jest.spyOn(civilityFormService, 'getCivilitySig').mockReturnValue({ civilityId: null });
      jest.spyOn(civilityService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ civility: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: civility }));
      saveSubject.complete();

      // THEN
      expect(civilityFormService.getCivilitySig).toHaveBeenCalled();
      expect(civilityService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICivilitySig>>();
      const civility = { civilityId: 123 };
      jest.spyOn(civilityService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ civility });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(civilityService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
