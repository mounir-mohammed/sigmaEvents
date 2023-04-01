import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AttachementTypeSigFormService } from './attachement-type-sig-form.service';
import { AttachementTypeSigService } from '../service/attachement-type-sig.service';
import { IAttachementTypeSig } from '../attachement-type-sig.model';

import { AttachementTypeSigUpdateComponent } from './attachement-type-sig-update.component';

describe('AttachementTypeSig Management Update Component', () => {
  let comp: AttachementTypeSigUpdateComponent;
  let fixture: ComponentFixture<AttachementTypeSigUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let attachementTypeFormService: AttachementTypeSigFormService;
  let attachementTypeService: AttachementTypeSigService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AttachementTypeSigUpdateComponent],
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
      .overrideTemplate(AttachementTypeSigUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AttachementTypeSigUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    attachementTypeFormService = TestBed.inject(AttachementTypeSigFormService);
    attachementTypeService = TestBed.inject(AttachementTypeSigService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const attachementType: IAttachementTypeSig = { attachementTypeId: 456 };

      activatedRoute.data = of({ attachementType });
      comp.ngOnInit();

      expect(comp.attachementType).toEqual(attachementType);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAttachementTypeSig>>();
      const attachementType = { attachementTypeId: 123 };
      jest.spyOn(attachementTypeFormService, 'getAttachementTypeSig').mockReturnValue(attachementType);
      jest.spyOn(attachementTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ attachementType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: attachementType }));
      saveSubject.complete();

      // THEN
      expect(attachementTypeFormService.getAttachementTypeSig).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(attachementTypeService.update).toHaveBeenCalledWith(expect.objectContaining(attachementType));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAttachementTypeSig>>();
      const attachementType = { attachementTypeId: 123 };
      jest.spyOn(attachementTypeFormService, 'getAttachementTypeSig').mockReturnValue({ attachementTypeId: null });
      jest.spyOn(attachementTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ attachementType: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: attachementType }));
      saveSubject.complete();

      // THEN
      expect(attachementTypeFormService.getAttachementTypeSig).toHaveBeenCalled();
      expect(attachementTypeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAttachementTypeSig>>();
      const attachementType = { attachementTypeId: 123 };
      jest.spyOn(attachementTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ attachementType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(attachementTypeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
