import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IEventFormSig } from '../event-form-sig.model';
import { EventFormSigService } from '../service/event-form-sig.service';

import { EventFormSigRoutingResolveService } from './event-form-sig-routing-resolve.service';

describe('EventFormSig routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: EventFormSigRoutingResolveService;
  let service: EventFormSigService;
  let resultEventFormSig: IEventFormSig | null | undefined;

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
    routingResolveService = TestBed.inject(EventFormSigRoutingResolveService);
    service = TestBed.inject(EventFormSigService);
    resultEventFormSig = undefined;
  });

  describe('resolve', () => {
    it('should return IEventFormSig returned by find', () => {
      // GIVEN
      service.find = jest.fn(formId => of(new HttpResponse({ body: { formId } })));
      mockActivatedRouteSnapshot.params = { formId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEventFormSig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultEventFormSig).toEqual({ formId: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEventFormSig = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultEventFormSig).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IEventFormSig>({ body: null })));
      mockActivatedRouteSnapshot.params = { formId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEventFormSig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultEventFormSig).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
