import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IPrintingModelSig } from '../printing-model-sig.model';
import { PrintingModelSigService } from '../service/printing-model-sig.service';

import { PrintingModelSigRoutingResolveService } from './printing-model-sig-routing-resolve.service';

describe('PrintingModelSig routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: PrintingModelSigRoutingResolveService;
  let service: PrintingModelSigService;
  let resultPrintingModelSig: IPrintingModelSig | null | undefined;

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
    routingResolveService = TestBed.inject(PrintingModelSigRoutingResolveService);
    service = TestBed.inject(PrintingModelSigService);
    resultPrintingModelSig = undefined;
  });

  describe('resolve', () => {
    it('should return IPrintingModelSig returned by find', () => {
      // GIVEN
      service.find = jest.fn(printingModelId => of(new HttpResponse({ body: { printingModelId } })));
      mockActivatedRouteSnapshot.params = { printingModelId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPrintingModelSig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPrintingModelSig).toEqual({ printingModelId: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPrintingModelSig = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultPrintingModelSig).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IPrintingModelSig>({ body: null })));
      mockActivatedRouteSnapshot.params = { printingModelId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPrintingModelSig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPrintingModelSig).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
