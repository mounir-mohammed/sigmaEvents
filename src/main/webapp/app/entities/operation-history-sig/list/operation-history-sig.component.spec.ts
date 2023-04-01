import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { OperationHistorySigService } from '../service/operation-history-sig.service';

import { OperationHistorySigComponent } from './operation-history-sig.component';
import SpyInstance = jest.SpyInstance;

describe('OperationHistorySig Management Component', () => {
  let comp: OperationHistorySigComponent;
  let fixture: ComponentFixture<OperationHistorySigComponent>;
  let service: OperationHistorySigService;
  let routerNavigateSpy: SpyInstance<Promise<boolean>>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'operation-history-sig', component: OperationHistorySigComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [OperationHistorySigComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'operationHistoryId,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'operationHistoryId,desc',
                'filter[someId.in]': 'dc4279ea-cfb9-11ec-9d64-0242ac120002',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(OperationHistorySigComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OperationHistorySigComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(OperationHistorySigService);
    routerNavigateSpy = jest.spyOn(comp.router, 'navigate');

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ operationHistoryId: 123 }],
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
    expect(comp.operationHistories?.[0]).toEqual(expect.objectContaining({ operationHistoryId: 123 }));
  });

  describe('trackOperationHistoryId', () => {
    it('Should forward to operationHistoryService', () => {
      const entity = { operationHistoryId: 123 };
      jest.spyOn(service, 'getOperationHistorySigIdentifier');
      const operationHistoryId = comp.trackOperationHistoryId(0, entity);
      expect(service.getOperationHistorySigIdentifier).toHaveBeenCalledWith(entity);
      expect(operationHistoryId).toBe(entity.operationHistoryId);
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
    expect(service.query).toHaveBeenLastCalledWith(expect.objectContaining({ sort: ['operationHistoryId,desc'] }));
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
