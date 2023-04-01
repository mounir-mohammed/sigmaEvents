import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IInfoSuppTypeSig } from '../info-supp-type-sig.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../info-supp-type-sig.test-samples';

import { InfoSuppTypeSigService } from './info-supp-type-sig.service';

const requireRestSample: IInfoSuppTypeSig = {
  ...sampleWithRequiredData,
};

describe('InfoSuppTypeSig Service', () => {
  let service: InfoSuppTypeSigService;
  let httpMock: HttpTestingController;
  let expectedResult: IInfoSuppTypeSig | IInfoSuppTypeSig[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(InfoSuppTypeSigService);
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

    it('should create a InfoSuppTypeSig', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const infoSuppType = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(infoSuppType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a InfoSuppTypeSig', () => {
      const infoSuppType = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(infoSuppType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a InfoSuppTypeSig', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of InfoSuppTypeSig', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a InfoSuppTypeSig', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addInfoSuppTypeSigToCollectionIfMissing', () => {
      it('should add a InfoSuppTypeSig to an empty array', () => {
        const infoSuppType: IInfoSuppTypeSig = sampleWithRequiredData;
        expectedResult = service.addInfoSuppTypeSigToCollectionIfMissing([], infoSuppType);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(infoSuppType);
      });

      it('should not add a InfoSuppTypeSig to an array that contains it', () => {
        const infoSuppType: IInfoSuppTypeSig = sampleWithRequiredData;
        const infoSuppTypeCollection: IInfoSuppTypeSig[] = [
          {
            ...infoSuppType,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addInfoSuppTypeSigToCollectionIfMissing(infoSuppTypeCollection, infoSuppType);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a InfoSuppTypeSig to an array that doesn't contain it", () => {
        const infoSuppType: IInfoSuppTypeSig = sampleWithRequiredData;
        const infoSuppTypeCollection: IInfoSuppTypeSig[] = [sampleWithPartialData];
        expectedResult = service.addInfoSuppTypeSigToCollectionIfMissing(infoSuppTypeCollection, infoSuppType);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(infoSuppType);
      });

      it('should add only unique InfoSuppTypeSig to an array', () => {
        const infoSuppTypeArray: IInfoSuppTypeSig[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const infoSuppTypeCollection: IInfoSuppTypeSig[] = [sampleWithRequiredData];
        expectedResult = service.addInfoSuppTypeSigToCollectionIfMissing(infoSuppTypeCollection, ...infoSuppTypeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const infoSuppType: IInfoSuppTypeSig = sampleWithRequiredData;
        const infoSuppType2: IInfoSuppTypeSig = sampleWithPartialData;
        expectedResult = service.addInfoSuppTypeSigToCollectionIfMissing([], infoSuppType, infoSuppType2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(infoSuppType);
        expect(expectedResult).toContain(infoSuppType2);
      });

      it('should accept null and undefined values', () => {
        const infoSuppType: IInfoSuppTypeSig = sampleWithRequiredData;
        expectedResult = service.addInfoSuppTypeSigToCollectionIfMissing([], null, infoSuppType, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(infoSuppType);
      });

      it('should return initial array if no InfoSuppTypeSig is added', () => {
        const infoSuppTypeCollection: IInfoSuppTypeSig[] = [sampleWithRequiredData];
        expectedResult = service.addInfoSuppTypeSigToCollectionIfMissing(infoSuppTypeCollection, undefined, null);
        expect(expectedResult).toEqual(infoSuppTypeCollection);
      });
    });

    describe('compareInfoSuppTypeSig', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareInfoSuppTypeSig(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { infoSuppTypeId: 123 };
        const entity2 = null;

        const compareResult1 = service.compareInfoSuppTypeSig(entity1, entity2);
        const compareResult2 = service.compareInfoSuppTypeSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { infoSuppTypeId: 123 };
        const entity2 = { infoSuppTypeId: 456 };

        const compareResult1 = service.compareInfoSuppTypeSig(entity1, entity2);
        const compareResult2 = service.compareInfoSuppTypeSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { infoSuppTypeId: 123 };
        const entity2 = { infoSuppTypeId: 123 };

        const compareResult1 = service.compareInfoSuppTypeSig(entity1, entity2);
        const compareResult2 = service.compareInfoSuppTypeSig(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
