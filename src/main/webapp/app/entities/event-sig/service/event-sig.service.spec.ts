import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IEventSig } from '../event-sig.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../event-sig.test-samples';

import { EventSigService, RestEventSig } from './event-sig.service';

const requireRestSample: RestEventSig = {
  ...sampleWithRequiredData,
  eventdateStart: sampleWithRequiredData.eventdateStart?.toJSON(),
  eventdateEnd: sampleWithRequiredData.eventdateEnd?.toJSON(),
};

describe('EventSig Service', () => {
  let service: EventSigService;
  let httpMock: HttpTestingController;
  let expectedResult: IEventSig | IEventSig[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(EventSigService);
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

    it('should create a EventSig', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const event = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(event).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a EventSig', () => {
      const event = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(event).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a EventSig', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of EventSig', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a EventSig', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addEventSigToCollectionIfMissing', () => {
      it('should add a EventSig to an empty array', () => {
        const event: IEventSig = sampleWithRequiredData;
        expectedResult = service.addEventSigToCollectionIfMissing([], event);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(event);
      });

      it('should not add a EventSig to an array that contains it', () => {
        const event: IEventSig = sampleWithRequiredData;
        const eventCollection: IEventSig[] = [
          {
            ...event,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addEventSigToCollectionIfMissing(eventCollection, event);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a EventSig to an array that doesn't contain it", () => {
        const event: IEventSig = sampleWithRequiredData;
        const eventCollection: IEventSig[] = [sampleWithPartialData];
        expectedResult = service.addEventSigToCollectionIfMissing(eventCollection, event);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(event);
      });

      it('should add only unique EventSig to an array', () => {
        const eventArray: IEventSig[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const eventCollection: IEventSig[] = [sampleWithRequiredData];
        expectedResult = service.addEventSigToCollectionIfMissing(eventCollection, ...eventArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const event: IEventSig = sampleWithRequiredData;
        const event2: IEventSig = sampleWithPartialData;
        expectedResult = service.addEventSigToCollectionIfMissing([], event, event2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(event);
        expect(expectedResult).toContain(event2);
      });

      it('should accept null and undefined values', () => {
        const event: IEventSig = sampleWithRequiredData;
        expectedResult = service.addEventSigToCollectionIfMissing([], null, event, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(event);
      });

      it('should return initial array if no EventSig is added', () => {
        const eventCollection: IEventSig[] = [sampleWithRequiredData];
        expectedResult = service.addEventSigToCollectionIfMissing(eventCollection, undefined, null);
        expect(expectedResult).toEqual(eventCollection);
      });
    });

    describe('compareEventSig', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareEventSig(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { eventId: 123 };
        const entity2 = null;

        const compareResult1 = service.compareEventSig(entity1, entity2);
        const compareResult2 = service.compareEventSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { eventId: 123 };
        const entity2 = { eventId: 456 };

        const compareResult1 = service.compareEventSig(entity1, entity2);
        const compareResult2 = service.compareEventSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { eventId: 123 };
        const entity2 = { eventId: 123 };

        const compareResult1 = service.compareEventSig(entity1, entity2);
        const compareResult2 = service.compareEventSig(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
