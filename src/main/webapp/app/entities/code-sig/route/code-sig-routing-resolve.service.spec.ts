import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ICodeSig } from '../code-sig.model';
import { CodeSigService } from '../service/code-sig.service';

import { CodeSigRoutingResolveService } from './code-sig-routing-resolve.service';

describe('CodeSig routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: CodeSigRoutingResolveService;
  let service: CodeSigService;
  let resultCodeSig: ICodeSig | null | undefined;

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
    routingResolveService = TestBed.inject(CodeSigRoutingResolveService);
    service = TestBed.inject(CodeSigService);
    resultCodeSig = undefined;
  });

  describe('resolve', () => {
    it('should return ICodeSig returned by find', () => {
      // GIVEN
      service.find = jest.fn(codeId => of(new HttpResponse({ body: { codeId } })));
      mockActivatedRouteSnapshot.params = { codeId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCodeSig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCodeSig).toEqual({ codeId: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCodeSig = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultCodeSig).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ICodeSig>({ body: null })));
      mockActivatedRouteSnapshot.params = { codeId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCodeSig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCodeSig).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
