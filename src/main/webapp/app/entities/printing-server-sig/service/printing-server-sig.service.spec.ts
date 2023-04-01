import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPrintingServerSig } from '../printing-server-sig.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../printing-server-sig.test-samples';

import { PrintingServerSigService } from './printing-server-sig.service';

const requireRestSample: IPrintingServerSig = {
  ...sampleWithRequiredData,
};

describe('PrintingServerSig Service', () => {
  let service: PrintingServerSigService;
  let httpMock: HttpTestingController;
  let expectedResult: IPrintingServerSig | IPrintingServerSig[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PrintingServerSigService);
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

    it('should create a PrintingServerSig', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const printingServer = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(printingServer).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PrintingServerSig', () => {
      const printingServer = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(printingServer).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PrintingServerSig', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PrintingServerSig', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PrintingServerSig', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPrintingServerSigToCollectionIfMissing', () => {
      it('should add a PrintingServerSig to an empty array', () => {
        const printingServer: IPrintingServerSig = sampleWithRequiredData;
        expectedResult = service.addPrintingServerSigToCollectionIfMissing([], printingServer);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(printingServer);
      });

      it('should not add a PrintingServerSig to an array that contains it', () => {
        const printingServer: IPrintingServerSig = sampleWithRequiredData;
        const printingServerCollection: IPrintingServerSig[] = [
          {
            ...printingServer,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPrintingServerSigToCollectionIfMissing(printingServerCollection, printingServer);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PrintingServerSig to an array that doesn't contain it", () => {
        const printingServer: IPrintingServerSig = sampleWithRequiredData;
        const printingServerCollection: IPrintingServerSig[] = [sampleWithPartialData];
        expectedResult = service.addPrintingServerSigToCollectionIfMissing(printingServerCollection, printingServer);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(printingServer);
      });

      it('should add only unique PrintingServerSig to an array', () => {
        const printingServerArray: IPrintingServerSig[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const printingServerCollection: IPrintingServerSig[] = [sampleWithRequiredData];
        expectedResult = service.addPrintingServerSigToCollectionIfMissing(printingServerCollection, ...printingServerArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const printingServer: IPrintingServerSig = sampleWithRequiredData;
        const printingServer2: IPrintingServerSig = sampleWithPartialData;
        expectedResult = service.addPrintingServerSigToCollectionIfMissing([], printingServer, printingServer2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(printingServer);
        expect(expectedResult).toContain(printingServer2);
      });

      it('should accept null and undefined values', () => {
        const printingServer: IPrintingServerSig = sampleWithRequiredData;
        expectedResult = service.addPrintingServerSigToCollectionIfMissing([], null, printingServer, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(printingServer);
      });

      it('should return initial array if no PrintingServerSig is added', () => {
        const printingServerCollection: IPrintingServerSig[] = [sampleWithRequiredData];
        expectedResult = service.addPrintingServerSigToCollectionIfMissing(printingServerCollection, undefined, null);
        expect(expectedResult).toEqual(printingServerCollection);
      });
    });

    describe('comparePrintingServerSig', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePrintingServerSig(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { printingServerId: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePrintingServerSig(entity1, entity2);
        const compareResult2 = service.comparePrintingServerSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { printingServerId: 123 };
        const entity2 = { printingServerId: 456 };

        const compareResult1 = service.comparePrintingServerSig(entity1, entity2);
        const compareResult2 = service.comparePrintingServerSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { printingServerId: 123 };
        const entity2 = { printingServerId: 123 };

        const compareResult1 = service.comparePrintingServerSig(entity1, entity2);
        const compareResult2 = service.comparePrintingServerSig(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
