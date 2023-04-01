import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ICategorySig } from '../category-sig.model';
import { CategorySigService } from '../service/category-sig.service';

import { CategorySigRoutingResolveService } from './category-sig-routing-resolve.service';

describe('CategorySig routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: CategorySigRoutingResolveService;
  let service: CategorySigService;
  let resultCategorySig: ICategorySig | null | undefined;

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
    routingResolveService = TestBed.inject(CategorySigRoutingResolveService);
    service = TestBed.inject(CategorySigService);
    resultCategorySig = undefined;
  });

  describe('resolve', () => {
    it('should return ICategorySig returned by find', () => {
      // GIVEN
      service.find = jest.fn(categoryId => of(new HttpResponse({ body: { categoryId } })));
      mockActivatedRouteSnapshot.params = { categoryId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCategorySig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCategorySig).toEqual({ categoryId: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCategorySig = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultCategorySig).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ICategorySig>({ body: null })));
      mockActivatedRouteSnapshot.params = { categoryId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCategorySig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCategorySig).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
