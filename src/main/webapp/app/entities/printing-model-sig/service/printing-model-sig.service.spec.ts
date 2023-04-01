import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPrintingModelSig } from '../printing-model-sig.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../printing-model-sig.test-samples';

import { PrintingModelSigService } from './printing-model-sig.service';

const requireRestSample: IPrintingModelSig = {
  ...sampleWithRequiredData,
};

describe('PrintingModelSig Service', () => {
  let service: PrintingModelSigService;
  let httpMock: HttpTestingController;
  let expectedResult: IPrintingModelSig | IPrintingModelSig[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PrintingModelSigService);
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

    it('should create a PrintingModelSig', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const printingModel = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(printingModel).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PrintingModelSig', () => {
      const printingModel = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(printingModel).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PrintingModelSig', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PrintingModelSig', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PrintingModelSig', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPrintingModelSigToCollectionIfMissing', () => {
      it('should add a PrintingModelSig to an empty array', () => {
        const printingModel: IPrintingModelSig = sampleWithRequiredData;
        expectedResult = service.addPrintingModelSigToCollectionIfMissing([], printingModel);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(printingModel);
      });

      it('should not add a PrintingModelSig to an array that contains it', () => {
        const printingModel: IPrintingModelSig = sampleWithRequiredData;
        const printingModelCollection: IPrintingModelSig[] = [
          {
            ...printingModel,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPrintingModelSigToCollectionIfMissing(printingModelCollection, printingModel);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PrintingModelSig to an array that doesn't contain it", () => {
        const printingModel: IPrintingModelSig = sampleWithRequiredData;
        const printingModelCollection: IPrintingModelSig[] = [sampleWithPartialData];
        expectedResult = service.addPrintingModelSigToCollectionIfMissing(printingModelCollection, printingModel);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(printingModel);
      });

      it('should add only unique PrintingModelSig to an array', () => {
        const printingModelArray: IPrintingModelSig[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const printingModelCollection: IPrintingModelSig[] = [sampleWithRequiredData];
        expectedResult = service.addPrintingModelSigToCollectionIfMissing(printingModelCollection, ...printingModelArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const printingModel: IPrintingModelSig = sampleWithRequiredData;
        const printingModel2: IPrintingModelSig = sampleWithPartialData;
        expectedResult = service.addPrintingModelSigToCollectionIfMissing([], printingModel, printingModel2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(printingModel);
        expect(expectedResult).toContain(printingModel2);
      });

      it('should accept null and undefined values', () => {
        const printingModel: IPrintingModelSig = sampleWithRequiredData;
        expectedResult = service.addPrintingModelSigToCollectionIfMissing([], null, printingModel, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(printingModel);
      });

      it('should return initial array if no PrintingModelSig is added', () => {
        const printingModelCollection: IPrintingModelSig[] = [sampleWithRequiredData];
        expectedResult = service.addPrintingModelSigToCollectionIfMissing(printingModelCollection, undefined, null);
        expect(expectedResult).toEqual(printingModelCollection);
      });
    });

    describe('comparePrintingModelSig', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePrintingModelSig(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { printingModelId: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePrintingModelSig(entity1, entity2);
        const compareResult2 = service.comparePrintingModelSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { printingModelId: 123 };
        const entity2 = { printingModelId: 456 };

        const compareResult1 = service.comparePrintingModelSig(entity1, entity2);
        const compareResult2 = service.comparePrintingModelSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { printingModelId: 123 };
        const entity2 = { printingModelId: 123 };

        const compareResult1 = service.comparePrintingModelSig(entity1, entity2);
        const compareResult2 = service.comparePrintingModelSig(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
