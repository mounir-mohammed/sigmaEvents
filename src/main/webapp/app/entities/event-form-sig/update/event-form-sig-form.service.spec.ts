import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../event-form-sig.test-samples';

import { EventFormSigFormService } from './event-form-sig-form.service';

describe('EventFormSig Form Service', () => {
  let service: EventFormSigFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EventFormSigFormService);
  });

  describe('Service methods', () => {
    describe('createEventFormSigFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEventFormSigFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            formId: expect.any(Object),
            formName: expect.any(Object),
            formDescription: expect.any(Object),
            formParams: expect.any(Object),
            formAttributs: expect.any(Object),
            formStat: expect.any(Object),
            event: expect.any(Object),
          })
        );
      });

      it('passing IEventFormSig should create a new form with FormGroup', () => {
        const formGroup = service.createEventFormSigFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            formId: expect.any(Object),
            formName: expect.any(Object),
            formDescription: expect.any(Object),
            formParams: expect.any(Object),
            formAttributs: expect.any(Object),
            formStat: expect.any(Object),
            event: expect.any(Object),
          })
        );
      });
    });

    describe('getEventFormSig', () => {
      it('should return NewEventFormSig for default EventFormSig initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createEventFormSigFormGroup(sampleWithNewData);

        const eventForm = service.getEventFormSig(formGroup) as any;

        expect(eventForm).toMatchObject(sampleWithNewData);
      });

      it('should return NewEventFormSig for empty EventFormSig initial value', () => {
        const formGroup = service.createEventFormSigFormGroup();

        const eventForm = service.getEventFormSig(formGroup) as any;

        expect(eventForm).toMatchObject({});
      });

      it('should return IEventFormSig', () => {
        const formGroup = service.createEventFormSigFormGroup(sampleWithRequiredData);

        const eventForm = service.getEventFormSig(formGroup) as any;

        expect(eventForm).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEventFormSig should not enable formId FormControl', () => {
        const formGroup = service.createEventFormSigFormGroup();
        expect(formGroup.controls.formId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.formId.disabled).toBe(true);
      });

      it('passing NewEventFormSig should disable formId FormControl', () => {
        const formGroup = service.createEventFormSigFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.formId.disabled).toBe(true);

        service.resetForm(formGroup, { formId: null });

        expect(formGroup.controls.formId.disabled).toBe(true);
      });
    });
  });
});
