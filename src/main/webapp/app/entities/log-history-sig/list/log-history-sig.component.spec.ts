import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { LogHistorySigService } from '../service/log-history-sig.service';

import { LogHistorySigComponent } from './log-history-sig.component';
import SpyInstance = jest.SpyInstance;

describe('LogHistorySig Management Component', () => {
  let comp: LogHistorySigComponent;
  let fixture: ComponentFixture<LogHistorySigComponent>;
  let service: LogHistorySigService;
  let routerNavigateSpy: SpyInstance<Promise<boolean>>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'log-history-sig', component: LogHistorySigComponent }]), HttpClientTestingModule],
      declarations: [LogHistorySigComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'logHistory,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'logHistory,desc',
                'filter[someId.in]': 'dc4279ea-cfb9-11ec-9d64-0242ac120002',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(LogHistorySigComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LogHistorySigComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(LogHistorySigService);
    routerNavigateSpy = jest.spyOn(comp.router, 'navigate');

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ logHistory: 123 }],
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
    expect(comp.logHistories?.[0]).toEqual(expect.objectContaining({ logHistory: 123 }));
  });

  describe('trackLogHistory', () => {
    it('Should forward to logHistoryService', () => {
      const entity = { logHistory: 123 };
      jest.spyOn(service, 'getLogHistorySigIdentifier');
      const logHistory = comp.trackLogHistory(0, entity);
      expect(service.getLogHistorySigIdentifier).toHaveBeenCalledWith(entity);
      expect(logHistory).toBe(entity.logHistory);
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
    expect(service.query).toHaveBeenLastCalledWith(expect.objectContaining({ sort: ['logHistory,desc'] }));
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
