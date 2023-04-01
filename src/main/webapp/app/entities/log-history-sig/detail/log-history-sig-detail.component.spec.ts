import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LogHistorySigDetailComponent } from './log-history-sig-detail.component';

describe('LogHistorySig Management Detail Component', () => {
  let comp: LogHistorySigDetailComponent;
  let fixture: ComponentFixture<LogHistorySigDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LogHistorySigDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ logHistory: { logHistory: 123 } }) },
        },
      ],
    })
      .overrideTemplate(LogHistorySigDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(LogHistorySigDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load logHistory on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.logHistory).toEqual(expect.objectContaining({ logHistory: 123 }));
    });
  });
});
