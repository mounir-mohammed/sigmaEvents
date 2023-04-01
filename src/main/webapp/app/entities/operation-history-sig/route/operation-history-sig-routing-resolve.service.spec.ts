import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IOperationHistorySig } from '../operation-history-sig.model';
import { OperationHistorySigService } from '../service/operation-history-sig.service';

import { OperationHistorySigRoutingResolveService } from './operation-history-sig-routing-resolve.service';

describe('OperationHistorySig routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: OperationHistorySigRoutingResolveService;
  let service: OperationHistorySigService;
  let resultOperationHistorySig: IOperationHistorySig | null | undefined;

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
    routingResolveService = TestBed.inject(OperationHistorySigRoutingResolveService);
    service = TestBed.inject(OperationHistorySigService);
    resultOperationHistorySig = undefined;
  });

  describe('resolve', () => {
    it('should return IOperationHistorySig returned by find', () => {
      // GIVEN
      service.find = jest.fn(operationHistoryId => of(new HttpResponse({ body: { operationHistoryId } })));
      mockActivatedRouteSnapshot.params = { operationHistoryId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultOperationHistorySig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultOperationHistorySig).toEqual({ operationHistoryId: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultOperationHistorySig = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultOperationHistorySig).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IOperationHistorySig>({ body: null })));
      mockActivatedRouteSnapshot.params = { operationHistoryId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultOperationHistorySig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultOperationHistorySig).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
