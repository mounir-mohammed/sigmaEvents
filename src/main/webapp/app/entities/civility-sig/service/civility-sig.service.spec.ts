import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICivilitySig } from '../civility-sig.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../civility-sig.test-samples';

import { CivilitySigService } from './civility-sig.service';

const requireRestSample: ICivilitySig = {
  ...sampleWithRequiredData,
};

describe('CivilitySig Service', () => {
  let service: CivilitySigService;
  let httpMock: HttpTestingController;
  let expectedResult: ICivilitySig | ICivilitySig[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CivilitySigService);
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

    it('should create a CivilitySig', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const civility = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(civility).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CivilitySig', () => {
      const civility = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(civility).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CivilitySig', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CivilitySig', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CivilitySig', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCivilitySigToCollectionIfMissing', () => {
      it('should add a CivilitySig to an empty array', () => {
        const civility: ICivilitySig = sampleWithRequiredData;
        expectedResult = service.addCivilitySigToCollectionIfMissing([], civility);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(civility);
      });

      it('should not add a CivilitySig to an array that contains it', () => {
        const civility: ICivilitySig = sampleWithRequiredData;
        const civilityCollection: ICivilitySig[] = [
          {
            ...civility,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCivilitySigToCollectionIfMissing(civilityCollection, civility);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CivilitySig to an array that doesn't contain it", () => {
        const civility: ICivilitySig = sampleWithRequiredData;
        const civilityCollection: ICivilitySig[] = [sampleWithPartialData];
        expectedResult = service.addCivilitySigToCollectionIfMissing(civilityCollection, civility);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(civility);
      });

      it('should add only unique CivilitySig to an array', () => {
        const civilityArray: ICivilitySig[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const civilityCollection: ICivilitySig[] = [sampleWithRequiredData];
        expectedResult = service.addCivilitySigToCollectionIfMissing(civilityCollection, ...civilityArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const civility: ICivilitySig = sampleWithRequiredData;
        const civility2: ICivilitySig = sampleWithPartialData;
        expectedResult = service.addCivilitySigToCollectionIfMissing([], civility, civility2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(civility);
        expect(expectedResult).toContain(civility2);
      });

      it('should accept null and undefined values', () => {
        const civility: ICivilitySig = sampleWithRequiredData;
        expectedResult = service.addCivilitySigToCollectionIfMissing([], null, civility, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(civility);
      });

      it('should return initial array if no CivilitySig is added', () => {
        const civilityCollection: ICivilitySig[] = [sampleWithRequiredData];
        expectedResult = service.addCivilitySigToCollectionIfMissing(civilityCollection, undefined, null);
        expect(expectedResult).toEqual(civilityCollection);
      });
    });

    describe('compareCivilitySig', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCivilitySig(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { civilityId: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCivilitySig(entity1, entity2);
        const compareResult2 = service.compareCivilitySig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { civilityId: 123 };
        const entity2 = { civilityId: 456 };

        const compareResult1 = service.compareCivilitySig(entity1, entity2);
        const compareResult2 = service.compareCivilitySig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { civilityId: 123 };
        const entity2 = { civilityId: 123 };

        const compareResult1 = service.compareCivilitySig(entity1, entity2);
        const compareResult2 = service.compareCivilitySig(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
