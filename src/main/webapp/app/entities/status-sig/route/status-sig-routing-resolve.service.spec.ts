import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IStatusSig } from '../status-sig.model';
import { StatusSigService } from '../service/status-sig.service';

import { StatusSigRoutingResolveService } from './status-sig-routing-resolve.service';

describe('StatusSig routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: StatusSigRoutingResolveService;
  let service: StatusSigService;
  let resultStatusSig: IStatusSig | null | undefined;

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
    routingResolveService = TestBed.inject(StatusSigRoutingResolveService);
    service = TestBed.inject(StatusSigService);
    resultStatusSig = undefined;
  });

  describe('resolve', () => {
    it('should return IStatusSig returned by find', () => {
      // GIVEN
      service.find = jest.fn(statusId => of(new HttpResponse({ body: { statusId } })));
      mockActivatedRouteSnapshot.params = { statusId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultStatusSig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultStatusSig).toEqual({ statusId: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultStatusSig = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultStatusSig).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IStatusSig>({ body: null })));
      mockActivatedRouteSnapshot.params = { statusId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultStatusSig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultStatusSig).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
