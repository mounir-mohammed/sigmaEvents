import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../setting-sig.test-samples';

import { SettingSigFormService } from './setting-sig-form.service';

describe('SettingSig Form Service', () => {
  let service: SettingSigFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SettingSigFormService);
  });

  describe('Service methods', () => {
    describe('createSettingSigFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSettingSigFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            settingId: expect.any(Object),
            settingParentId: expect.any(Object),
            settingType: expect.any(Object),
            settingNameClass: expect.any(Object),
            settingDataType: expect.any(Object),
            settingDescription: expect.any(Object),
            settingValueString: expect.any(Object),
            settingValueLong: expect.any(Object),
            settingValueDate: expect.any(Object),
            settingValueBoolean: expect.any(Object),
            settingValueBlob: expect.any(Object),
            settingParams: expect.any(Object),
            settingAttributs: expect.any(Object),
            settingStat: expect.any(Object),
            language: expect.any(Object),
            event: expect.any(Object),
          })
        );
      });

      it('passing ISettingSig should create a new form with FormGroup', () => {
        const formGroup = service.createSettingSigFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            settingId: expect.any(Object),
            settingParentId: expect.any(Object),
            settingType: expect.any(Object),
            settingNameClass: expect.any(Object),
            settingDataType: expect.any(Object),
            settingDescription: expect.any(Object),
            settingValueString: expect.any(Object),
            settingValueLong: expect.any(Object),
            settingValueDate: expect.any(Object),
            settingValueBoolean: expect.any(Object),
            settingValueBlob: expect.any(Object),
            settingParams: expect.any(Object),
            settingAttributs: expect.any(Object),
            settingStat: expect.any(Object),
            language: expect.any(Object),
            event: expect.any(Object),
          })
        );
      });
    });

    describe('getSettingSig', () => {
      it('should return NewSettingSig for default SettingSig initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createSettingSigFormGroup(sampleWithNewData);

        const setting = service.getSettingSig(formGroup) as any;

        expect(setting).toMatchObject(sampleWithNewData);
      });

      it('should return NewSettingSig for empty SettingSig initial value', () => {
        const formGroup = service.createSettingSigFormGroup();

        const setting = service.getSettingSig(formGroup) as any;

        expect(setting).toMatchObject({});
      });

      it('should return ISettingSig', () => {
        const formGroup = service.createSettingSigFormGroup(sampleWithRequiredData);

        const setting = service.getSettingSig(formGroup) as any;

        expect(setting).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISettingSig should not enable settingId FormControl', () => {
        const formGroup = service.createSettingSigFormGroup();
        expect(formGroup.controls.settingId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.settingId.disabled).toBe(true);
      });

      it('passing NewSettingSig should disable settingId FormControl', () => {
        const formGroup = service.createSettingSigFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.settingId.disabled).toBe(true);

        service.resetForm(formGroup, { settingId: null });

        expect(formGroup.controls.settingId.disabled).toBe(true);
      });
    });
  });
});
