import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICheckAccreditationReportSig } from '../check-accreditation-report-sig.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../check-accreditation-report-sig.test-samples';

import { CheckAccreditationReportSigService } from './check-accreditation-report-sig.service';

const requireRestSample: ICheckAccreditationReportSig = {
  ...sampleWithRequiredData,
};

describe('CheckAccreditationReportSig Service', () => {
  let service: CheckAccreditationReportSigService;
  let httpMock: HttpTestingController;
  let expectedResult: ICheckAccreditationReportSig | ICheckAccreditationReportSig[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CheckAccreditationReportSigService);
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

    it('should create a CheckAccreditationReportSig', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const checkAccreditationReport = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(checkAccreditationReport).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CheckAccreditationReportSig', () => {
      const checkAccreditationReport = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(checkAccreditationReport).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CheckAccreditationReportSig', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CheckAccreditationReportSig', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CheckAccreditationReportSig', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCheckAccreditationReportSigToCollectionIfMissing', () => {
      it('should add a CheckAccreditationReportSig to an empty array', () => {
        const checkAccreditationReport: ICheckAccreditationReportSig = sampleWithRequiredData;
        expectedResult = service.addCheckAccreditationReportSigToCollectionIfMissing([], checkAccreditationReport);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(checkAccreditationReport);
      });

      it('should not add a CheckAccreditationReportSig to an array that contains it', () => {
        const checkAccreditationReport: ICheckAccreditationReportSig = sampleWithRequiredData;
        const checkAccreditationReportCollection: ICheckAccreditationReportSig[] = [
          {
            ...checkAccreditationReport,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCheckAccreditationReportSigToCollectionIfMissing(
          checkAccreditationReportCollection,
          checkAccreditationReport
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CheckAccreditationReportSig to an array that doesn't contain it", () => {
        const checkAccreditationReport: ICheckAccreditationReportSig = sampleWithRequiredData;
        const checkAccreditationReportCollection: ICheckAccreditationReportSig[] = [sampleWithPartialData];
        expectedResult = service.addCheckAccreditationReportSigToCollectionIfMissing(
          checkAccreditationReportCollection,
          checkAccreditationReport
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(checkAccreditationReport);
      });

      it('should add only unique CheckAccreditationReportSig to an array', () => {
        const checkAccreditationReportArray: ICheckAccreditationReportSig[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const checkAccreditationReportCollection: ICheckAccreditationReportSig[] = [sampleWithRequiredData];
        expectedResult = service.addCheckAccreditationReportSigToCollectionIfMissing(
          checkAccreditationReportCollection,
          ...checkAccreditationReportArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const checkAccreditationReport: ICheckAccreditationReportSig = sampleWithRequiredData;
        const checkAccreditationReport2: ICheckAccreditationReportSig = sampleWithPartialData;
        expectedResult = service.addCheckAccreditationReportSigToCollectionIfMissing(
          [],
          checkAccreditationReport,
          checkAccreditationReport2
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(checkAccreditationReport);
        expect(expectedResult).toContain(checkAccreditationReport2);
      });

      it('should accept null and undefined values', () => {
        const checkAccreditationReport: ICheckAccreditationReportSig = sampleWithRequiredData;
        expectedResult = service.addCheckAccreditationReportSigToCollectionIfMissing([], null, checkAccreditationReport, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(checkAccreditationReport);
      });

      it('should return initial array if no CheckAccreditationReportSig is added', () => {
        const checkAccreditationReportCollection: ICheckAccreditationReportSig[] = [sampleWithRequiredData];
        expectedResult = service.addCheckAccreditationReportSigToCollectionIfMissing(checkAccreditationReportCollection, undefined, null);
        expect(expectedResult).toEqual(checkAccreditationReportCollection);
      });
    });

    describe('compareCheckAccreditationReportSig', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCheckAccreditationReportSig(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { checkAccreditationReportId: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCheckAccreditationReportSig(entity1, entity2);
        const compareResult2 = service.compareCheckAccreditationReportSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { checkAccreditationReportId: 123 };
        const entity2 = { checkAccreditationReportId: 456 };

        const compareResult1 = service.compareCheckAccreditationReportSig(entity1, entity2);
        const compareResult2 = service.compareCheckAccreditationReportSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { checkAccreditationReportId: 123 };
        const entity2 = { checkAccreditationReportId: 123 };

        const compareResult1 = service.compareCheckAccreditationReportSig(entity1, entity2);
        const compareResult2 = service.compareCheckAccreditationReportSig(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
