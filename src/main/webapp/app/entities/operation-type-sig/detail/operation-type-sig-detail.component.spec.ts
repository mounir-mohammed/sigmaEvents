import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OperationTypeSigDetailComponent } from './operation-type-sig-detail.component';

describe('OperationTypeSig Management Detail Component', () => {
  let comp: OperationTypeSigDetailComponent;
  let fixture: ComponentFixture<OperationTypeSigDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OperationTypeSigDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ operationType: { operationTypeId: 123 } }) },
        },
      ],
    })
      .overrideTemplate(OperationTypeSigDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(OperationTypeSigDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load operationType on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.operationType).toEqual(expect.objectContaining({ operationTypeId: 123 }));
    });
  });
});
