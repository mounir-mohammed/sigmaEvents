import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EventFieldSigDetailComponent } from './event-field-sig-detail.component';

describe('EventFieldSig Management Detail Component', () => {
  let comp: EventFieldSigDetailComponent;
  let fixture: ComponentFixture<EventFieldSigDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EventFieldSigDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ eventField: { fieldId: 123 } }) },
        },
      ],
    })
      .overrideTemplate(EventFieldSigDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(EventFieldSigDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load eventField on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.eventField).toEqual(expect.objectContaining({ fieldId: 123 }));
    });
  });
});
