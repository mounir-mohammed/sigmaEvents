import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ILogHistorySig } from '../log-history-sig.model';
import { LogHistorySigService } from '../service/log-history-sig.service';

import { LogHistorySigRoutingResolveService } from './log-history-sig-routing-resolve.service';

describe('LogHistorySig routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: LogHistorySigRoutingResolveService;
  let service: LogHistorySigService;
  let resultLogHistorySig: ILogHistorySig | null | undefined;

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
    routingResolveService = TestBed.inject(LogHistorySigRoutingResolveService);
    service = TestBed.inject(LogHistorySigService);
    resultLogHistorySig = undefined;
  });

  describe('resolve', () => {
    it('should return ILogHistorySig returned by find', () => {
      // GIVEN
      service.find = jest.fn(logHistory => of(new HttpResponse({ body: { logHistory } })));
      mockActivatedRouteSnapshot.params = { logHistory: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultLogHistorySig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultLogHistorySig).toEqual({ logHistory: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultLogHistorySig = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultLogHistorySig).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ILogHistorySig>({ body: null })));
      mockActivatedRouteSnapshot.params = { logHistory: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultLogHistorySig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultLogHistorySig).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
