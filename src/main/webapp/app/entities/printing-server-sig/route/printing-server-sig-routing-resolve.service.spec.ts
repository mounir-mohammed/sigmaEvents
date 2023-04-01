import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IPrintingServerSig } from '../printing-server-sig.model';
import { PrintingServerSigService } from '../service/printing-server-sig.service';

import { PrintingServerSigRoutingResolveService } from './printing-server-sig-routing-resolve.service';

describe('PrintingServerSig routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: PrintingServerSigRoutingResolveService;
  let service: PrintingServerSigService;
  let resultPrintingServerSig: IPrintingServerSig | null | undefined;

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
    routingResolveService = TestBed.inject(PrintingServerSigRoutingResolveService);
    service = TestBed.inject(PrintingServerSigService);
    resultPrintingServerSig = undefined;
  });

  describe('resolve', () => {
    it('should return IPrintingServerSig returned by find', () => {
      // GIVEN
      service.find = jest.fn(printingServerId => of(new HttpResponse({ body: { printingServerId } })));
      mockActivatedRouteSnapshot.params = { printingServerId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPrintingServerSig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPrintingServerSig).toEqual({ printingServerId: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPrintingServerSig = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultPrintingServerSig).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IPrintingServerSig>({ body: null })));
      mockActivatedRouteSnapshot.params = { printingServerId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPrintingServerSig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPrintingServerSig).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
