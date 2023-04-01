import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../event-control-sig.test-samples';

import { EventControlSigFormService } from './event-control-sig-form.service';

describe('EventControlSig Form Service', () => {
  let service: EventControlSigFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EventControlSigFormService);
  });

  describe('Service methods', () => {
    describe('createEventControlSigFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEventControlSigFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            controlId: expect.any(Object),
            controlName: expect.any(Object),
            controlDescription: expect.any(Object),
            controlType: expect.any(Object),
            controlValueString: expect.any(Object),
            controlValueLong: expect.any(Object),
            controlValueDate: expect.any(Object),
            controlParams: expect.any(Object),
            controlAttributs: expect.any(Object),
            controlValueStat: expect.any(Object),
            event: expect.any(Object),
            eventField: expect.any(Object),
          })
        );
      });

      it('passing IEventControlSig should create a new form with FormGroup', () => {
        const formGroup = service.createEventControlSigFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            controlId: expect.any(Object),
            controlName: expect.any(Object),
            controlDescription: expect.any(Object),
            controlType: expect.any(Object),
            controlValueString: expect.any(Object),
            controlValueLong: expect.any(Object),
            controlValueDate: expect.any(Object),
            controlParams: expect.any(Object),
            controlAttributs: expect.any(Object),
            controlValueStat: expect.any(Object),
            event: expect.any(Object),
            eventField: expect.any(Object),
          })
        );
      });
    });

    describe('getEventControlSig', () => {
      it('should return NewEventControlSig for default EventControlSig initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createEventControlSigFormGroup(sampleWithNewData);

        const eventControl = service.getEventControlSig(formGroup) as any;

        expect(eventControl).toMatchObject(sampleWithNewData);
      });

      it('should return NewEventControlSig for empty EventControlSig initial value', () => {
        const formGroup = service.createEventControlSigFormGroup();

        const eventControl = service.getEventControlSig(formGroup) as any;

        expect(eventControl).toMatchObject({});
      });

      it('should return IEventControlSig', () => {
        const formGroup = service.createEventControlSigFormGroup(sampleWithRequiredData);

        const eventControl = service.getEventControlSig(formGroup) as any;

        expect(eventControl).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEventControlSig should not enable controlId FormControl', () => {
        const formGroup = service.createEventControlSigFormGroup();
        expect(formGroup.controls.controlId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.controlId.disabled).toBe(true);
      });

      it('passing NewEventControlSig should disable controlId FormControl', () => {
        const formGroup = service.createEventControlSigFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.controlId.disabled).toBe(true);

        service.resetForm(formGroup, { controlId: null });

        expect(formGroup.controls.controlId.disabled).toBe(true);
      });
    });
  });
});
