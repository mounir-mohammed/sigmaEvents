import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IAttachementSig } from '../attachement-sig.model';
import { AttachementSigService } from '../service/attachement-sig.service';

import { AttachementSigRoutingResolveService } from './attachement-sig-routing-resolve.service';

describe('AttachementSig routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: AttachementSigRoutingResolveService;
  let service: AttachementSigService;
  let resultAttachementSig: IAttachementSig | null | undefined;

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
    routingResolveService = TestBed.inject(AttachementSigRoutingResolveService);
    service = TestBed.inject(AttachementSigService);
    resultAttachementSig = undefined;
  });

  describe('resolve', () => {
    it('should return IAttachementSig returned by find', () => {
      // GIVEN
      service.find = jest.fn(attachementId => of(new HttpResponse({ body: { attachementId } })));
      mockActivatedRouteSnapshot.params = { attachementId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAttachementSig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAttachementSig).toEqual({ attachementId: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAttachementSig = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultAttachementSig).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IAttachementSig>({ body: null })));
      mockActivatedRouteSnapshot.params = { attachementId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAttachementSig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAttachementSig).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
