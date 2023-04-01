import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AttachementSigFormService } from './attachement-sig-form.service';
import { AttachementSigService } from '../service/attachement-sig.service';
import { IAttachementSig } from '../attachement-sig.model';
import { IAttachementTypeSig } from 'app/entities/attachement-type-sig/attachement-type-sig.model';
import { AttachementTypeSigService } from 'app/entities/attachement-type-sig/service/attachement-type-sig.service';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';
import { EventSigService } from 'app/entities/event-sig/service/event-sig.service';

import { AttachementSigUpdateComponent } from './attachement-sig-update.component';

describe('AttachementSig Management Update Component', () => {
  let comp: AttachementSigUpdateComponent;
  let fixture: ComponentFixture<AttachementSigUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let attachementFormService: AttachementSigFormService;
  let attachementService: AttachementSigService;
  let attachementTypeService: AttachementTypeSigService;
  let eventService: EventSigService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AttachementSigUpdateComponent],
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
      .overrideTemplate(AttachementSigUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AttachementSigUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    attachementFormService = TestBed.inject(AttachementSigFormService);
    attachementService = TestBed.inject(AttachementSigService);
    attachementTypeService = TestBed.inject(AttachementTypeSigService);
    eventService = TestBed.inject(EventSigService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call AttachementTypeSig query and add missing value', () => {
      const attachement: IAttachementSig = { attachementId: 456 };
      const attachementType: IAttachementTypeSig = { attachementTypeId: 87096 };
      attachement.attachementType = attachementType;

      const attachementTypeCollection: IAttachementTypeSig[] = [{ attachementTypeId: 91515 }];
      jest.spyOn(attachementTypeService, 'query').mockReturnValue(of(new HttpResponse({ body: attachementTypeCollection })));
      const additionalAttachementTypeSigs = [attachementType];
      const expectedCollection: IAttachementTypeSig[] = [...additionalAttachementTypeSigs, ...attachementTypeCollection];
      jest.spyOn(attachementTypeService, 'addAttachementTypeSigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ attachement });
      comp.ngOnInit();

      expect(attachementTypeService.query).toHaveBeenCalled();
      expect(attachementTypeService.addAttachementTypeSigToCollectionIfMissing).toHaveBeenCalledWith(
        attachementTypeCollection,
        ...additionalAttachementTypeSigs.map(expect.objectContaining)
      );
      expect(comp.attachementTypesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call EventSig query and add missing value', () => {
      const attachement: IAttachementSig = { attachementId: 456 };
      const event: IEventSig = { eventId: 15136 };
      attachement.event = event;

      const eventCollection: IEventSig[] = [{ eventId: 45370 }];
      jest.spyOn(eventService, 'query').mockReturnValue(of(new HttpResponse({ body: eventCollection })));
      const additionalEventSigs = [event];
      const expectedCollection: IEventSig[] = [...additionalEventSigs, ...eventCollection];
      jest.spyOn(eventService, 'addEventSigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ attachement });
      comp.ngOnInit();

      expect(eventService.query).toHaveBeenCalled();
      expect(eventService.addEventSigToCollectionIfMissing).toHaveBeenCalledWith(
        eventCollection,
        ...additionalEventSigs.map(expect.objectContaining)
      );
      expect(comp.eventsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const attachement: IAttachementSig = { attachementId: 456 };
      const attachementType: IAttachementTypeSig = { attachementTypeId: 31403 };
      attachement.attachementType = attachementType;
      const event: IEventSig = { eventId: 92401 };
      attachement.event = event;

      activatedRoute.data = of({ attachement });
      comp.ngOnInit();

      expect(comp.attachementTypesSharedCollection).toContain(attachementType);
      expect(comp.eventsSharedCollection).toContain(event);
      expect(comp.attachement).toEqual(attachement);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAttachementSig>>();
      const attachement = { attachementId: 123 };
      jest.spyOn(attachementFormService, 'getAttachementSig').mockReturnValue(attachement);
      jest.spyOn(attachementService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ attachement });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: attachement }));
      saveSubject.complete();

      // THEN
      expect(attachementFormService.getAttachementSig).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(attachementService.update).toHaveBeenCalledWith(expect.objectContaining(attachement));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAttachementSig>>();
      const attachement = { attachementId: 123 };
      jest.spyOn(attachementFormService, 'getAttachementSig').mockReturnValue({ attachementId: null });
      jest.spyOn(attachementService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ attachement: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: attachement }));
      saveSubject.complete();

      // THEN
      expect(attachementFormService.getAttachementSig).toHaveBeenCalled();
      expect(attachementService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAttachementSig>>();
      const attachement = { attachementId: 123 };
      jest.spyOn(attachementService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ attachement });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(attachementService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareAttachementTypeSig', () => {
      it('Should forward to attachementTypeService', () => {
        const entity = { attachementTypeId: 123 };
        const entity2 = { attachementTypeId: 456 };
        jest.spyOn(attachementTypeService, 'compareAttachementTypeSig');
        comp.compareAttachementTypeSig(entity, entity2);
        expect(attachementTypeService.compareAttachementTypeSig).toHaveBeenCalledWith(entity, entity2);
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
