import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { OperationTypeSigService } from '../service/operation-type-sig.service';

import { OperationTypeSigComponent } from './operation-type-sig.component';
import SpyInstance = jest.SpyInstance;

describe('OperationTypeSig Management Component', () => {
  let comp: OperationTypeSigComponent;
  let fixture: ComponentFixture<OperationTypeSigComponent>;
  let service: OperationTypeSigService;
  let routerNavigateSpy: SpyInstance<Promise<boolean>>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'operation-type-sig', component: OperationTypeSigComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [OperationTypeSigComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'operationTypeId,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'operationTypeId,desc',
                'filter[someId.in]': 'dc4279ea-cfb9-11ec-9d64-0242ac120002',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(OperationTypeSigComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OperationTypeSigComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(OperationTypeSigService);
    routerNavigateSpy = jest.spyOn(comp.router, 'navigate');

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ operationTypeId: 123 }],
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
    expect(comp.operationTypes?.[0]).toEqual(expect.objectContaining({ operationTypeId: 123 }));
  });

  describe('trackOperationTypeId', () => {
    it('Should forward to operationTypeService', () => {
      const entity = { operationTypeId: 123 };
      jest.spyOn(service, 'getOperationTypeSigIdentifier');
      const operationTypeId = comp.trackOperationTypeId(0, entity);
      expect(service.getOperationTypeSigIdentifier).toHaveBeenCalledWith(entity);
      expect(operationTypeId).toBe(entity.operationTypeId);
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
    expect(service.query).toHaveBeenLastCalledWith(expect.objectContaining({ sort: ['operationTypeId,desc'] }));
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
