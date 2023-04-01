import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { DayPassInfoSigService } from '../service/day-pass-info-sig.service';

import { DayPassInfoSigComponent } from './day-pass-info-sig.component';
import SpyInstance = jest.SpyInstance;

describe('DayPassInfoSig Management Component', () => {
  let comp: DayPassInfoSigComponent;
  let fixture: ComponentFixture<DayPassInfoSigComponent>;
  let service: DayPassInfoSigService;
  let routerNavigateSpy: SpyInstance<Promise<boolean>>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'day-pass-info-sig', component: DayPassInfoSigComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [DayPassInfoSigComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'dayPassInfoId,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'dayPassInfoId,desc',
                'filter[someId.in]': 'dc4279ea-cfb9-11ec-9d64-0242ac120002',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(DayPassInfoSigComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DayPassInfoSigComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(DayPassInfoSigService);
    routerNavigateSpy = jest.spyOn(comp.router, 'navigate');

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ dayPassInfoId: 123 }],
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
    expect(comp.dayPassInfos?.[0]).toEqual(expect.objectContaining({ dayPassInfoId: 123 }));
  });

  describe('trackDayPassInfoId', () => {
    it('Should forward to dayPassInfoService', () => {
      const entity = { dayPassInfoId: 123 };
      jest.spyOn(service, 'getDayPassInfoSigIdentifier');
      const dayPassInfoId = comp.trackDayPassInfoId(0, entity);
      expect(service.getDayPassInfoSigIdentifier).toHaveBeenCalledWith(entity);
      expect(dayPassInfoId).toBe(entity.dayPassInfoId);
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
    expect(service.query).toHaveBeenLastCalledWith(expect.objectContaining({ sort: ['dayPassInfoId,desc'] }));
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
