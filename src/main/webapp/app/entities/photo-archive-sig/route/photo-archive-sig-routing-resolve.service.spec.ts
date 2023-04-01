import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IPhotoArchiveSig } from '../photo-archive-sig.model';
import { PhotoArchiveSigService } from '../service/photo-archive-sig.service';

import { PhotoArchiveSigRoutingResolveService } from './photo-archive-sig-routing-resolve.service';

describe('PhotoArchiveSig routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: PhotoArchiveSigRoutingResolveService;
  let service: PhotoArchiveSigService;
  let resultPhotoArchiveSig: IPhotoArchiveSig | null | undefined;

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
    routingResolveService = TestBed.inject(PhotoArchiveSigRoutingResolveService);
    service = TestBed.inject(PhotoArchiveSigService);
    resultPhotoArchiveSig = undefined;
  });

  describe('resolve', () => {
    it('should return IPhotoArchiveSig returned by find', () => {
      // GIVEN
      service.find = jest.fn(photoArchiveId => of(new HttpResponse({ body: { photoArchiveId } })));
      mockActivatedRouteSnapshot.params = { photoArchiveId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPhotoArchiveSig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPhotoArchiveSig).toEqual({ photoArchiveId: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPhotoArchiveSig = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultPhotoArchiveSig).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IPhotoArchiveSig>({ body: null })));
      mockActivatedRouteSnapshot.params = { photoArchiveId: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPhotoArchiveSig = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPhotoArchiveSig).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
