import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PrintingTypeSigFormService } from './printing-type-sig-form.service';
import { PrintingTypeSigService } from '../service/printing-type-sig.service';
import { IPrintingTypeSig } from '../printing-type-sig.model';

import { PrintingTypeSigUpdateComponent } from './printing-type-sig-update.component';

describe('PrintingTypeSig Management Update Component', () => {
  let comp: PrintingTypeSigUpdateComponent;
  let fixture: ComponentFixture<PrintingTypeSigUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let printingTypeFormService: PrintingTypeSigFormService;
  let printingTypeService: PrintingTypeSigService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PrintingTypeSigUpdateComponent],
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
      .overrideTemplate(PrintingTypeSigUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PrintingTypeSigUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    printingTypeFormService = TestBed.inject(PrintingTypeSigFormService);
    printingTypeService = TestBed.inject(PrintingTypeSigService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const printingType: IPrintingTypeSig = { printingTypeId: 456 };

      activatedRoute.data = of({ printingType });
      comp.ngOnInit();

      expect(comp.printingType).toEqual(printingType);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPrintingTypeSig>>();
      const printingType = { printingTypeId: 123 };
      jest.spyOn(printingTypeFormService, 'getPrintingTypeSig').mockReturnValue(printingType);
      jest.spyOn(printingTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ printingType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: printingType }));
      saveSubject.complete();

      // THEN
      expect(printingTypeFormService.getPrintingTypeSig).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(printingTypeService.update).toHaveBeenCalledWith(expect.objectContaining(printingType));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPrintingTypeSig>>();
      const printingType = { printingTypeId: 123 };
      jest.spyOn(printingTypeFormService, 'getPrintingTypeSig').mockReturnValue({ printingTypeId: null });
      jest.spyOn(printingTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ printingType: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: printingType }));
      saveSubject.complete();

      // THEN
      expect(printingTypeFormService.getPrintingTypeSig).toHaveBeenCalled();
      expect(printingTypeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPrintingTypeSig>>();
      const printingType = { printingTypeId: 123 };
      jest.spyOn(printingTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ printingType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(printingTypeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
