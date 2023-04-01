import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IAccreditationSig } from '../accreditation-sig.model';
import { AccreditationSigService } from '../service/accreditation-sig.service';

import { AccreditationSigRoutingResolveService } from './accreditation-sig-routing-resolve.service';

describe('AccreditationSig routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: AccreditationSigRoutingResolveService;
  let service: AccreditationSigService;
  let resultAccreditationSig: IAccreditationSig | null | undefined;

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
    routingResolveService = TestBed.inject(AccreditationSigRoutingResolveService);
    service = TestBed.inject(AccreditationSigService);
    resultAccreditationSig = undefined;
  });

  describe('resolve', () => {
    it('should return IAccreditationSig returned by find', () => {
      // GIVEN
      service.find = jest.fn(accreditationId => of(new HttpResponse({ body: { accreditationId } })));
      mockActivatedRouteSnapshot.params = { accreditationId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAccreditationSig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAccreditationSig).toEqual({ accreditationId: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAccreditationSig = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultAccreditationSig).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IAccreditationSig>({ body: null })));
      mockActivatedRouteSnapshot.params = { accreditationId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAccreditationSig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAccreditationSig).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
