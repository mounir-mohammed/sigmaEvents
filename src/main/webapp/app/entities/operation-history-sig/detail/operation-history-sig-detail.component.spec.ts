import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OperationHistorySigDetailComponent } from './operation-history-sig-detail.component';

describe('OperationHistorySig Management Detail Component', () => {
  let comp: OperationHistorySigDetailComponent;
  let fixture: ComponentFixture<OperationHistorySigDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OperationHistorySigDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ operationHistory: { operationHistoryId: 123 } }) },
        },
      ],
    })
      .overrideTemplate(OperationHistorySigDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(OperationHistorySigDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load operationHistory on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.operationHistory).toEqual(expect.objectContaining({ operationHistoryId: 123 }));
    });
  });
});
