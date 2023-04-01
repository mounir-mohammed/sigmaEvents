import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../event-sig.test-samples';

import { EventSigFormService } from './event-sig-form.service';

describe('EventSig Form Service', () => {
  let service: EventSigFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EventSigFormService);
  });

  describe('Service methods', () => {
    describe('createEventSigFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEventSigFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            eventId: expect.any(Object),
            eventName: expect.any(Object),
            eventColor: expect.any(Object),
            eventDescription: expect.any(Object),
            eventAbreviation: expect.any(Object),
            eventdateStart: expect.any(Object),
            eventdateEnd: expect.any(Object),
            eventPrintingModelId: expect.any(Object),
            eventLogo: expect.any(Object),
            eventBannerCenter: expect.any(Object),
            eventBannerRight: expect.any(Object),
            eventBannerLeft: expect.any(Object),
            eventBannerBas: expect.any(Object),
            eventWithPhoto: expect.any(Object),
            eventNoCode: expect.any(Object),
            eventCodeNoFilter: expect.any(Object),
            eventCodeByTypeAccreditation: expect.any(Object),
            eventCodeByTypeCategorie: expect.any(Object),
            eventCodeByTypeFonction: expect.any(Object),
            eventCodeByTypeOrganiz: expect.any(Object),
            eventDefaultPrintingLanguage: expect.any(Object),
            eventPCenterPrintingLanguage: expect.any(Object),
            eventImported: expect.any(Object),
            eventArchived: expect.any(Object),
            eventArchiveFileName: expect.any(Object),
            eventURL: expect.any(Object),
            eventDomaine: expect.any(Object),
            eventSousDomaine: expect.any(Object),
            eventCloned: expect.any(Object),
            eventParams: expect.any(Object),
            eventAttributs: expect.any(Object),
            eventStat: expect.any(Object),
            language: expect.any(Object),
          })
        );
      });

      it('passing IEventSig should create a new form with FormGroup', () => {
        const formGroup = service.createEventSigFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            eventId: expect.any(Object),
            eventName: expect.any(Object),
            eventColor: expect.any(Object),
            eventDescription: expect.any(Object),
            eventAbreviation: expect.any(Object),
            eventdateStart: expect.any(Object),
            eventdateEnd: expect.any(Object),
            eventPrintingModelId: expect.any(Object),
            eventLogo: expect.any(Object),
            eventBannerCenter: expect.any(Object),
            eventBannerRight: expect.any(Object),
            eventBannerLeft: expect.any(Object),
            eventBannerBas: expect.any(Object),
            eventWithPhoto: expect.any(Object),
            eventNoCode: expect.any(Object),
            eventCodeNoFilter: expect.any(Object),
            eventCodeByTypeAccreditation: expect.any(Object),
            eventCodeByTypeCategorie: expect.any(Object),
            eventCodeByTypeFonction: expect.any(Object),
            eventCodeByTypeOrganiz: expect.any(Object),
            eventDefaultPrintingLanguage: expect.any(Object),
            eventPCenterPrintingLanguage: expect.any(Object),
            eventImported: expect.any(Object),
            eventArchived: expect.any(Object),
            eventArchiveFileName: expect.any(Object),
            eventURL: expect.any(Object),
            eventDomaine: expect.any(Object),
            eventSousDomaine: expect.any(Object),
            eventCloned: expect.any(Object),
            eventParams: expect.any(Object),
            eventAttributs: expect.any(Object),
            eventStat: expect.any(Object),
            language: expect.any(Object),
          })
        );
      });
    });

    describe('getEventSig', () => {
      it('should return NewEventSig for default EventSig initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createEventSigFormGroup(sampleWithNewData);

        const event = service.getEventSig(formGroup) as any;

        expect(event).toMatchObject(sampleWithNewData);
      });

      it('should return NewEventSig for empty EventSig initial value', () => {
        const formGroup = service.createEventSigFormGroup();

        const event = service.getEventSig(formGroup) as any;

        expect(event).toMatchObject({});
      });

      it('should return IEventSig', () => {
        const formGroup = service.createEventSigFormGroup(sampleWithRequiredData);

        const event = service.getEventSig(formGroup) as any;

        expect(event).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEventSig should not enable eventId FormControl', () => {
        const formGroup = service.createEventSigFormGroup();
        expect(formGroup.controls.eventId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.eventId.disabled).toBe(true);
      });

      it('passing NewEventSig should disable eventId FormControl', () => {
        const formGroup = service.createEventSigFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.eventId.disabled).toBe(true);

        service.resetForm(formGroup, { eventId: null });

        expect(formGroup.controls.eventId.disabled).toBe(true);
      });
    });
  });
});
