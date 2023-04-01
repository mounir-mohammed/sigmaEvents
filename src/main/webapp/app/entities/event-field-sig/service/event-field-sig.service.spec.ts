import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IEventFieldSig } from '../event-field-sig.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../event-field-sig.test-samples';

import { EventFieldSigService } from './event-field-sig.service';

const requireRestSample: IEventFieldSig = {
  ...sampleWithRequiredData,
};

describe('EventFieldSig Service', () => {
  let service: EventFieldSigService;
  let httpMock: HttpTestingController;
  let expectedResult: IEventFieldSig | IEventFieldSig[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(EventFieldSigService);
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

    it('should create a EventFieldSig', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const eventField = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(eventField).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a EventFieldSig', () => {
      const eventField = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(eventField).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a EventFieldSig', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of EventFieldSig', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a EventFieldSig', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addEventFieldSigToCollectionIfMissing', () => {
      it('should add a EventFieldSig to an empty array', () => {
        const eventField: IEventFieldSig = sampleWithRequiredData;
        expectedResult = service.addEventFieldSigToCollectionIfMissing([], eventField);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(eventField);
      });

      it('should not add a EventFieldSig to an array that contains it', () => {
        const eventField: IEventFieldSig = sampleWithRequiredData;
        const eventFieldCollection: IEventFieldSig[] = [
          {
            ...eventField,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addEventFieldSigToCollectionIfMissing(eventFieldCollection, eventField);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a EventFieldSig to an array that doesn't contain it", () => {
        const eventField: IEventFieldSig = sampleWithRequiredData;
        const eventFieldCollection: IEventFieldSig[] = [sampleWithPartialData];
        expectedResult = service.addEventFieldSigToCollectionIfMissing(eventFieldCollection, eventField);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(eventField);
      });

      it('should add only unique EventFieldSig to an array', () => {
        const eventFieldArray: IEventFieldSig[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const eventFieldCollection: IEventFieldSig[] = [sampleWithRequiredData];
        expectedResult = service.addEventFieldSigToCollectionIfMissing(eventFieldCollection, ...eventFieldArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const eventField: IEventFieldSig = sampleWithRequiredData;
        const eventField2: IEventFieldSig = sampleWithPartialData;
        expectedResult = service.addEventFieldSigToCollectionIfMissing([], eventField, eventField2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(eventField);
        expect(expectedResult).toContain(eventField2);
      });

      it('should accept null and undefined values', () => {
        const eventField: IEventFieldSig = sampleWithRequiredData;
        expectedResult = service.addEventFieldSigToCollectionIfMissing([], null, eventField, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(eventField);
      });

      it('should return initial array if no EventFieldSig is added', () => {
        const eventFieldCollection: IEventFieldSig[] = [sampleWithRequiredData];
        expectedResult = service.addEventFieldSigToCollectionIfMissing(eventFieldCollection, undefined, null);
        expect(expectedResult).toEqual(eventFieldCollection);
      });
    });

    describe('compareEventFieldSig', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareEventFieldSig(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { fieldId: 123 };
        const entity2 = null;

        const compareResult1 = service.compareEventFieldSig(entity1, entity2);
        const compareResult2 = service.compareEventFieldSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { fieldId: 123 };
        const entity2 = { fieldId: 456 };

        const compareResult1 = service.compareEventFieldSig(entity1, entity2);
        const compareResult2 = service.compareEventFieldSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { fieldId: 123 };
        const entity2 = { fieldId: 123 };

        const compareResult1 = service.compareEventFieldSig(entity1, entity2);
        const compareResult2 = service.compareEventFieldSig(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
