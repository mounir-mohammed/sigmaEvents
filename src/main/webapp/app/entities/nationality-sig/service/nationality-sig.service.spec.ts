import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { INationalitySig } from '../nationality-sig.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../nationality-sig.test-samples';

import { NationalitySigService } from './nationality-sig.service';

const requireRestSample: INationalitySig = {
  ...sampleWithRequiredData,
};

describe('NationalitySig Service', () => {
  let service: NationalitySigService;
  let httpMock: HttpTestingController;
  let expectedResult: INationalitySig | INationalitySig[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(NationalitySigService);
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

    it('should create a NationalitySig', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const nationality = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(nationality).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a NationalitySig', () => {
      const nationality = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(nationality).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a NationalitySig', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of NationalitySig', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a NationalitySig', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addNationalitySigToCollectionIfMissing', () => {
      it('should add a NationalitySig to an empty array', () => {
        const nationality: INationalitySig = sampleWithRequiredData;
        expectedResult = service.addNationalitySigToCollectionIfMissing([], nationality);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(nationality);
      });

      it('should not add a NationalitySig to an array that contains it', () => {
        const nationality: INationalitySig = sampleWithRequiredData;
        const nationalityCollection: INationalitySig[] = [
          {
            ...nationality,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addNationalitySigToCollectionIfMissing(nationalityCollection, nationality);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a NationalitySig to an array that doesn't contain it", () => {
        const nationality: INationalitySig = sampleWithRequiredData;
        const nationalityCollection: INationalitySig[] = [sampleWithPartialData];
        expectedResult = service.addNationalitySigToCollectionIfMissing(nationalityCollection, nationality);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(nationality);
      });

      it('should add only unique NationalitySig to an array', () => {
        const nationalityArray: INationalitySig[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const nationalityCollection: INationalitySig[] = [sampleWithRequiredData];
        expectedResult = service.addNationalitySigToCollectionIfMissing(nationalityCollection, ...nationalityArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const nationality: INationalitySig = sampleWithRequiredData;
        const nationality2: INationalitySig = sampleWithPartialData;
        expectedResult = service.addNationalitySigToCollectionIfMissing([], nationality, nationality2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(nationality);
        expect(expectedResult).toContain(nationality2);
      });

      it('should accept null and undefined values', () => {
        const nationality: INationalitySig = sampleWithRequiredData;
        expectedResult = service.addNationalitySigToCollectionIfMissing([], null, nationality, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(nationality);
      });

      it('should return initial array if no NationalitySig is added', () => {
        const nationalityCollection: INationalitySig[] = [sampleWithRequiredData];
        expectedResult = service.addNationalitySigToCollectionIfMissing(nationalityCollection, undefined, null);
        expect(expectedResult).toEqual(nationalityCollection);
      });
    });

    describe('compareNationalitySig', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareNationalitySig(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { nationalityId: 123 };
        const entity2 = null;

        const compareResult1 = service.compareNationalitySig(entity1, entity2);
        const compareResult2 = service.compareNationalitySig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { nationalityId: 123 };
        const entity2 = { nationalityId: 456 };

        const compareResult1 = service.compareNationalitySig(entity1, entity2);
        const compareResult2 = service.compareNationalitySig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { nationalityId: 123 };
        const entity2 = { nationalityId: 123 };

        const compareResult1 = service.compareNationalitySig(entity1, entity2);
        const compareResult2 = service.compareNationalitySig(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
