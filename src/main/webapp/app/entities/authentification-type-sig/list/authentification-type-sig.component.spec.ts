import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { AuthentificationTypeSigService } from '../service/authentification-type-sig.service';

import { AuthentificationTypeSigComponent } from './authentification-type-sig.component';
import SpyInstance = jest.SpyInstance;

describe('AuthentificationTypeSig Management Component', () => {
  let comp: AuthentificationTypeSigComponent;
  let fixture: ComponentFixture<AuthentificationTypeSigComponent>;
  let service: AuthentificationTypeSigService;
  let routerNavigateSpy: SpyInstance<Promise<boolean>>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'authentification-type-sig', component: AuthentificationTypeSigComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [AuthentificationTypeSigComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'authentificationTypeId,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'authentificationTypeId,desc',
                'filter[someId.in]': 'dc4279ea-cfb9-11ec-9d64-0242ac120002',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(AuthentificationTypeSigComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AuthentificationTypeSigComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AuthentificationTypeSigService);
    routerNavigateSpy = jest.spyOn(comp.router, 'navigate');

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ authentificationTypeId: 123 }],
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
    expect(comp.authentificationTypes?.[0]).toEqual(expect.objectContaining({ authentificationTypeId: 123 }));
  });

  describe('trackAuthentificationTypeId', () => {
    it('Should forward to authentificationTypeService', () => {
      const entity = { authentificationTypeId: 123 };
      jest.spyOn(service, 'getAuthentificationTypeSigIdentifier');
      const authentificationTypeId = comp.trackAuthentificationTypeId(0, entity);
      expect(service.getAuthentificationTypeSigIdentifier).toHaveBeenCalledWith(entity);
      expect(authentificationTypeId).toBe(entity.authentificationTypeId);
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
    expect(service.query).toHaveBeenLastCalledWith(expect.objectContaining({ sort: ['authentificationTypeId,desc'] }));
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
