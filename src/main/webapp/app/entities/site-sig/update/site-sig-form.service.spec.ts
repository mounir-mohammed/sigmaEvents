import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../site-sig.test-samples';

import { SiteSigFormService } from './site-sig-form.service';

describe('SiteSig Form Service', () => {
  let service: SiteSigFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SiteSigFormService);
  });

  describe('Service methods', () => {
    describe('createSiteSigFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSiteSigFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            siteId: expect.any(Object),
            siteName: expect.any(Object),
            siteColor: expect.any(Object),
            siteAbreviation: expect.any(Object),
            siteDescription: expect.any(Object),
            siteLogo: expect.any(Object),
            siteAdresse: expect.any(Object),
            siteEmail: expect.any(Object),
            siteTel: expect.any(Object),
            siteFax: expect.any(Object),
            siteResponsableName: expect.any(Object),
            siteParams: expect.any(Object),
            siteAttributs: expect.any(Object),
            siteStat: expect.any(Object),
            city: expect.any(Object),
            event: expect.any(Object),
            accreditations: expect.any(Object),
          })
        );
      });

      it('passing ISiteSig should create a new form with FormGroup', () => {
        const formGroup = service.createSiteSigFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            siteId: expect.any(Object),
            siteName: expect.any(Object),
            siteColor: expect.any(Object),
            siteAbreviation: expect.any(Object),
            siteDescription: expect.any(Object),
            siteLogo: expect.any(Object),
            siteAdresse: expect.any(Object),
            siteEmail: expect.any(Object),
            siteTel: expect.any(Object),
            siteFax: expect.any(Object),
            siteResponsableName: expect.any(Object),
            siteParams: expect.any(Object),
            siteAttributs: expect.any(Object),
            siteStat: expect.any(Object),
            city: expect.any(Object),
            event: expect.any(Object),
            accreditations: expect.any(Object),
          })
        );
      });
    });

    describe('getSiteSig', () => {
      it('should return NewSiteSig for default SiteSig initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createSiteSigFormGroup(sampleWithNewData);

        const site = service.getSiteSig(formGroup) as any;

        expect(site).toMatchObject(sampleWithNewData);
      });

      it('should return NewSiteSig for empty SiteSig initial value', () => {
        const formGroup = service.createSiteSigFormGroup();

        const site = service.getSiteSig(formGroup) as any;

        expect(site).toMatchObject({});
      });

      it('should return ISiteSig', () => {
        const formGroup = service.createSiteSigFormGroup(sampleWithRequiredData);

        const site = service.getSiteSig(formGroup) as any;

        expect(site).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISiteSig should not enable siteId FormControl', () => {
        const formGroup = service.createSiteSigFormGroup();
        expect(formGroup.controls.siteId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.siteId.disabled).toBe(true);
      });

      it('passing NewSiteSig should disable siteId FormControl', () => {
        const formGroup = service.createSiteSigFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.siteId.disabled).toBe(true);

        service.resetForm(formGroup, { siteId: null });

        expect(formGroup.controls.siteId.disabled).toBe(true);
      });
    });
  });
});
