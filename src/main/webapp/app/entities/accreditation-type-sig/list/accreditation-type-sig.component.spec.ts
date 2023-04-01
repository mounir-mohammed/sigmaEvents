import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { AccreditationTypeSigService } from '../service/accreditation-type-sig.service';

import { AccreditationTypeSigComponent } from './accreditation-type-sig.component';
import SpyInstance = jest.SpyInstance;

describe('AccreditationTypeSig Management Component', () => {
  let comp: AccreditationTypeSigComponent;
  let fixture: ComponentFixture<AccreditationTypeSigComponent>;
  let service: AccreditationTypeSigService;
  let routerNavigateSpy: SpyInstance<Promise<boolean>>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'accreditation-type-sig', component: AccreditationTypeSigComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [AccreditationTypeSigComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'accreditationTypeId,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'accreditationTypeId,desc',
                'filter[someId.in]': 'dc4279ea-cfb9-11ec-9d64-0242ac120002',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(AccreditationTypeSigComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AccreditationTypeSigComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AccreditationTypeSigService);
    routerNavigateSpy = jest.spyOn(comp.router, 'navigate');

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ accreditationTypeId: 123 }],
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
    expect(comp.accreditationTypes?.[0]).toEqual(expect.objectContaining({ accreditationTypeId: 123 }));
  });

  describe('trackAccreditationTypeId', () => {
    it('Should forward to accreditationTypeService', () => {
      const entity = { accreditationTypeId: 123 };
      jest.spyOn(service, 'getAccreditationTypeSigIdentifier');
      const accreditationTypeId = comp.trackAccreditationTypeId(0, entity);
      expect(service.getAccreditationTypeSigIdentifier).toHaveBeenCalledWith(entity);
      expect(accreditationTypeId).toBe(entity.accreditationTypeId);
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
    expect(service.query).toHaveBeenLastCalledWith(expect.objectContaining({ sort: ['accreditationTypeId,desc'] }));
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
