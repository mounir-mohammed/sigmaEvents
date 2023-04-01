import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { INoteSig } from '../note-sig.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../note-sig.test-samples';

import { NoteSigService } from './note-sig.service';

const requireRestSample: INoteSig = {
  ...sampleWithRequiredData,
};

describe('NoteSig Service', () => {
  let service: NoteSigService;
  let httpMock: HttpTestingController;
  let expectedResult: INoteSig | INoteSig[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(NoteSigService);
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

    it('should create a NoteSig', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const note = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(note).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a NoteSig', () => {
      const note = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(note).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a NoteSig', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of NoteSig', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a NoteSig', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addNoteSigToCollectionIfMissing', () => {
      it('should add a NoteSig to an empty array', () => {
        const note: INoteSig = sampleWithRequiredData;
        expectedResult = service.addNoteSigToCollectionIfMissing([], note);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(note);
      });

      it('should not add a NoteSig to an array that contains it', () => {
        const note: INoteSig = sampleWithRequiredData;
        const noteCollection: INoteSig[] = [
          {
            ...note,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addNoteSigToCollectionIfMissing(noteCollection, note);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a NoteSig to an array that doesn't contain it", () => {
        const note: INoteSig = sampleWithRequiredData;
        const noteCollection: INoteSig[] = [sampleWithPartialData];
        expectedResult = service.addNoteSigToCollectionIfMissing(noteCollection, note);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(note);
      });

      it('should add only unique NoteSig to an array', () => {
        const noteArray: INoteSig[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const noteCollection: INoteSig[] = [sampleWithRequiredData];
        expectedResult = service.addNoteSigToCollectionIfMissing(noteCollection, ...noteArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const note: INoteSig = sampleWithRequiredData;
        const note2: INoteSig = sampleWithPartialData;
        expectedResult = service.addNoteSigToCollectionIfMissing([], note, note2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(note);
        expect(expectedResult).toContain(note2);
      });

      it('should accept null and undefined values', () => {
        const note: INoteSig = sampleWithRequiredData;
        expectedResult = service.addNoteSigToCollectionIfMissing([], null, note, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(note);
      });

      it('should return initial array if no NoteSig is added', () => {
        const noteCollection: INoteSig[] = [sampleWithRequiredData];
        expectedResult = service.addNoteSigToCollectionIfMissing(noteCollection, undefined, null);
        expect(expectedResult).toEqual(noteCollection);
      });
    });

    describe('compareNoteSig', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareNoteSig(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { noteId: 123 };
        const entity2 = null;

        const compareResult1 = service.compareNoteSig(entity1, entity2);
        const compareResult2 = service.compareNoteSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { noteId: 123 };
        const entity2 = { noteId: 456 };

        const compareResult1 = service.compareNoteSig(entity1, entity2);
        const compareResult2 = service.compareNoteSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { noteId: 123 };
        const entity2 = { noteId: 123 };

        const compareResult1 = service.compareNoteSig(entity1, entity2);
        const compareResult2 = service.compareNoteSig(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
