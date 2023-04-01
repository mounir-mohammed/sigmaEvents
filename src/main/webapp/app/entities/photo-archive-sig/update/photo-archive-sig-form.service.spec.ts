import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../photo-archive-sig.test-samples';

import { PhotoArchiveSigFormService } from './photo-archive-sig-form.service';

describe('PhotoArchiveSig Form Service', () => {
  let service: PhotoArchiveSigFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PhotoArchiveSigFormService);
  });

  describe('Service methods', () => {
    describe('createPhotoArchiveSigFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPhotoArchiveSigFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            photoArchiveId: expect.any(Object),
            photoArchiveName: expect.any(Object),
            photoArchivePath: expect.any(Object),
            photoArchivePhoto: expect.any(Object),
            photoArchiveDescription: expect.any(Object),
            photoArchiveParams: expect.any(Object),
            photoArchiveAttributs: expect.any(Object),
            photoArchiveStat: expect.any(Object),
            accreditation: expect.any(Object),
            event: expect.any(Object),
          })
        );
      });

      it('passing IPhotoArchiveSig should create a new form with FormGroup', () => {
        const formGroup = service.createPhotoArchiveSigFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            photoArchiveId: expect.any(Object),
            photoArchiveName: expect.any(Object),
            photoArchivePath: expect.any(Object),
            photoArchivePhoto: expect.any(Object),
            photoArchiveDescription: expect.any(Object),
            photoArchiveParams: expect.any(Object),
            photoArchiveAttributs: expect.any(Object),
            photoArchiveStat: expect.any(Object),
            accreditation: expect.any(Object),
            event: expect.any(Object),
          })
        );
      });
    });

    describe('getPhotoArchiveSig', () => {
      it('should return NewPhotoArchiveSig for default PhotoArchiveSig initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPhotoArchiveSigFormGroup(sampleWithNewData);

        const photoArchive = service.getPhotoArchiveSig(formGroup) as any;

        expect(photoArchive).toMatchObject(sampleWithNewData);
      });

      it('should return NewPhotoArchiveSig for empty PhotoArchiveSig initial value', () => {
        const formGroup = service.createPhotoArchiveSigFormGroup();

        const photoArchive = service.getPhotoArchiveSig(formGroup) as any;

        expect(photoArchive).toMatchObject({});
      });

      it('should return IPhotoArchiveSig', () => {
        const formGroup = service.createPhotoArchiveSigFormGroup(sampleWithRequiredData);

        const photoArchive = service.getPhotoArchiveSig(formGroup) as any;

        expect(photoArchive).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPhotoArchiveSig should not enable photoArchiveId FormControl', () => {
        const formGroup = service.createPhotoArchiveSigFormGroup();
        expect(formGroup.controls.photoArchiveId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.photoArchiveId.disabled).toBe(true);
      });

      it('passing NewPhotoArchiveSig should disable photoArchiveId FormControl', () => {
        const formGroup = service.createPhotoArchiveSigFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.photoArchiveId.disabled).toBe(true);

        service.resetForm(formGroup, { photoArchiveId: null });

        expect(formGroup.controls.photoArchiveId.disabled).toBe(true);
      });
    });
  });
});
