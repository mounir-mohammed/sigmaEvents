import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IAccreditationTypeSig } from '../accreditation-type-sig.model';
import { AccreditationTypeSigService } from '../service/accreditation-type-sig.service';

import { AccreditationTypeSigRoutingResolveService } from './accreditation-type-sig-routing-resolve.service';

describe('AccreditationTypeSig routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: AccreditationTypeSigRoutingResolveService;
  let service: AccreditationTypeSigService;
  let resultAccreditationTypeSig: IAccreditationTypeSig | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(AccreditationTypeSigRoutingResolveService);
    service = TestBed.inject(AccreditationTypeSigService);
    resultAccreditationTypeSig = undefined;
  });

  describe('resolve', () => {
    it('should return IAccreditationTypeSig returned by find', () => {
      // GIVEN
      service.find = jest.fn(accreditationTypeId => of(new HttpResponse({ body: { accreditationTypeId } })));
      mockActivatedRouteSnapshot.params = { accreditationTypeId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAccreditationTypeSig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAccreditationTypeSig).toEqual({ accreditationTypeId: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAccreditationTypeSig = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultAccreditationTypeSig).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IAccreditationTypeSig>({ body: null })));
      mockActivatedRouteSnapshot.params = { accreditationTypeId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAccreditationTypeSig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAccreditationTypeSig).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
