import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CloningSigFormService } from './cloning-sig-form.service';
import { CloningSigService } from '../service/cloning-sig.service';
import { ICloningSig } from '../cloning-sig.model';

import { CloningSigUpdateComponent } from './cloning-sig-update.component';

describe('CloningSig Management Update Component', () => {
  let comp: CloningSigUpdateComponent;
  let fixture: ComponentFixture<CloningSigUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let cloningFormService: CloningSigFormService;
  let cloningService: CloningSigService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CloningSigUpdateComponent],
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
      .overrideTemplate(CloningSigUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CloningSigUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    cloningFormService = TestBed.inject(CloningSigFormService);
    cloningService = TestBed.inject(CloningSigService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const cloning: ICloningSig = { cloningId: 456 };

      activatedRoute.data = of({ cloning });
      comp.ngOnInit();

      expect(comp.cloning).toEqual(cloning);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICloningSig>>();
      const cloning = { cloningId: 123 };
      jest.spyOn(cloningFormService, 'getCloningSig').mockReturnValue(cloning);
      jest.spyOn(cloningService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cloning });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cloning }));
      saveSubject.complete();

      // THEN
      expect(cloningFormService.getCloningSig).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(cloningService.update).toHaveBeenCalledWith(expect.objectContaining(cloning));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICloningSig>>();
      const cloning = { cloningId: 123 };
      jest.spyOn(cloningFormService, 'getCloningSig').mockReturnValue({ cloningId: null });
      jest.spyOn(cloningService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cloning: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cloning }));
      saveSubject.complete();

      // THEN
      expect(cloningFormService.getCloningSig).toHaveBeenCalled();
      expect(cloningService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICloningSig>>();
      const cloning = { cloningId: 123 };
      jest.spyOn(cloningService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cloning });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(cloningService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
