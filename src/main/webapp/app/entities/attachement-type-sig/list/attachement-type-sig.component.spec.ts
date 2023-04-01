import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { AttachementTypeSigService } from '../service/attachement-type-sig.service';

import { AttachementTypeSigComponent } from './attachement-type-sig.component';
import SpyInstance = jest.SpyInstance;

describe('AttachementTypeSig Management Component', () => {
  let comp: AttachementTypeSigComponent;
  let fixture: ComponentFixture<AttachementTypeSigComponent>;
  let service: AttachementTypeSigService;
  let routerNavigateSpy: SpyInstance<Promise<boolean>>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'attachement-type-sig', component: AttachementTypeSigComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [AttachementTypeSigComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'attachementTypeId,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'attachementTypeId,desc',
                'filter[someId.in]': 'dc4279ea-cfb9-11ec-9d64-0242ac120002',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(AttachementTypeSigComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AttachementTypeSigComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AttachementTypeSigService);
    routerNavigateSpy = jest.spyOn(comp.router, 'navigate');

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ attachementTypeId: 123 }],
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
    expect(comp.attachementTypes?.[0]).toEqual(expect.objectContaining({ attachementTypeId: 123 }));
  });

  describe('trackAttachementTypeId', () => {
    it('Should forward to attachementTypeService', () => {
      const entity = { attachementTypeId: 123 };
      jest.spyOn(service, 'getAttachementTypeSigIdentifier');
      const attachementTypeId = comp.trackAttachementTypeId(0, entity);
      expect(service.getAttachementTypeSigIdentifier).toHaveBeenCalledWith(entity);
      expect(attachementTypeId).toBe(entity.attachementTypeId);
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
    expect(service.query).toHaveBeenLastCalledWith(expect.objectContaining({ sort: ['attachementTypeId,desc'] }));
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
