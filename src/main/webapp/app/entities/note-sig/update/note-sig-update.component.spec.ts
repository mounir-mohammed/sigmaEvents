import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { NoteSigFormService } from './note-sig-form.service';
import { NoteSigService } from '../service/note-sig.service';
import { INoteSig } from '../note-sig.model';
import { IAccreditationSig } from 'app/entities/accreditation-sig/accreditation-sig.model';
import { AccreditationSigService } from 'app/entities/accreditation-sig/service/accreditation-sig.service';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';
import { EventSigService } from 'app/entities/event-sig/service/event-sig.service';

import { NoteSigUpdateComponent } from './note-sig-update.component';

describe('NoteSig Management Update Component', () => {
  let comp: NoteSigUpdateComponent;
  let fixture: ComponentFixture<NoteSigUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let noteFormService: NoteSigFormService;
  let noteService: NoteSigService;
  let accreditationService: AccreditationSigService;
  let eventService: EventSigService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [NoteSigUpdateComponent],
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
      .overrideTemplate(NoteSigUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NoteSigUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    noteFormService = TestBed.inject(NoteSigFormService);
    noteService = TestBed.inject(NoteSigService);
    accreditationService = TestBed.inject(AccreditationSigService);
    eventService = TestBed.inject(EventSigService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call AccreditationSig query and add missing value', () => {
      const note: INoteSig = { noteId: 456 };
      const accreditation: IAccreditationSig = { accreditationId: 83158 };
      note.accreditation = accreditation;

      const accreditationCollection: IAccreditationSig[] = [{ accreditationId: 9261 }];
      jest.spyOn(accreditationService, 'query').mockReturnValue(of(new HttpResponse({ body: accreditationCollection })));
      const additionalAccreditationSigs = [accreditation];
      const expectedCollection: IAccreditationSig[] = [...additionalAccreditationSigs, ...accreditationCollection];
      jest.spyOn(accreditationService, 'addAccreditationSigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ note });
      comp.ngOnInit();

      expect(accreditationService.query).toHaveBeenCalled();
      expect(accreditationService.addAccreditationSigToCollectionIfMissing).toHaveBeenCalledWith(
        accreditationCollection,
        ...additionalAccreditationSigs.map(expect.objectContaining)
      );
      expect(comp.accreditationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call EventSig query and add missing value', () => {
      const note: INoteSig = { noteId: 456 };
      const event: IEventSig = { eventId: 22517 };
      note.event = event;

      const eventCollection: IEventSig[] = [{ eventId: 10675 }];
      jest.spyOn(eventService, 'query').mockReturnValue(of(new HttpResponse({ body: eventCollection })));
      const additionalEventSigs = [event];
      const expectedCollection: IEventSig[] = [...additionalEventSigs, ...eventCollection];
      jest.spyOn(eventService, 'addEventSigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ note });
      comp.ngOnInit();

      expect(eventService.query).toHaveBeenCalled();
      expect(eventService.addEventSigToCollectionIfMissing).toHaveBeenCalledWith(
        eventCollection,
        ...additionalEventSigs.map(expect.objectContaining)
      );
      expect(comp.eventsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const note: INoteSig = { noteId: 456 };
      const accreditation: IAccreditationSig = { accreditationId: 49784 };
      note.accreditation = accreditation;
      const event: IEventSig = { eventId: 84648 };
      note.event = event;

      activatedRoute.data = of({ note });
      comp.ngOnInit();

      expect(comp.accreditationsSharedCollection).toContain(accreditation);
      expect(comp.eventsSharedCollection).toContain(event);
      expect(comp.note).toEqual(note);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INoteSig>>();
      const note = { noteId: 123 };
      jest.spyOn(noteFormService, 'getNoteSig').mockReturnValue(note);
      jest.spyOn(noteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ note });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: note }));
      saveSubject.complete();

      // THEN
      expect(noteFormService.getNoteSig).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(noteService.update).toHaveBeenCalledWith(expect.objectContaining(note));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INoteSig>>();
      const note = { noteId: 123 };
      jest.spyOn(noteFormService, 'getNoteSig').mockReturnValue({ noteId: null });
      jest.spyOn(noteService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ note: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: note }));
      saveSubject.complete();

      // THEN
      expect(noteFormService.getNoteSig).toHaveBeenCalled();
      expect(noteService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INoteSig>>();
      const note = { noteId: 123 };
      jest.spyOn(noteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ note });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(noteService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareAccreditationSig', () => {
      it('Should forward to accreditationService', () => {
        const entity = { accreditationId: 123 };
        const entity2 = { accreditationId: 456 };
        jest.spyOn(accreditationService, 'compareAccreditationSig');
        comp.compareAccreditationSig(entity, entity2);
        expect(accreditationService.compareAccreditationSig).toHaveBeenCalledWith(entity, entity2);
      });
    });

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
