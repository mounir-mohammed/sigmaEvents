import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IAuthentificationTypeSig } from '../authentification-type-sig.model';
import { AuthentificationTypeSigService } from '../service/authentification-type-sig.service';

import { AuthentificationTypeSigRoutingResolveService } from './authentification-type-sig-routing-resolve.service';

describe('AuthentificationTypeSig routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: AuthentificationTypeSigRoutingResolveService;
  let service: AuthentificationTypeSigService;
  let resultAuthentificationTypeSig: IAuthentificationTypeSig | null | undefined;

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
    routingResolveService = TestBed.inject(AuthentificationTypeSigRoutingResolveService);
    service = TestBed.inject(AuthentificationTypeSigService);
    resultAuthentificationTypeSig = undefined;
  });

  describe('resolve', () => {
    it('should return IAuthentificationTypeSig returned by find', () => {
      // GIVEN
      service.find = jest.fn(authentificationTypeId => of(new HttpResponse({ body: { authentificationTypeId } })));
      mockActivatedRouteSnapshot.params = { authentificationTypeId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAuthentificationTypeSig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAuthentificationTypeSig).toEqual({ authentificationTypeId: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAuthentificationTypeSig = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultAuthentificationTypeSig).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IAuthentificationTypeSig>({ body: null })));
      mockActivatedRouteSnapshot.params = { authentificationTypeId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAuthentificationTypeSig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAuthentificationTypeSig).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
