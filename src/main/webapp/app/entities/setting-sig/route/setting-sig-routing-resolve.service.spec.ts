import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ISettingSig } from '../setting-sig.model';
import { SettingSigService } from '../service/setting-sig.service';

import { SettingSigRoutingResolveService } from './setting-sig-routing-resolve.service';

describe('SettingSig routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: SettingSigRoutingResolveService;
  let service: SettingSigService;
  let resultSettingSig: ISettingSig | null | undefined;

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
    routingResolveService = TestBed.inject(SettingSigRoutingResolveService);
    service = TestBed.inject(SettingSigService);
    resultSettingSig = undefined;
  });

  describe('resolve', () => {
    it('should return ISettingSig returned by find', () => {
      // GIVEN
      service.find = jest.fn(settingId => of(new HttpResponse({ body: { settingId } })));
      mockActivatedRouteSnapshot.params = { settingId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSettingSig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSettingSig).toEqual({ settingId: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSettingSig = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultSettingSig).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ISettingSig>({ body: null })));
      mockActivatedRouteSnapshot.params = { settingId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSettingSig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSettingSig).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
