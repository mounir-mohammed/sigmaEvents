import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { StatusSigFormService } from './status-sig-form.service';
import { StatusSigService } from '../service/status-sig.service';
import { IStatusSig } from '../status-sig.model';

import { StatusSigUpdateComponent } from './status-sig-update.component';

describe('StatusSig Management Update Component', () => {
  let comp: StatusSigUpdateComponent;
  let fixture: ComponentFixture<StatusSigUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let statusFormService: StatusSigFormService;
  let statusService: StatusSigService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [StatusSigUpdateComponent],
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
      .overrideTemplate(StatusSigUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(StatusSigUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    statusFormService = TestBed.inject(StatusSigFormService);
    statusService = TestBed.inject(StatusSigService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const status: IStatusSig = { statusId: 456 };

      activatedRoute.data = of({ status });
      comp.ngOnInit();

      expect(comp.status).toEqual(status);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStatusSig>>();
      const status = { statusId: 123 };
      jest.spyOn(statusFormService, 'getStatusSig').mockReturnValue(status);
      jest.spyOn(statusService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ status });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: status }));
      saveSubject.complete();

      // THEN
      expect(statusFormService.getStatusSig).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(statusService.update).toHaveBeenCalledWith(expect.objectContaining(status));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStatusSig>>();
      const status = { statusId: 123 };
      jest.spyOn(statusFormService, 'getStatusSig').mockReturnValue({ statusId: null });
      jest.spyOn(statusService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ status: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: status }));
      saveSubject.complete();

      // THEN
      expect(statusFormService.getStatusSig).toHaveBeenCalled();
      expect(statusService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStatusSig>>();
      const status = { statusId: 123 };
      jest.spyOn(statusService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ status });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(statusService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
