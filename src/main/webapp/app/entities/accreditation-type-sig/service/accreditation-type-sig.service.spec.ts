import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAccreditationTypeSig } from '../accreditation-type-sig.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../accreditation-type-sig.test-samples';

import { AccreditationTypeSigService } from './accreditation-type-sig.service';

const requireRestSample: IAccreditationTypeSig = {
  ...sampleWithRequiredData,
};

describe('AccreditationTypeSig Service', () => {
  let service: AccreditationTypeSigService;
  let httpMock: HttpTestingController;
  let expectedResult: IAccreditationTypeSig | IAccreditationTypeSig[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AccreditationTypeSigService);
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

    it('should create a AccreditationTypeSig', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const accreditationType = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(accreditationType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AccreditationTypeSig', () => {
      const accreditationType = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(accreditationType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AccreditationTypeSig', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AccreditationTypeSig', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AccreditationTypeSig', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAccreditationTypeSigToCollectionIfMissing', () => {
      it('should add a AccreditationTypeSig to an empty array', () => {
        const accreditationType: IAccreditationTypeSig = sampleWithRequiredData;
        expectedResult = service.addAccreditationTypeSigToCollectionIfMissing([], accreditationType);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(accreditationType);
      });

      it('should not add a AccreditationTypeSig to an array that contains it', () => {
        const accreditationType: IAccreditationTypeSig = sampleWithRequiredData;
        const accreditationTypeCollection: IAccreditationTypeSig[] = [
          {
            ...accreditationType,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAccreditationTypeSigToCollectionIfMissing(accreditationTypeCollection, accreditationType);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AccreditationTypeSig to an array that doesn't contain it", () => {
        const accreditationType: IAccreditationTypeSig = sampleWithRequiredData;
        const accreditationTypeCollection: IAccreditationTypeSig[] = [sampleWithPartialData];
        expectedResult = service.addAccreditationTypeSigToCollectionIfMissing(accreditationTypeCollection, accreditationType);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(accreditationType);
      });

      it('should add only unique AccreditationTypeSig to an array', () => {
        const accreditationTypeArray: IAccreditationTypeSig[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const accreditationTypeCollection: IAccreditationTypeSig[] = [sampleWithRequiredData];
        expectedResult = service.addAccreditationTypeSigToCollectionIfMissing(accreditationTypeCollection, ...accreditationTypeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const accreditationType: IAccreditationTypeSig = sampleWithRequiredData;
        const accreditationType2: IAccreditationTypeSig = sampleWithPartialData;
        expectedResult = service.addAccreditationTypeSigToCollectionIfMissing([], accreditationType, accreditationType2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(accreditationType);
        expect(expectedResult).toContain(accreditationType2);
      });

      it('should accept null and undefined values', () => {
        const accreditationType: IAccreditationTypeSig = sampleWithRequiredData;
        expectedResult = service.addAccreditationTypeSigToCollectionIfMissing([], null, accreditationType, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(accreditationType);
      });

      it('should return initial array if no AccreditationTypeSig is added', () => {
        const accreditationTypeCollection: IAccreditationTypeSig[] = [sampleWithRequiredData];
        expectedResult = service.addAccreditationTypeSigToCollectionIfMissing(accreditationTypeCollection, undefined, null);
        expect(expectedResult).toEqual(accreditationTypeCollection);
      });
    });

    describe('compareAccreditationTypeSig', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAccreditationTypeSig(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { accreditationTypeId: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAccreditationTypeSig(entity1, entity2);
        const compareResult2 = service.compareAccreditationTypeSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { accreditationTypeId: 123 };
        const entity2 = { accreditationTypeId: 456 };

        const compareResult1 = service.compareAccreditationTypeSig(entity1, entity2);
        const compareResult2 = service.compareAccreditationTypeSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { accreditationTypeId: 123 };
        const entity2 = { accreditationTypeId: 123 };

        const compareResult1 = service.compareAccreditationTypeSig(entity1, entity2);
        const compareResult2 = service.compareAccreditationTypeSig(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
