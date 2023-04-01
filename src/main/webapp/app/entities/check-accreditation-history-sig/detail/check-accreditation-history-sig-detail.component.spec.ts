import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CheckAccreditationHistorySigDetailComponent } from './check-accreditation-history-sig-detail.component';

describe('CheckAccreditationHistorySig Management Detail Component', () => {
  let comp: CheckAccreditationHistorySigDetailComponent;
  let fixture: ComponentFixture<CheckAccreditationHistorySigDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CheckAccreditationHistorySigDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ checkAccreditationHistory: { checkAccreditationHistoryId: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CheckAccreditationHistorySigDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CheckAccreditationHistorySigDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load checkAccreditationHistory on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.checkAccreditationHistory).toEqual(expect.objectContaining({ checkAccreditationHistoryId: 123 }));
    });
  });
});
