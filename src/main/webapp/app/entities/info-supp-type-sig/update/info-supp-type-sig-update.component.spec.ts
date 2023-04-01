import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { InfoSuppTypeSigFormService } from './info-supp-type-sig-form.service';
import { InfoSuppTypeSigService } from '../service/info-supp-type-sig.service';
import { IInfoSuppTypeSig } from '../info-supp-type-sig.model';

import { InfoSuppTypeSigUpdateComponent } from './info-supp-type-sig-update.component';

describe('InfoSuppTypeSig Management Update Component', () => {
  let comp: InfoSuppTypeSigUpdateComponent;
  let fixture: ComponentFixture<InfoSuppTypeSigUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let infoSuppTypeFormService: InfoSuppTypeSigFormService;
  let infoSuppTypeService: InfoSuppTypeSigService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [InfoSuppTypeSigUpdateComponent],
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
      .overrideTemplate(InfoSuppTypeSigUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(InfoSuppTypeSigUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    infoSuppTypeFormService = TestBed.inject(InfoSuppTypeSigFormService);
    infoSuppTypeService = TestBed.inject(InfoSuppTypeSigService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const infoSuppType: IInfoSuppTypeSig = { infoSuppTypeId: 456 };

      activatedRoute.data = of({ infoSuppType });
      comp.ngOnInit();

      expect(comp.infoSuppType).toEqual(infoSuppType);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInfoSuppTypeSig>>();
      const infoSuppType = { infoSuppTypeId: 123 };
      jest.spyOn(infoSuppTypeFormService, 'getInfoSuppTypeSig').mockReturnValue(infoSuppType);
      jest.spyOn(infoSuppTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ infoSuppType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: infoSuppType }));
      saveSubject.complete();

      // THEN
      expect(infoSuppTypeFormService.getInfoSuppTypeSig).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(infoSuppTypeService.update).toHaveBeenCalledWith(expect.objectContaining(infoSuppType));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInfoSuppTypeSig>>();
      const infoSuppType = { infoSuppTypeId: 123 };
      jest.spyOn(infoSuppTypeFormService, 'getInfoSuppTypeSig').mockReturnValue({ infoSuppTypeId: null });
      jest.spyOn(infoSuppTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ infoSuppType: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: infoSuppType }));
      saveSubject.complete();

      // THEN
      expect(infoSuppTypeFormService.getInfoSuppTypeSig).toHaveBeenCalled();
      expect(infoSuppTypeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInfoSuppTypeSig>>();
      const infoSuppType = { infoSuppTypeId: 123 };
      jest.spyOn(infoSuppTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ infoSuppType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(infoSuppTypeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
