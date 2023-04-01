import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IInfoSuppTypeSig } from '../info-supp-type-sig.model';
import { InfoSuppTypeSigService } from '../service/info-supp-type-sig.service';

import { InfoSuppTypeSigRoutingResolveService } from './info-supp-type-sig-routing-resolve.service';

describe('InfoSuppTypeSig routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: InfoSuppTypeSigRoutingResolveService;
  let service: InfoSuppTypeSigService;
  let resultInfoSuppTypeSig: IInfoSuppTypeSig | null | undefined;

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
    routingResolveService = TestBed.inject(InfoSuppTypeSigRoutingResolveService);
    service = TestBed.inject(InfoSuppTypeSigService);
    resultInfoSuppTypeSig = undefined;
  });

  describe('resolve', () => {
    it('should return IInfoSuppTypeSig returned by find', () => {
      // GIVEN
      service.find = jest.fn(infoSuppTypeId => of(new HttpResponse({ body: { infoSuppTypeId } })));
      mockActivatedRouteSnapshot.params = { infoSuppTypeId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultInfoSuppTypeSig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultInfoSuppTypeSig).toEqual({ infoSuppTypeId: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultInfoSuppTypeSig = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultInfoSuppTypeSig).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IInfoSuppTypeSig>({ body: null })));
      mockActivatedRouteSnapshot.params = { infoSuppTypeId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultInfoSuppTypeSig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultInfoSuppTypeSig).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
