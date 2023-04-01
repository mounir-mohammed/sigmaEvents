import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { PrintingModelSigService } from '../service/printing-model-sig.service';

import { PrintingModelSigComponent } from './printing-model-sig.component';
import SpyInstance = jest.SpyInstance;

describe('PrintingModelSig Management Component', () => {
  let comp: PrintingModelSigComponent;
  let fixture: ComponentFixture<PrintingModelSigComponent>;
  let service: PrintingModelSigService;
  let routerNavigateSpy: SpyInstance<Promise<boolean>>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'printing-model-sig', component: PrintingModelSigComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [PrintingModelSigComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'printingModelId,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'printingModelId,desc',
                'filter[someId.in]': 'dc4279ea-cfb9-11ec-9d64-0242ac120002',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(PrintingModelSigComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PrintingModelSigComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PrintingModelSigService);
    routerNavigateSpy = jest.spyOn(comp.router, 'navigate');

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ printingModelId: 123 }],
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
    expect(comp.printingModels?.[0]).toEqual(expect.objectContaining({ printingModelId: 123 }));
  });

  describe('trackPrintingModelId', () => {
    it('Should forward to printingModelService', () => {
      const entity = { printingModelId: 123 };
      jest.spyOn(service, 'getPrintingModelSigIdentifier');
      const printingModelId = comp.trackPrintingModelId(0, entity);
      expect(service.getPrintingModelSigIdentifier).toHaveBeenCalledWith(entity);
      expect(printingModelId).toBe(entity.printingModelId);
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
    expect(service.query).toHaveBeenLastCalledWith(expect.objectContaining({ sort: ['printingModelId,desc'] }));
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
