import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICheckAccreditationHistorySig } from '../check-accreditation-history-sig.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../check-accreditation-history-sig.test-samples';

import { CheckAccreditationHistorySigService, RestCheckAccreditationHistorySig } from './check-accreditation-history-sig.service';

const requireRestSample: RestCheckAccreditationHistorySig = {
  ...sampleWithRequiredData,
  checkAccreditationHistoryDate: sampleWithRequiredData.checkAccreditationHistoryDate?.toJSON(),
};

describe('CheckAccreditationHistorySig Service', () => {
  let service: CheckAccreditationHistorySigService;
  let httpMock: HttpTestingController;
  let expectedResult: ICheckAccreditationHistorySig | ICheckAccreditationHistorySig[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CheckAccreditationHistorySigService);
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

    it('should create a CheckAccreditationHistorySig', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const checkAccreditationHistory = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(checkAccreditationHistory).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CheckAccreditationHistorySig', () => {
      const checkAccreditationHistory = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(checkAccreditationHistory).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CheckAccreditationHistorySig', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CheckAccreditationHistorySig', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CheckAccreditationHistorySig', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCheckAccreditationHistorySigToCollectionIfMissing', () => {
      it('should add a CheckAccreditationHistorySig to an empty array', () => {
        const checkAccreditationHistory: ICheckAccreditationHistorySig = sampleWithRequiredData;
        expectedResult = service.addCheckAccreditationHistorySigToCollectionIfMissing([], checkAccreditationHistory);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(checkAccreditationHistory);
      });

      it('should not add a CheckAccreditationHistorySig to an array that contains it', () => {
        const checkAccreditationHistory: ICheckAccreditationHistorySig = sampleWithRequiredData;
        const checkAccreditationHistoryCollection: ICheckAccreditationHistorySig[] = [
          {
            ...checkAccreditationHistory,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCheckAccreditationHistorySigToCollectionIfMissing(
          checkAccreditationHistoryCollection,
          checkAccreditationHistory
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CheckAccreditationHistorySig to an array that doesn't contain it", () => {
        const checkAccreditationHistory: ICheckAccreditationHistorySig = sampleWithRequiredData;
        const checkAccreditationHistoryCollection: ICheckAccreditationHistorySig[] = [sampleWithPartialData];
        expectedResult = service.addCheckAccreditationHistorySigToCollectionIfMissing(
          checkAccreditationHistoryCollection,
          checkAccreditationHistory
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(checkAccreditationHistory);
      });

      it('should add only unique CheckAccreditationHistorySig to an array', () => {
        const checkAccreditationHistoryArray: ICheckAccreditationHistorySig[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const checkAccreditationHistoryCollection: ICheckAccreditationHistorySig[] = [sampleWithRequiredData];
        expectedResult = service.addCheckAccreditationHistorySigToCollectionIfMissing(
          checkAccreditationHistoryCollection,
          ...checkAccreditationHistoryArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const checkAccreditationHistory: ICheckAccreditationHistorySig = sampleWithRequiredData;
        const checkAccreditationHistory2: ICheckAccreditationHistorySig = sampleWithPartialData;
        expectedResult = service.addCheckAccreditationHistorySigToCollectionIfMissing(
          [],
          checkAccreditationHistory,
          checkAccreditationHistory2
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(checkAccreditationHistory);
        expect(expectedResult).toContain(checkAccreditationHistory2);
      });

      it('should accept null and undefined values', () => {
        const checkAccreditationHistory: ICheckAccreditationHistorySig = sampleWithRequiredData;
        expectedResult = service.addCheckAccreditationHistorySigToCollectionIfMissing([], null, checkAccreditationHistory, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(checkAccreditationHistory);
      });

      it('should return initial array if no CheckAccreditationHistorySig is added', () => {
        const checkAccreditationHistoryCollection: ICheckAccreditationHistorySig[] = [sampleWithRequiredData];
        expectedResult = service.addCheckAccreditationHistorySigToCollectionIfMissing(checkAccreditationHistoryCollection, undefined, null);
        expect(expectedResult).toEqual(checkAccreditationHistoryCollection);
      });
    });

    describe('compareCheckAccreditationHistorySig', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCheckAccreditationHistorySig(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { checkAccreditationHistoryId: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCheckAccreditationHistorySig(entity1, entity2);
        const compareResult2 = service.compareCheckAccreditationHistorySig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { checkAccreditationHistoryId: 123 };
        const entity2 = { checkAccreditationHistoryId: 456 };

        const compareResult1 = service.compareCheckAccreditationHistorySig(entity1, entity2);
        const compareResult2 = service.compareCheckAccreditationHistorySig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { checkAccreditationHistoryId: 123 };
        const entity2 = { checkAccreditationHistoryId: 123 };

        const compareResult1 = service.compareCheckAccreditationHistorySig(entity1, entity2);
        const compareResult2 = service.compareCheckAccreditationHistorySig(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
