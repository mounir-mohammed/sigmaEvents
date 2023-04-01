import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISexeSig } from '../sexe-sig.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../sexe-sig.test-samples';

import { SexeSigService } from './sexe-sig.service';

const requireRestSample: ISexeSig = {
  ...sampleWithRequiredData,
};

describe('SexeSig Service', () => {
  let service: SexeSigService;
  let httpMock: HttpTestingController;
  let expectedResult: ISexeSig | ISexeSig[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SexeSigService);
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

    it('should create a SexeSig', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const sexe = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(sexe).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SexeSig', () => {
      const sexe = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(sexe).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SexeSig', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SexeSig', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a SexeSig', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSexeSigToCollectionIfMissing', () => {
      it('should add a SexeSig to an empty array', () => {
        const sexe: ISexeSig = sampleWithRequiredData;
        expectedResult = service.addSexeSigToCollectionIfMissing([], sexe);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sexe);
      });

      it('should not add a SexeSig to an array that contains it', () => {
        const sexe: ISexeSig = sampleWithRequiredData;
        const sexeCollection: ISexeSig[] = [
          {
            ...sexe,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSexeSigToCollectionIfMissing(sexeCollection, sexe);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SexeSig to an array that doesn't contain it", () => {
        const sexe: ISexeSig = sampleWithRequiredData;
        const sexeCollection: ISexeSig[] = [sampleWithPartialData];
        expectedResult = service.addSexeSigToCollectionIfMissing(sexeCollection, sexe);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sexe);
      });

      it('should add only unique SexeSig to an array', () => {
        const sexeArray: ISexeSig[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const sexeCollection: ISexeSig[] = [sampleWithRequiredData];
        expectedResult = service.addSexeSigToCollectionIfMissing(sexeCollection, ...sexeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const sexe: ISexeSig = sampleWithRequiredData;
        const sexe2: ISexeSig = sampleWithPartialData;
        expectedResult = service.addSexeSigToCollectionIfMissing([], sexe, sexe2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sexe);
        expect(expectedResult).toContain(sexe2);
      });

      it('should accept null and undefined values', () => {
        const sexe: ISexeSig = sampleWithRequiredData;
        expectedResult = service.addSexeSigToCollectionIfMissing([], null, sexe, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sexe);
      });

      it('should return initial array if no SexeSig is added', () => {
        const sexeCollection: ISexeSig[] = [sampleWithRequiredData];
        expectedResult = service.addSexeSigToCollectionIfMissing(sexeCollection, undefined, null);
        expect(expectedResult).toEqual(sexeCollection);
      });
    });

    describe('compareSexeSig', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSexeSig(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { sexeId: 123 };
        const entity2 = null;

        const compareResult1 = service.compareSexeSig(entity1, entity2);
        const compareResult2 = service.compareSexeSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { sexeId: 123 };
        const entity2 = { sexeId: 456 };

        const compareResult1 = service.compareSexeSig(entity1, entity2);
        const compareResult2 = service.compareSexeSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { sexeId: 123 };
        const entity2 = { sexeId: 123 };

        const compareResult1 = service.compareSexeSig(entity1, entity2);
        const compareResult2 = service.compareSexeSig(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
