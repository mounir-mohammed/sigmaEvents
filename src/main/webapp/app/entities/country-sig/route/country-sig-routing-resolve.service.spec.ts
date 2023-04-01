import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ICountrySig } from '../country-sig.model';
import { CountrySigService } from '../service/country-sig.service';

import { CountrySigRoutingResolveService } from './country-sig-routing-resolve.service';

describe('CountrySig routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: CountrySigRoutingResolveService;
  let service: CountrySigService;
  let resultCountrySig: ICountrySig | null | undefined;

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
    routingResolveService = TestBed.inject(CountrySigRoutingResolveService);
    service = TestBed.inject(CountrySigService);
    resultCountrySig = undefined;
  });

  describe('resolve', () => {
    it('should return ICountrySig returned by find', () => {
      // GIVEN
      service.find = jest.fn(countryId => of(new HttpResponse({ body: { countryId } })));
      mockActivatedRouteSnapshot.params = { countryId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCountrySig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCountrySig).toEqual({ countryId: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCountrySig = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultCountrySig).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ICountrySig>({ body: null })));
      mockActivatedRouteSnapshot.params = { countryId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCountrySig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCountrySig).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
