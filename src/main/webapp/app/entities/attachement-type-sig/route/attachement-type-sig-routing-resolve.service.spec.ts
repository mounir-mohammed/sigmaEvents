import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IAttachementTypeSig } from '../attachement-type-sig.model';
import { AttachementTypeSigService } from '../service/attachement-type-sig.service';

import { AttachementTypeSigRoutingResolveService } from './attachement-type-sig-routing-resolve.service';

describe('AttachementTypeSig routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: AttachementTypeSigRoutingResolveService;
  let service: AttachementTypeSigService;
  let resultAttachementTypeSig: IAttachementTypeSig | null | undefined;

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
    routingResolveService = TestBed.inject(AttachementTypeSigRoutingResolveService);
    service = TestBed.inject(AttachementTypeSigService);
    resultAttachementTypeSig = undefined;
  });

  describe('resolve', () => {
    it('should return IAttachementTypeSig returned by find', () => {
      // GIVEN
      service.find = jest.fn(attachementTypeId => of(new HttpResponse({ body: { attachementTypeId } })));
      mockActivatedRouteSnapshot.params = { attachementTypeId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAttachementTypeSig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAttachementTypeSig).toEqual({ attachementTypeId: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAttachementTypeSig = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultAttachementTypeSig).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IAttachementTypeSig>({ body: null })));
      mockActivatedRouteSnapshot.params = { attachementTypeId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAttachementTypeSig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAttachementTypeSig).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
