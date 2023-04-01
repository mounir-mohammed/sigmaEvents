import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { PhotoArchiveSigService } from '../service/photo-archive-sig.service';

import { PhotoArchiveSigComponent } from './photo-archive-sig.component';
import SpyInstance = jest.SpyInstance;

describe('PhotoArchiveSig Management Component', () => {
  let comp: PhotoArchiveSigComponent;
  let fixture: ComponentFixture<PhotoArchiveSigComponent>;
  let service: PhotoArchiveSigService;
  let routerNavigateSpy: SpyInstance<Promise<boolean>>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'photo-archive-sig', component: PhotoArchiveSigComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [PhotoArchiveSigComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'photoArchiveId,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'photoArchiveId,desc',
                'filter[someId.in]': 'dc4279ea-cfb9-11ec-9d64-0242ac120002',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(PhotoArchiveSigComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PhotoArchiveSigComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PhotoArchiveSigService);
    routerNavigateSpy = jest.spyOn(comp.router, 'navigate');

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ photoArchiveId: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.photoArchives?.[0]).toEqual(expect.objectContaining({ photoArchiveId: 123 }));
  });

  describe('trackPhotoArchiveId', () => {
    it('Should forward to photoArchiveService', () => {
      const entity = { photoArchiveId: 123 };
      jest.spyOn(service, 'getPhotoArchiveSigIdentifier');
      const photoArchiveId = comp.trackPhotoArchiveId(0, entity);
      expect(service.getPhotoArchiveSigIdentifier).toHaveBeenCalledWith(entity);
      expect(photoArchiveId).toBe(entity.photoArchiveId);
    });
  });

  it('should load a page', () => {
    // WHEN
    comp.navigateToPage(1);

    // THEN
    expect(routerNavigateSpy).toHaveBeenCalled();
  });

  it('should calculate the sort attribute for an id', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenLastCalledWith(expect.objectContaining({ sort: ['photoArchiveId,desc'] }));
  });

  it('should calculate the sort attribute for a non-id attribute', () => {
    // GIVEN
    comp.predicate = 'name';

    // WHEN
    comp.navigateToWithComponentValues();

    // THEN
    expect(routerNavigateSpy).toHaveBeenLastCalledWith(
      expect.anything(),
      expect.objectContaining({
        queryParams: expect.objectContaining({
          sort: ['name,asc'],
        }),
      })
    );
  });

  it('should calculate the filter attribute', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenLastCalledWith(expect.objectContaining({ 'someId.in': ['dc4279ea-cfb9-11ec-9d64-0242ac120002'] }));
  });
});
