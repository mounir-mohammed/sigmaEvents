import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EventControlSigDetailComponent } from './event-control-sig-detail.component';

describe('EventControlSig Management Detail Component', () => {
  let comp: EventControlSigDetailComponent;
  let fixture: ComponentFixture<EventControlSigDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EventControlSigDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ eventControl: { controlId: 123 } }) },
        },
      ],
    })
      .overrideTemplate(EventControlSigDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(EventControlSigDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load eventControl on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.eventControl).toEqual(expect.objectContaining({ controlId: 123 }));
    });
  });
});
