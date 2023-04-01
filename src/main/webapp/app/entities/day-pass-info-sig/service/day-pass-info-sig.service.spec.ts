import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDayPassInfoSig } from '../day-pass-info-sig.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../day-pass-info-sig.test-samples';

import { DayPassInfoSigService, RestDayPassInfoSig } from './day-pass-info-sig.service';

const requireRestSample: RestDayPassInfoSig = {
  ...sampleWithRequiredData,
  dayPassInfoCreationDate: sampleWithRequiredData.dayPassInfoCreationDate?.toJSON(),
  dayPassInfoUpdateDate: sampleWithRequiredData.dayPassInfoUpdateDate?.toJSON(),
  dayPassInfoDateStart: sampleWithRequiredData.dayPassInfoDateStart?.toJSON(),
  dayPassInfoDateEnd: sampleWithRequiredData.dayPassInfoDateEnd?.toJSON(),
};

describe('DayPassInfoSig Service', () => {
  let service: DayPassInfoSigService;
  let httpMock: HttpTestingController;
  let expectedResult: IDayPassInfoSig | IDayPassInfoSig[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DayPassInfoSigService);
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

    it('should create a DayPassInfoSig', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const dayPassInfo = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(dayPassInfo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DayPassInfoSig', () => {
      const dayPassInfo = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(dayPassInfo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DayPassInfoSig', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DayPassInfoSig', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a DayPassInfoSig', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDayPassInfoSigToCollectionIfMissing', () => {
      it('should add a DayPassInfoSig to an empty array', () => {
        const dayPassInfo: IDayPassInfoSig = sampleWithRequiredData;
        expectedResult = service.addDayPassInfoSigToCollectionIfMissing([], dayPassInfo);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dayPassInfo);
      });

      it('should not add a DayPassInfoSig to an array that contains it', () => {
        const dayPassInfo: IDayPassInfoSig = sampleWithRequiredData;
        const dayPassInfoCollection: IDayPassInfoSig[] = [
          {
            ...dayPassInfo,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDayPassInfoSigToCollectionIfMissing(dayPassInfoCollection, dayPassInfo);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DayPassInfoSig to an array that doesn't contain it", () => {
        const dayPassInfo: IDayPassInfoSig = sampleWithRequiredData;
        const dayPassInfoCollection: IDayPassInfoSig[] = [sampleWithPartialData];
        expectedResult = service.addDayPassInfoSigToCollectionIfMissing(dayPassInfoCollection, dayPassInfo);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dayPassInfo);
      });

      it('should add only unique DayPassInfoSig to an array', () => {
        const dayPassInfoArray: IDayPassInfoSig[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const dayPassInfoCollection: IDayPassInfoSig[] = [sampleWithRequiredData];
        expectedResult = service.addDayPassInfoSigToCollectionIfMissing(dayPassInfoCollection, ...dayPassInfoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const dayPassInfo: IDayPassInfoSig = sampleWithRequiredData;
        const dayPassInfo2: IDayPassInfoSig = sampleWithPartialData;
        expectedResult = service.addDayPassInfoSigToCollectionIfMissing([], dayPassInfo, dayPassInfo2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dayPassInfo);
        expect(expectedResult).toContain(dayPassInfo2);
      });

      it('should accept null and undefined values', () => {
        const dayPassInfo: IDayPassInfoSig = sampleWithRequiredData;
        expectedResult = service.addDayPassInfoSigToCollectionIfMissing([], null, dayPassInfo, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dayPassInfo);
      });

      it('should return initial array if no DayPassInfoSig is added', () => {
        const dayPassInfoCollection: IDayPassInfoSig[] = [sampleWithRequiredData];
        expectedResult = service.addDayPassInfoSigToCollectionIfMissing(dayPassInfoCollection, undefined, null);
        expect(expectedResult).toEqual(dayPassInfoCollection);
      });
    });

    describe('compareDayPassInfoSig', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDayPassInfoSig(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { dayPassInfoId: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDayPassInfoSig(entity1, entity2);
        const compareResult2 = service.compareDayPassInfoSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { dayPassInfoId: 123 };
        const entity2 = { dayPassInfoId: 456 };

        const compareResult1 = service.compareDayPassInfoSig(entity1, entity2);
        const compareResult2 = service.compareDayPassInfoSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { dayPassInfoId: 123 };
        const entity2 = { dayPassInfoId: 123 };

        const compareResult1 = service.compareDayPassInfoSig(entity1, entity2);
        const compareResult2 = service.compareDayPassInfoSig(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
