import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../country-sig.test-samples';

import { CountrySigFormService } from './country-sig-form.service';

describe('CountrySig Form Service', () => {
  let service: CountrySigFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CountrySigFormService);
  });

  describe('Service methods', () => {
    describe('createCountrySigFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCountrySigFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            countryId: expect.any(Object),
            countryName: expect.any(Object),
            countryCodeAlpha2: expect.any(Object),
            countryCodeAlpha3: expect.any(Object),
            countryTelCode: expect.any(Object),
            countryDescription: expect.any(Object),
            countryFlag: expect.any(Object),
            countryParams: expect.any(Object),
            countryAttributs: expect.any(Object),
            countryStat: expect.any(Object),
          })
        );
      });

      it('passing ICountrySig should create a new form with FormGroup', () => {
        const formGroup = service.createCountrySigFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            countryId: expect.any(Object),
            countryName: expect.any(Object),
            countryCodeAlpha2: expect.any(Object),
            countryCodeAlpha3: expect.any(Object),
            countryTelCode: expect.any(Object),
            countryDescription: expect.any(Object),
            countryFlag: expect.any(Object),
            countryParams: expect.any(Object),
            countryAttributs: expect.any(Object),
            countryStat: expect.any(Object),
          })
        );
      });
    });

    describe('getCountrySig', () => {
      it('should return NewCountrySig for default CountrySig initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCountrySigFormGroup(sampleWithNewData);

        const country = service.getCountrySig(formGroup) as any;

        expect(country).toMatchObject(sampleWithNewData);
      });

      it('should return NewCountrySig for empty CountrySig initial value', () => {
        const formGroup = service.createCountrySigFormGroup();

        const country = service.getCountrySig(formGroup) as any;

        expect(country).toMatchObject({});
      });

      it('should return ICountrySig', () => {
        const formGroup = service.createCountrySigFormGroup(sampleWithRequiredData);

        const country = service.getCountrySig(formGroup) as any;

        expect(country).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICountrySig should not enable countryId FormControl', () => {
        const formGroup = service.createCountrySigFormGroup();
        expect(formGroup.controls.countryId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.countryId.disabled).toBe(true);
      });

      it('passing NewCountrySig should disable countryId FormControl', () => {
        const formGroup = service.createCountrySigFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.countryId.disabled).toBe(true);

        service.resetForm(formGroup, { countryId: null });

        expect(formGroup.controls.countryId.disabled).toBe(true);
      });
    });
  });
});
