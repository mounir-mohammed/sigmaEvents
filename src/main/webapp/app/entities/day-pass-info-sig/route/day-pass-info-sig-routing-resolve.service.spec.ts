import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IDayPassInfoSig } from '../day-pass-info-sig.model';
import { DayPassInfoSigService } from '../service/day-pass-info-sig.service';

import { DayPassInfoSigRoutingResolveService } from './day-pass-info-sig-routing-resolve.service';

describe('DayPassInfoSig routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: DayPassInfoSigRoutingResolveService;
  let service: DayPassInfoSigService;
  let resultDayPassInfoSig: IDayPassInfoSig | null | undefined;

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
    routingResolveService = TestBed.inject(DayPassInfoSigRoutingResolveService);
    service = TestBed.inject(DayPassInfoSigService);
    resultDayPassInfoSig = undefined;
  });

  describe('resolve', () => {
    it('should return IDayPassInfoSig returned by find', () => {
      // GIVEN
      service.find = jest.fn(dayPassInfoId => of(new HttpResponse({ body: { dayPassInfoId } })));
      mockActivatedRouteSnapshot.params = { dayPassInfoId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDayPassInfoSig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDayPassInfoSig).toEqual({ dayPassInfoId: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDayPassInfoSig = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultDayPassInfoSig).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IDayPassInfoSig>({ body: null })));
      mockActivatedRouteSnapshot.params = { dayPassInfoId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDayPassInfoSig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDayPassInfoSig).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
