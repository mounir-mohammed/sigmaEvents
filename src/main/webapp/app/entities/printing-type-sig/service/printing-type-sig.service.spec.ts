import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPrintingTypeSig } from '../printing-type-sig.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../printing-type-sig.test-samples';

import { PrintingTypeSigService } from './printing-type-sig.service';

const requireRestSample: IPrintingTypeSig = {
  ...sampleWithRequiredData,
};

describe('PrintingTypeSig Service', () => {
  let service: PrintingTypeSigService;
  let httpMock: HttpTestingController;
  let expectedResult: IPrintingTypeSig | IPrintingTypeSig[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PrintingTypeSigService);
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

    it('should create a PrintingTypeSig', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const printingType = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(printingType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PrintingTypeSig', () => {
      const printingType = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(printingType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PrintingTypeSig', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PrintingTypeSig', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PrintingTypeSig', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPrintingTypeSigToCollectionIfMissing', () => {
      it('should add a PrintingTypeSig to an empty array', () => {
        const printingType: IPrintingTypeSig = sampleWithRequiredData;
        expectedResult = service.addPrintingTypeSigToCollectionIfMissing([], printingType);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(printingType);
      });

      it('should not add a PrintingTypeSig to an array that contains it', () => {
        const printingType: IPrintingTypeSig = sampleWithRequiredData;
        const printingTypeCollection: IPrintingTypeSig[] = [
          {
            ...printingType,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPrintingTypeSigToCollectionIfMissing(printingTypeCollection, printingType);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PrintingTypeSig to an array that doesn't contain it", () => {
        const printingType: IPrintingTypeSig = sampleWithRequiredData;
        const printingTypeCollection: IPrintingTypeSig[] = [sampleWithPartialData];
        expectedResult = service.addPrintingTypeSigToCollectionIfMissing(printingTypeCollection, printingType);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(printingType);
      });

      it('should add only unique PrintingTypeSig to an array', () => {
        const printingTypeArray: IPrintingTypeSig[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const printingTypeCollection: IPrintingTypeSig[] = [sampleWithRequiredData];
        expectedResult = service.addPrintingTypeSigToCollectionIfMissing(printingTypeCollection, ...printingTypeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const printingType: IPrintingTypeSig = sampleWithRequiredData;
        const printingType2: IPrintingTypeSig = sampleWithPartialData;
        expectedResult = service.addPrintingTypeSigToCollectionIfMissing([], printingType, printingType2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(printingType);
        expect(expectedResult).toContain(printingType2);
      });

      it('should accept null and undefined values', () => {
        const printingType: IPrintingTypeSig = sampleWithRequiredData;
        expectedResult = service.addPrintingTypeSigToCollectionIfMissing([], null, printingType, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(printingType);
      });

      it('should return initial array if no PrintingTypeSig is added', () => {
        const printingTypeCollection: IPrintingTypeSig[] = [sampleWithRequiredData];
        expectedResult = service.addPrintingTypeSigToCollectionIfMissing(printingTypeCollection, undefined, null);
        expect(expectedResult).toEqual(printingTypeCollection);
      });
    });

    describe('comparePrintingTypeSig', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePrintingTypeSig(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { printingTypeId: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePrintingTypeSig(entity1, entity2);
        const compareResult2 = service.comparePrintingTypeSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { printingTypeId: 123 };
        const entity2 = { printingTypeId: 456 };

        const compareResult1 = service.comparePrintingTypeSig(entity1, entity2);
        const compareResult2 = service.comparePrintingTypeSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { printingTypeId: 123 };
        const entity2 = { printingTypeId: 123 };

        const compareResult1 = service.comparePrintingTypeSig(entity1, entity2);
        const compareResult2 = service.comparePrintingTypeSig(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
