import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICodeTypeSig } from '../code-type-sig.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../code-type-sig.test-samples';

import { CodeTypeSigService } from './code-type-sig.service';

const requireRestSample: ICodeTypeSig = {
  ...sampleWithRequiredData,
};

describe('CodeTypeSig Service', () => {
  let service: CodeTypeSigService;
  let httpMock: HttpTestingController;
  let expectedResult: ICodeTypeSig | ICodeTypeSig[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CodeTypeSigService);
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

    it('should create a CodeTypeSig', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const codeType = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(codeType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CodeTypeSig', () => {
      const codeType = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(codeType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CodeTypeSig', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CodeTypeSig', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CodeTypeSig', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCodeTypeSigToCollectionIfMissing', () => {
      it('should add a CodeTypeSig to an empty array', () => {
        const codeType: ICodeTypeSig = sampleWithRequiredData;
        expectedResult = service.addCodeTypeSigToCollectionIfMissing([], codeType);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(codeType);
      });

      it('should not add a CodeTypeSig to an array that contains it', () => {
        const codeType: ICodeTypeSig = sampleWithRequiredData;
        const codeTypeCollection: ICodeTypeSig[] = [
          {
            ...codeType,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCodeTypeSigToCollectionIfMissing(codeTypeCollection, codeType);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CodeTypeSig to an array that doesn't contain it", () => {
        const codeType: ICodeTypeSig = sampleWithRequiredData;
        const codeTypeCollection: ICodeTypeSig[] = [sampleWithPartialData];
        expectedResult = service.addCodeTypeSigToCollectionIfMissing(codeTypeCollection, codeType);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(codeType);
      });

      it('should add only unique CodeTypeSig to an array', () => {
        const codeTypeArray: ICodeTypeSig[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const codeTypeCollection: ICodeTypeSig[] = [sampleWithRequiredData];
        expectedResult = service.addCodeTypeSigToCollectionIfMissing(codeTypeCollection, ...codeTypeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const codeType: ICodeTypeSig = sampleWithRequiredData;
        const codeType2: ICodeTypeSig = sampleWithPartialData;
        expectedResult = service.addCodeTypeSigToCollectionIfMissing([], codeType, codeType2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(codeType);
        expect(expectedResult).toContain(codeType2);
      });

      it('should accept null and undefined values', () => {
        const codeType: ICodeTypeSig = sampleWithRequiredData;
        expectedResult = service.addCodeTypeSigToCollectionIfMissing([], null, codeType, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(codeType);
      });

      it('should return initial array if no CodeTypeSig is added', () => {
        const codeTypeCollection: ICodeTypeSig[] = [sampleWithRequiredData];
        expectedResult = service.addCodeTypeSigToCollectionIfMissing(codeTypeCollection, undefined, null);
        expect(expectedResult).toEqual(codeTypeCollection);
      });
    });

    describe('compareCodeTypeSig', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCodeTypeSig(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { codeTypeId: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCodeTypeSig(entity1, entity2);
        const compareResult2 = service.compareCodeTypeSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { codeTypeId: 123 };
        const entity2 = { codeTypeId: 456 };

        const compareResult1 = service.compareCodeTypeSig(entity1, entity2);
        const compareResult2 = service.compareCodeTypeSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { codeTypeId: 123 };
        const entity2 = { codeTypeId: 123 };

        const compareResult1 = service.compareCodeTypeSig(entity1, entity2);
        const compareResult2 = service.compareCodeTypeSig(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
