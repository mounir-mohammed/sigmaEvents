import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../city-sig.test-samples';

import { CitySigFormService } from './city-sig-form.service';

describe('CitySig Form Service', () => {
  let service: CitySigFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CitySigFormService);
  });

  describe('Service methods', () => {
    describe('createCitySigFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCitySigFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            cityId: expect.any(Object),
            cityName: expect.any(Object),
            cityZipCode: expect.any(Object),
            cityAbreviation: expect.any(Object),
            cityDescription: expect.any(Object),
            cityParams: expect.any(Object),
            cityAttributs: expect.any(Object),
            cityStat: expect.any(Object),
            country: expect.any(Object),
          })
        );
      });

      it('passing ICitySig should create a new form with FormGroup', () => {
        const formGroup = service.createCitySigFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            cityId: expect.any(Object),
            cityName: expect.any(Object),
            cityZipCode: expect.any(Object),
            cityAbreviation: expect.any(Object),
            cityDescription: expect.any(Object),
            cityParams: expect.any(Object),
            cityAttributs: expect.any(Object),
            cityStat: expect.any(Object),
            country: expect.any(Object),
          })
        );
      });
    });

    describe('getCitySig', () => {
      it('should return NewCitySig for default CitySig initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCitySigFormGroup(sampleWithNewData);

        const city = service.getCitySig(formGroup) as any;

        expect(city).toMatchObject(sampleWithNewData);
      });

      it('should return NewCitySig for empty CitySig initial value', () => {
        const formGroup = service.createCitySigFormGroup();

        const city = service.getCitySig(formGroup) as any;

        expect(city).toMatchObject({});
      });

      it('should return ICitySig', () => {
        const formGroup = service.createCitySigFormGroup(sampleWithRequiredData);

        const city = service.getCitySig(formGroup) as any;

        expect(city).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICitySig should not enable cityId FormControl', () => {
        const formGroup = service.createCitySigFormGroup();
        expect(formGroup.controls.cityId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.cityId.disabled).toBe(true);
      });

      it('passing NewCitySig should disable cityId FormControl', () => {
        const formGroup = service.createCitySigFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.cityId.disabled).toBe(true);

        service.resetForm(formGroup, { cityId: null });

        expect(formGroup.controls.cityId.disabled).toBe(true);
      });
    });
  });
});
