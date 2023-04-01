import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CivilitySigDetailComponent } from './civility-sig-detail.component';

describe('CivilitySig Management Detail Component', () => {
  let comp: CivilitySigDetailComponent;
  let fixture: ComponentFixture<CivilitySigDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CivilitySigDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ civility: { civilityId: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CivilitySigDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CivilitySigDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load civility on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.civility).toEqual(expect.objectContaining({ civilityId: 123 }));
    });
  });
});
