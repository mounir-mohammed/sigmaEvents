import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IStatusSig } from '../status-sig.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../status-sig.test-samples';

import { StatusSigService } from './status-sig.service';

const requireRestSample: IStatusSig = {
  ...sampleWithRequiredData,
};

describe('StatusSig Service', () => {
  let service: StatusSigService;
  let httpMock: HttpTestingController;
  let expectedResult: IStatusSig | IStatusSig[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(StatusSigService);
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

    it('should create a StatusSig', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const status = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(status).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a StatusSig', () => {
      const status = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(status).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a StatusSig', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of StatusSig', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a StatusSig', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addStatusSigToCollectionIfMissing', () => {
      it('should add a StatusSig to an empty array', () => {
        const status: IStatusSig = sampleWithRequiredData;
        expectedResult = service.addStatusSigToCollectionIfMissing([], status);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(status);
      });

      it('should not add a StatusSig to an array that contains it', () => {
        const status: IStatusSig = sampleWithRequiredData;
        const statusCollection: IStatusSig[] = [
          {
            ...status,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addStatusSigToCollectionIfMissing(statusCollection, status);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a StatusSig to an array that doesn't contain it", () => {
        const status: IStatusSig = sampleWithRequiredData;
        const statusCollection: IStatusSig[] = [sampleWithPartialData];
        expectedResult = service.addStatusSigToCollectionIfMissing(statusCollection, status);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(status);
      });

      it('should add only unique StatusSig to an array', () => {
        const statusArray: IStatusSig[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const statusCollection: IStatusSig[] = [sampleWithRequiredData];
        expectedResult = service.addStatusSigToCollectionIfMissing(statusCollection, ...statusArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const status: IStatusSig = sampleWithRequiredData;
        const status2: IStatusSig = sampleWithPartialData;
        expectedResult = service.addStatusSigToCollectionIfMissing([], status, status2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(status);
        expect(expectedResult).toContain(status2);
      });

      it('should accept null and undefined values', () => {
        const status: IStatusSig = sampleWithRequiredData;
        expectedResult = service.addStatusSigToCollectionIfMissing([], null, status, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(status);
      });

      it('should return initial array if no StatusSig is added', () => {
        const statusCollection: IStatusSig[] = [sampleWithRequiredData];
        expectedResult = service.addStatusSigToCollectionIfMissing(statusCollection, undefined, null);
        expect(expectedResult).toEqual(statusCollection);
      });
    });

    describe('compareStatusSig', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareStatusSig(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { statusId: 123 };
        const entity2 = null;

        const compareResult1 = service.compareStatusSig(entity1, entity2);
        const compareResult2 = service.compareStatusSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { statusId: 123 };
        const entity2 = { statusId: 456 };

        const compareResult1 = service.compareStatusSig(entity1, entity2);
        const compareResult2 = service.compareStatusSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { statusId: 123 };
        const entity2 = { statusId: 123 };

        const compareResult1 = service.compareStatusSig(entity1, entity2);
        const compareResult2 = service.compareStatusSig(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
