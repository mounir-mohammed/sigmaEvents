import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAreaSig } from '../area-sig.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../area-sig.test-samples';

import { AreaSigService } from './area-sig.service';

const requireRestSample: IAreaSig = {
  ...sampleWithRequiredData,
};

describe('AreaSig Service', () => {
  let service: AreaSigService;
  let httpMock: HttpTestingController;
  let expectedResult: IAreaSig | IAreaSig[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AreaSigService);
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

    it('should create a AreaSig', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const area = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(area).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AreaSig', () => {
      const area = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(area).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AreaSig', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AreaSig', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AreaSig', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAreaSigToCollectionIfMissing', () => {
      it('should add a AreaSig to an empty array', () => {
        const area: IAreaSig = sampleWithRequiredData;
        expectedResult = service.addAreaSigToCollectionIfMissing([], area);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(area);
      });

      it('should not add a AreaSig to an array that contains it', () => {
        const area: IAreaSig = sampleWithRequiredData;
        const areaCollection: IAreaSig[] = [
          {
            ...area,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAreaSigToCollectionIfMissing(areaCollection, area);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AreaSig to an array that doesn't contain it", () => {
        const area: IAreaSig = sampleWithRequiredData;
        const areaCollection: IAreaSig[] = [sampleWithPartialData];
        expectedResult = service.addAreaSigToCollectionIfMissing(areaCollection, area);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(area);
      });

      it('should add only unique AreaSig to an array', () => {
        const areaArray: IAreaSig[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const areaCollection: IAreaSig[] = [sampleWithRequiredData];
        expectedResult = service.addAreaSigToCollectionIfMissing(areaCollection, ...areaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const area: IAreaSig = sampleWithRequiredData;
        const area2: IAreaSig = sampleWithPartialData;
        expectedResult = service.addAreaSigToCollectionIfMissing([], area, area2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(area);
        expect(expectedResult).toContain(area2);
      });

      it('should accept null and undefined values', () => {
        const area: IAreaSig = sampleWithRequiredData;
        expectedResult = service.addAreaSigToCollectionIfMissing([], null, area, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(area);
      });

      it('should return initial array if no AreaSig is added', () => {
        const areaCollection: IAreaSig[] = [sampleWithRequiredData];
        expectedResult = service.addAreaSigToCollectionIfMissing(areaCollection, undefined, null);
        expect(expectedResult).toEqual(areaCollection);
      });
    });

    describe('compareAreaSig', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAreaSig(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { areaId: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAreaSig(entity1, entity2);
        const compareResult2 = service.compareAreaSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { areaId: 123 };
        const entity2 = { areaId: 456 };

        const compareResult1 = service.compareAreaSig(entity1, entity2);
        const compareResult2 = service.compareAreaSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { areaId: 123 };
        const entity2 = { areaId: 123 };

        const compareResult1 = service.compareAreaSig(entity1, entity2);
        const compareResult2 = service.compareAreaSig(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
