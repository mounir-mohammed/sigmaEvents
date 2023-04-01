import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPhotoArchiveSig } from '../photo-archive-sig.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../photo-archive-sig.test-samples';

import { PhotoArchiveSigService } from './photo-archive-sig.service';

const requireRestSample: IPhotoArchiveSig = {
  ...sampleWithRequiredData,
};

describe('PhotoArchiveSig Service', () => {
  let service: PhotoArchiveSigService;
  let httpMock: HttpTestingController;
  let expectedResult: IPhotoArchiveSig | IPhotoArchiveSig[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PhotoArchiveSigService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a PhotoArchiveSig', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const photoArchive = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(photoArchive).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PhotoArchiveSig', () => {
      const photoArchive = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(photoArchive).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PhotoArchiveSig', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PhotoArchiveSig', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PhotoArchiveSig', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPhotoArchiveSigToCollectionIfMissing', () => {
      it('should add a PhotoArchiveSig to an empty array', () => {
        const photoArchive: IPhotoArchiveSig = sampleWithRequiredData;
        expectedResult = service.addPhotoArchiveSigToCollectionIfMissing([], photoArchive);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(photoArchive);
      });

      it('should not add a PhotoArchiveSig to an array that contains it', () => {
        const photoArchive: IPhotoArchiveSig = sampleWithRequiredData;
        const photoArchiveCollection: IPhotoArchiveSig[] = [
          {
            ...photoArchive,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPhotoArchiveSigToCollectionIfMissing(photoArchiveCollection, photoArchive);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PhotoArchiveSig to an array that doesn't contain it", () => {
        const photoArchive: IPhotoArchiveSig = sampleWithRequiredData;
        const photoArchiveCollection: IPhotoArchiveSig[] = [sampleWithPartialData];
        expectedResult = service.addPhotoArchiveSigToCollectionIfMissing(photoArchiveCollection, photoArchive);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(photoArchive);
      });

      it('should add only unique PhotoArchiveSig to an array', () => {
        const photoArchiveArray: IPhotoArchiveSig[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const photoArchiveCollection: IPhotoArchiveSig[] = [sampleWithRequiredData];
        expectedResult = service.addPhotoArchiveSigToCollectionIfMissing(photoArchiveCollection, ...photoArchiveArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const photoArchive: IPhotoArchiveSig = sampleWithRequiredData;
        const photoArchive2: IPhotoArchiveSig = sampleWithPartialData;
        expectedResult = service.addPhotoArchiveSigToCollectionIfMissing([], photoArchive, photoArchive2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(photoArchive);
        expect(expectedResult).toContain(photoArchive2);
      });

      it('should accept null and undefined values', () => {
        const photoArchive: IPhotoArchiveSig = sampleWithRequiredData;
        expectedResult = service.addPhotoArchiveSigToCollectionIfMissing([], null, photoArchive, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(photoArchive);
      });

      it('should return initial array if no PhotoArchiveSig is added', () => {
        const photoArchiveCollection: IPhotoArchiveSig[] = [sampleWithRequiredData];
        expectedResult = service.addPhotoArchiveSigToCollectionIfMissing(photoArchiveCollection, undefined, null);
        expect(expectedResult).toEqual(photoArchiveCollection);
      });
    });

    describe('comparePhotoArchiveSig', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePhotoArchiveSig(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { photoArchiveId: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePhotoArchiveSig(entity1, entity2);
        const compareResult2 = service.comparePhotoArchiveSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { photoArchiveId: 123 };
        const entity2 = { photoArchiveId: 456 };

        const compareResult1 = service.comparePhotoArchiveSig(entity1, entity2);
        const compareResult2 = service.comparePhotoArchiveSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { photoArchiveId: 123 };
        const entity2 = { photoArchiveId: 123 };

        const compareResult1 = service.comparePhotoArchiveSig(entity1, entity2);
        const compareResult2 = service.comparePhotoArchiveSig(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
