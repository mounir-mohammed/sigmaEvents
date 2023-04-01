import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ILanguageSig } from '../language-sig.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../language-sig.test-samples';

import { LanguageSigService } from './language-sig.service';

const requireRestSample: ILanguageSig = {
  ...sampleWithRequiredData,
};

describe('LanguageSig Service', () => {
  let service: LanguageSigService;
  let httpMock: HttpTestingController;
  let expectedResult: ILanguageSig | ILanguageSig[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(LanguageSigService);
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

    it('should create a LanguageSig', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const language = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(language).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a LanguageSig', () => {
      const language = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(language).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a LanguageSig', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of LanguageSig', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a LanguageSig', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addLanguageSigToCollectionIfMissing', () => {
      it('should add a LanguageSig to an empty array', () => {
        const language: ILanguageSig = sampleWithRequiredData;
        expectedResult = service.addLanguageSigToCollectionIfMissing([], language);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(language);
      });

      it('should not add a LanguageSig to an array that contains it', () => {
        const language: ILanguageSig = sampleWithRequiredData;
        const languageCollection: ILanguageSig[] = [
          {
            ...language,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addLanguageSigToCollectionIfMissing(languageCollection, language);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a LanguageSig to an array that doesn't contain it", () => {
        const language: ILanguageSig = sampleWithRequiredData;
        const languageCollection: ILanguageSig[] = [sampleWithPartialData];
        expectedResult = service.addLanguageSigToCollectionIfMissing(languageCollection, language);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(language);
      });

      it('should add only unique LanguageSig to an array', () => {
        const languageArray: ILanguageSig[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const languageCollection: ILanguageSig[] = [sampleWithRequiredData];
        expectedResult = service.addLanguageSigToCollectionIfMissing(languageCollection, ...languageArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const language: ILanguageSig = sampleWithRequiredData;
        const language2: ILanguageSig = sampleWithPartialData;
        expectedResult = service.addLanguageSigToCollectionIfMissing([], language, language2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(language);
        expect(expectedResult).toContain(language2);
      });

      it('should accept null and undefined values', () => {
        const language: ILanguageSig = sampleWithRequiredData;
        expectedResult = service.addLanguageSigToCollectionIfMissing([], null, language, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(language);
      });

      it('should return initial array if no LanguageSig is added', () => {
        const languageCollection: ILanguageSig[] = [sampleWithRequiredData];
        expectedResult = service.addLanguageSigToCollectionIfMissing(languageCollection, undefined, null);
        expect(expectedResult).toEqual(languageCollection);
      });
    });

    describe('compareLanguageSig', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareLanguageSig(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { languageId: 123 };
        const entity2 = null;

        const compareResult1 = service.compareLanguageSig(entity1, entity2);
        const compareResult2 = service.compareLanguageSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { languageId: 123 };
        const entity2 = { languageId: 456 };

        const compareResult1 = service.compareLanguageSig(entity1, entity2);
        const compareResult2 = service.compareLanguageSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { languageId: 123 };
        const entity2 = { languageId: 123 };

        const compareResult1 = service.compareLanguageSig(entity1, entity2);
        const compareResult2 = service.compareLanguageSig(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
