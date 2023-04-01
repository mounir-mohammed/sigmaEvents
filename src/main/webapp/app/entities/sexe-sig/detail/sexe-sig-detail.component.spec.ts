import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SexeSigDetailComponent } from './sexe-sig-detail.component';

describe('SexeSig Management Detail Component', () => {
  let comp: SexeSigDetailComponent;
  let fixture: ComponentFixture<SexeSigDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SexeSigDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ sexe: { sexeId: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SexeSigDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SexeSigDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load sexe on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.sexe).toEqual(expect.objectContaining({ sexeId: 123 }));
    });
  });
});
