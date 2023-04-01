import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { LogHistorySigFormService } from './log-history-sig-form.service';
import { LogHistorySigService } from '../service/log-history-sig.service';
import { ILogHistorySig } from '../log-history-sig.model';

import { LogHistorySigUpdateComponent } from './log-history-sig-update.component';

describe('LogHistorySig Management Update Component', () => {
  let comp: LogHistorySigUpdateComponent;
  let fixture: ComponentFixture<LogHistorySigUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let logHistoryFormService: LogHistorySigFormService;
  let logHistoryService: LogHistorySigService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [LogHistorySigUpdateComponent],
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
      .overrideTemplate(LogHistorySigUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LogHistorySigUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    logHistoryFormService = TestBed.inject(LogHistorySigFormService);
    logHistoryService = TestBed.inject(LogHistorySigService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const logHistory: ILogHistorySig = { logHistory: 456 };

      activatedRoute.data = of({ logHistory });
      comp.ngOnInit();

      expect(comp.logHistory).toEqual(logHistory);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILogHistorySig>>();
      const logHistory = { logHistory: 123 };
      jest.spyOn(logHistoryFormService, 'getLogHistorySig').mockReturnValue(logHistory);
      jest.spyOn(logHistoryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ logHistory });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: logHistory }));
      saveSubject.complete();

      // THEN
      expect(logHistoryFormService.getLogHistorySig).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(logHistoryService.update).toHaveBeenCalledWith(expect.objectContaining(logHistory));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILogHistorySig>>();
      const logHistory = { logHistory: 123 };
      jest.spyOn(logHistoryFormService, 'getLogHistorySig').mockReturnValue({ logHistory: null });
      jest.spyOn(logHistoryService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ logHistory: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: logHistory }));
      saveSubject.complete();

      // THEN
      expect(logHistoryFormService.getLogHistorySig).toHaveBeenCalled();
      expect(logHistoryService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILogHistorySig>>();
      const logHistory = { logHistory: 123 };
      jest.spyOn(logHistoryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ logHistory });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(logHistoryService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
