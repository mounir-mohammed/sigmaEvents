import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { PrintingCentreSigService } from '../service/printing-centre-sig.service';

import { PrintingCentreSigComponent } from './printing-centre-sig.component';
import SpyInstance = jest.SpyInstance;

describe('PrintingCentreSig Management Component', () => {
  let comp: PrintingCentreSigComponent;
  let fixture: ComponentFixture<PrintingCentreSigComponent>;
  let service: PrintingCentreSigService;
  let routerNavigateSpy: SpyInstance<Promise<boolean>>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'printing-centre-sig', component: PrintingCentreSigComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [PrintingCentreSigComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'printingCentreId,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'printingCentreId,desc',
                'filter[someId.in]': 'dc4279ea-cfb9-11ec-9d64-0242ac120002',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(PrintingCentreSigComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PrintingCentreSigComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PrintingCentreSigService);
    routerNavigateSpy = jest.spyOn(comp.router, 'navigate');

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ printingCentreId: 123 }],
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
    expect(comp.printingCentres?.[0]).toEqual(expect.objectContaining({ printingCentreId: 123 }));
  });

  describe('trackPrintingCentreId', () => {
    it('Should forward to printingCentreService', () => {
      const entity = { printingCentreId: 123 };
      jest.spyOn(service, 'getPrintingCentreSigIdentifier');
      const printingCentreId = comp.trackPrintingCentreId(0, entity);
      expect(service.getPrintingCentreSigIdentifier).toHaveBeenCalledWith(entity);
      expect(printingCentreId).toBe(entity.printingCentreId);
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
    expect(service.query).toHaveBeenLastCalledWith(expect.objectContaining({ sort: ['printingCentreId,desc'] }));
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
