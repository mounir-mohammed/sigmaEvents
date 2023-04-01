import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PhotoArchiveSigFormService } from './photo-archive-sig-form.service';
import { PhotoArchiveSigService } from '../service/photo-archive-sig.service';
import { IPhotoArchiveSig } from '../photo-archive-sig.model';
import { IAccreditationSig } from 'app/entities/accreditation-sig/accreditation-sig.model';
import { AccreditationSigService } from 'app/entities/accreditation-sig/service/accreditation-sig.service';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';
import { EventSigService } from 'app/entities/event-sig/service/event-sig.service';

import { PhotoArchiveSigUpdateComponent } from './photo-archive-sig-update.component';

describe('PhotoArchiveSig Management Update Component', () => {
  let comp: PhotoArchiveSigUpdateComponent;
  let fixture: ComponentFixture<PhotoArchiveSigUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let photoArchiveFormService: PhotoArchiveSigFormService;
  let photoArchiveService: PhotoArchiveSigService;
  let accreditationService: AccreditationSigService;
  let eventService: EventSigService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PhotoArchiveSigUpdateComponent],
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
      .overrideTemplate(PhotoArchiveSigUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PhotoArchiveSigUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    photoArchiveFormService = TestBed.inject(PhotoArchiveSigFormService);
    photoArchiveService = TestBed.inject(PhotoArchiveSigService);
    accreditationService = TestBed.inject(AccreditationSigService);
    eventService = TestBed.inject(EventSigService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call AccreditationSig query and add missing value', () => {
      const photoArchive: IPhotoArchiveSig = { photoArchiveId: 456 };
      const accreditation: IAccreditationSig = { accreditationId: 94805 };
      photoArchive.accreditation = accreditation;

      const accreditationCollection: IAccreditationSig[] = [{ accreditationId: 32163 }];
      jest.spyOn(accreditationService, 'query').mockReturnValue(of(new HttpResponse({ body: accreditationCollection })));
      const additionalAccreditationSigs = [accreditation];
      const expectedCollection: IAccreditationSig[] = [...additionalAccreditationSigs, ...accreditationCollection];
      jest.spyOn(accreditationService, 'addAccreditationSigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ photoArchive });
      comp.ngOnInit();

      expect(accreditationService.query).toHaveBeenCalled();
      expect(accreditationService.addAccreditationSigToCollectionIfMissing).toHaveBeenCalledWith(
        accreditationCollection,
        ...additionalAccreditationSigs.map(expect.objectContaining)
      );
      expect(comp.accreditationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call EventSig query and add missing value', () => {
      const photoArchive: IPhotoArchiveSig = { photoArchiveId: 456 };
      const event: IEventSig = { eventId: 34051 };
      photoArchive.event = event;

      const eventCollection: IEventSig[] = [{ eventId: 57359 }];
      jest.spyOn(eventService, 'query').mockReturnValue(of(new HttpResponse({ body: eventCollection })));
      const additionalEventSigs = [event];
      const expectedCollection: IEventSig[] = [...additionalEventSigs, ...eventCollection];
      jest.spyOn(eventService, 'addEventSigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ photoArchive });
      comp.ngOnInit();

      expect(eventService.query).toHaveBeenCalled();
      expect(eventService.addEventSigToCollectionIfMissing).toHaveBeenCalledWith(
        eventCollection,
        ...additionalEventSigs.map(expect.objectContaining)
      );
      expect(comp.eventsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const photoArchive: IPhotoArchiveSig = { photoArchiveId: 456 };
      const accreditation: IAccreditationSig = { accreditationId: 85509 };
      photoArchive.accreditation = accreditation;
      const event: IEventSig = { eventId: 41259 };
      photoArchive.event = event;

      activatedRoute.data = of({ photoArchive });
      comp.ngOnInit();

      expect(comp.accreditationsSharedCollection).toContain(accreditation);
      expect(comp.eventsSharedCollection).toContain(event);
      expect(comp.photoArchive).toEqual(photoArchive);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPhotoArchiveSig>>();
      const photoArchive = { photoArchiveId: 123 };
      jest.spyOn(photoArchiveFormService, 'getPhotoArchiveSig').mockReturnValue(photoArchive);
      jest.spyOn(photoArchiveService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ photoArchive });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: photoArchive }));
      saveSubject.complete();

      // THEN
      expect(photoArchiveFormService.getPhotoArchiveSig).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(photoArchiveService.update).toHaveBeenCalledWith(expect.objectContaining(photoArchive));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPhotoArchiveSig>>();
      const photoArchive = { photoArchiveId: 123 };
      jest.spyOn(photoArchiveFormService, 'getPhotoArchiveSig').mockReturnValue({ photoArchiveId: null });
      jest.spyOn(photoArchiveService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ photoArchive: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: photoArchive }));
      saveSubject.complete();

      // THEN
      expect(photoArchiveFormService.getPhotoArchiveSig).toHaveBeenCalled();
      expect(photoArchiveService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPhotoArchiveSig>>();
      const photoArchive = { photoArchiveId: 123 };
      jest.spyOn(photoArchiveService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ photoArchive });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(photoArchiveService.update).toHaveBeenCalled();
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
