import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../accreditation-sig.test-samples';

import { AccreditationSigFormService } from './accreditation-sig-form.service';

describe('AccreditationSig Form Service', () => {
  let service: AccreditationSigFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AccreditationSigFormService);
  });

  describe('Service methods', () => {
    describe('createAccreditationSigFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAccreditationSigFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            accreditationId: expect.any(Object),
            accreditationFirstName: expect.any(Object),
            accreditationSecondName: expect.any(Object),
            accreditationLastName: expect.any(Object),
            accreditationBirthDay: expect.any(Object),
            accreditationSexe: expect.any(Object),
            accreditationOccupation: expect.any(Object),
            accreditationDescription: expect.any(Object),
            accreditationEmail: expect.any(Object),
            accreditationTel: expect.any(Object),
            accreditationPhoto: expect.any(Object),
            accreditationCinId: expect.any(Object),
            accreditationPasseportId: expect.any(Object),
            accreditationCartePresseId: expect.any(Object),
            accreditationCarteProfessionnelleId: expect.any(Object),
            accreditationCreationDate: expect.any(Object),
            accreditationUpdateDate: expect.any(Object),
            accreditationCreatedByuser: expect.any(Object),
            accreditationDateStart: expect.any(Object),
            accreditationDateEnd: expect.any(Object),
            accreditationPrintStat: expect.any(Object),
            accreditationPrintNumber: expect.any(Object),
            accreditationParams: expect.any(Object),
            accreditationAttributs: expect.any(Object),
            accreditationStat: expect.any(Object),
            sites: expect.any(Object),
            event: expect.any(Object),
            civility: expect.any(Object),
            sexe: expect.any(Object),
            nationality: expect.any(Object),
            country: expect.any(Object),
            city: expect.any(Object),
            category: expect.any(Object),
            fonction: expect.any(Object),
            organiz: expect.any(Object),
            accreditationType: expect.any(Object),
            status: expect.any(Object),
            attachement: expect.any(Object),
            code: expect.any(Object),
            dayPassInfo: expect.any(Object),
          })
        );
      });

      it('passing IAccreditationSig should create a new form with FormGroup', () => {
        const formGroup = service.createAccreditationSigFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            accreditationId: expect.any(Object),
            accreditationFirstName: expect.any(Object),
            accreditationSecondName: expect.any(Object),
            accreditationLastName: expect.any(Object),
            accreditationBirthDay: expect.any(Object),
            accreditationSexe: expect.any(Object),
            accreditationOccupation: expect.any(Object),
            accreditationDescription: expect.any(Object),
            accreditationEmail: expect.any(Object),
            accreditationTel: expect.any(Object),
            accreditationPhoto: expect.any(Object),
            accreditationCinId: expect.any(Object),
            accreditationPasseportId: expect.any(Object),
            accreditationCartePresseId: expect.any(Object),
            accreditationCarteProfessionnelleId: expect.any(Object),
            accreditationCreationDate: expect.any(Object),
            accreditationUpdateDate: expect.any(Object),
            accreditationCreatedByuser: expect.any(Object),
            accreditationDateStart: expect.any(Object),
            accreditationDateEnd: expect.any(Object),
            accreditationPrintStat: expect.any(Object),
            accreditationPrintNumber: expect.any(Object),
            accreditationParams: expect.any(Object),
            accreditationAttributs: expect.any(Object),
            accreditationStat: expect.any(Object),
            sites: expect.any(Object),
            event: expect.any(Object),
            civility: expect.any(Object),
            sexe: expect.any(Object),
            nationality: expect.any(Object),
            country: expect.any(Object),
            city: expect.any(Object),
            category: expect.any(Object),
            fonction: expect.any(Object),
            organiz: expect.any(Object),
            accreditationType: expect.any(Object),
            status: expect.any(Object),
            attachement: expect.any(Object),
            code: expect.any(Object),
            dayPassInfo: expect.any(Object),
          })
        );
      });
    });

    describe('getAccreditationSig', () => {
      it('should return NewAccreditationSig for default AccreditationSig initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createAccreditationSigFormGroup(sampleWithNewData);

        const accreditation = service.getAccreditationSig(formGroup) as any;

        expect(accreditation).toMatchObject(sampleWithNewData);
      });

      it('should return NewAccreditationSig for empty AccreditationSig initial value', () => {
        const formGroup = service.createAccreditationSigFormGroup();

        const accreditation = service.getAccreditationSig(formGroup) as any;

        expect(accreditation).toMatchObject({});
      });

      it('should return IAccreditationSig', () => {
        const formGroup = service.createAccreditationSigFormGroup(sampleWithRequiredData);

        const accreditation = service.getAccreditationSig(formGroup) as any;

        expect(accreditation).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAccreditationSig should not enable accreditationId FormControl', () => {
        const formGroup = service.createAccreditationSigFormGroup();
        expect(formGroup.controls.accreditationId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.accreditationId.disabled).toBe(true);
      });

      it('passing NewAccreditationSig should disable accreditationId FormControl', () => {
        const formGroup = service.createAccreditationSigFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.accreditationId.disabled).toBe(true);

        service.resetForm(formGroup, { accreditationId: null });

        expect(formGroup.controls.accreditationId.disabled).toBe(true);
      });
    });
  });
});
