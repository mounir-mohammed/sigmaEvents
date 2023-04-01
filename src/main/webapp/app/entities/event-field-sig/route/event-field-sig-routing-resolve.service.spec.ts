import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IEventFieldSig } from '../event-field-sig.model';
import { EventFieldSigService } from '../service/event-field-sig.service';

import { EventFieldSigRoutingResolveService } from './event-field-sig-routing-resolve.service';

describe('EventFieldSig routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: EventFieldSigRoutingResolveService;
  let service: EventFieldSigService;
  let resultEventFieldSig: IEventFieldSig | null | undefined;

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
    routingResolveService = TestBed.inject(EventFieldSigRoutingResolveService);
    service = TestBed.inject(EventFieldSigService);
    resultEventFieldSig = undefined;
  });

  describe('resolve', () => {
    it('should return IEventFieldSig returned by find', () => {
      // GIVEN
      service.find = jest.fn(fieldId => of(new HttpResponse({ body: { fieldId } })));
      mockActivatedRouteSnapshot.params = { fieldId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEventFieldSig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultEventFieldSig).toEqual({ fieldId: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEventFieldSig = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultEventFieldSig).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IEventFieldSig>({ body: null })));
      mockActivatedRouteSnapshot.params = { fieldId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEventFieldSig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultEventFieldSig).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
