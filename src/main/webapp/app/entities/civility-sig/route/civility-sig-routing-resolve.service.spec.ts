import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ICivilitySig } from '../civility-sig.model';
import { CivilitySigService } from '../service/civility-sig.service';

import { CivilitySigRoutingResolveService } from './civility-sig-routing-resolve.service';

describe('CivilitySig routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: CivilitySigRoutingResolveService;
  let service: CivilitySigService;
  let resultCivilitySig: ICivilitySig | null | undefined;

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
    routingResolveService = TestBed.inject(CivilitySigRoutingResolveService);
    service = TestBed.inject(CivilitySigService);
    resultCivilitySig = undefined;
  });

  describe('resolve', () => {
    it('should return ICivilitySig returned by find', () => {
      // GIVEN
      service.find = jest.fn(civilityId => of(new HttpResponse({ body: { civilityId } })));
      mockActivatedRouteSnapshot.params = { civilityId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCivilitySig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCivilitySig).toEqual({ civilityId: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCivilitySig = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultCivilitySig).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ICivilitySig>({ body: null })));
      mockActivatedRouteSnapshot.params = { civilityId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCivilitySig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCivilitySig).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
