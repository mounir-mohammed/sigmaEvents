import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAttachementSig } from '../attachement-sig.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../attachement-sig.test-samples';

import { AttachementSigService } from './attachement-sig.service';

const requireRestSample: IAttachementSig = {
  ...sampleWithRequiredData,
};

describe('AttachementSig Service', () => {
  let service: AttachementSigService;
  let httpMock: HttpTestingController;
  let expectedResult: IAttachementSig | IAttachementSig[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AttachementSigService);
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

    it('should create a AttachementSig', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const attachement = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(attachement).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AttachementSig', () => {
      const attachement = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(attachement).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AttachementSig', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AttachementSig', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AttachementSig', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAttachementSigToCollectionIfMissing', () => {
      it('should add a AttachementSig to an empty array', () => {
        const attachement: IAttachementSig = sampleWithRequiredData;
        expectedResult = service.addAttachementSigToCollectionIfMissing([], attachement);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(attachement);
      });

      it('should not add a AttachementSig to an array that contains it', () => {
        const attachement: IAttachementSig = sampleWithRequiredData;
        const attachementCollection: IAttachementSig[] = [
          {
            ...attachement,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAttachementSigToCollectionIfMissing(attachementCollection, attachement);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AttachementSig to an array that doesn't contain it", () => {
        const attachement: IAttachementSig = sampleWithRequiredData;
        const attachementCollection: IAttachementSig[] = [sampleWithPartialData];
        expectedResult = service.addAttachementSigToCollectionIfMissing(attachementCollection, attachement);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(attachement);
      });

      it('should add only unique AttachementSig to an array', () => {
        const attachementArray: IAttachementSig[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const attachementCollection: IAttachementSig[] = [sampleWithRequiredData];
        expectedResult = service.addAttachementSigToCollectionIfMissing(attachementCollection, ...attachementArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const attachement: IAttachementSig = sampleWithRequiredData;
        const attachement2: IAttachementSig = sampleWithPartialData;
        expectedResult = service.addAttachementSigToCollectionIfMissing([], attachement, attachement2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(attachement);
        expect(expectedResult).toContain(attachement2);
      });

      it('should accept null and undefined values', () => {
        const attachement: IAttachementSig = sampleWithRequiredData;
        expectedResult = service.addAttachementSigToCollectionIfMissing([], null, attachement, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(attachement);
      });

      it('should return initial array if no AttachementSig is added', () => {
        const attachementCollection: IAttachementSig[] = [sampleWithRequiredData];
        expectedResult = service.addAttachementSigToCollectionIfMissing(attachementCollection, undefined, null);
        expect(expectedResult).toEqual(attachementCollection);
      });
    });

    describe('compareAttachementSig', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAttachementSig(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { attachementId: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAttachementSig(entity1, entity2);
        const compareResult2 = service.compareAttachementSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { attachementId: 123 };
        const entity2 = { attachementId: 456 };

        const compareResult1 = service.compareAttachementSig(entity1, entity2);
        const compareResult2 = service.compareAttachementSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { attachementId: 123 };
        const entity2 = { attachementId: 123 };

        const compareResult1 = service.compareAttachementSig(entity1, entity2);
        const compareResult2 = service.compareAttachementSig(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
