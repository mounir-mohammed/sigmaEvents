import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CloningSigDetailComponent } from './cloning-sig-detail.component';

describe('CloningSig Management Detail Component', () => {
  let comp: CloningSigDetailComponent;
  let fixture: ComponentFixture<CloningSigDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CloningSigDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ cloning: { cloningId: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CloningSigDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CloningSigDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load cloning on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.cloning).toEqual(expect.objectContaining({ cloningId: 123 }));
    });
  });
});
