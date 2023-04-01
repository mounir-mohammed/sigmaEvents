import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAuthentificationTypeSig } from '../authentification-type-sig.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../authentification-type-sig.test-samples';

import { AuthentificationTypeSigService } from './authentification-type-sig.service';

const requireRestSample: IAuthentificationTypeSig = {
  ...sampleWithRequiredData,
};

describe('AuthentificationTypeSig Service', () => {
  let service: AuthentificationTypeSigService;
  let httpMock: HttpTestingController;
  let expectedResult: IAuthentificationTypeSig | IAuthentificationTypeSig[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AuthentificationTypeSigService);
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

    it('should create a AuthentificationTypeSig', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const authentificationType = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(authentificationType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AuthentificationTypeSig', () => {
      const authentificationType = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(authentificationType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AuthentificationTypeSig', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AuthentificationTypeSig', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AuthentificationTypeSig', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAuthentificationTypeSigToCollectionIfMissing', () => {
      it('should add a AuthentificationTypeSig to an empty array', () => {
        const authentificationType: IAuthentificationTypeSig = sampleWithRequiredData;
        expectedResult = service.addAuthentificationTypeSigToCollectionIfMissing([], authentificationType);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(authentificationType);
      });

      it('should not add a AuthentificationTypeSig to an array that contains it', () => {
        const authentificationType: IAuthentificationTypeSig = sampleWithRequiredData;
        const authentificationTypeCollection: IAuthentificationTypeSig[] = [
          {
            ...authentificationType,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAuthentificationTypeSigToCollectionIfMissing(authentificationTypeCollection, authentificationType);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AuthentificationTypeSig to an array that doesn't contain it", () => {
        const authentificationType: IAuthentificationTypeSig = sampleWithRequiredData;
        const authentificationTypeCollection: IAuthentificationTypeSig[] = [sampleWithPartialData];
        expectedResult = service.addAuthentificationTypeSigToCollectionIfMissing(authentificationTypeCollection, authentificationType);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(authentificationType);
      });

      it('should add only unique AuthentificationTypeSig to an array', () => {
        const authentificationTypeArray: IAuthentificationTypeSig[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const authentificationTypeCollection: IAuthentificationTypeSig[] = [sampleWithRequiredData];
        expectedResult = service.addAuthentificationTypeSigToCollectionIfMissing(
          authentificationTypeCollection,
          ...authentificationTypeArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const authentificationType: IAuthentificationTypeSig = sampleWithRequiredData;
        const authentificationType2: IAuthentificationTypeSig = sampleWithPartialData;
        expectedResult = service.addAuthentificationTypeSigToCollectionIfMissing([], authentificationType, authentificationType2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(authentificationType);
        expect(expectedResult).toContain(authentificationType2);
      });

      it('should accept null and undefined values', () => {
        const authentificationType: IAuthentificationTypeSig = sampleWithRequiredData;
        expectedResult = service.addAuthentificationTypeSigToCollectionIfMissing([], null, authentificationType, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(authentificationType);
      });

      it('should return initial array if no AuthentificationTypeSig is added', () => {
        const authentificationTypeCollection: IAuthentificationTypeSig[] = [sampleWithRequiredData];
        expectedResult = service.addAuthentificationTypeSigToCollectionIfMissing(authentificationTypeCollection, undefined, null);
        expect(expectedResult).toEqual(authentificationTypeCollection);
      });
    });

    describe('compareAuthentificationTypeSig', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAuthentificationTypeSig(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { authentificationTypeId: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAuthentificationTypeSig(entity1, entity2);
        const compareResult2 = service.compareAuthentificationTypeSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { authentificationTypeId: 123 };
        const entity2 = { authentificationTypeId: 456 };

        const compareResult1 = service.compareAuthentificationTypeSig(entity1, entity2);
        const compareResult2 = service.compareAuthentificationTypeSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { authentificationTypeId: 123 };
        const entity2 = { authentificationTypeId: 123 };

        const compareResult1 = service.compareAuthentificationTypeSig(entity1, entity2);
        const compareResult2 = service.compareAuthentificationTypeSig(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
