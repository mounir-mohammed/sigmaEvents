import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ICodeTypeSig } from '../code-type-sig.model';
import { CodeTypeSigService } from '../service/code-type-sig.service';

import { CodeTypeSigRoutingResolveService } from './code-type-sig-routing-resolve.service';

describe('CodeTypeSig routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: CodeTypeSigRoutingResolveService;
  let service: CodeTypeSigService;
  let resultCodeTypeSig: ICodeTypeSig | null | undefined;

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
    routingResolveService = TestBed.inject(CodeTypeSigRoutingResolveService);
    service = TestBed.inject(CodeTypeSigService);
    resultCodeTypeSig = undefined;
  });

  describe('resolve', () => {
    it('should return ICodeTypeSig returned by find', () => {
      // GIVEN
      service.find = jest.fn(codeTypeId => of(new HttpResponse({ body: { codeTypeId } })));
      mockActivatedRouteSnapshot.params = { codeTypeId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCodeTypeSig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCodeTypeSig).toEqual({ codeTypeId: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCodeTypeSig = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultCodeTypeSig).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ICodeTypeSig>({ body: null })));
      mockActivatedRouteSnapshot.params = { codeTypeId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCodeTypeSig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCodeTypeSig).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
