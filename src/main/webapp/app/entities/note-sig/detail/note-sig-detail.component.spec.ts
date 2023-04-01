import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NoteSigDetailComponent } from './note-sig-detail.component';

describe('NoteSig Management Detail Component', () => {
  let comp: NoteSigDetailComponent;
  let fixture: ComponentFixture<NoteSigDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NoteSigDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ note: { noteId: 123 } }) },
        },
      ],
    })
      .overrideTemplate(NoteSigDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(NoteSigDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load note on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.note).toEqual(expect.objectContaining({ noteId: 123 }));
    });
  });
});
