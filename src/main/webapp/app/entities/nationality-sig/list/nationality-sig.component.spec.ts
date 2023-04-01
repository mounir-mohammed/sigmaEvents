import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { NationalitySigService } from '../service/nationality-sig.service';

import { NationalitySigComponent } from './nationality-sig.component';
import SpyInstance = jest.SpyInstance;

describe('NationalitySig Management Component', () => {
  let comp: NationalitySigComponent;
  let fixture: ComponentFixture<NationalitySigComponent>;
  let service: NationalitySigService;
  let routerNavigateSpy: SpyInstance<Promise<boolean>>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'nationality-sig', component: NationalitySigComponent }]), HttpClientTestingModule],
      declarations: [NationalitySigComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'nationalityId,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'nationalityId,desc',
                'filter[someId.in]': 'dc4279ea-cfb9-11ec-9d64-0242ac120002',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(NationalitySigComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NationalitySigComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(NationalitySigService);
    routerNavigateSpy = jest.spyOn(comp.router, 'navigate');

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ nationalityId: 123 }],
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
    expect(comp.nationalities?.[0]).toEqual(expect.objectContaining({ nationalityId: 123 }));
  });

  describe('trackNationalityId', () => {
    it('Should forward to nationalityService', () => {
      const entity = { nationalityId: 123 };
      jest.spyOn(service, 'getNationalitySigIdentifier');
      const nationalityId = comp.trackNationalityId(0, entity);
      expect(service.getNationalitySigIdentifier).toHaveBeenCalledWith(entity);
      expect(nationalityId).toBe(entity.nationalityId);
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
    expect(service.query).toHaveBeenLastCalledWith(expect.objectContaining({ sort: ['nationalityId,desc'] }));
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
