import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICountrySig } from '../country-sig.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../country-sig.test-samples';

import { CountrySigService } from './country-sig.service';

const requireRestSample: ICountrySig = {
  ...sampleWithRequiredData,
};

describe('CountrySig Service', () => {
  let service: CountrySigService;
  let httpMock: HttpTestingController;
  let expectedResult: ICountrySig | ICountrySig[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CountrySigService);
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

    it('should create a CountrySig', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const country = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(country).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CountrySig', () => {
      const country = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(country).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CountrySig', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CountrySig', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CountrySig', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCountrySigToCollectionIfMissing', () => {
      it('should add a CountrySig to an empty array', () => {
        const country: ICountrySig = sampleWithRequiredData;
        expectedResult = service.addCountrySigToCollectionIfMissing([], country);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(country);
      });

      it('should not add a CountrySig to an array that contains it', () => {
        const country: ICountrySig = sampleWithRequiredData;
        const countryCollection: ICountrySig[] = [
          {
            ...country,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCountrySigToCollectionIfMissing(countryCollection, country);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CountrySig to an array that doesn't contain it", () => {
        const country: ICountrySig = sampleWithRequiredData;
        const countryCollection: ICountrySig[] = [sampleWithPartialData];
        expectedResult = service.addCountrySigToCollectionIfMissing(countryCollection, country);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(country);
      });

      it('should add only unique CountrySig to an array', () => {
        const countryArray: ICountrySig[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const countryCollection: ICountrySig[] = [sampleWithRequiredData];
        expectedResult = service.addCountrySigToCollectionIfMissing(countryCollection, ...countryArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const country: ICountrySig = sampleWithRequiredData;
        const country2: ICountrySig = sampleWithPartialData;
        expectedResult = service.addCountrySigToCollectionIfMissing([], country, country2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(country);
        expect(expectedResult).toContain(country2);
      });

      it('should accept null and undefined values', () => {
        const country: ICountrySig = sampleWithRequiredData;
        expectedResult = service.addCountrySigToCollectionIfMissing([], null, country, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(country);
      });

      it('should return initial array if no CountrySig is added', () => {
        const countryCollection: ICountrySig[] = [sampleWithRequiredData];
        expectedResult = service.addCountrySigToCollectionIfMissing(countryCollection, undefined, null);
        expect(expectedResult).toEqual(countryCollection);
      });
    });

    describe('compareCountrySig', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCountrySig(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { countryId: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCountrySig(entity1, entity2);
        const compareResult2 = service.compareCountrySig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { countryId: 123 };
        const entity2 = { countryId: 456 };

        const compareResult1 = service.compareCountrySig(entity1, entity2);
        const compareResult2 = service.compareCountrySig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { countryId: 123 };
        const entity2 = { countryId: 123 };

        const compareResult1 = service.compareCountrySig(entity1, entity2);
        const compareResult2 = service.compareCountrySig(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
