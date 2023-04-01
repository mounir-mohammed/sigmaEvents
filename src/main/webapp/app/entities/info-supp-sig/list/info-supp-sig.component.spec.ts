import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { InfoSuppSigService } from '../service/info-supp-sig.service';

import { InfoSuppSigComponent } from './info-supp-sig.component';
import SpyInstance = jest.SpyInstance;

describe('InfoSuppSig Management Component', () => {
  let comp: InfoSuppSigComponent;
  let fixture: ComponentFixture<InfoSuppSigComponent>;
  let service: InfoSuppSigService;
  let routerNavigateSpy: SpyInstance<Promise<boolean>>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'info-supp-sig', component: InfoSuppSigComponent }]), HttpClientTestingModule],
      declarations: [InfoSuppSigComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'infoSuppId,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'infoSuppId,desc',
                'filter[someId.in]': 'dc4279ea-cfb9-11ec-9d64-0242ac120002',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(InfoSuppSigComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(InfoSuppSigComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(InfoSuppSigService);
    routerNavigateSpy = jest.spyOn(comp.router, 'navigate');

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ infoSuppId: 123 }],
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
    expect(comp.infoSupps?.[0]).toEqual(expect.objectContaining({ infoSuppId: 123 }));
  });

  describe('trackInfoSuppId', () => {
    it('Should forward to infoSuppService', () => {
      const entity = { infoSuppId: 123 };
      jest.spyOn(service, 'getInfoSuppSigIdentifier');
      const infoSuppId = comp.trackInfoSuppId(0, entity);
      expect(service.getInfoSuppSigIdentifier).toHaveBeenCalledWith(entity);
      expect(infoSuppId).toBe(entity.infoSuppId);
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
    expect(service.query).toHaveBeenLastCalledWith(expect.objectContaining({ sort: ['infoSuppId,desc'] }));
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
