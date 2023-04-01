import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AccreditationTypeSigDetailComponent } from './accreditation-type-sig-detail.component';

describe('AccreditationTypeSig Management Detail Component', () => {
  let comp: AccreditationTypeSigDetailComponent;
  let fixture: ComponentFixture<AccreditationTypeSigDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AccreditationTypeSigDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ accreditationType: { accreditationTypeId: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AccreditationTypeSigDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AccreditationTypeSigDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load accreditationType on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.accreditationType).toEqual(expect.objectContaining({ accreditationTypeId: 123 }));
    });
  });
});
