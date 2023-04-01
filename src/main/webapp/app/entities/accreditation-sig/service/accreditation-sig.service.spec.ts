import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IAccreditationSig } from '../accreditation-sig.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../accreditation-sig.test-samples';

import { AccreditationSigService, RestAccreditationSig } from './accreditation-sig.service';

const requireRestSample: RestAccreditationSig = {
  ...sampleWithRequiredData,
  accreditationBirthDay: sampleWithRequiredData.accreditationBirthDay?.format(DATE_FORMAT),
  accreditationCreationDate: sampleWithRequiredData.accreditationCreationDate?.toJSON(),
  accreditationUpdateDate: sampleWithRequiredData.accreditationUpdateDate?.toJSON(),
  accreditationDateStart: sampleWithRequiredData.accreditationDateStart?.toJSON(),
  accreditationDateEnd: sampleWithRequiredData.accreditationDateEnd?.toJSON(),
};

describe('AccreditationSig Service', () => {
  let service: AccreditationSigService;
  let httpMock: HttpTestingController;
  let expectedResult: IAccreditationSig | IAccreditationSig[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AccreditationSigService);
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

    it('should create a AccreditationSig', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const accreditation = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(accreditation).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AccreditationSig', () => {
      const accreditation = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(accreditation).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AccreditationSig', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AccreditationSig', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AccreditationSig', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAccreditationSigToCollectionIfMissing', () => {
      it('should add a AccreditationSig to an empty array', () => {
        const accreditation: IAccreditationSig = sampleWithRequiredData;
        expectedResult = service.addAccreditationSigToCollectionIfMissing([], accreditation);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(accreditation);
      });

      it('should not add a AccreditationSig to an array that contains it', () => {
        const accreditation: IAccreditationSig = sampleWithRequiredData;
        const accreditationCollection: IAccreditationSig[] = [
          {
            ...accreditation,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAccreditationSigToCollectionIfMissing(accreditationCollection, accreditation);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AccreditationSig to an array that doesn't contain it", () => {
        const accreditation: IAccreditationSig = sampleWithRequiredData;
        const accreditationCollection: IAccreditationSig[] = [sampleWithPartialData];
        expectedResult = service.addAccreditationSigToCollectionIfMissing(accreditationCollection, accreditation);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(accreditation);
      });

      it('should add only unique AccreditationSig to an array', () => {
        const accreditationArray: IAccreditationSig[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const accreditationCollection: IAccreditationSig[] = [sampleWithRequiredData];
        expectedResult = service.addAccreditationSigToCollectionIfMissing(accreditationCollection, ...accreditationArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const accreditation: IAccreditationSig = sampleWithRequiredData;
        const accreditation2: IAccreditationSig = sampleWithPartialData;
        expectedResult = service.addAccreditationSigToCollectionIfMissing([], accreditation, accreditation2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(accreditation);
        expect(expectedResult).toContain(accreditation2);
      });

      it('should accept null and undefined values', () => {
        const accreditation: IAccreditationSig = sampleWithRequiredData;
        expectedResult = service.addAccreditationSigToCollectionIfMissing([], null, accreditation, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(accreditation);
      });

      it('should return initial array if no AccreditationSig is added', () => {
        const accreditationCollection: IAccreditationSig[] = [sampleWithRequiredData];
        expectedResult = service.addAccreditationSigToCollectionIfMissing(accreditationCollection, undefined, null);
        expect(expectedResult).toEqual(accreditationCollection);
      });
    });

    describe('compareAccreditationSig', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAccreditationSig(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { accreditationId: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAccreditationSig(entity1, entity2);
        const compareResult2 = service.compareAccreditationSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { accreditationId: 123 };
        const entity2 = { accreditationId: 456 };

        const compareResult1 = service.compareAccreditationSig(entity1, entity2);
        const compareResult2 = service.compareAccreditationSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { accreditationId: 123 };
        const entity2 = { accreditationId: 123 };

        const compareResult1 = service.compareAccreditationSig(entity1, entity2);
        const compareResult2 = service.compareAccreditationSig(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
