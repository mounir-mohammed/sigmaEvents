import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ISexeSig } from '../sexe-sig.model';
import { SexeSigService } from '../service/sexe-sig.service';

import { SexeSigRoutingResolveService } from './sexe-sig-routing-resolve.service';

describe('SexeSig routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: SexeSigRoutingResolveService;
  let service: SexeSigService;
  let resultSexeSig: ISexeSig | null | undefined;

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
    routingResolveService = TestBed.inject(SexeSigRoutingResolveService);
    service = TestBed.inject(SexeSigService);
    resultSexeSig = undefined;
  });

  describe('resolve', () => {
    it('should return ISexeSig returned by find', () => {
      // GIVEN
      service.find = jest.fn(sexeId => of(new HttpResponse({ body: { sexeId } })));
      mockActivatedRouteSnapshot.params = { sexeId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSexeSig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSexeSig).toEqual({ sexeId: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSexeSig = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultSexeSig).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ISexeSig>({ body: null })));
      mockActivatedRouteSnapshot.params = { sexeId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSexeSig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSexeSig).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
