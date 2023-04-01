import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { PrintingTypeSigService } from '../service/printing-type-sig.service';

import { PrintingTypeSigComponent } from './printing-type-sig.component';
import SpyInstance = jest.SpyInstance;

describe('PrintingTypeSig Management Component', () => {
  let comp: PrintingTypeSigComponent;
  let fixture: ComponentFixture<PrintingTypeSigComponent>;
  let service: PrintingTypeSigService;
  let routerNavigateSpy: SpyInstance<Promise<boolean>>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'printing-type-sig', component: PrintingTypeSigComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [PrintingTypeSigComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'printingTypeId,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'printingTypeId,desc',
                'filter[someId.in]': 'dc4279ea-cfb9-11ec-9d64-0242ac120002',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(PrintingTypeSigComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PrintingTypeSigComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PrintingTypeSigService);
    routerNavigateSpy = jest.spyOn(comp.router, 'navigate');

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ printingTypeId: 123 }],
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
    expect(comp.printingTypes?.[0]).toEqual(expect.objectContaining({ printingTypeId: 123 }));
  });

  describe('trackPrintingTypeId', () => {
    it('Should forward to printingTypeService', () => {
      const entity = { printingTypeId: 123 };
      jest.spyOn(service, 'getPrintingTypeSigIdentifier');
      const printingTypeId = comp.trackPrintingTypeId(0, entity);
      expect(service.getPrintingTypeSigIdentifier).toHaveBeenCalledWith(entity);
      expect(printingTypeId).toBe(entity.printingTypeId);
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
    expect(service.query).toHaveBeenLastCalledWith(expect.objectContaining({ sort: ['printingTypeId,desc'] }));
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
