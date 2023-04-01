import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../event-field-sig.test-samples';

import { EventFieldSigFormService } from './event-field-sig-form.service';

describe('EventFieldSig Form Service', () => {
  let service: EventFieldSigFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EventFieldSigFormService);
  });

  describe('Service methods', () => {
    describe('createEventFieldSigFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEventFieldSigFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            fieldId: expect.any(Object),
            fieldName: expect.any(Object),
            fieldCategorie: expect.any(Object),
            fieldDescription: expect.any(Object),
            fieldType: expect.any(Object),
            fieldParams: expect.any(Object),
            fieldAttributs: expect.any(Object),
            fieldStat: expect.any(Object),
            event: expect.any(Object),
            eventForm: expect.any(Object),
          })
        );
      });

      it('passing IEventFieldSig should create a new form with FormGroup', () => {
        const formGroup = service.createEventFieldSigFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            fieldId: expect.any(Object),
            fieldName: expect.any(Object),
            fieldCategorie: expect.any(Object),
            fieldDescription: expect.any(Object),
            fieldType: expect.any(Object),
            fieldParams: expect.any(Object),
            fieldAttributs: expect.any(Object),
            fieldStat: expect.any(Object),
            event: expect.any(Object),
            eventForm: expect.any(Object),
          })
        );
      });
    });

    describe('getEventFieldSig', () => {
      it('should return NewEventFieldSig for default EventFieldSig initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createEventFieldSigFormGroup(sampleWithNewData);

        const eventField = service.getEventFieldSig(formGroup) as any;

        expect(eventField).toMatchObject(sampleWithNewData);
      });

      it('should return NewEventFieldSig for empty EventFieldSig initial value', () => {
        const formGroup = service.createEventFieldSigFormGroup();

        const eventField = service.getEventFieldSig(formGroup) as any;

        expect(eventField).toMatchObject({});
      });

      it('should return IEventFieldSig', () => {
        const formGroup = service.createEventFieldSigFormGroup(sampleWithRequiredData);

        const eventField = service.getEventFieldSig(formGroup) as any;

        expect(eventField).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEventFieldSig should not enable fieldId FormControl', () => {
        const formGroup = service.createEventFieldSigFormGroup();
        expect(formGroup.controls.fieldId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.fieldId.disabled).toBe(true);
      });

      it('passing NewEventFieldSig should disable fieldId FormControl', () => {
        const formGroup = service.createEventFieldSigFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.fieldId.disabled).toBe(true);

        service.resetForm(formGroup, { fieldId: null });

        expect(formGroup.controls.fieldId.disabled).toBe(true);
      });
    });
  });
});
