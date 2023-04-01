import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { StatusSigDetailComponent } from './status-sig-detail.component';

describe('StatusSig Management Detail Component', () => {
  let comp: StatusSigDetailComponent;
  let fixture: ComponentFixture<StatusSigDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [StatusSigDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ status: { statusId: 123 } }) },
        },
      ],
    })
      .overrideTemplate(StatusSigDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(StatusSigDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load status on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.status).toEqual(expect.objectContaining({ statusId: 123 }));
    });
  });
});
