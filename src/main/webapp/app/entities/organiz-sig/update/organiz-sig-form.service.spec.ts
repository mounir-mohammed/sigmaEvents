import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../organiz-sig.test-samples';

import { OrganizSigFormService } from './organiz-sig-form.service';

describe('OrganizSig Form Service', () => {
  let service: OrganizSigFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OrganizSigFormService);
  });

  describe('Service methods', () => {
    describe('createOrganizSigFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createOrganizSigFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            organizId: expect.any(Object),
            organizName: expect.any(Object),
            organizDescription: expect.any(Object),
            organizLogo: expect.any(Object),
            organizTel: expect.any(Object),
            organizFax: expect.any(Object),
            organizEmail: expect.any(Object),
            organizAdresse: expect.any(Object),
            organizParams: expect.any(Object),
            organizAttributs: expect.any(Object),
            organizStat: expect.any(Object),
            country: expect.any(Object),
            city: expect.any(Object),
            event: expect.any(Object),
          })
        );
      });

      it('passing IOrganizSig should create a new form with FormGroup', () => {
        const formGroup = service.createOrganizSigFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            organizId: expect.any(Object),
            organizName: expect.any(Object),
            organizDescription: expect.any(Object),
            organizLogo: expect.any(Object),
            organizTel: expect.any(Object),
            organizFax: expect.any(Object),
            organizEmail: expect.any(Object),
            organizAdresse: expect.any(Object),
            organizParams: expect.any(Object),
            organizAttributs: expect.any(Object),
            organizStat: expect.any(Object),
            country: expect.any(Object),
            city: expect.any(Object),
            event: expect.any(Object),
          })
        );
      });
    });

    describe('getOrganizSig', () => {
      it('should return NewOrganizSig for default OrganizSig initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createOrganizSigFormGroup(sampleWithNewData);

        const organiz = service.getOrganizSig(formGroup) as any;

        expect(organiz).toMatchObject(sampleWithNewData);
      });

      it('should return NewOrganizSig for empty OrganizSig initial value', () => {
        const formGroup = service.createOrganizSigFormGroup();

        const organiz = service.getOrganizSig(formGroup) as any;

        expect(organiz).toMatchObject({});
      });

      it('should return IOrganizSig', () => {
        const formGroup = service.createOrganizSigFormGroup(sampleWithRequiredData);

        const organiz = service.getOrganizSig(formGroup) as any;

        expect(organiz).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IOrganizSig should not enable organizId FormControl', () => {
        const formGroup = service.createOrganizSigFormGroup();
        expect(formGroup.controls.organizId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.organizId.disabled).toBe(true);
      });

      it('passing NewOrganizSig should disable organizId FormControl', () => {
        const formGroup = service.createOrganizSigFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.organizId.disabled).toBe(true);

        service.resetForm(formGroup, { organizId: null });

        expect(formGroup.controls.organizId.disabled).toBe(true);
      });
    });
  });
});
