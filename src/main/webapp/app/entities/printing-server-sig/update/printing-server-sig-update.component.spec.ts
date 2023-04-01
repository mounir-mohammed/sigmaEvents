import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PrintingServerSigFormService } from './printing-server-sig-form.service';
import { PrintingServerSigService } from '../service/printing-server-sig.service';
import { IPrintingServerSig } from '../printing-server-sig.model';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';
import { EventSigService } from 'app/entities/event-sig/service/event-sig.service';

import { PrintingServerSigUpdateComponent } from './printing-server-sig-update.component';

describe('PrintingServerSig Management Update Component', () => {
  let comp: PrintingServerSigUpdateComponent;
  let fixture: ComponentFixture<PrintingServerSigUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let printingServerFormService: PrintingServerSigFormService;
  let printingServerService: PrintingServerSigService;
  let eventService: EventSigService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PrintingServerSigUpdateComponent],
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
      .overrideTemplate(PrintingServerSigUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PrintingServerSigUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    printingServerFormService = TestBed.inject(PrintingServerSigFormService);
    printingServerService = TestBed.inject(PrintingServerSigService);
    eventService = TestBed.inject(EventSigService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call EventSig query and add missing value', () => {
      const printingServer: IPrintingServerSig = { printingServerId: 456 };
      const event: IEventSig = { eventId: 62147 };
      printingServer.event = event;

      const eventCollection: IEventSig[] = [{ eventId: 12675 }];
      jest.spyOn(eventService, 'query').mockReturnValue(of(new HttpResponse({ body: eventCollection })));
      const additionalEventSigs = [event];
      const expectedCollection: IEventSig[] = [...additionalEventSigs, ...eventCollection];
      jest.spyOn(eventService, 'addEventSigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ printingServer });
      comp.ngOnInit();

      expect(eventService.query).toHaveBeenCalled();
      expect(eventService.addEventSigToCollectionIfMissing).toHaveBeenCalledWith(
        eventCollection,
        ...additionalEventSigs.map(expect.objectContaining)
      );
      expect(comp.eventsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const printingServer: IPrintingServerSig = { printingServerId: 456 };
      const event: IEventSig = { eventId: 98411 };
      printingServer.event = event;

      activatedRoute.data = of({ printingServer });
      comp.ngOnInit();

      expect(comp.eventsSharedCollection).toContain(event);
      expect(comp.printingServer).toEqual(printingServer);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPrintingServerSig>>();
      const printingServer = { printingServerId: 123 };
      jest.spyOn(printingServerFormService, 'getPrintingServerSig').mockReturnValue(printingServer);
      jest.spyOn(printingServerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ printingServer });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: printingServer }));
      saveSubject.complete();

      // THEN
      expect(printingServerFormService.getPrintingServerSig).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(printingServerService.update).toHaveBeenCalledWith(expect.objectContaining(printingServer));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPrintingServerSig>>();
      const printingServer = { printingServerId: 123 };
      jest.spyOn(printingServerFormService, 'getPrintingServerSig').mockReturnValue({ printingServerId: null });
      jest.spyOn(printingServerService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ printingServer: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: printingServer }));
      saveSubject.complete();

      // THEN
      expect(printingServerFormService.getPrintingServerSig).toHaveBeenCalled();
      expect(printingServerService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPrintingServerSig>>();
      const printingServer = { printingServerId: 123 };
      jest.spyOn(printingServerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ printingServer });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(printingServerService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareEventSig', () => {
      it('Should forward to eventService', () => {
        const entity = { eventId: 123 };
        const entity2 = { eventId: 456 };
        jest.spyOn(eventService, 'compareEventSig');
        comp.compareEventSig(entity, entity2);
        expect(eventService.compareEventSig).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
