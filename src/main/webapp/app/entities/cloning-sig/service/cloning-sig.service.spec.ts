import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICloningSig } from '../cloning-sig.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../cloning-sig.test-samples';

import { CloningSigService, RestCloningSig } from './cloning-sig.service';

const requireRestSample: RestCloningSig = {
  ...sampleWithRequiredData,
  cloningDate: sampleWithRequiredData.cloningDate?.toJSON(),
};

describe('CloningSig Service', () => {
  let service: CloningSigService;
  let httpMock: HttpTestingController;
  let expectedResult: ICloningSig | ICloningSig[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CloningSigService);
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

    it('should create a CloningSig', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const cloning = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(cloning).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CloningSig', () => {
      const cloning = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(cloning).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CloningSig', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CloningSig', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CloningSig', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCloningSigToCollectionIfMissing', () => {
      it('should add a CloningSig to an empty array', () => {
        const cloning: ICloningSig = sampleWithRequiredData;
        expectedResult = service.addCloningSigToCollectionIfMissing([], cloning);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cloning);
      });

      it('should not add a CloningSig to an array that contains it', () => {
        const cloning: ICloningSig = sampleWithRequiredData;
        const cloningCollection: ICloningSig[] = [
          {
            ...cloning,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCloningSigToCollectionIfMissing(cloningCollection, cloning);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CloningSig to an array that doesn't contain it", () => {
        const cloning: ICloningSig = sampleWithRequiredData;
        const cloningCollection: ICloningSig[] = [sampleWithPartialData];
        expectedResult = service.addCloningSigToCollectionIfMissing(cloningCollection, cloning);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cloning);
      });

      it('should add only unique CloningSig to an array', () => {
        const cloningArray: ICloningSig[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const cloningCollection: ICloningSig[] = [sampleWithRequiredData];
        expectedResult = service.addCloningSigToCollectionIfMissing(cloningCollection, ...cloningArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const cloning: ICloningSig = sampleWithRequiredData;
        const cloning2: ICloningSig = sampleWithPartialData;
        expectedResult = service.addCloningSigToCollectionIfMissing([], cloning, cloning2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cloning);
        expect(expectedResult).toContain(cloning2);
      });

      it('should accept null and undefined values', () => {
        const cloning: ICloningSig = sampleWithRequiredData;
        expectedResult = service.addCloningSigToCollectionIfMissing([], null, cloning, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cloning);
      });

      it('should return initial array if no CloningSig is added', () => {
        const cloningCollection: ICloningSig[] = [sampleWithRequiredData];
        expectedResult = service.addCloningSigToCollectionIfMissing(cloningCollection, undefined, null);
        expect(expectedResult).toEqual(cloningCollection);
      });
    });

    describe('compareCloningSig', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCloningSig(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { cloningId: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCloningSig(entity1, entity2);
        const compareResult2 = service.compareCloningSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { cloningId: 123 };
        const entity2 = { cloningId: 456 };

        const compareResult1 = service.compareCloningSig(entity1, entity2);
        const compareResult2 = service.compareCloningSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { cloningId: 123 };
        const entity2 = { cloningId: 123 };

        const compareResult1 = service.compareCloningSig(entity1, entity2);
        const compareResult2 = service.compareCloningSig(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
