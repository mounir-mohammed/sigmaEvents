import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ICitySig } from '../city-sig.model';
import { CitySigService } from '../service/city-sig.service';

import { CitySigRoutingResolveService } from './city-sig-routing-resolve.service';

describe('CitySig routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: CitySigRoutingResolveService;
  let service: CitySigService;
  let resultCitySig: ICitySig | null | undefined;

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
    routingResolveService = TestBed.inject(CitySigRoutingResolveService);
    service = TestBed.inject(CitySigService);
    resultCitySig = undefined;
  });

  describe('resolve', () => {
    it('should return ICitySig returned by find', () => {
      // GIVEN
      service.find = jest.fn(cityId => of(new HttpResponse({ body: { cityId } })));
      mockActivatedRouteSnapshot.params = { cityId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCitySig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCitySig).toEqual({ cityId: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCitySig = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultCitySig).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ICitySig>({ body: null })));
      mockActivatedRouteSnapshot.params = { cityId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCitySig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCitySig).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
