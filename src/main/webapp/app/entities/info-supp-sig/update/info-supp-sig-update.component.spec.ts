import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { InfoSuppSigFormService } from './info-supp-sig-form.service';
import { InfoSuppSigService } from '../service/info-supp-sig.service';
import { IInfoSuppSig } from '../info-supp-sig.model';
import { IInfoSuppTypeSig } from 'app/entities/info-supp-type-sig/info-supp-type-sig.model';
import { InfoSuppTypeSigService } from 'app/entities/info-supp-type-sig/service/info-supp-type-sig.service';
import { IAccreditationSig } from 'app/entities/accreditation-sig/accreditation-sig.model';
import { AccreditationSigService } from 'app/entities/accreditation-sig/service/accreditation-sig.service';
import { IEventSig } from 'app/entities/event-sig/event-sig.model';
import { EventSigService } from 'app/entities/event-sig/service/event-sig.service';

import { InfoSuppSigUpdateComponent } from './info-supp-sig-update.component';

describe('InfoSuppSig Management Update Component', () => {
  let comp: InfoSuppSigUpdateComponent;
  let fixture: ComponentFixture<InfoSuppSigUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let infoSuppFormService: InfoSuppSigFormService;
  let infoSuppService: InfoSuppSigService;
  let infoSuppTypeService: InfoSuppTypeSigService;
  let accreditationService: AccreditationSigService;
  let eventService: EventSigService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [InfoSuppSigUpdateComponent],
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
      .overrideTemplate(InfoSuppSigUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(InfoSuppSigUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    infoSuppFormService = TestBed.inject(InfoSuppSigFormService);
    infoSuppService = TestBed.inject(InfoSuppSigService);
    infoSuppTypeService = TestBed.inject(InfoSuppTypeSigService);
    accreditationService = TestBed.inject(AccreditationSigService);
    eventService = TestBed.inject(EventSigService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call InfoSuppTypeSig query and add missing value', () => {
      const infoSupp: IInfoSuppSig = { infoSuppId: 456 };
      const infoSuppType: IInfoSuppTypeSig = { infoSuppTypeId: 42225 };
      infoSupp.infoSuppType = infoSuppType;

      const infoSuppTypeCollection: IInfoSuppTypeSig[] = [{ infoSuppTypeId: 70834 }];
      jest.spyOn(infoSuppTypeService, 'query').mockReturnValue(of(new HttpResponse({ body: infoSuppTypeCollection })));
      const additionalInfoSuppTypeSigs = [infoSuppType];
      const expectedCollection: IInfoSuppTypeSig[] = [...additionalInfoSuppTypeSigs, ...infoSuppTypeCollection];
      jest.spyOn(infoSuppTypeService, 'addInfoSuppTypeSigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ infoSupp });
      comp.ngOnInit();

      expect(infoSuppTypeService.query).toHaveBeenCalled();
      expect(infoSuppTypeService.addInfoSuppTypeSigToCollectionIfMissing).toHaveBeenCalledWith(
        infoSuppTypeCollection,
        ...additionalInfoSuppTypeSigs.map(expect.objectContaining)
      );
      expect(comp.infoSuppTypesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call AccreditationSig query and add missing value', () => {
      const infoSupp: IInfoSuppSig = { infoSuppId: 456 };
      const accreditation: IAccreditationSig = { accreditationId: 67884 };
      infoSupp.accreditation = accreditation;

      const accreditationCollection: IAccreditationSig[] = [{ accreditationId: 84307 }];
      jest.spyOn(accreditationService, 'query').mockReturnValue(of(new HttpResponse({ body: accreditationCollection })));
      const additionalAccreditationSigs = [accreditation];
      const expectedCollection: IAccreditationSig[] = [...additionalAccreditationSigs, ...accreditationCollection];
      jest.spyOn(accreditationService, 'addAccreditationSigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ infoSupp });
      comp.ngOnInit();

      expect(accreditationService.query).toHaveBeenCalled();
      expect(accreditationService.addAccreditationSigToCollectionIfMissing).toHaveBeenCalledWith(
        accreditationCollection,
        ...additionalAccreditationSigs.map(expect.objectContaining)
      );
      expect(comp.accreditationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call EventSig query and add missing value', () => {
      const infoSupp: IInfoSuppSig = { infoSuppId: 456 };
      const event: IEventSig = { eventId: 76731 };
      infoSupp.event = event;

      const eventCollection: IEventSig[] = [{ eventId: 46399 }];
      jest.spyOn(eventService, 'query').mockReturnValue(of(new HttpResponse({ body: eventCollection })));
      const additionalEventSigs = [event];
      const expectedCollection: IEventSig[] = [...additionalEventSigs, ...eventCollection];
      jest.spyOn(eventService, 'addEventSigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ infoSupp });
      comp.ngOnInit();

      expect(eventService.query).toHaveBeenCalled();
      expect(eventService.addEventSigToCollectionIfMissing).toHaveBeenCalledWith(
        eventCollection,
        ...additionalEventSigs.map(expect.objectContaining)
      );
      expect(comp.eventsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const infoSupp: IInfoSuppSig = { infoSuppId: 456 };
      const infoSuppType: IInfoSuppTypeSig = { infoSuppTypeId: 52933 };
      infoSupp.infoSuppType = infoSuppType;
      const accreditation: IAccreditationSig = { accreditationId: 6487 };
      infoSupp.accreditation = accreditation;
      const event: IEventSig = { eventId: 94345 };
      infoSupp.event = event;

      activatedRoute.data = of({ infoSupp });
      comp.ngOnInit();

      expect(comp.infoSuppTypesSharedCollection).toContain(infoSuppType);
      expect(comp.accreditationsSharedCollection).toContain(accreditation);
      expect(comp.eventsSharedCollection).toContain(event);
      expect(comp.infoSupp).toEqual(infoSupp);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInfoSuppSig>>();
      const infoSupp = { infoSuppId: 123 };
      jest.spyOn(infoSuppFormService, 'getInfoSuppSig').mockReturnValue(infoSupp);
      jest.spyOn(infoSuppService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ infoSupp });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: infoSupp }));
      saveSubject.complete();

      // THEN
      expect(infoSuppFormService.getInfoSuppSig).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(infoSuppService.update).toHaveBeenCalledWith(expect.objectContaining(infoSupp));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInfoSuppSig>>();
      const infoSupp = { infoSuppId: 123 };
      jest.spyOn(infoSuppFormService, 'getInfoSuppSig').mockReturnValue({ infoSuppId: null });
      jest.spyOn(infoSuppService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ infoSupp: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: infoSupp }));
      saveSubject.complete();

      // THEN
      expect(infoSuppFormService.getInfoSuppSig).toHaveBeenCalled();
      expect(infoSuppService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInfoSuppSig>>();
      const infoSupp = { infoSuppId: 123 };
      jest.spyOn(infoSuppService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ infoSupp });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(infoSuppService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareInfoSuppTypeSig', () => {
      it('Should forward to infoSuppTypeService', () => {
        const entity = { infoSuppTypeId: 123 };
        const entity2 = { infoSuppTypeId: 456 };
        jest.spyOn(infoSuppTypeService, 'compareInfoSuppTypeSig');
        comp.compareInfoSuppTypeSig(entity, entity2);
        expect(infoSuppTypeService.compareInfoSuppTypeSig).toHaveBeenCalledWith(entity, entity2);
      });
    });

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
