import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { InfoSuppTypeSigDetailComponent } from './info-supp-type-sig-detail.component';

describe('InfoSuppTypeSig Management Detail Component', () => {
  let comp: InfoSuppTypeSigDetailComponent;
  let fixture: ComponentFixture<InfoSuppTypeSigDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InfoSuppTypeSigDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ infoSuppType: { infoSuppTypeId: 123 } }) },
        },
      ],
    })
      .overrideTemplate(InfoSuppTypeSigDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(InfoSuppTypeSigDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load infoSuppType on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.infoSuppType).toEqual(expect.objectContaining({ infoSuppTypeId: 123 }));
    });
  });
});
