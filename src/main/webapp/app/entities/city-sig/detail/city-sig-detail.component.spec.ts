import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CitySigDetailComponent } from './city-sig-detail.component';

describe('CitySig Management Detail Component', () => {
  let comp: CitySigDetailComponent;
  let fixture: ComponentFixture<CitySigDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CitySigDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ city: { cityId: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CitySigDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CitySigDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load city on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.city).toEqual(expect.objectContaining({ cityId: 123 }));
    });
  });
});
