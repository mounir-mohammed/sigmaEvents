import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICitySig } from '../city-sig.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../city-sig.test-samples';

import { CitySigService } from './city-sig.service';

const requireRestSample: ICitySig = {
  ...sampleWithRequiredData,
};

describe('CitySig Service', () => {
  let service: CitySigService;
  let httpMock: HttpTestingController;
  let expectedResult: ICitySig | ICitySig[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CitySigService);
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

    it('should create a CitySig', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const city = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(city).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CitySig', () => {
      const city = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(city).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CitySig', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CitySig', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CitySig', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCitySigToCollectionIfMissing', () => {
      it('should add a CitySig to an empty array', () => {
        const city: ICitySig = sampleWithRequiredData;
        expectedResult = service.addCitySigToCollectionIfMissing([], city);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(city);
      });

      it('should not add a CitySig to an array that contains it', () => {
        const city: ICitySig = sampleWithRequiredData;
        const cityCollection: ICitySig[] = [
          {
            ...city,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCitySigToCollectionIfMissing(cityCollection, city);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CitySig to an array that doesn't contain it", () => {
        const city: ICitySig = sampleWithRequiredData;
        const cityCollection: ICitySig[] = [sampleWithPartialData];
        expectedResult = service.addCitySigToCollectionIfMissing(cityCollection, city);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(city);
      });

      it('should add only unique CitySig to an array', () => {
        const cityArray: ICitySig[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const cityCollection: ICitySig[] = [sampleWithRequiredData];
        expectedResult = service.addCitySigToCollectionIfMissing(cityCollection, ...cityArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const city: ICitySig = sampleWithRequiredData;
        const city2: ICitySig = sampleWithPartialData;
        expectedResult = service.addCitySigToCollectionIfMissing([], city, city2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(city);
        expect(expectedResult).toContain(city2);
      });

      it('should accept null and undefined values', () => {
        const city: ICitySig = sampleWithRequiredData;
        expectedResult = service.addCitySigToCollectionIfMissing([], null, city, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(city);
      });

      it('should return initial array if no CitySig is added', () => {
        const cityCollection: ICitySig[] = [sampleWithRequiredData];
        expectedResult = service.addCitySigToCollectionIfMissing(cityCollection, undefined, null);
        expect(expectedResult).toEqual(cityCollection);
      });
    });

    describe('compareCitySig', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCitySig(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { cityId: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCitySig(entity1, entity2);
        const compareResult2 = service.compareCitySig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { cityId: 123 };
        const entity2 = { cityId: 456 };

        const compareResult1 = service.compareCitySig(entity1, entity2);
        const compareResult2 = service.compareCitySig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { cityId: 123 };
        const entity2 = { cityId: 123 };

        const compareResult1 = service.compareCitySig(entity1, entity2);
        const compareResult2 = service.compareCitySig(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
