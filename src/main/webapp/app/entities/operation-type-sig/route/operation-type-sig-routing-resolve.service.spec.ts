import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IOperationTypeSig } from '../operation-type-sig.model';
import { OperationTypeSigService } from '../service/operation-type-sig.service';

import { OperationTypeSigRoutingResolveService } from './operation-type-sig-routing-resolve.service';

describe('OperationTypeSig routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: OperationTypeSigRoutingResolveService;
  let service: OperationTypeSigService;
  let resultOperationTypeSig: IOperationTypeSig | null | undefined;

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
    routingResolveService = TestBed.inject(OperationTypeSigRoutingResolveService);
    service = TestBed.inject(OperationTypeSigService);
    resultOperationTypeSig = undefined;
  });

  describe('resolve', () => {
    it('should return IOperationTypeSig returned by find', () => {
      // GIVEN
      service.find = jest.fn(operationTypeId => of(new HttpResponse({ body: { operationTypeId } })));
      mockActivatedRouteSnapshot.params = { operationTypeId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultOperationTypeSig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultOperationTypeSig).toEqual({ operationTypeId: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultOperationTypeSig = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultOperationTypeSig).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IOperationTypeSig>({ body: null })));
      mockActivatedRouteSnapshot.params = { operationTypeId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultOperationTypeSig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultOperationTypeSig).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
