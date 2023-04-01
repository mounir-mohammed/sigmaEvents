import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EventFormSigDetailComponent } from './event-form-sig-detail.component';

describe('EventFormSig Management Detail Component', () => {
  let comp: EventFormSigDetailComponent;
  let fixture: ComponentFixture<EventFormSigDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EventFormSigDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ eventForm: { formId: 123 } }) },
        },
      ],
    })
      .overrideTemplate(EventFormSigDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(EventFormSigDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load eventForm on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.eventForm).toEqual(expect.objectContaining({ formId: 123 }));
    });
  });
});
