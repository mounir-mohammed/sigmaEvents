import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { INationalitySig } from '../nationality-sig.model';
import { NationalitySigService } from '../service/nationality-sig.service';

import { NationalitySigRoutingResolveService } from './nationality-sig-routing-resolve.service';

describe('NationalitySig routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: NationalitySigRoutingResolveService;
  let service: NationalitySigService;
  let resultNationalitySig: INationalitySig | null | undefined;

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
    routingResolveService = TestBed.inject(NationalitySigRoutingResolveService);
    service = TestBed.inject(NationalitySigService);
    resultNationalitySig = undefined;
  });

  describe('resolve', () => {
    it('should return INationalitySig returned by find', () => {
      // GIVEN
      service.find = jest.fn(nationalityId => of(new HttpResponse({ body: { nationalityId } })));
      mockActivatedRouteSnapshot.params = { nationalityId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultNationalitySig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultNationalitySig).toEqual({ nationalityId: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultNationalitySig = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultNationalitySig).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<INationalitySig>({ body: null })));
      mockActivatedRouteSnapshot.params = { nationalityId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultNationalitySig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultNationalitySig).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
