import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { AccreditationSigService } from '../service/accreditation-sig.service';

import { AccreditationSigComponent } from './accreditation-sig.component';
import SpyInstance = jest.SpyInstance;

describe('AccreditationSig Management Component', () => {
  let comp: AccreditationSigComponent;
  let fixture: ComponentFixture<AccreditationSigComponent>;
  let service: AccreditationSigService;
  let routerNavigateSpy: SpyInstance<Promise<boolean>>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'accreditation-sig', component: AccreditationSigComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [AccreditationSigComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'accreditationId,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'accreditationId,desc',
                'filter[someId.in]': 'dc4279ea-cfb9-11ec-9d64-0242ac120002',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(AccreditationSigComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AccreditationSigComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AccreditationSigService);
    routerNavigateSpy = jest.spyOn(comp.router, 'navigate');

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ accreditationId: 123 }],
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
    expect(comp.accreditations?.[0]).toEqual(expect.objectContaining({ accreditationId: 123 }));
  });

  describe('trackAccreditationId', () => {
    it('Should forward to accreditationService', () => {
      const entity = { accreditationId: 123 };
      jest.spyOn(service, 'getAccreditationSigIdentifier');
      const accreditationId = comp.trackAccreditationId(0, entity);
      expect(service.getAccreditationSigIdentifier).toHaveBeenCalledWith(entity);
      expect(accreditationId).toBe(entity.accreditationId);
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
    expect(service.query).toHaveBeenLastCalledWith(expect.objectContaining({ sort: ['accreditationId,desc'] }));
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
