import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ILogHistorySig } from '../log-history-sig.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../log-history-sig.test-samples';

import { LogHistorySigService } from './log-history-sig.service';

const requireRestSample: ILogHistorySig = {
  ...sampleWithRequiredData,
};

describe('LogHistorySig Service', () => {
  let service: LogHistorySigService;
  let httpMock: HttpTestingController;
  let expectedResult: ILogHistorySig | ILogHistorySig[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(LogHistorySigService);
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

    it('should create a LogHistorySig', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const logHistory = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(logHistory).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a LogHistorySig', () => {
      const logHistory = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(logHistory).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a LogHistorySig', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of LogHistorySig', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a LogHistorySig', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addLogHistorySigToCollectionIfMissing', () => {
      it('should add a LogHistorySig to an empty array', () => {
        const logHistory: ILogHistorySig = sampleWithRequiredData;
        expectedResult = service.addLogHistorySigToCollectionIfMissing([], logHistory);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(logHistory);
      });

      it('should not add a LogHistorySig to an array that contains it', () => {
        const logHistory: ILogHistorySig = sampleWithRequiredData;
        const logHistoryCollection: ILogHistorySig[] = [
          {
            ...logHistory,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addLogHistorySigToCollectionIfMissing(logHistoryCollection, logHistory);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a LogHistorySig to an array that doesn't contain it", () => {
        const logHistory: ILogHistorySig = sampleWithRequiredData;
        const logHistoryCollection: ILogHistorySig[] = [sampleWithPartialData];
        expectedResult = service.addLogHistorySigToCollectionIfMissing(logHistoryCollection, logHistory);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(logHistory);
      });

      it('should add only unique LogHistorySig to an array', () => {
        const logHistoryArray: ILogHistorySig[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const logHistoryCollection: ILogHistorySig[] = [sampleWithRequiredData];
        expectedResult = service.addLogHistorySigToCollectionIfMissing(logHistoryCollection, ...logHistoryArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const logHistory: ILogHistorySig = sampleWithRequiredData;
        const logHistory2: ILogHistorySig = sampleWithPartialData;
        expectedResult = service.addLogHistorySigToCollectionIfMissing([], logHistory, logHistory2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(logHistory);
        expect(expectedResult).toContain(logHistory2);
      });

      it('should accept null and undefined values', () => {
        const logHistory: ILogHistorySig = sampleWithRequiredData;
        expectedResult = service.addLogHistorySigToCollectionIfMissing([], null, logHistory, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(logHistory);
      });

      it('should return initial array if no LogHistorySig is added', () => {
        const logHistoryCollection: ILogHistorySig[] = [sampleWithRequiredData];
        expectedResult = service.addLogHistorySigToCollectionIfMissing(logHistoryCollection, undefined, null);
        expect(expectedResult).toEqual(logHistoryCollection);
      });
    });

    describe('compareLogHistorySig', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareLogHistorySig(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { logHistory: 123 };
        const entity2 = null;

        const compareResult1 = service.compareLogHistorySig(entity1, entity2);
        const compareResult2 = service.compareLogHistorySig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { logHistory: 123 };
        const entity2 = { logHistory: 456 };

        const compareResult1 = service.compareLogHistorySig(entity1, entity2);
        const compareResult2 = service.compareLogHistorySig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { logHistory: 123 };
        const entity2 = { logHistory: 123 };

        const compareResult1 = service.compareLogHistorySig(entity1, entity2);
        const compareResult2 = service.compareLogHistorySig(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
