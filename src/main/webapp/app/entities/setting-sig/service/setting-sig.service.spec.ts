import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISettingSig } from '../setting-sig.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../setting-sig.test-samples';

import { SettingSigService, RestSettingSig } from './setting-sig.service';

const requireRestSample: RestSettingSig = {
  ...sampleWithRequiredData,
  settingValueDate: sampleWithRequiredData.settingValueDate?.toJSON(),
};

describe('SettingSig Service', () => {
  let service: SettingSigService;
  let httpMock: HttpTestingController;
  let expectedResult: ISettingSig | ISettingSig[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SettingSigService);
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

    it('should create a SettingSig', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const setting = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(setting).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SettingSig', () => {
      const setting = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(setting).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SettingSig', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SettingSig', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a SettingSig', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSettingSigToCollectionIfMissing', () => {
      it('should add a SettingSig to an empty array', () => {
        const setting: ISettingSig = sampleWithRequiredData;
        expectedResult = service.addSettingSigToCollectionIfMissing([], setting);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(setting);
      });

      it('should not add a SettingSig to an array that contains it', () => {
        const setting: ISettingSig = sampleWithRequiredData;
        const settingCollection: ISettingSig[] = [
          {
            ...setting,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSettingSigToCollectionIfMissing(settingCollection, setting);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SettingSig to an array that doesn't contain it", () => {
        const setting: ISettingSig = sampleWithRequiredData;
        const settingCollection: ISettingSig[] = [sampleWithPartialData];
        expectedResult = service.addSettingSigToCollectionIfMissing(settingCollection, setting);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(setting);
      });

      it('should add only unique SettingSig to an array', () => {
        const settingArray: ISettingSig[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const settingCollection: ISettingSig[] = [sampleWithRequiredData];
        expectedResult = service.addSettingSigToCollectionIfMissing(settingCollection, ...settingArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const setting: ISettingSig = sampleWithRequiredData;
        const setting2: ISettingSig = sampleWithPartialData;
        expectedResult = service.addSettingSigToCollectionIfMissing([], setting, setting2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(setting);
        expect(expectedResult).toContain(setting2);
      });

      it('should accept null and undefined values', () => {
        const setting: ISettingSig = sampleWithRequiredData;
        expectedResult = service.addSettingSigToCollectionIfMissing([], null, setting, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(setting);
      });

      it('should return initial array if no SettingSig is added', () => {
        const settingCollection: ISettingSig[] = [sampleWithRequiredData];
        expectedResult = service.addSettingSigToCollectionIfMissing(settingCollection, undefined, null);
        expect(expectedResult).toEqual(settingCollection);
      });
    });

    describe('compareSettingSig', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSettingSig(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { settingId: 123 };
        const entity2 = null;

        const compareResult1 = service.compareSettingSig(entity1, entity2);
        const compareResult2 = service.compareSettingSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { settingId: 123 };
        const entity2 = { settingId: 456 };

        const compareResult1 = service.compareSettingSig(entity1, entity2);
        const compareResult2 = service.compareSettingSig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { settingId: 123 };
        const entity2 = { settingId: 123 };

        const compareResult1 = service.compareSettingSig(entity1, entity2);
        const compareResult2 = service.compareSettingSig(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
