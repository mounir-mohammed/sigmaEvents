import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISiteSig } from '../site-sig.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../site-sig.test-samples';

import { SiteSigService } from './site-sig.service';

const requireRestSample: ISiteSig = {
  ...sampleWithRequiredData,
};

describe('SiteSig Service', () => {
  let service: SiteSigService;
  let httpMock: HttpTestingController;
  let expectedResult: ISiteSig | ISiteSig[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SiteSigService);
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

    it('should create a SiteSig', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const site = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(site).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SiteSig', () => {
      const site = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(site).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SiteSig', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SiteSig', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a SiteSig', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSiteSigToCollectionIfMissing', () => {
      it('should add a SiteSig to an empty array', () => {
        const site: ISiteSig = sampleWithRequiredData;
        expectedResult = service.addSiteSigToCollectionIfMissing([], site);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(site);
      });

      it('should not add a SiteSig to an array that contains it', () => {
        const site: ISiteSig = sampleWithRequiredData;
        const siteCollection: ISiteSig[] = [
          {
            ...site,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSiteSigToCollectionIfMissing(siteCollection, site);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SiteSig to an array that doesn't contain it", () => {
        const site: ISiteSig = sampleWithRequiredData;
        const siteCollection: ISiteSig[] = [sampleWithPartialData];
        expectedResult = service.addSiteSigToCollectionIfMissing(siteCollection, site);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(site);
      });

      it('should add only unique SiteSig to an array', () => {
        const siteArray: ISiteSig[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const siteCollection: ISiteSig[] = [sampleWithRequiredData];
        expectedResult = service.addSiteSigToCollectionIfMissing(siteCollection, ...siteArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const site: ISiteSig = sampleWithRequiredData;
        const site2: ISiteSig = sampleWithPartialData;
        expectedResult = service.addSiteSigToCollectionIfMissing([], site, site2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(site);
        expect(expectedResult).toContain(site2);
      });

      it('should accept null and undefined values', () => {
        const site: ISiteSig = sampleWithRequiredData;
        expectedResult = service.addSiteSigToCollectionIfMissing([], null, site, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(site);
      });

      it('should return initial array if no SiteSig is added', () => {
        const siteCollection: ISiteSig[] = [sampleWithRequiredData];
        expectedResult = service.addSiteSigToCollectionIfMissing(siteCollection, undefined, null);
        expect(expectedResult).toEqual(siteCollection);
      });
    });

    describe('compareSiteSig', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSiteSig(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { siteId: 123 };
        const entity2 = null;

        const compareResult1 = service.compareSiteSig(entity1, entity2);
        const compareResult2 = service.compareSiteSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { siteId: 123 };
        const entity2 = { siteId: 456 };

        const compareResult1 = service.compareSiteSig(entity1, entity2);
        const compareResult2 = service.compareSiteSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { siteId: 123 };
        const entity2 = { siteId: 123 };

        const compareResult1 = service.compareSiteSig(entity1, entity2);
        const compareResult2 = service.compareSiteSig(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
