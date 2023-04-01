import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { CheckAccreditationHistorySigService } from '../service/check-accreditation-history-sig.service';

import { CheckAccreditationHistorySigComponent } from './check-accreditation-history-sig.component';
import SpyInstance = jest.SpyInstance;

describe('CheckAccreditationHistorySig Management Component', () => {
  let comp: CheckAccreditationHistorySigComponent;
  let fixture: ComponentFixture<CheckAccreditationHistorySigComponent>;
  let service: CheckAccreditationHistorySigService;
  let routerNavigateSpy: SpyInstance<Promise<boolean>>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'check-accreditation-history-sig', component: CheckAccreditationHistorySigComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [CheckAccreditationHistorySigComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'checkAccreditationHistoryId,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'checkAccreditationHistoryId,desc',
                'filter[someId.in]': 'dc4279ea-cfb9-11ec-9d64-0242ac120002',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(CheckAccreditationHistorySigComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CheckAccreditationHistorySigComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(CheckAccreditationHistorySigService);
    routerNavigateSpy = jest.spyOn(comp.router, 'navigate');

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ checkAccreditationHistoryId: 123 }],
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
    expect(comp.checkAccreditationHistories?.[0]).toEqual(expect.objectContaining({ checkAccreditationHistoryId: 123 }));
  });

  describe('trackCheckAccreditationHistoryId', () => {
    it('Should forward to checkAccreditationHistoryService', () => {
      const entity = { checkAccreditationHistoryId: 123 };
      jest.spyOn(service, 'getCheckAccreditationHistorySigIdentifier');
      const checkAccreditationHistoryId = comp.trackCheckAccreditationHistoryId(0, entity);
      expect(service.getCheckAccreditationHistorySigIdentifier).toHaveBeenCalledWith(entity);
      expect(checkAccreditationHistoryId).toBe(entity.checkAccreditationHistoryId);
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
    expect(service.query).toHaveBeenLastCalledWith(expect.objectContaining({ sort: ['checkAccreditationHistoryId,desc'] }));
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
