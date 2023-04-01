import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { CodeTypeSigService } from '../service/code-type-sig.service';

import { CodeTypeSigComponent } from './code-type-sig.component';
import SpyInstance = jest.SpyInstance;

describe('CodeTypeSig Management Component', () => {
  let comp: CodeTypeSigComponent;
  let fixture: ComponentFixture<CodeTypeSigComponent>;
  let service: CodeTypeSigService;
  let routerNavigateSpy: SpyInstance<Promise<boolean>>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'code-type-sig', component: CodeTypeSigComponent }]), HttpClientTestingModule],
      declarations: [CodeTypeSigComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'codeTypeId,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'codeTypeId,desc',
                'filter[someId.in]': 'dc4279ea-cfb9-11ec-9d64-0242ac120002',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(CodeTypeSigComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CodeTypeSigComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(CodeTypeSigService);
    routerNavigateSpy = jest.spyOn(comp.router, 'navigate');

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ codeTypeId: 123 }],
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
    expect(comp.codeTypes?.[0]).toEqual(expect.objectContaining({ codeTypeId: 123 }));
  });

  describe('trackCodeTypeId', () => {
    it('Should forward to codeTypeService', () => {
      const entity = { codeTypeId: 123 };
      jest.spyOn(service, 'getCodeTypeSigIdentifier');
      const codeTypeId = comp.trackCodeTypeId(0, entity);
      expect(service.getCodeTypeSigIdentifier).toHaveBeenCalledWith(entity);
      expect(codeTypeId).toBe(entity.codeTypeId);
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
    expect(service.query).toHaveBeenLastCalledWith(expect.objectContaining({ sort: ['codeTypeId,desc'] }));
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
