import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { NationalitySigFormService } from './nationality-sig-form.service';
import { NationalitySigService } from '../service/nationality-sig.service';
import { INationalitySig } from '../nationality-sig.model';

import { NationalitySigUpdateComponent } from './nationality-sig-update.component';

describe('NationalitySig Management Update Component', () => {
  let comp: NationalitySigUpdateComponent;
  let fixture: ComponentFixture<NationalitySigUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let nationalityFormService: NationalitySigFormService;
  let nationalityService: NationalitySigService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [NationalitySigUpdateComponent],
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
      .overrideTemplate(NationalitySigUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NationalitySigUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    nationalityFormService = TestBed.inject(NationalitySigFormService);
    nationalityService = TestBed.inject(NationalitySigService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const nationality: INationalitySig = { nationalityId: 456 };

      activatedRoute.data = of({ nationality });
      comp.ngOnInit();

      expect(comp.nationality).toEqual(nationality);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INationalitySig>>();
      const nationality = { nationalityId: 123 };
      jest.spyOn(nationalityFormService, 'getNationalitySig').mockReturnValue(nationality);
      jest.spyOn(nationalityService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ nationality });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: nationality }));
      saveSubject.complete();

      // THEN
      expect(nationalityFormService.getNationalitySig).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(nationalityService.update).toHaveBeenCalledWith(expect.objectContaining(nationality));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INationalitySig>>();
      const nationality = { nationalityId: 123 };
      jest.spyOn(nationalityFormService, 'getNationalitySig').mockReturnValue({ nationalityId: null });
      jest.spyOn(nationalityService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ nationality: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: nationality }));
      saveSubject.complete();

      // THEN
      expect(nationalityFormService.getNationalitySig).toHaveBeenCalled();
      expect(nationalityService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INationalitySig>>();
      const nationality = { nationalityId: 123 };
      jest.spyOn(nationalityService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ nationality });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(nationalityService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
