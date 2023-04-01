import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IEventControlSig } from '../event-control-sig.model';
import { EventControlSigService } from '../service/event-control-sig.service';

import { EventControlSigRoutingResolveService } from './event-control-sig-routing-resolve.service';

describe('EventControlSig routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: EventControlSigRoutingResolveService;
  let service: EventControlSigService;
  let resultEventControlSig: IEventControlSig | null | undefined;

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
    routingResolveService = TestBed.inject(EventControlSigRoutingResolveService);
    service = TestBed.inject(EventControlSigService);
    resultEventControlSig = undefined;
  });

  describe('resolve', () => {
    it('should return IEventControlSig returned by find', () => {
      // GIVEN
      service.find = jest.fn(controlId => of(new HttpResponse({ body: { controlId } })));
      mockActivatedRouteSnapshot.params = { controlId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEventControlSig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultEventControlSig).toEqual({ controlId: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEventControlSig = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultEventControlSig).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IEventControlSig>({ body: null })));
      mockActivatedRouteSnapshot.params = { controlId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEventControlSig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultEventControlSig).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
