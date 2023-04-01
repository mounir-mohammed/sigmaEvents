import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICodeSig } from '../code-sig.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../code-sig.test-samples';

import { CodeSigService } from './code-sig.service';

const requireRestSample: ICodeSig = {
  ...sampleWithRequiredData,
};

describe('CodeSig Service', () => {
  let service: CodeSigService;
  let httpMock: HttpTestingController;
  let expectedResult: ICodeSig | ICodeSig[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CodeSigService);
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

    it('should create a CodeSig', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const code = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(code).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CodeSig', () => {
      const code = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(code).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CodeSig', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CodeSig', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CodeSig', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCodeSigToCollectionIfMissing', () => {
      it('should add a CodeSig to an empty array', () => {
        const code: ICodeSig = sampleWithRequiredData;
        expectedResult = service.addCodeSigToCollectionIfMissing([], code);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(code);
      });

      it('should not add a CodeSig to an array that contains it', () => {
        const code: ICodeSig = sampleWithRequiredData;
        const codeCollection: ICodeSig[] = [
          {
            ...code,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCodeSigToCollectionIfMissing(codeCollection, code);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CodeSig to an array that doesn't contain it", () => {
        const code: ICodeSig = sampleWithRequiredData;
        const codeCollection: ICodeSig[] = [sampleWithPartialData];
        expectedResult = service.addCodeSigToCollectionIfMissing(codeCollection, code);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(code);
      });

      it('should add only unique CodeSig to an array', () => {
        const codeArray: ICodeSig[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const codeCollection: ICodeSig[] = [sampleWithRequiredData];
        expectedResult = service.addCodeSigToCollectionIfMissing(codeCollection, ...codeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const code: ICodeSig = sampleWithRequiredData;
        const code2: ICodeSig = sampleWithPartialData;
        expectedResult = service.addCodeSigToCollectionIfMissing([], code, code2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(code);
        expect(expectedResult).toContain(code2);
      });

      it('should accept null and undefined values', () => {
        const code: ICodeSig = sampleWithRequiredData;
        expectedResult = service.addCodeSigToCollectionIfMissing([], null, code, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(code);
      });

      it('should return initial array if no CodeSig is added', () => {
        const codeCollection: ICodeSig[] = [sampleWithRequiredData];
        expectedResult = service.addCodeSigToCollectionIfMissing(codeCollection, undefined, null);
        expect(expectedResult).toEqual(codeCollection);
      });
    });

    describe('compareCodeSig', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCodeSig(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { codeId: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCodeSig(entity1, entity2);
        const compareResult2 = service.compareCodeSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { codeId: 123 };
        const entity2 = { codeId: 456 };

        const compareResult1 = service.compareCodeSig(entity1, entity2);
        const compareResult2 = service.compareCodeSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { codeId: 123 };
        const entity2 = { codeId: 123 };

        const compareResult1 = service.compareCodeSig(entity1, entity2);
        const compareResult2 = service.compareCodeSig(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
