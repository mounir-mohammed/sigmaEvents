import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IPrintingTypeSig } from '../printing-type-sig.model';
import { PrintingTypeSigService } from '../service/printing-type-sig.service';

import { PrintingTypeSigRoutingResolveService } from './printing-type-sig-routing-resolve.service';

describe('PrintingTypeSig routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: PrintingTypeSigRoutingResolveService;
  let service: PrintingTypeSigService;
  let resultPrintingTypeSig: IPrintingTypeSig | null | undefined;

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
    routingResolveService = TestBed.inject(PrintingTypeSigRoutingResolveService);
    service = TestBed.inject(PrintingTypeSigService);
    resultPrintingTypeSig = undefined;
  });

  describe('resolve', () => {
    it('should return IPrintingTypeSig returned by find', () => {
      // GIVEN
      service.find = jest.fn(printingTypeId => of(new HttpResponse({ body: { printingTypeId } })));
      mockActivatedRouteSnapshot.params = { printingTypeId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPrintingTypeSig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPrintingTypeSig).toEqual({ printingTypeId: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPrintingTypeSig = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultPrintingTypeSig).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IPrintingTypeSig>({ body: null })));
      mockActivatedRouteSnapshot.params = { printingTypeId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPrintingTypeSig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPrintingTypeSig).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
