import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ICheckAccreditationReportSig } from '../check-accreditation-report-sig.model';
import { CheckAccreditationReportSigService } from '../service/check-accreditation-report-sig.service';

import { CheckAccreditationReportSigRoutingResolveService } from './check-accreditation-report-sig-routing-resolve.service';

describe('CheckAccreditationReportSig routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: CheckAccreditationReportSigRoutingResolveService;
  let service: CheckAccreditationReportSigService;
  let resultCheckAccreditationReportSig: ICheckAccreditationReportSig | null | undefined;

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
    routingResolveService = TestBed.inject(CheckAccreditationReportSigRoutingResolveService);
    service = TestBed.inject(CheckAccreditationReportSigService);
    resultCheckAccreditationReportSig = undefined;
  });

  describe('resolve', () => {
    it('should return ICheckAccreditationReportSig returned by find', () => {
      // GIVEN
      service.find = jest.fn(checkAccreditationReportId => of(new HttpResponse({ body: { checkAccreditationReportId } })));
      mockActivatedRouteSnapshot.params = { checkAccreditationReportId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCheckAccreditationReportSig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCheckAccreditationReportSig).toEqual({ checkAccreditationReportId: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCheckAccreditationReportSig = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultCheckAccreditationReportSig).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ICheckAccreditationReportSig>({ body: null })));
      mockActivatedRouteSnapshot.params = { checkAccreditationReportId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCheckAccreditationReportSig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCheckAccreditationReportSig).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
