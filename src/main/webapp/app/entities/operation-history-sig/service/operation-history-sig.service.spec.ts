import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IOperationHistorySig } from '../operation-history-sig.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../operation-history-sig.test-samples';

import { OperationHistorySigService, RestOperationHistorySig } from './operation-history-sig.service';

const requireRestSample: RestOperationHistorySig = {
  ...sampleWithRequiredData,
  operationHistoryDate: sampleWithRequiredData.operationHistoryDate?.toJSON(),
};

describe('OperationHistorySig Service', () => {
  let service: OperationHistorySigService;
  let httpMock: HttpTestingController;
  let expectedResult: IOperationHistorySig | IOperationHistorySig[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(OperationHistorySigService);
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

    it('should create a OperationHistorySig', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const operationHistory = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(operationHistory).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a OperationHistorySig', () => {
      const operationHistory = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(operationHistory).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a OperationHistorySig', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of OperationHistorySig', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a OperationHistorySig', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addOperationHistorySigToCollectionIfMissing', () => {
      it('should add a OperationHistorySig to an empty array', () => {
        const operationHistory: IOperationHistorySig = sampleWithRequiredData;
        expectedResult = service.addOperationHistorySigToCollectionIfMissing([], operationHistory);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(operationHistory);
      });

      it('should not add a OperationHistorySig to an array that contains it', () => {
        const operationHistory: IOperationHistorySig = sampleWithRequiredData;
        const operationHistoryCollection: IOperationHistorySig[] = [
          {
            ...operationHistory,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addOperationHistorySigToCollectionIfMissing(operationHistoryCollection, operationHistory);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a OperationHistorySig to an array that doesn't contain it", () => {
        const operationHistory: IOperationHistorySig = sampleWithRequiredData;
        const operationHistoryCollection: IOperationHistorySig[] = [sampleWithPartialData];
        expectedResult = service.addOperationHistorySigToCollectionIfMissing(operationHistoryCollection, operationHistory);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(operationHistory);
      });

      it('should add only unique OperationHistorySig to an array', () => {
        const operationHistoryArray: IOperationHistorySig[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const operationHistoryCollection: IOperationHistorySig[] = [sampleWithRequiredData];
        expectedResult = service.addOperationHistorySigToCollectionIfMissing(operationHistoryCollection, ...operationHistoryArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const operationHistory: IOperationHistorySig = sampleWithRequiredData;
        const operationHistory2: IOperationHistorySig = sampleWithPartialData;
        expectedResult = service.addOperationHistorySigToCollectionIfMissing([], operationHistory, operationHistory2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(operationHistory);
        expect(expectedResult).toContain(operationHistory2);
      });

      it('should accept null and undefined values', () => {
        const operationHistory: IOperationHistorySig = sampleWithRequiredData;
        expectedResult = service.addOperationHistorySigToCollectionIfMissing([], null, operationHistory, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(operationHistory);
      });

      it('should return initial array if no OperationHistorySig is added', () => {
        const operationHistoryCollection: IOperationHistorySig[] = [sampleWithRequiredData];
        expectedResult = service.addOperationHistorySigToCollectionIfMissing(operationHistoryCollection, undefined, null);
        expect(expectedResult).toEqual(operationHistoryCollection);
      });
    });

    describe('compareOperationHistorySig', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareOperationHistorySig(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { operationHistoryId: 123 };
        const entity2 = null;

        const compareResult1 = service.compareOperationHistorySig(entity1, entity2);
        const compareResult2 = service.compareOperationHistorySig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { operationHistoryId: 123 };
        const entity2 = { operationHistoryId: 456 };

        const compareResult1 = service.compareOperationHistorySig(entity1, entity2);
        const compareResult2 = service.compareOperationHistorySig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { operationHistoryId: 123 };
        const entity2 = { operationHistoryId: 123 };

        const compareResult1 = service.compareOperationHistorySig(entity1, entity2);
        const compareResult2 = service.compareOperationHistorySig(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
