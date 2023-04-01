import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { PrintingServerSigService } from '../service/printing-server-sig.service';

import { PrintingServerSigComponent } from './printing-server-sig.component';
import SpyInstance = jest.SpyInstance;

describe('PrintingServerSig Management Component', () => {
  let comp: PrintingServerSigComponent;
  let fixture: ComponentFixture<PrintingServerSigComponent>;
  let service: PrintingServerSigService;
  let routerNavigateSpy: SpyInstance<Promise<boolean>>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'printing-server-sig', component: PrintingServerSigComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [PrintingServerSigComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'printingServerId,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'printingServerId,desc',
                'filter[someId.in]': 'dc4279ea-cfb9-11ec-9d64-0242ac120002',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(PrintingServerSigComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PrintingServerSigComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PrintingServerSigService);
    routerNavigateSpy = jest.spyOn(comp.router, 'navigate');

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ printingServerId: 123 }],
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
    expect(comp.printingServers?.[0]).toEqual(expect.objectContaining({ printingServerId: 123 }));
  });

  describe('trackPrintingServerId', () => {
    it('Should forward to printingServerService', () => {
      const entity = { printingServerId: 123 };
      jest.spyOn(service, 'getPrintingServerSigIdentifier');
      const printingServerId = comp.trackPrintingServerId(0, entity);
      expect(service.getPrintingServerSigIdentifier).toHaveBeenCalledWith(entity);
      expect(printingServerId).toBe(entity.printingServerId);
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
    expect(service.query).toHaveBeenLastCalledWith(expect.objectContaining({ sort: ['printingServerId,desc'] }));
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
