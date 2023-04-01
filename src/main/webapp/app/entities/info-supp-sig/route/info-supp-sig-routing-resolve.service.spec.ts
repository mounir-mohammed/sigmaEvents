import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IInfoSuppSig } from '../info-supp-sig.model';
import { InfoSuppSigService } from '../service/info-supp-sig.service';

import { InfoSuppSigRoutingResolveService } from './info-supp-sig-routing-resolve.service';

describe('InfoSuppSig routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: InfoSuppSigRoutingResolveService;
  let service: InfoSuppSigService;
  let resultInfoSuppSig: IInfoSuppSig | null | undefined;

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
    routingResolveService = TestBed.inject(InfoSuppSigRoutingResolveService);
    service = TestBed.inject(InfoSuppSigService);
    resultInfoSuppSig = undefined;
  });

  describe('resolve', () => {
    it('should return IInfoSuppSig returned by find', () => {
      // GIVEN
      service.find = jest.fn(infoSuppId => of(new HttpResponse({ body: { infoSuppId } })));
      mockActivatedRouteSnapshot.params = { infoSuppId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultInfoSuppSig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultInfoSuppSig).toEqual({ infoSuppId: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultInfoSuppSig = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultInfoSuppSig).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IInfoSuppSig>({ body: null })));
      mockActivatedRouteSnapshot.params = { infoSuppId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultInfoSuppSig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultInfoSuppSig).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
