import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ILanguageSig } from '../language-sig.model';
import { LanguageSigService } from '../service/language-sig.service';

import { LanguageSigRoutingResolveService } from './language-sig-routing-resolve.service';

describe('LanguageSig routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: LanguageSigRoutingResolveService;
  let service: LanguageSigService;
  let resultLanguageSig: ILanguageSig | null | undefined;

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
    routingResolveService = TestBed.inject(LanguageSigRoutingResolveService);
    service = TestBed.inject(LanguageSigService);
    resultLanguageSig = undefined;
  });

  describe('resolve', () => {
    it('should return ILanguageSig returned by find', () => {
      // GIVEN
      service.find = jest.fn(languageId => of(new HttpResponse({ body: { languageId } })));
      mockActivatedRouteSnapshot.params = { languageId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultLanguageSig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultLanguageSig).toEqual({ languageId: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultLanguageSig = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultLanguageSig).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ILanguageSig>({ body: null })));
      mockActivatedRouteSnapshot.params = { languageId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultLanguageSig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultLanguageSig).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
