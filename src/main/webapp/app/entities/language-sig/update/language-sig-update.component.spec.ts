import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { LanguageSigFormService } from './language-sig-form.service';
import { LanguageSigService } from '../service/language-sig.service';
import { ILanguageSig } from '../language-sig.model';

import { LanguageSigUpdateComponent } from './language-sig-update.component';

describe('LanguageSig Management Update Component', () => {
  let comp: LanguageSigUpdateComponent;
  let fixture: ComponentFixture<LanguageSigUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let languageFormService: LanguageSigFormService;
  let languageService: LanguageSigService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [LanguageSigUpdateComponent],
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
      .overrideTemplate(LanguageSigUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LanguageSigUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    languageFormService = TestBed.inject(LanguageSigFormService);
    languageService = TestBed.inject(LanguageSigService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const language: ILanguageSig = { languageId: 456 };

      activatedRoute.data = of({ language });
      comp.ngOnInit();

      expect(comp.language).toEqual(language);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILanguageSig>>();
      const language = { languageId: 123 };
      jest.spyOn(languageFormService, 'getLanguageSig').mockReturnValue(language);
      jest.spyOn(languageService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ language });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: language }));
      saveSubject.complete();

      // THEN
      expect(languageFormService.getLanguageSig).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(languageService.update).toHaveBeenCalledWith(expect.objectContaining(language));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILanguageSig>>();
      const language = { languageId: 123 };
      jest.spyOn(languageFormService, 'getLanguageSig').mockReturnValue({ languageId: null });
      jest.spyOn(languageService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ language: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: language }));
      saveSubject.complete();

      // THEN
      expect(languageFormService.getLanguageSig).toHaveBeenCalled();
      expect(languageService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILanguageSig>>();
      const language = { languageId: 123 };
      jest.spyOn(languageService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ language });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(languageService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
