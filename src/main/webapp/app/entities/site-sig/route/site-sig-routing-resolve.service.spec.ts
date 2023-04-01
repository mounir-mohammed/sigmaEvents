import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ISiteSig } from '../site-sig.model';
import { SiteSigService } from '../service/site-sig.service';

import { SiteSigRoutingResolveService } from './site-sig-routing-resolve.service';

describe('SiteSig routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: SiteSigRoutingResolveService;
  let service: SiteSigService;
  let resultSiteSig: ISiteSig | null | undefined;

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
    routingResolveService = TestBed.inject(SiteSigRoutingResolveService);
    service = TestBed.inject(SiteSigService);
    resultSiteSig = undefined;
  });

  describe('resolve', () => {
    it('should return ISiteSig returned by find', () => {
      // GIVEN
      service.find = jest.fn(siteId => of(new HttpResponse({ body: { siteId } })));
      mockActivatedRouteSnapshot.params = { siteId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSiteSig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSiteSig).toEqual({ siteId: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSiteSig = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultSiteSig).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ISiteSig>({ body: null })));
      mockActivatedRouteSnapshot.params = { siteId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSiteSig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSiteSig).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
