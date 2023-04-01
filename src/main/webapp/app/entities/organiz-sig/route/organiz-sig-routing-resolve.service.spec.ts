import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IOrganizSig } from '../organiz-sig.model';
import { OrganizSigService } from '../service/organiz-sig.service';

import { OrganizSigRoutingResolveService } from './organiz-sig-routing-resolve.service';

describe('OrganizSig routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: OrganizSigRoutingResolveService;
  let service: OrganizSigService;
  let resultOrganizSig: IOrganizSig | null | undefined;

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
    routingResolveService = TestBed.inject(OrganizSigRoutingResolveService);
    service = TestBed.inject(OrganizSigService);
    resultOrganizSig = undefined;
  });

  describe('resolve', () => {
    it('should return IOrganizSig returned by find', () => {
      // GIVEN
      service.find = jest.fn(organizId => of(new HttpResponse({ body: { organizId } })));
      mockActivatedRouteSnapshot.params = { organizId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultOrganizSig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultOrganizSig).toEqual({ organizId: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultOrganizSig = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultOrganizSig).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IOrganizSig>({ body: null })));
      mockActivatedRouteSnapshot.params = { organizId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultOrganizSig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultOrganizSig).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
