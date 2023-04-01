import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPrintingCentreSig } from '../printing-centre-sig.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../printing-centre-sig.test-samples';

import { PrintingCentreSigService } from './printing-centre-sig.service';

const requireRestSample: IPrintingCentreSig = {
  ...sampleWithRequiredData,
};

describe('PrintingCentreSig Service', () => {
  let service: PrintingCentreSigService;
  let httpMock: HttpTestingController;
  let expectedResult: IPrintingCentreSig | IPrintingCentreSig[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PrintingCentreSigService);
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

    it('should create a PrintingCentreSig', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const printingCentre = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(printingCentre).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PrintingCentreSig', () => {
      const printingCentre = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(printingCentre).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PrintingCentreSig', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PrintingCentreSig', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PrintingCentreSig', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPrintingCentreSigToCollectionIfMissing', () => {
      it('should add a PrintingCentreSig to an empty array', () => {
        const printingCentre: IPrintingCentreSig = sampleWithRequiredData;
        expectedResult = service.addPrintingCentreSigToCollectionIfMissing([], printingCentre);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(printingCentre);
      });

      it('should not add a PrintingCentreSig to an array that contains it', () => {
        const printingCentre: IPrintingCentreSig = sampleWithRequiredData;
        const printingCentreCollection: IPrintingCentreSig[] = [
          {
            ...printingCentre,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPrintingCentreSigToCollectionIfMissing(printingCentreCollection, printingCentre);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PrintingCentreSig to an array that doesn't contain it", () => {
        const printingCentre: IPrintingCentreSig = sampleWithRequiredData;
        const printingCentreCollection: IPrintingCentreSig[] = [sampleWithPartialData];
        expectedResult = service.addPrintingCentreSigToCollectionIfMissing(printingCentreCollection, printingCentre);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(printingCentre);
      });

      it('should add only unique PrintingCentreSig to an array', () => {
        const printingCentreArray: IPrintingCentreSig[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const printingCentreCollection: IPrintingCentreSig[] = [sampleWithRequiredData];
        expectedResult = service.addPrintingCentreSigToCollectionIfMissing(printingCentreCollection, ...printingCentreArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const printingCentre: IPrintingCentreSig = sampleWithRequiredData;
        const printingCentre2: IPrintingCentreSig = sampleWithPartialData;
        expectedResult = service.addPrintingCentreSigToCollectionIfMissing([], printingCentre, printingCentre2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(printingCentre);
        expect(expectedResult).toContain(printingCentre2);
      });

      it('should accept null and undefined values', () => {
        const printingCentre: IPrintingCentreSig = sampleWithRequiredData;
        expectedResult = service.addPrintingCentreSigToCollectionIfMissing([], null, printingCentre, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(printingCentre);
      });

      it('should return initial array if no PrintingCentreSig is added', () => {
        const printingCentreCollection: IPrintingCentreSig[] = [sampleWithRequiredData];
        expectedResult = service.addPrintingCentreSigToCollectionIfMissing(printingCentreCollection, undefined, null);
        expect(expectedResult).toEqual(printingCentreCollection);
      });
    });

    describe('comparePrintingCentreSig', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePrintingCentreSig(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { printingCentreId: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePrintingCentreSig(entity1, entity2);
        const compareResult2 = service.comparePrintingCentreSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { printingCentreId: 123 };
        const entity2 = { printingCentreId: 456 };

        const compareResult1 = service.comparePrintingCentreSig(entity1, entity2);
        const compareResult2 = service.comparePrintingCentreSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { printingCentreId: 123 };
        const entity2 = { printingCentreId: 123 };

        const compareResult1 = service.comparePrintingCentreSig(entity1, entity2);
        const compareResult2 = service.comparePrintingCentreSig(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
