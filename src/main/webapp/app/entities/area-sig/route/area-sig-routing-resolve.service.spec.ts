import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IAreaSig } from '../area-sig.model';
import { AreaSigService } from '../service/area-sig.service';

import { AreaSigRoutingResolveService } from './area-sig-routing-resolve.service';

describe('AreaSig routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: AreaSigRoutingResolveService;
  let service: AreaSigService;
  let resultAreaSig: IAreaSig | null | undefined;

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
    routingResolveService = TestBed.inject(AreaSigRoutingResolveService);
    service = TestBed.inject(AreaSigService);
    resultAreaSig = undefined;
  });

  describe('resolve', () => {
    it('should return IAreaSig returned by find', () => {
      // GIVEN
      service.find = jest.fn(areaId => of(new HttpResponse({ body: { areaId } })));
      mockActivatedRouteSnapshot.params = { areaId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAreaSig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAreaSig).toEqual({ areaId: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAreaSig = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultAreaSig).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IAreaSig>({ body: null })));
      mockActivatedRouteSnapshot.params = { areaId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAreaSig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAreaSig).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
