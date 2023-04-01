import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IEventFormSig } from '../event-form-sig.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../event-form-sig.test-samples';

import { EventFormSigService } from './event-form-sig.service';

const requireRestSample: IEventFormSig = {
  ...sampleWithRequiredData,
};

describe('EventFormSig Service', () => {
  let service: EventFormSigService;
  let httpMock: HttpTestingController;
  let expectedResult: IEventFormSig | IEventFormSig[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(EventFormSigService);
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

    it('should create a EventFormSig', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const eventForm = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(eventForm).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a EventFormSig', () => {
      const eventForm = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(eventForm).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a EventFormSig', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of EventFormSig', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a EventFormSig', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addEventFormSigToCollectionIfMissing', () => {
      it('should add a EventFormSig to an empty array', () => {
        const eventForm: IEventFormSig = sampleWithRequiredData;
        expectedResult = service.addEventFormSigToCollectionIfMissing([], eventForm);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(eventForm);
      });

      it('should not add a EventFormSig to an array that contains it', () => {
        const eventForm: IEventFormSig = sampleWithRequiredData;
        const eventFormCollection: IEventFormSig[] = [
          {
            ...eventForm,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addEventFormSigToCollectionIfMissing(eventFormCollection, eventForm);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a EventFormSig to an array that doesn't contain it", () => {
        const eventForm: IEventFormSig = sampleWithRequiredData;
        const eventFormCollection: IEventFormSig[] = [sampleWithPartialData];
        expectedResult = service.addEventFormSigToCollectionIfMissing(eventFormCollection, eventForm);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(eventForm);
      });

      it('should add only unique EventFormSig to an array', () => {
        const eventFormArray: IEventFormSig[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const eventFormCollection: IEventFormSig[] = [sampleWithRequiredData];
        expectedResult = service.addEventFormSigToCollectionIfMissing(eventFormCollection, ...eventFormArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const eventForm: IEventFormSig = sampleWithRequiredData;
        const eventForm2: IEventFormSig = sampleWithPartialData;
        expectedResult = service.addEventFormSigToCollectionIfMissing([], eventForm, eventForm2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(eventForm);
        expect(expectedResult).toContain(eventForm2);
      });

      it('should accept null and undefined values', () => {
        const eventForm: IEventFormSig = sampleWithRequiredData;
        expectedResult = service.addEventFormSigToCollectionIfMissing([], null, eventForm, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(eventForm);
      });

      it('should return initial array if no EventFormSig is added', () => {
        const eventFormCollection: IEventFormSig[] = [sampleWithRequiredData];
        expectedResult = service.addEventFormSigToCollectionIfMissing(eventFormCollection, undefined, null);
        expect(expectedResult).toEqual(eventFormCollection);
      });
    });

    describe('compareEventFormSig', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareEventFormSig(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { formId: 123 };
        const entity2 = null;

        const compareResult1 = service.compareEventFormSig(entity1, entity2);
        const compareResult2 = service.compareEventFormSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { formId: 123 };
        const entity2 = { formId: 456 };

        const compareResult1 = service.compareEventFormSig(entity1, entity2);
        const compareResult2 = service.compareEventFormSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { formId: 123 };
        const entity2 = { formId: 123 };

        const compareResult1 = service.compareEventFormSig(entity1, entity2);
        const compareResult2 = service.compareEventFormSig(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
