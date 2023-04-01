import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IEventSig } from '../event-sig.model';
import { EventSigService } from '../service/event-sig.service';

import { EventSigRoutingResolveService } from './event-sig-routing-resolve.service';

describe('EventSig routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: EventSigRoutingResolveService;
  let service: EventSigService;
  let resultEventSig: IEventSig | null | undefined;

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
    routingResolveService = TestBed.inject(EventSigRoutingResolveService);
    service = TestBed.inject(EventSigService);
    resultEventSig = undefined;
  });

  describe('resolve', () => {
    it('should return IEventSig returned by find', () => {
      // GIVEN
      service.find = jest.fn(eventId => of(new HttpResponse({ body: { eventId } })));
      mockActivatedRouteSnapshot.params = { eventId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEventSig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultEventSig).toEqual({ eventId: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEventSig = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultEventSig).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IEventSig>({ body: null })));
      mockActivatedRouteSnapshot.params = { eventId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEventSig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultEventSig).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
