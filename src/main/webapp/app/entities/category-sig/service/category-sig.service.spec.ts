import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICategorySig } from '../category-sig.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../category-sig.test-samples';

import { CategorySigService } from './category-sig.service';

const requireRestSample: ICategorySig = {
  ...sampleWithRequiredData,
};

describe('CategorySig Service', () => {
  let service: CategorySigService;
  let httpMock: HttpTestingController;
  let expectedResult: ICategorySig | ICategorySig[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CategorySigService);
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

    it('should create a CategorySig', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const category = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(category).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CategorySig', () => {
      const category = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(category).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CategorySig', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CategorySig', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CategorySig', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCategorySigToCollectionIfMissing', () => {
      it('should add a CategorySig to an empty array', () => {
        const category: ICategorySig = sampleWithRequiredData;
        expectedResult = service.addCategorySigToCollectionIfMissing([], category);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(category);
      });

      it('should not add a CategorySig to an array that contains it', () => {
        const category: ICategorySig = sampleWithRequiredData;
        const categoryCollection: ICategorySig[] = [
          {
            ...category,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCategorySigToCollectionIfMissing(categoryCollection, category);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CategorySig to an array that doesn't contain it", () => {
        const category: ICategorySig = sampleWithRequiredData;
        const categoryCollection: ICategorySig[] = [sampleWithPartialData];
        expectedResult = service.addCategorySigToCollectionIfMissing(categoryCollection, category);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(category);
      });

      it('should add only unique CategorySig to an array', () => {
        const categoryArray: ICategorySig[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const categoryCollection: ICategorySig[] = [sampleWithRequiredData];
        expectedResult = service.addCategorySigToCollectionIfMissing(categoryCollection, ...categoryArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const category: ICategorySig = sampleWithRequiredData;
        const category2: ICategorySig = sampleWithPartialData;
        expectedResult = service.addCategorySigToCollectionIfMissing([], category, category2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(category);
        expect(expectedResult).toContain(category2);
      });

      it('should accept null and undefined values', () => {
        const category: ICategorySig = sampleWithRequiredData;
        expectedResult = service.addCategorySigToCollectionIfMissing([], null, category, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(category);
      });

      it('should return initial array if no CategorySig is added', () => {
        const categoryCollection: ICategorySig[] = [sampleWithRequiredData];
        expectedResult = service.addCategorySigToCollectionIfMissing(categoryCollection, undefined, null);
        expect(expectedResult).toEqual(categoryCollection);
      });
    });

    describe('compareCategorySig', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCategorySig(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { categoryId: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCategorySig(entity1, entity2);
        const compareResult2 = service.compareCategorySig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { categoryId: 123 };
        const entity2 = { categoryId: 456 };

        const compareResult1 = service.compareCategorySig(entity1, entity2);
        const compareResult2 = service.compareCategorySig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { categoryId: 123 };
        const entity2 = { categoryId: 123 };

        const compareResult1 = service.compareCategorySig(entity1, entity2);
        const compareResult2 = service.compareCategorySig(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
