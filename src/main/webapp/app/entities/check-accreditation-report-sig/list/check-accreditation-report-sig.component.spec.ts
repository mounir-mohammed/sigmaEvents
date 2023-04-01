import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { CheckAccreditationReportSigService } from '../service/check-accreditation-report-sig.service';

import { CheckAccreditationReportSigComponent } from './check-accreditation-report-sig.component';
import SpyInstance = jest.SpyInstance;

describe('CheckAccreditationReportSig Management Component', () => {
  let comp: CheckAccreditationReportSigComponent;
  let fixture: ComponentFixture<CheckAccreditationReportSigComponent>;
  let service: CheckAccreditationReportSigService;
  let routerNavigateSpy: SpyInstance<Promise<boolean>>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'check-accreditation-report-sig', component: CheckAccreditationReportSigComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [CheckAccreditationReportSigComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'checkAccreditationReportId,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'checkAccreditationReportId,desc',
                'filter[someId.in]': 'dc4279ea-cfb9-11ec-9d64-0242ac120002',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(CheckAccreditationReportSigComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CheckAccreditationReportSigComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(CheckAccreditationReportSigService);
    routerNavigateSpy = jest.spyOn(comp.router, 'navigate');

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ checkAccreditationReportId: 123 }],
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
    expect(comp.checkAccreditationReports?.[0]).toEqual(expect.objectContaining({ checkAccreditationReportId: 123 }));
  });

  describe('trackCheckAccreditationReportId', () => {
    it('Should forward to checkAccreditationReportService', () => {
      const entity = { checkAccreditationReportId: 123 };
      jest.spyOn(service, 'getCheckAccreditationReportSigIdentifier');
      const checkAccreditationReportId = comp.trackCheckAccreditationReportId(0, entity);
      expect(service.getCheckAccreditationReportSigIdentifier).toHaveBeenCalledWith(entity);
      expect(checkAccreditationReportId).toBe(entity.checkAccreditationReportId);
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
    expect(service.query).toHaveBeenLastCalledWith(expect.objectContaining({ sort: ['checkAccreditationReportId,desc'] }));
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
