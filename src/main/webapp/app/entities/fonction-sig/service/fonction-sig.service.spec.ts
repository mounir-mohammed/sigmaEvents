import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IFonctionSig } from '../fonction-sig.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../fonction-sig.test-samples';

import { FonctionSigService } from './fonction-sig.service';

const requireRestSample: IFonctionSig = {
  ...sampleWithRequiredData,
};

describe('FonctionSig Service', () => {
  let service: FonctionSigService;
  let httpMock: HttpTestingController;
  let expectedResult: IFonctionSig | IFonctionSig[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FonctionSigService);
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

    it('should create a FonctionSig', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const fonction = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(fonction).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FonctionSig', () => {
      const fonction = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(fonction).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a FonctionSig', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FonctionSig', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a FonctionSig', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFonctionSigToCollectionIfMissing', () => {
      it('should add a FonctionSig to an empty array', () => {
        const fonction: IFonctionSig = sampleWithRequiredData;
        expectedResult = service.addFonctionSigToCollectionIfMissing([], fonction);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fonction);
      });

      it('should not add a FonctionSig to an array that contains it', () => {
        const fonction: IFonctionSig = sampleWithRequiredData;
        const fonctionCollection: IFonctionSig[] = [
          {
            ...fonction,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFonctionSigToCollectionIfMissing(fonctionCollection, fonction);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FonctionSig to an array that doesn't contain it", () => {
        const fonction: IFonctionSig = sampleWithRequiredData;
        const fonctionCollection: IFonctionSig[] = [sampleWithPartialData];
        expectedResult = service.addFonctionSigToCollectionIfMissing(fonctionCollection, fonction);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fonction);
      });

      it('should add only unique FonctionSig to an array', () => {
        const fonctionArray: IFonctionSig[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const fonctionCollection: IFonctionSig[] = [sampleWithRequiredData];
        expectedResult = service.addFonctionSigToCollectionIfMissing(fonctionCollection, ...fonctionArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const fonction: IFonctionSig = sampleWithRequiredData;
        const fonction2: IFonctionSig = sampleWithPartialData;
        expectedResult = service.addFonctionSigToCollectionIfMissing([], fonction, fonction2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fonction);
        expect(expectedResult).toContain(fonction2);
      });

      it('should accept null and undefined values', () => {
        const fonction: IFonctionSig = sampleWithRequiredData;
        expectedResult = service.addFonctionSigToCollectionIfMissing([], null, fonction, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fonction);
      });

      it('should return initial array if no FonctionSig is added', () => {
        const fonctionCollection: IFonctionSig[] = [sampleWithRequiredData];
        expectedResult = service.addFonctionSigToCollectionIfMissing(fonctionCollection, undefined, null);
        expect(expectedResult).toEqual(fonctionCollection);
      });
    });

    describe('compareFonctionSig', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFonctionSig(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { fonctionId: 123 };
        const entity2 = null;

        const compareResult1 = service.compareFonctionSig(entity1, entity2);
        const compareResult2 = service.compareFonctionSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { fonctionId: 123 };
        const entity2 = { fonctionId: 456 };

        const compareResult1 = service.compareFonctionSig(entity1, entity2);
        const compareResult2 = service.compareFonctionSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { fonctionId: 123 };
        const entity2 = { fonctionId: 123 };

        const compareResult1 = service.compareFonctionSig(entity1, entity2);
        const compareResult2 = service.compareFonctionSig(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
