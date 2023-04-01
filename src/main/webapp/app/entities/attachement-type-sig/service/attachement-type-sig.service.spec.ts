import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAttachementTypeSig } from '../attachement-type-sig.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../attachement-type-sig.test-samples';

import { AttachementTypeSigService } from './attachement-type-sig.service';

const requireRestSample: IAttachementTypeSig = {
  ...sampleWithRequiredData,
};

describe('AttachementTypeSig Service', () => {
  let service: AttachementTypeSigService;
  let httpMock: HttpTestingController;
  let expectedResult: IAttachementTypeSig | IAttachementTypeSig[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AttachementTypeSigService);
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

    it('should create a AttachementTypeSig', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const attachementType = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(attachementType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AttachementTypeSig', () => {
      const attachementType = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(attachementType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AttachementTypeSig', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AttachementTypeSig', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AttachementTypeSig', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAttachementTypeSigToCollectionIfMissing', () => {
      it('should add a AttachementTypeSig to an empty array', () => {
        const attachementType: IAttachementTypeSig = sampleWithRequiredData;
        expectedResult = service.addAttachementTypeSigToCollectionIfMissing([], attachementType);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(attachementType);
      });

      it('should not add a AttachementTypeSig to an array that contains it', () => {
        const attachementType: IAttachementTypeSig = sampleWithRequiredData;
        const attachementTypeCollection: IAttachementTypeSig[] = [
          {
            ...attachementType,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAttachementTypeSigToCollectionIfMissing(attachementTypeCollection, attachementType);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AttachementTypeSig to an array that doesn't contain it", () => {
        const attachementType: IAttachementTypeSig = sampleWithRequiredData;
        const attachementTypeCollection: IAttachementTypeSig[] = [sampleWithPartialData];
        expectedResult = service.addAttachementTypeSigToCollectionIfMissing(attachementTypeCollection, attachementType);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(attachementType);
      });

      it('should add only unique AttachementTypeSig to an array', () => {
        const attachementTypeArray: IAttachementTypeSig[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const attachementTypeCollection: IAttachementTypeSig[] = [sampleWithRequiredData];
        expectedResult = service.addAttachementTypeSigToCollectionIfMissing(attachementTypeCollection, ...attachementTypeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const attachementType: IAttachementTypeSig = sampleWithRequiredData;
        const attachementType2: IAttachementTypeSig = sampleWithPartialData;
        expectedResult = service.addAttachementTypeSigToCollectionIfMissing([], attachementType, attachementType2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(attachementType);
        expect(expectedResult).toContain(attachementType2);
      });

      it('should accept null and undefined values', () => {
        const attachementType: IAttachementTypeSig = sampleWithRequiredData;
        expectedResult = service.addAttachementTypeSigToCollectionIfMissing([], null, attachementType, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(attachementType);
      });

      it('should return initial array if no AttachementTypeSig is added', () => {
        const attachementTypeCollection: IAttachementTypeSig[] = [sampleWithRequiredData];
        expectedResult = service.addAttachementTypeSigToCollectionIfMissing(attachementTypeCollection, undefined, null);
        expect(expectedResult).toEqual(attachementTypeCollection);
      });
    });

    describe('compareAttachementTypeSig', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAttachementTypeSig(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { attachementTypeId: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAttachementTypeSig(entity1, entity2);
        const compareResult2 = service.compareAttachementTypeSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { attachementTypeId: 123 };
        const entity2 = { attachementTypeId: 456 };

        const compareResult1 = service.compareAttachementTypeSig(entity1, entity2);
        const compareResult2 = service.compareAttachementTypeSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { attachementTypeId: 123 };
        const entity2 = { attachementTypeId: 123 };

        const compareResult1 = service.compareAttachementTypeSig(entity1, entity2);
        const compareResult2 = service.compareAttachementTypeSig(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
