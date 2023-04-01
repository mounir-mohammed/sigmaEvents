import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { InfoSuppSigDetailComponent } from './info-supp-sig-detail.component';

describe('InfoSuppSig Management Detail Component', () => {
  let comp: InfoSuppSigDetailComponent;
  let fixture: ComponentFixture<InfoSuppSigDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InfoSuppSigDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ infoSupp: { infoSuppId: 123 } }) },
        },
      ],
    })
      .overrideTemplate(InfoSuppSigDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(InfoSuppSigDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load infoSupp on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.infoSupp).toEqual(expect.objectContaining({ infoSuppId: 123 }));
    });
  });
});
