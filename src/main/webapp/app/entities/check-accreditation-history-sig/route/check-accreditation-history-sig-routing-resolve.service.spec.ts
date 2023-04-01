import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ICheckAccreditationHistorySig } from '../check-accreditation-history-sig.model';
import { CheckAccreditationHistorySigService } from '../service/check-accreditation-history-sig.service';

import { CheckAccreditationHistorySigRoutingResolveService } from './check-accreditation-history-sig-routing-resolve.service';

describe('CheckAccreditationHistorySig routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: CheckAccreditationHistorySigRoutingResolveService;
  let service: CheckAccreditationHistorySigService;
  let resultCheckAccreditationHistorySig: ICheckAccreditationHistorySig | null | undefined;

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
    routingResolveService = TestBed.inject(CheckAccreditationHistorySigRoutingResolveService);
    service = TestBed.inject(CheckAccreditationHistorySigService);
    resultCheckAccreditationHistorySig = undefined;
  });

  describe('resolve', () => {
    it('should return ICheckAccreditationHistorySig returned by find', () => {
      // GIVEN
      service.find = jest.fn(checkAccreditationHistoryId => of(new HttpResponse({ body: { checkAccreditationHistoryId } })));
      mockActivatedRouteSnapshot.params = { checkAccreditationHistoryId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCheckAccreditationHistorySig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCheckAccreditationHistorySig).toEqual({ checkAccreditationHistoryId: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCheckAccreditationHistorySig = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultCheckAccreditationHistorySig).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ICheckAccreditationHistorySig>({ body: null })));
      mockActivatedRouteSnapshot.params = { checkAccreditationHistoryId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCheckAccreditationHistorySig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCheckAccreditationHistorySig).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
