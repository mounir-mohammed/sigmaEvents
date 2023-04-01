import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IOrganizSig } from '../organiz-sig.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../organiz-sig.test-samples';

import { OrganizSigService } from './organiz-sig.service';

const requireRestSample: IOrganizSig = {
  ...sampleWithRequiredData,
};

describe('OrganizSig Service', () => {
  let service: OrganizSigService;
  let httpMock: HttpTestingController;
  let expectedResult: IOrganizSig | IOrganizSig[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(OrganizSigService);
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

    it('should create a OrganizSig', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const organiz = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(organiz).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a OrganizSig', () => {
      const organiz = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(organiz).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a OrganizSig', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of OrganizSig', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a OrganizSig', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addOrganizSigToCollectionIfMissing', () => {
      it('should add a OrganizSig to an empty array', () => {
        const organiz: IOrganizSig = sampleWithRequiredData;
        expectedResult = service.addOrganizSigToCollectionIfMissing([], organiz);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(organiz);
      });

      it('should not add a OrganizSig to an array that contains it', () => {
        const organiz: IOrganizSig = sampleWithRequiredData;
        const organizCollection: IOrganizSig[] = [
          {
            ...organiz,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addOrganizSigToCollectionIfMissing(organizCollection, organiz);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a OrganizSig to an array that doesn't contain it", () => {
        const organiz: IOrganizSig = sampleWithRequiredData;
        const organizCollection: IOrganizSig[] = [sampleWithPartialData];
        expectedResult = service.addOrganizSigToCollectionIfMissing(organizCollection, organiz);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(organiz);
      });

      it('should add only unique OrganizSig to an array', () => {
        const organizArray: IOrganizSig[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const organizCollection: IOrganizSig[] = [sampleWithRequiredData];
        expectedResult = service.addOrganizSigToCollectionIfMissing(organizCollection, ...organizArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const organiz: IOrganizSig = sampleWithRequiredData;
        const organiz2: IOrganizSig = sampleWithPartialData;
        expectedResult = service.addOrganizSigToCollectionIfMissing([], organiz, organiz2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(organiz);
        expect(expectedResult).toContain(organiz2);
      });

      it('should accept null and undefined values', () => {
        const organiz: IOrganizSig = sampleWithRequiredData;
        expectedResult = service.addOrganizSigToCollectionIfMissing([], null, organiz, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(organiz);
      });

      it('should return initial array if no OrganizSig is added', () => {
        const organizCollection: IOrganizSig[] = [sampleWithRequiredData];
        expectedResult = service.addOrganizSigToCollectionIfMissing(organizCollection, undefined, null);
        expect(expectedResult).toEqual(organizCollection);
      });
    });

    describe('compareOrganizSig', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareOrganizSig(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { organizId: 123 };
        const entity2 = null;

        const compareResult1 = service.compareOrganizSig(entity1, entity2);
        const compareResult2 = service.compareOrganizSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { organizId: 123 };
        const entity2 = { organizId: 456 };

        const compareResult1 = service.compareOrganizSig(entity1, entity2);
        const compareResult2 = service.compareOrganizSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { organizId: 123 };
        const entity2 = { organizId: 123 };

        const compareResult1 = service.compareOrganizSig(entity1, entity2);
        const compareResult2 = service.compareOrganizSig(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
